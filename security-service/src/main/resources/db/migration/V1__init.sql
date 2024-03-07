CREATE TABLE users(
    id SERIAL NOT NULL PRIMARY KEY,
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    age INTEGER,
    email VARCHAR(100),
    password VARCHAR(350),
    role VARCHAR(30)
)