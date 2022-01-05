INSERT INTO role (name) values ('ROLE_DIRECTOR');
INSERT INTO role (name) values ('ROLE_MANAGER');
INSERT INTO role (name) values ('ROLE_CHEF');
INSERT INTO role (name) values ('ROLE_COOK');
INSERT INTO role (name) values ('ROLE_HEAD_BARTENDER');
INSERT INTO role (name) values ('ROLE_BARTENDER');
INSERT INTO role (name) values ('ROLE_WAITER');

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
values ('Waiter', 'Ana', 'Kokic', 'ana', 'ana@gmail.com', '$2a$10$LsmYgfjbevONSwZ6QErYaOGKOh6cL4wxbd16cl3PWphlM3zBW7beO', false, 7); --waiter


INSERT INTO menu (id)
values (1);

INSERT INTO drink_card (id)
values (1);



INSERT INTO price (value, start_date, end_date)
values (1000.0, 1637177653457, 0);

INSERT INTO price (value, start_date, end_date)
values (1500.0, 1637177653457, 0);

INSERT INTO price (value, start_date, end_date)
values (120.0, 1637177653457, 0);


INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('MenuItem', 'Pasta karbonara', 'sastojci', '../../../../assets/images/pasta-carbonara.jpg', 'opis', 1, 100.0, 1);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time,price_id)
values ('MenuItem', 'Pica', 'sastojci', '../../../../assets/images/pizza.jpg', 'opis', 1, 100.0, 2);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time,price_id)
values ('MenuItem', 'Pileci file', 'sastojci', '../../../../assets/images/chicken.jpg', 'opis', 1, 100.0, 2);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time,price_id)
values ('MenuItem', 'Palacinke', 'sastojci', '../../../../assets/images/pancakes.jpg', 'opis', 1, 100.0, 2);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time,price_id)
values ('MenuItem', 'Burger', 'sastojci', '../../../../assets/images/burger.jpg', 'opis', 1, 100.0, 2);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time,price_id)
values ('MenuItem', 'Sladoled od pistaca', 'sastojci', '../../../../assets/images/ice-cream.jpg', 'opis', 1, 100.0, 2);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time,price_id)
values ('MenuItem', 'Cizkejk', 'sastojci', '../../../../assets/images/cheesecake.jpg', 'opis', 1, 100.0, 2);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time,price_id)
values ('MenuItem', 'Pomfrit', 'sastojci', '../../../../assets/images/fries.jpg', 'opis', 1, 100.0, 2);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time,price_id)
values ('MenuItem', 'Kroasan', 'sastojci', '../../../../assets/images/croissant.jpg', 'opis', 1, 100.0, 2);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time,price_id)
values ('MenuItem', 'Tuna sendvic', 'sastojci', '../../../../assets/images/tuna-sandwich.jpg', 'opis', 1, 100.0, 2);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time,price_id)
values ('MenuItem', 'Engleski dorucak', 'sastojci', '../../../../assets/images/english-breakfast.jpg', 'opis', 1, 100.0, 2);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('DrinkCardItem', 'Espreso', 'sastojci', '../../../../assets/images/espreso.jpg', 'opis', 1, 0.0, 3);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('DrinkCardItem', 'Koka kola', 'sastojci', '../../../../assets/images/cocacola.png', 'opis', 1, 0.0, 3);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('DrinkCardItem', 'Iced kafa', 'sastojci', '../../../../assets/images/iced-coffee.jpg', 'opis', 1, 0.0, 3);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('DrinkCardItem', 'Pepsi', 'sastojci', '../../../../assets/images/pepsi.jpg', 'opis', 1, 0.0, 3);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('DrinkCardItem', 'Limunada', 'sastojci', '../../../../assets/images/lemonade.png', 'opis', 1, 0.0, 3);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('DrinkCardItem', 'Fanta', 'sastojci', '../../../../assets/images/fanta.png', 'opis', 1, 0.0, 3);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('DrinkCardItem', 'Rosa', 'sastojci', '../../../../assets/images/rosa.png', 'opis', 1, 0.0, 3);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('DrinkCardItem', 'Belo vino', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut ac mi turpis.', '../../../../assets/images/white-wine.png', 'opis', 1, 0.0, 3);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('DrinkCardItem', 'Kisela voda', 'sastojci', '../../../../assets/images/sparkling-water.png', 'opis', 1, 0.0, 3);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('DrinkCardItem', 'Crveno vino', 'sastojci', '../../../../assets/images/red-wine.png', 'opis', 1, 0.0, 3);


UPDATE price SET item_id = 1 WHERE id = 1;
UPDATE price SET item_id = 2 WHERE id = 2;
UPDATE price SET item_id = 3 WHERE id = 3;




------------------------------------------------------------------------------------------------------------------

INSERT INTO receipts (id, issue_date)
values (1, 1637193115);

INSERT INTO receipts (id, issue_date)
values (2, 1637193600);





INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id)
values (2, 'poruka', 0, 1, 1);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id)
values (1, 'poruka2', 0, 2, 1);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id)
values (1, 'poruka3', 0, 3, 1);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id)
values (1, 'poruka4', 0, 2, 2);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id)
values (1, 'poruka5', 0, 3, 2);

INSERT INTO restaurant_table (id, table_status)
values (1, 0);

INSERT INTO expenses (text, value, date, deleted)
values ('nabavka salate', 2000,  1637193115, false);

INSERT INTO expenses ( text, value, date, deleted)
values ('nabavka vina', 2000,  1637193115, false);

INSERT INTO expenses (text, value, date, deleted)
values ('nabavka makarona', 2690,  1637193600, false);

INSERT INTO expenses (text, value, date, deleted)
values ('nabavka pepsija i fante', 8000,  1637193600, true);

INSERT INTO salary (value, start_date, end_date, user_id)
values (50000, 1640466900, -1, 2);
INSERT INTO salary (value, start_date, end_date, user_id)
values (45000, 1640466900, -1, 3);
INSERT INTO salary (value, start_date, end_date, user_id)
values (40000, 1640466900, -1, 4);
UPDATE users SET salary_id = 1 WHERE id = 2;
UPDATE users SET salary_id = 2 WHERE id = 3;
UPDATE users SET salary_id = 3 WHERE id = 4;
