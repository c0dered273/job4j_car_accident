INSERT INTO accidenttype(name) VALUES ('Две машины');
INSERT INTO accidenttype(name) VALUES ('Машина и человек');
INSERT INTO accidenttype(name) VALUES ('Машина и велосипед');

INSERT INTO rule(name) VALUES ('Статья 1');
INSERT INTO rule(name) VALUES ('Статья 2');
INSERT INTO rule(name) VALUES ('Статья 3');

INSERT INTO accident(name, text, address, type_id)
VALUES ('Test Name_1', 'Test Text_1', 'Test Address 1.0',
        (SELECT id FROM accidenttype act WHERE act.name = 'Две машины'));
INSERT INTO accident(name, text, address, type_id)
VALUES ('Test Name_2', 'Test Text_2', 'Test Address 2.0',
        (SELECT id FROM accidenttype act WHERE act.name = 'Машина и человек'));
INSERT INTO accident(name, text, address, type_id)
VALUES ('Test Name_3', 'Test Text_3', 'Test Address 3.0',
        (SELECT id FROM accidenttype act WHERE act.name = 'Машина и велосипед'));
INSERT INTO accident(name, text, address, type_id)
VALUES ('Test Name_4', 'Test Text_4', 'Test Address 4.0',
        (SELECT id FROM accidenttype act WHERE act.name = 'Две машины'));

INSERT INTO accident_rule(accident_id, rule_id)
VALUES ((SELECT id FROM accident a WHERE a.name = 'Test Name_1'),
        (SELECT id FROM rule r WHERE r.name = 'Статья 1'));
INSERT INTO accident_rule(accident_id, rule_id)
VALUES ((SELECT id FROM accident a WHERE a.name = 'Test Name_1'),
        (SELECT id FROM rule r WHERE r.name = 'Статья 2'));
INSERT INTO accident_rule(accident_id, rule_id)
VALUES ((SELECT id FROM accident a WHERE a.name = 'Test Name_2'),
        (SELECT id FROM rule r WHERE r.name = 'Статья 2'));
INSERT INTO accident_rule(accident_id, rule_id)
VALUES ((SELECT id FROM accident a WHERE a.name = 'Test Name_2'),
        (SELECT id FROM rule r WHERE r.name = 'Статья 3'));
INSERT INTO accident_rule(accident_id, rule_id)
VALUES ((SELECT id FROM accident a WHERE a.name = 'Test Name_3'),
        (SELECT id FROM rule r WHERE r.name = 'Статья 1'));
INSERT INTO accident_rule(accident_id, rule_id)
VALUES ((SELECT id FROM accident a WHERE a.name = 'Test Name_3'),
        (SELECT id FROM rule r WHERE r.name = 'Статья 2'));
INSERT INTO accident_rule(accident_id, rule_id)
VALUES ((SELECT id FROM accident a WHERE a.name = 'Test Name_3'),
        (SELECT id FROM rule r WHERE r.name = 'Статья 3'));
INSERT INTO accident_rule(accident_id, rule_id)
VALUES ((SELECT id FROM accident a WHERE a.name = 'Test Name_4'),
        (SELECT id FROM rule r WHERE r.name = 'Статья 3'));