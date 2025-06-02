--liquibase formatted sql

--changeset hakyur:1
create table languages
(
    id   serial primary key,
    code varchar(10) unique not null
);

--changeset hakyur:2
insert into languages (code)
values ('ru'),
       ('en');

--changeset hakyur:3
create table tour_translations
(
    id          serial primary key,
    tour_id     int          not null references tours (id) on delete cascade,
    language_id int          not null references languages (id),
    name        varchar(255) not null check ( length(trim(name)) > 0 ),
    description text,
    city        varchar(100) not null,
    category    varchar(100),
    type        varchar(100),
    unique (tour_id, language_id)
);

--changeset hakyur:4
alter table tours drop column name, drop column description, drop column city, drop column category, drop column type;

--changeset hakyur:5
create table sight_translations
(
    id serial primary key,
    sight_id int not null references sights(id) on delete cascade,
    language_id int not null references languages(id),
    name varchar(255) not null check ( length(trim(name)) > 0 ),
    description text,
    city varchar(100) not null,
    unique (sight_id, language_id)
);

--changeset hakyur:6
alter table sights drop column name, drop column description, drop column city;

--changeset hakyur:7
create table moment_translations
(
    id serial primary key,
    moment_id int not null references moments(id) on delete cascade,
    language_id int not null references languages(id),
    name varchar(255) not null check ( length(trim(name)) > 0 ),
    content text,
    unique (moment_id, language_id)
);

--changeset hakyur:8
alter table moments drop column name, drop column content;