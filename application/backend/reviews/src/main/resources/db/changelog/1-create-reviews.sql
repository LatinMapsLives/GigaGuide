--liquibase formatted sql

--changeset hakyur:1
CREATE TABLE tour_reviews
(
    id serial primary key,
    user_id int not null,
    tour_id int not null,
    rating int not null check ( rating between 1 and 5 ),
    comment text,
    created_at timestamp default current_timestamp,
    constraint fk_tour_review_user foreign key (user_id) references users(id),
    constraint fk_tour_review_tour foreign key (tour_id) references tours(id)

);

--changeset hakyur:2
CREATE TABLE sight_reviews
(
    id serial primary key,
    user_id int not null,
    sight_id int not null,
    rating int not null check ( rating between 1 and 5 ),
    comment text,
    created_at timestamp default current_timestamp,
    constraint fk_sight_review_user foreign key (user_id) references users(id),
    constraint fk_sight_review_sight foreign key (sight_id) references sights(id)
);