--liquibase formatted sql

--changeset hakyur:1
INSERT INTO roles (name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

--changeset hakyur:2
INSERT INTO users (username, password, email)
VALUES ('admin', '$2y$10$W4EAZvrw9g1FHfdyEtUFlebNmgMy9vzRr86lzyZZXVW.zZqBJLcBO', 'admin@gmail.com'),
       ('user', '$2y$10$PyTQadrKKrHgi1uxkVYVduOlO2xAsKjjiu4bG3A5aLmCT4EoEFlEW', 'user@gmail.com');

--changeset hakyur:3
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 2), (2, 1);
