INSERT INTO role (name) values ('ROLE_DIRECTOR');
INSERT INTO role (name) values ('ROLE_MANAGER');
INSERT INTO role (name) values ('ROLE_CHEF');
INSERT INTO role (name) values ('ROLE_COOK');
INSERT INTO role (name) values ('ROLE_HEAD_BARTENDER');
INSERT INTO role (name) values ('ROLE_BARTENDER');
INSERT INTO role (name) values ('ROLE_WAITER');


INSERT INTO expenses (text, value, date, deleted)
values ('nabavka salate', 2000,  1637193115, false);

INSERT INTO expenses (text, value, date, deleted)
values ('nabavka makarona', 2690,  1637193600, false);

INSERT INTO expenses (text, value, date, deleted)
values ('nabavka salate', 2000,  1637193115, true);


INSERT INTO menu (id)
values (1);

INSERT INTO drink_card (id)
values (1);


INSERT INTO price (value, start_date, end_date)
values (1000.0, 1637177653457, 0);

INSERT INTO price (value, start_date, end_date)
values (300.0, 1637177653457, 0);


INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('MenuItem', 'Pizza', 'ingredients', 'image', 'description', 1, 100.0, 1, false);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('DrinkCardItem', 'Coca Cola', 'ingredients', 'image', 'description', 1, 0.0, 2, false);


INSERT INTO receipts (issue_date)
values (1637193115);


INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id, deleted)
values (1, 'message', 0, 2, 1, false);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id, deleted)
values (2, 'message', 0, 1, 1, false);


INSERT INTO restaurant_table (table_status, table_shape, coordinateX, coordinateY, deleted)
values (0, 0, 0.0, 0.0, false);


INSERT INTO users (dtype,  name, last_name, username, email_address, password, deleted, role_id)
values ('Director', 'Nemanja', 'Milutinovic', 'nemanja', 'nemanja@gmail.com', '$2a$10$vnbp6TE0PEATtxRoxzzGHOUfb76RxBI.O9l8WAJAA.L.aZIE6O5ry', false, 1);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Manager', 'Dusan', 'Antic', 'dusan', 'dusan@gmail.com', '$2a$10$vnbp6TE0PEATtxRoxzzGHOUfb76RxBI.O9l8WAJAA.L.aZIE6O5ry', false, 2);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Chef', 'Loreana', 'Oluic', 'loreana', 'loreana@gmail.com', '$2a$10$vnbp6TE0PEATtxRoxzzGHOUfb76RxBI.O9l8WAJAA.L.aZIE6O5ry', false, 3);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Cook', 'Mladen', 'Vasic', 'mladen', 'mladen@gmail.com', '$2a$10$vnbp6TE0PEATtxRoxzzGHOUfb76RxBI.O9l8WAJAA.L.aZIE6O5ry', false, 4);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('HeadBartender', 'Nikola', 'Nikolic', 'nikola', 'nikola@gmail.com', '$2a$10$vnbp6TE0PEATtxRoxzzGHOUfb76RxBI.O9l8WAJAA.L.aZIE6O5ry', false, 5);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Bartender', 'Milica', 'Mitrovic', 'milica', 'milica@gmail.com', '$2a$10$vnbp6TE0PEATtxRoxzzGHOUfb76RxBI.O9l8WAJAA.L.aZIE6O5ry', false, 6);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Waiter', 'Ana', 'Kokic', 'ana', 'ana@gmail.com', '$2a$10$ErYpjyxQT6hhXaJz4c0vDu0vG8XaS8U2N6VbaKl.06S4.dHJUD3u2', false, 7);


INSERT INTO salary (value, start_date, end_date, user_id)
values (50000, 1640466900, 0, 2);


INSERT INTO request (price, item_name, ingredients, description, image, preparation_time, user_id, deleted)
values (370.0, 'Chocolate cake', 'ingredients', 'description', '../../../../assets/images/choco-cake.jpg', 5.0, 3, false);

INSERT INTO request (price, item_name, ingredients, description, image, preparation_time, user_id, deleted)
values (270.0, 'Latte', 'ingredients', 'description', '../../../../assets/images/latte.jpg', 5.0, 5, false);
