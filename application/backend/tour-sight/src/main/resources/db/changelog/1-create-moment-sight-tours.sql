--liquibase formatted sql

--changeset hakyur:1
CREATE TABLE tours
(
    id serial primary key,
    name varchar(255) not null check ( length(trim(name)) > 0 ),
    description text,
    city varchar(100) not null,
    duration int,
    distance decimal(5,2),
    category varchar(100),
    type varchar(100),
    rating decimal(2,1) default 0.0,
    image_url text
);

--changeset hakyur:2
CREATE TABLE sights
(
    id    serial primary key,
    name varchar(255) not null check ( length(trim(name)) > 0 ),
    description text,
    city varchar(100) not null,
    latitude decimal(9,6),
    longitude decimal(9,6),
    rating decimal(2,1) default 0.0,
    image_url text
);

--changeset hakyur:3
create table tour_sights
(
    id   serial primary key,
    tour_id int not null references tours(id),
    sight_id int not null references sights(id),
    constraint uk_tour_sight unique (tour_id, sight_id)
);

--changeset hakyur:4
create table moments
(
    id   serial primary key ,
    name varchar(255) not null check ( length(trim(name)) > 0 ),
    order_number integer not null,
    content text,
    image_url text,
    latitude decimal(9,6),
    longitude decimal(9,6),
    sight_id integer not null,
    constraint fk_moment_sight foreign key (sight_id) references sights(id) on delete cascade
);