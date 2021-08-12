package ru.job4j.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

@Repository
@RequiredArgsConstructor
public class RuleJdbcTemplate {

    private static final Logger logger = LogManager.getLogger(RuleJdbcTemplate.class);

    private final JdbcTemplate jdbc;

    public Rule save(Rule rule) {
        final var sql = "insert into rule(name) values (?) RETURNING id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, rule.getName());
            return ps;
        }, keyHolder);
        rule.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return rule;
    }

    public Rule update(Rule rule) {
        final var sql = "update rule set name = ? where id = ?";
        jdbc.update(
                sql,
                rule.getName(),
                rule.getId());
        return rule;
    }

    public Optional<Rule> findById(long id) {
        final var sql = "select * from rule where id = ?";
        return Optional.ofNullable(jdbc.queryForObject(
                sql,
                (resultSet, rowNum) -> mapResultToRule(resultSet),
                id
        ));
    }

    public List<Rule> findAll() {
        final var sql = "select * from rule";
        return jdbc.query(
                sql,
                (resultSet, rowNum) -> mapResultToRule(resultSet)
        );
    }

    public List<Rule> findRulesByAccidentId(long id) {
        final var sql = """
                select r.id, r.name from rule r
                join accident_rule ar on r.id = ar.rule_id
                where ar.accident_id = ?""";
        return jdbc.query(
                sql,
                (resultSet, rowNum) -> mapResultToRule(resultSet),
                id
        );
    }

    public int deleteRulesByAccidentId(long id) {
        final var sql = "delete from accident_rule ar where ar.accident_id = ?";
        return jdbc.update(sql, id);
    }

    public int saveRuleByAccidentId(long accidentId, long ruleId) {
        final var sql = "insert into accident_rule(accident_id, rule_id) values (?, ?)";
        return jdbc.update(sql, accidentId, ruleId);
    }

    private Rule mapResultToRule(ResultSet resultSet) throws SQLException {
        var newRule = new Rule();
        newRule.setId(resultSet.getLong("id"));
        newRule.setName(resultSet.getString("name"));
        return newRule;
    }
}
