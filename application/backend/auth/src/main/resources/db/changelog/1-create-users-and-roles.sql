--liquibase formatted sql

--changeset hakyur:1
create table roles
(
    id   serial primary key ,
    name varchar(50) not null unique check ( length(trim(name)) > 0 )
);

--changeset hakyur:2
CREATE TABLE users
(
    id    serial primary key,
    username  varchar(255) not null unique,
    email varchar(255) not null unique,
    password varchar not null
);

--changeset hakyur:3
CREATE TABLE user_roles
(
    id serial primary key,
    user_id int not null references users (id),
    role_id int not null references roles (id),
    constraint uk_user_role unique (user_id, role_id)
);