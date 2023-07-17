CREATE TABLE owner (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

INSERT INTO owner (first_name, last_name)
VALUES ('Volodja', 'Muhin'),
       ('Petya', 'Ivanov'),
       ('Dima', 'Novik'),
       ('Gena', 'Malahov'),
       ('Mike', 'Brown'),
       ('Sarah', 'Lee'),
       ('Tom', 'Wilson');

CREATE TABLE car (
    id BIGSERIAL PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    owner_id BIGINT,
    FOREIGN KEY(owner_id) REFERENCES owner(id)
);

INSERT INTO car (brand, model, owner_id)
VALUES ('BMW', '750', 3),
       ('Opel', 'Vectra', 1),
       ('Aurus', 'Senat', 3),
       ('Lada', 'Granta', 4),
       ('Volkswagen', 'Golf', 5),
       ('Ford', 'Mondeo', 6),
       ('Honda', 'Civic', 4),
       ('Skoda', 'Roomster', 7),
       ('Mersedes', '190', 2),
       ('Audi', '100', 2);

CREATE TABLE spare_part (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    serial_number VARCHAR(255)
);

INSERT INTO spare_part (name, serial_number)
VALUES ('Detail1', '555-1234'),
       ('Detail2', '555-5678'),
       ('Detail3', '555-9999'),
       ('Detail4', '555-2468'),
       ('Detail5', '555-1357'),
       ('Detail6', '555-7890'),
       ('Detail7', '555-4321'),
       ('Detail8', '555-9876'),
       ('Detail9', '555-3698');

CREATE TABLE car_spare_part (
    car_id BIGINT NOT NULL,
    spare_part_id BIGINT NOT NULL,
    PRIMARY KEY (car_id, spare_part_id),
    FOREIGN KEY(car_id) REFERENCES car(id),
    FOREIGN KEY(spare_part_id) REFERENCES spare_part(id)
);

INSERT INTO car_spare_part (car_id, spare_part_id)
VALUES (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 4),
       (3, 4),
       (4, 4),
       (4, 5),
       (5, 1),
       (5, 3),
       (6, 2),
       (6, 3),
       (6, 5),
       (7, 6),
       (7, 7),
       (8, 7),
       (8, 8),
       (9, 8),
       (10, 6),
       (10, 9);