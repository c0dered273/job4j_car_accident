package ru.job4j.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.job4j.model.AccidentType;

@Repository
public class AccidentTypeJdbcTemplate {

    private static final Logger logger = LogManager.getLogger(AccidentTypeJdbcTemplate.class);

    private final JdbcTemplate jdbc;
    private final TransactionTemplate transactionTemplate;

    private final RowMapper<AccidentType> accidentTypeRowMapper = (resultSet, rowNum) -> {
        var newAccidentType = new AccidentType();
        newAccidentType.setId(resultSet.getLong("id"));
        newAccidentType.setName(resultSet.getString("name"));
        return newAccidentType;
    };

    public AccidentTypeJdbcTemplate(
            @Autowired JdbcTemplate jdbc,
            @Autowired PlatformTransactionManager transactionManager) {
        this.jdbc = jdbc;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public AccidentType save(AccidentType accidentType) {
        final var sql = "insert into accidenttype(name) values (?) RETURNING id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        return transactionTemplate.execute(status -> {
            jdbc.update(connection -> {
                PreparedStatement ps =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, accidentType.getName());
                return ps;
            }, keyHolder);
            accidentType.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            return accidentType;
        });
    }

    public AccidentType update(AccidentType accidentType) {
        final var sql = "update accidenttype set name = ? where id = ?";
        return transactionTemplate.execute(status -> {
            jdbc.update(
                    sql,
                    accidentType.getName(),
                    accidentType.getId());
            return accidentType;
        });
    }

    public Optional<AccidentType> findById(long id) {
        final var sql = "select * from accidenttype where id = ?";
        return Optional.ofNullable(jdbc.queryForObject(
                sql,
                accidentTypeRowMapper,
                id
        ));
    }

    public List<AccidentType> findAll() {
        final var sql = "select * from accidenttype";
        return jdbc.query(
                sql,
                accidentTypeRowMapper
        );
    }
}
