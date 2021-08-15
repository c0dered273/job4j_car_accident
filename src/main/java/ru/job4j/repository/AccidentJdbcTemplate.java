package ru.job4j.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;

//@Repository
public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;
    private final AccidentTypeJdbcTemplate accidentTypeRepo;
    private final RuleJdbcTemplate ruleRepo;
    private final TransactionTemplate transactionTemplate;

    private final RowMapper<Accident> accidentRowMapper = (resultSet, rowNum) -> {
        var newAccident = new Accident();
        newAccident.setId(resultSet.getLong("id"));
        newAccident.setName(resultSet.getString("name"));
        newAccident.setText(resultSet.getString("text"));
        newAccident.setAddress(resultSet.getString("address"));
        newAccident.setType(mapResultToAccidentType(resultSet));
        newAccident.setRules(mapResultToRulesSet(resultSet));
        return newAccident;
    };

    public AccidentJdbcTemplate(
            @Autowired JdbcTemplate jdbc,
            @Autowired AccidentTypeJdbcTemplate accidentTypeRepo,
            @Autowired RuleJdbcTemplate ruleRepo,
            @Autowired PlatformTransactionManager transactionManager) {
        this.jdbc = jdbc;
        this.accidentTypeRepo = accidentTypeRepo;
        this.ruleRepo = ruleRepo;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public Accident save(Accident accident) {
        final var sql = """
                insert into accident(name, text, address, type_id)
                values (?, ?, ?, ?) RETURNING id""";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        return transactionTemplate.execute(status -> {
            jdbc.update(connection -> {
                PreparedStatement ps =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, accident.getName());
                ps.setString(2, accident.getText());
                ps.setString(3, accident.getAddress());
                ps.setLong(4, accident.getType().getId());
                return ps;
            }, keyHolder);
            accident.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            saveRulesFromSet(accident);
            return accident;
        });
    }

    public Accident update(Accident accident) {
        final var sql = """
                update accident set
                name = ?, text = ?, address = ?, type_id = ?
                where id = ?""";
        return transactionTemplate.execute(status -> {
            jdbc.update(
                    sql,
                    accident.getName(),
                    accident.getText(),
                    accident.getAddress(),
                    accident.getType().getId(),
                    accident.getId());
            saveRulesFromSet(accident);
            return accident;
        });
    }

    public Optional<Accident> findById(long id) {
        final var sql = "select * from accident where id = ?";
        return Optional.ofNullable(jdbc.queryForObject(
                sql,
                accidentRowMapper,
                id
        ));
    }

    public List<Accident> findAll() {
        final var sql = "select * from accident";
        return jdbc.query(
                sql,
                accidentRowMapper
        );
    }

    private AccidentType mapResultToAccidentType(ResultSet resultSet) throws SQLException {
        var typeId = resultSet.getLong("type_id");
        return accidentTypeRepo.findById(typeId).get();
    }

    private Set<Rule> mapResultToRulesSet(ResultSet resultSet) throws SQLException {
        var accidentId = resultSet.getLong("id");
        return new HashSet<>(ruleRepo.findRulesByAccidentId(accidentId));
    }

    private void saveRulesFromSet(Accident accident) {
        ruleRepo.deleteRulesByAccidentId(accident.getId());
        accident.getRules().forEach(
                rule -> ruleRepo.saveRuleByAccidentId(accident.getId(), rule.getId()));
    }
}