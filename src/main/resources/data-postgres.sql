INSERT INTO role (name) values ('ROLE_DIRECTOR');
INSERT INTO role (name) values ('ROLE_MANAGER');
INSERT INTO role (name) values ('ROLE_CHEF');
INSERT INTO role (name) values ('ROLE_COOK');
INSERT INTO role (name) values ('ROLE_HEAD_BARTENDER');
INSERT INTO role (name) values ('ROLE_BARTENDER');
INSERT INTO role (name) values ('ROLE_WAITER');

INSERT INTO users (dtype,  name, last_name, username, email_address, password, deleted, role_id)
values ('Director', 'Nemanja', 'Milutinovic', 'nemanja', 'nemanja@gmail.com', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', false, 1);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Manager', 'Dusan', 'Antic', 'dusan', 'dusan@gmail.com', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', false, 2);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Chef', 'Loreana', 'Oluic', 'loreana', 'loreana@gmail.com', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', false, 3);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Cook', 'Mladen', 'Vasic', 'mladen', 'mladen@gmail.com', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', false, 4);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('HeadBartender', 'Nikola', 'Nikolic', 'nikola', 'nikola@gmail.com', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', false, 5);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Bartender', 'Milica', 'Mitrovic', 'milica', 'milica@gmail.com', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', false, 6);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Waiter', 'Ana', 'Kokic', 'ana', 'ana@gmail.com', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', false, 7); --waiter


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

INSERT INTO price (value, start_date, end_date)
values (150.0, 1637177653457, 0);

INSERT INTO price (value, start_date, end_date)
values (100.0, 1637177653457, 0);

INSERT INTO price (value, start_date, end_date)
values (2000.0, 1637177653457, 0);


INSERT INTO request (price, item_name, ingredients, description, image, preparation_time, user_id, deleted)
values (800.0, 'Pasta bolognese', 'ingredients', 'description', '../../../../assets/images/pasta-bolognese.jpg', 60.0, 3, false);

INSERT INTO request (price, item_name, ingredients, description, image, preparation_time, user_id, deleted)
values (370.0, 'Chocolate cake', 'ingredients', 'description', '../../../../assets/images/choco-cake.jpg', 5.0, 3, false);

INSERT INTO request (price, item_name, ingredients, description, image, preparation_time, user_id, deleted)
values (270.0, 'Latte', 'ingredients', 'description', '../../../../assets/images/latte.jpg', 5.0, 5, false);



INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('MenuItem', 'Carbonara paste', 'ingredients', '../../../../assets/images/pasta-carbonara.jpg', 'description', 1, 100.0, 1, false);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('MenuItem', 'Pizza', 'ingredients', '../../../../assets/images/pizza.jpg', 'description', 1, 100.0, 6, false);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('MenuItem', 'Chicken fillet', 'ingredients', '../../../../assets/images/chicken.jpg', 'description', 1, 100.0, 6, false);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('MenuItem', 'Pancakes', 'ingredients', '../../../../assets/images/pancakes.jpg', 'description', 1, 100.0, 2, false);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('MenuItem', 'Burger', 'ingredients', '../../../../assets/images/burger.jpg', 'description', 1, 100.0, 6, false);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('MenuItem', 'Pistachio ice cream', 'ingredients', '../../../../assets/images/ice-cream.jpg', 'description', 1, 100.0, 2, false);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('MenuItem', 'Cheesecake', 'ingredients', '../../../../assets/images/cheesecake.jpg', 'description', 1, 100.0, 2, false);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('MenuItem', 'French fries', 'ingredients', '../../../../assets/images/fries.jpg', 'description', 1, 100.0, 2, false);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('MenuItem', 'Croissant', 'ingredients', '../../../../assets/images/croissant.jpg', 'description', 1, 100.0, 2, false);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('MenuItem', 'Tuna sandwich', 'ingredients', '../../../../assets/images/tuna-sandwich.jpg', 'description', 1, 100.0, 2, false);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id, deleted)
values ('MenuItem', 'English breakfast', 'ingredients', '../../../../assets/images/english-breakfast.jpg', 'description', 1, 100.0, 2, false);

INSERT INTO item (dtype, name, ingredients, image, description, drink_card_id, preparation_time, price_id, deleted)
values ('DrinkCardItem', 'Espresso', 'ingredients', '../../../../assets/images/espreso.jpg', 'description', 1, 0.0, 3, false);

INSERT INTO item (dtype, name, ingredients, image, description, drink_card_id, preparation_time, price_id, deleted)
values ('DrinkCardItem', 'Coca Cola', 'ingredients', '../../../../assets/images/cocacola.png', 'description', 1, 0.0, 4, false);

INSERT INTO item (dtype, name, ingredients, image, description, drink_card_id, preparation_time, price_id, deleted)
values ('DrinkCardItem', 'Iced coffee', 'ingredients', '../../../../assets/images/iced-coffee.jpg', 'description', 1, 0.0, 3, false);

