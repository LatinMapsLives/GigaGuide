--liquibase formatted sql

--changeset hakyur:1
CREATE TABLE user_favorite_tours
(
    id serial primary key,
    user_id int not null,
    tour_id int not null,
    constraint fk_user_fav_tour_user foreign key (user_id) references users(id),
    constraint fk_user_fav_tour_tour foreign key (tour_id) references tours(id),
    constraint uq_user_tour unique (user_id, tour_id)
);

--changeset hakyur:2
CREATE TABLE user_favorite_sights
(
    id    serial primary key,
    user_id int not null,
    sight_id int not null,
    constraint fk_user_fav_sight_user foreign key (user_id) references users(id),
    constraint fk_user_fav_sight_sight foreign key (sight_id) references sights(id),
    constraint uq_user_sight unique (user_id, sight_id)
);