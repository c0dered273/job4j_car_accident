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
import ru.job4j.model.AccidentType;

@Repository
@RequiredArgsConstructor
public class AccidentTypeJdbcTemplate {

    private static final Logger logger = LogManager.getLogger(AccidentTypeJdbcTemplate.class);

    private final JdbcTemplate jdbc;

    public AccidentType save(AccidentType accidentType) {
        final var sql = "insert into accidenttype(name) values (?) RETURNING id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, accidentType.getName());
            return ps;
        }, keyHolder);
        accidentType.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return accidentType;
    }

    public AccidentType update(AccidentType accidentType) {
        final var sql = "update accidenttype set name = ? where id = ?";
        jdbc.update(
                sql,
                accidentType.getName(),
                accidentType.getId());
        return accidentType;
    }

    public Optional<AccidentType> findById(long id) {
        final var sql = "select * from accidenttype where id = ?";
        return Optional.ofNullable(jdbc.queryForObject(
                sql,
                (resultSet, rowNum) -> mapResultToAccidentType(resultSet),
                id
        ));
    }

    public List<AccidentType> findAll() {
        final var sql = "select * from accidenttype";
        return jdbc.query(
                sql,
                (resultSet, rowNum) -> mapResultToAccidentType(resultSet)
        );
    }

    private AccidentType mapResultToAccidentType(ResultSet resultSet) throws SQLException {
        var newAccidentType = new AccidentType();
        newAccidentType.setId(resultSet.getLong("id"));
        newAccidentType.setName(resultSet.getString("name"));
        return newAccidentType;
    }
}