INSERT INTO item (dtype, name, ingredients, image, description, drink_card_id, preparation_time, price_id, deleted)
values ('DrinkCardItem', 'Pepsi', 'ingredients', '../../../../assets/images/pepsi.jpg', 'description', 1, 0.0, 4, false);

INSERT INTO item (dtype, name, ingredients, image, description, drink_card_id, preparation_time, price_id, deleted)
values ('DrinkCardItem', 'Lemonade', 'ingredients', '../../../../assets/images/lemonade.png', 'description', 1, 0.0, 4, false);

INSERT INTO item (dtype, name, ingredients, image, description, drink_card_id, preparation_time, price_id, deleted)
values ('DrinkCardItem', 'Fanta', 'ingredients', '../../../../assets/images/fanta.png', 'description', 1, 0.0, 4, false);

INSERT INTO item (dtype, name, ingredients, image, description, drink_card_id, preparation_time, price_id, deleted)
values ('DrinkCardItem', 'Rosa', 'ingredients', '../../../../assets/images/rosa.png', 'description', 1, 0.0, 5, false);

INSERT INTO item (dtype, name, ingredients, image, description, drink_card_id, preparation_time, price_id, deleted)
values ('DrinkCardItem', 'White wine', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut ac mi turpis.', '../../../../assets/images/white-wine.png', 'description', 1, 0.0, 3, false);

INSERT INTO item (dtype, name, ingredients, image, description, drink_card_id, preparation_time, price_id, deleted)
values ('DrinkCardItem', 'Sparkling water', 'ingredients', '../../../../assets/images/sparkling-water.png', 'description', 1, 0.0, 5, false);

INSERT INTO item (dtype, name, ingredients, image, description, drink_card_id, preparation_time, price_id, deleted)
values ('DrinkCardItem', 'Red wine', 'ingredients', '../../../../assets/images/red-wine.png', 'description', 1, 0.0, 4, false);


UPDATE price SET item_id = 1 WHERE id = 1;
UPDATE price SET item_id = 2 WHERE id = 2;
UPDATE price SET item_id = 3 WHERE id = 3;




------------------------------------------------------------------------------------------------------------------

INSERT INTO receipts (issue_date)
values (1637193115000);

INSERT INTO receipts (issue_date)
values (1637193600000);

INSERT INTO receipts (issue_date)
values (1637063911000);




INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id, deleted)
values (2, 'message', 0, 1, 1, false);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id, deleted)
values (1, 'message2', 0, 2, 1, false);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id, deleted)
values (1, 'message3', 0, 13, 1, false);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id, deleted)
values (1, 'message4', 0, 14, 2, false);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id, deleted)
values (1, 'message5', 0, 3, 2, false);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id, deleted)
values (1, 'message', 1, 14, 2, false);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id, deleted)
values (1, 'message', 1, 3, 2, false);


INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id, deleted)
values (2, 'message', 0, 1, 3, false);


INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id, deleted)
values (2, 'message', 0, 13, 3, false);

INSERT INTO restaurant_table (table_status, table_shape, coordinateX, coordinateY, deleted)
values (1, 1, 0.0, 0.0, false);

INSERT INTO restaurant_table (table_status, table_shape, coordinateX, coordinateY, deleted)
values (0, 0, 0.0, 0.0, false);

INSERT INTO restaurant_table (table_status, table_shape, coordinateX, coordinateY, deleted)
values (1, 0, 0.0, 0.0, false);


INSERT INTO expenses (text, value, date, deleted)
values ('purchase of salad', 2000,  1637193115000, false);


INSERT INTO expenses (text, value, date, deleted)
values ('purchase of salad', 2000,  1637063911000, false);


INSERT INTO expenses ( text, value, date, deleted)
values ('procurement of wine', 2000,  1637193115000, false);

INSERT INTO expenses (text, value, date, deleted)
values ('purchase of pasta', 2690,  1637193600000, false);

INSERT INTO expenses (text, value, date, deleted)
values ('procurement of pepsi and fanta', 8000,  1637193600000, true);

INSERT INTO salary (value, start_date, end_date, user_id)
values (50000, 1640466900, 0, 2);
INSERT INTO salary (value, start_date, end_date, user_id)
values (45000, 1640466900, 0, 3);
INSERT INTO salary (value, start_date, end_date, user_id)
values (40000, 1640466900, 0, 4);
UPDATE users SET salary_id = 1 WHERE id = 2;
UPDATE users SET salary_id = 2 WHERE id = 3;
UPDATE users SET salary_id = 3 WHERE id = 4;
UPDATE users SET salary_id = 1 WHERE id = 1;
UPDATE users SET salary_id = 1 WHERE id = 5;
UPDATE users SET salary_id = 3 WHERE id = 6;
UPDATE users SET salary_id = 3 WHERE id = 7;
