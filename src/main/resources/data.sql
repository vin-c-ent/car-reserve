DROP TABLE IF EXISTS reserve;
DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS model;
DROP TABLE IF EXISTS brand;

CREATE TABLE brand
(
    id         LONG AUTO_INCREMENT,
    brand_name VARCHAR(20),
    CONSTRAINT Brand_pk
        PRIMARY KEY (id)
);
INSERT INTO brand(id, brand_name)
VALUES (1, 'BMW');
INSERT INTO brand(id, brand_name)
VALUES (2, 'Toyota');

CREATE TABLE model
(
    id         LONG AUTO_INCREMENT,
    model_name VARCHAR(20) NOT NULL,
    brand_id   LONG,
    CONSTRAINT Model_pk PRIMARY KEY (id),
    CONSTRAINT Model_BRAND_ID_fk FOREIGN KEY (brand_id) REFERENCES brand
);
INSERT INTO model(id, model_name, brand_id)
VALUES (1, '650', 1);
INSERT INTO model(id, model_name, brand_id)
VALUES (2, '730', 1);
INSERT INTO model(id, model_name, brand_id)
VALUES (3, '740', 1);
INSERT INTO model(id, model_name, brand_id)
VALUES (4, 'Camry', 2);

CREATE TABLE car
(
    id       UUID DEFAULT random_uuid(),
    model_id LONG,
    CONSTRAINT Car_pk PRIMARY KEY (id),
    CONSTRAINT "CAR_MODEL_ID_fk" FOREIGN KEY (model_id) REFERENCES model
);
INSERT INTO car(id, model_id)
VALUES ('f80923ed-162a-4ded-82ef-658dda3fa82b', 1);
INSERT INTO car(id, model_id)
VALUES ('47f0801e-ad3d-4b3a-9ebd-659ce5f2eb50', 1);
INSERT INTO car(id, model_id)
VALUES ('49c4b9b6-11fb-41a5-aeb1-712078682172', 2);
INSERT INTO car(id, model_id)
VALUES ('b4b22f2c-6a19-4e69-80d1-0ee42d11d977', 3);
INSERT INTO car(id, model_id)
VALUES ('a79028a4-4383-4eef-ad12-fafb7cc1e3d3', 4);
INSERT INTO car(id, model_id)
VALUES ('2cf40788-0c36-4415-ad70-f5292cad56be', 4);

CREATE TABLE reserve
(
    id         UUID DEFAULT random_uuid(),
    user_id    UUID,
    start_date DATE,
    end_date   DATE,
    car_id     UUID,
    CONSTRAINT Reserve_pk PRIMARY KEY (id),
    CONSTRAINT "RESERVE_CAR_ID_fk" FOREIGN KEY (car_id) REFERENCES car
);
INSERT INTO reserve(id, user_id, start_date, end_date, car_id)
VALUES ('f80923ed-162a-4ded-82ef-658dda3fa82b', '08fff984-b782-4a31-8d67-c1c12b84f727', '2021-03-01', '2021-03-02',
        'f80923ed-162a-4ded-82ef-658dda3fa82b');
INSERT INTO reserve(id, user_id, start_date, end_date, car_id)
VALUES ('53a04965-5522-47da-8855-7514ca9e2445', '08fff984-b782-4a31-8d67-c1c12b84f727', '2021-03-03', '2021-03-04',
        'f80923ed-162a-4ded-82ef-658dda3fa82b');
INSERT INTO reserve(id, user_id, start_date, end_date, car_id)
VALUES ('94c1942b-5313-4ac7-9fc7-22197c51a911', '08fff984-b782-4a31-8d67-c1c12b84f727', '2021-03-05', '2021-03-06',
        '49c4b9b6-11fb-41a5-aeb1-712078682172');
INSERT INTO reserve(id, user_id, start_date, end_date, car_id)
VALUES ('ba7940dd-35a8-45e3-87fb-3c8bada58e43', '08fff984-b782-4a31-8d67-c1c12b84f727', '2021-03-07', '2021-03-08',
        '49c4b9b6-11fb-41a5-aeb1-712078682172');
INSERT INTO reserve(id, user_id, start_date, end_date, car_id)
VALUES ('57e057b5-6b93-4864-a34a-301813984eee', '08fff984-b782-4a31-8d67-c1c12b84f727', '2021-03-09', '2021-03-10',
        'a79028a4-4383-4eef-ad12-fafb7cc1e3d3');
INSERT INTO reserve(id, user_id, start_date, end_date, car_id)
VALUES ('3626d11a-eb78-4b4e-a02d-9ee168574cd2', '08fff984-b782-4a31-8d67-c1c12b84f727', '2021-03-11', '2021-03-12',
        'a79028a4-4383-4eef-ad12-fafb7cc1e3d3');
INSERT INTO reserve(id, user_id, start_date, end_date, car_id)
VALUES ('7e5f4d37-cf31-454c-bcdf-f6b061487b6a', '08fff984-b782-4a31-8d67-c1c12b84f727', '2021-03-13', '2021-03-14',
        'a79028a4-4383-4eef-ad12-fafb7cc1e3d3');
INSERT INTO reserve(id, user_id, start_date, end_date, car_id)
VALUES ('b97f5029-ac30-41da-b739-c81aecbb542b', '08fff984-b782-4a31-8d67-c1c12b84f727', '2021-03-15', '2021-03-16',
        'a79028a4-4383-4eef-ad12-fafb7cc1e3d3');
INSERT INTO reserve(id, user_id, start_date, end_date, car_id)
VALUES ('c731da5b-b7ba-45aa-8da0-e5c5a4a2049d', '08fff984-b782-4a31-8d67-c1c12b84f727', '2021-03-17', '2021-03-18',
        'a79028a4-4383-4eef-ad12-fafb7cc1e3d3');
