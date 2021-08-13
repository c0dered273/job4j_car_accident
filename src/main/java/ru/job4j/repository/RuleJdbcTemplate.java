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
import ru.job4j.model.Rule;

//@Repository
public class RuleJdbcTemplate {

    private static final Logger logger = LogManager.getLogger(RuleJdbcTemplate.class);

    private final JdbcTemplate jdbc;
    private final TransactionTemplate transactionTemplate;

    private final RowMapper<Rule> ruleRowMapper = (resultSet, rowNum) -> {
        var newRule = new Rule();
        newRule.setId(resultSet.getLong("id"));
        newRule.setName(resultSet.getString("name"));
        return newRule;
    };

    public RuleJdbcTemplate(
            @Autowired JdbcTemplate jdbc,
            @Autowired PlatformTransactionManager transactionManager) {
        this.jdbc = jdbc;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public Rule save(Rule rule) {
        final var sql = "insert into rule(name) values (?) RETURNING id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        return transactionTemplate.execute(status -> {
            jdbc.update(connection -> {
                PreparedStatement ps =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, rule.getName());
                return ps;
            }, keyHolder);
            rule.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            return rule;
        });
    }

    public Rule update(Rule rule) {
        final var sql = "update rule set name = ? where id = ?";
        return transactionTemplate.execute(status -> {
            jdbc.update(
                    sql,
                    rule.getName(),
                    rule.getId());
            return rule;
        });
    }

    public Optional<Rule> findById(long id) {
        final var sql = "select * from rule where id = ?";
        return Optional.ofNullable(jdbc.queryForObject(
                sql,
                ruleRowMapper,
                id
        ));
    }

    public List<Rule> findAll() {
        final var sql = "select * from rule";
        return jdbc.query(
                sql,
                ruleRowMapper
        );
    }

    public List<Rule> findRulesByAccidentId(long id) {
        final var sql = """
                select r.id, r.name from rule r
                join accident_rule ar on r.id = ar.rule_id
                where ar.accident_id = ?""";
        return jdbc.query(
                sql,
                ruleRowMapper,
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
}
