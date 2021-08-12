DROP TABLE IF EXISTS accident;
DROP TABLE IF EXISTS accidentType;
DROP TABLE IF EXISTS rule;
DROP TABLE IF EXISTS accident_rule;

CREATE TABLE accident
(
    id      INT GENERATED ALWAYS AS IDENTITY,
    name    VARCHAR(255) NOT NULL,
    text    TEXT,
    address VARCHAR(1024),
    type_id INT          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE accidentType
(
    id   INT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE rule
(
    id   INT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE accident_rule
(
    id   INT GENERATED ALWAYS AS IDENTITY,
    accident_id INT NOT NULL,
    rule_id INT NOT NULL,
    PRIMARY KEY (id)
);