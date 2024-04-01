-- following tables created after the creation of the database using PostgreSQL 16

CREATE TABLE brands (
    brand_id int NOT NULL,
    name text NOT NULL,
    PRIMARY KEY (brand_id)
);

CREATE TABLE models (
    model_id int NOT NULL,
    brand_id int NOT NULL,
    name text NOT NULL,
    release_year int NOT NULL,
    body_type text NOT NULL,
    fuel_type text NOT NULL, 
    gear_type text NOT NULL,
    PRIMARY KEY (model_id),
    FOREIGN KEY (brand_id) REFERENCES brands(brand_id),
    CONSTRAINT CHK_model_date CHECK (release_year >= 2015 AND release_year <= 2024)
);

CREATE TABLE cars (
    car_id int NOT NULL,
    brand_id int NOT NULL,
    model_id int NOT NULL,
    color text NOT NULL,
    mileage_km int NOT NULL,
    Plate text NOT NULL UNIQUE, 
    PRIMARY KEY (car_id),
    FOREIGN KEY (brand_id) REFERENCES brands(brand_id),
    FOREIGN KEY (model_id) REFERENCES models(model_id)
);

CREATE TABLE customers (
    customer_id int NOT NULL,
    full_name text NOT NULL,
    phone int NOT NULL,
    email text NOT NULL,
    username text NOT NULL,
    user_password text NOT NULL,
    PRIMARY KEY (customer_id)
);

CREATE TABLE bookings (
    booking_id int NOT NULL,
    car_id int NOT NULL,
    customer_id int NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    price int NOT NULL,
    status text NOT NULL,
    note text,
    PRIMARY KEY (booking_id),
    FOREIGN KEY (car_id) REFERENCES cars(car_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);