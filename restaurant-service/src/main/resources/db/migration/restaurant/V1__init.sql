CREATE TABLE restaurants (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255) NOT NULL
);

CREATE TABLE menus (
    id SERIAL PRIMARY KEY,
    restaurant_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

CREATE TABLE menu_items (
    id SERIAL PRIMARY KEY,
    menu_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menus(id)
);

CREATE TABLE inventory (
    id SERIAL PRIMARY KEY,
    restaurant_id INT NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);
