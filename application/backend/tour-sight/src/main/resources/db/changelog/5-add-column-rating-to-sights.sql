--liquibase formatted sql

--changeset hakyur:add-rating-to-sights
alter table sights
    add column rating decimal(2,1) default 0.0;