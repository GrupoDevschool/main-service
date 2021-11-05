INSERT INTO project (id , created_date, name, status, updated_date)
    VALUES (1, '2021-11-04', 'Games of Thrones', 1, '2021-11-04');


INSERT INTO version (id, created_date, deploy_date, description, gmud, order, status, updated_date, version_clone_id, version_number, project_id)
    VALUE (1, '2021-11-04', '2021-11-04', 'Tretas five?', 'Tretas', 1, TRUE, '2021-11-04', NULL, '1', 1);

INSERT INTO screen (id, active, created_date, image, name, order, updated_date, urlog, clone_version_id, screen_father_id, version_id)
    VALUE (1, TRUE, '2021-11-04', 'qualquer coisa', 'teste', NULL, '2021-11-04', 'five', NULL, NULL, 1);

INSERT INTO event_type(id, created_date, name, order, status, updated_date)
    VALUE (1, '2021-11-04', 'Games of Thrones', NULL, TRUE, '2021-11-04');

INSERT INTO event(id, active, created_date, order, parameter, updated_date, event_type_id, screen_id)
    VALUE (1, TRUE, '2021-11-04', NULL, 'parametro', '2021-11-04', 1, 2);

INSERT INTO request(id, created_date, descrption, layer, order, status, updated_date, uri_homolog, uri_prod, event_id, request_father_id)
    VALUE (1, '2021-11-04', 'Série dos Dragões', 'E dos Lobos', NULL, TRUE, '2021-11-04', 'Stank', 'Lenister', 1, NULL);

INSERT INTO request_property(id, created_date, key, order, updated_date, value, request_id)
    VALUE (1, '2021-11-04', 'GOT', NULL, '2021-11-04', 1,1);

INSERT INTO USER (ID, EMAIL, NAME, PASS)
    VALUE (1, 'exemplo@teste.com.br', 'exemplo1', '$2a$12$wiCIqToOy3DIkHo4DL7qNuMeQma8bX.I0DoJNK7rUzj7G0u5IXpCG');