--liquibase formatted sql

--changeset hakyur:1
ALTER TABLE tours
    RENAME COLUMN image_path TO image_url;

ALTER TABLE tours
ALTER COLUMN image_url TYPE TEXT;

--changeset hakyur:2
ALTER TABLE sights
    RENAME COLUMN image_path TO image_url;

ALTER TABLE sights
ALTER COLUMN image_url TYPE TEXT;

--changeset hakyur:3
ALTER TABLE moments
    RENAME COLUMN image_path TO image_url;

ALTER TABLE moments
ALTER COLUMN image_url TYPE TEXT;