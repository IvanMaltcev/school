-- liquibase formatted sql

-- changeset Imaltcev:1
CREATE INDEX student_name_index ON student (name);

-- changeset IMaltcev:2
CREATE INDEX faculty_cn_index ON faculty (color, name);