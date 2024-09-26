CREATE TABLE person (
    id Serial PRIMARY KEY,
    name text,
    age Integer,
    isHaveDriverLicense boolean,
    car_id Integer REFERENCES car (id)
);
CREATE TABLE car (
    id Serial PRIMARY KEY,
    brand text,
    model text,
    price Integer
);