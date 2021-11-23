INSERT INTO role (name) values ('ROLE_DIRECTOR');
INSERT INTO role (name) values ('ROLE_MANAGER');
INSERT INTO role (name) values ('ROLE_CHEF');
INSERT INTO role (name) values ('ROLE_COOK');
INSERT INTO role (name) values ('ROLE_HEAD_BARTENDER');
INSERT INTO role (name) values ('ROLE_BARTENDER');
INSERT INTO role (name) values ('ROLE_WAITER');

INSERT INTO users (dtype,  name, last_name, username, email_address, password, deleted, role_id)
values ('Director', 'Nemanja', 'Milutinovic', 'nemanja', 'nemanja@gmail.com', '1234', false, 1);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Manager', 'Dusan', 'Antic', 'dusan', 'dusan@gmail.com', '1234', false, 2);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Chef', 'Loreana', 'Oluic', 'loreana', 'loreana@gmail.com', '1234', false, 3);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Cook', 'Mladen', 'Vasic', 'mladen', 'mladen@gmail.com', '1234', false, 4);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('HeadBartender', 'Nikola', 'Nikolic', 'nikola', 'nikola@gmail.com', '1234', false, 5);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Bartender', 'Milica', 'Mitrovic', 'milica', 'milica@gmail.com', '1234', false, 6);

INSERT INTO users (dtype, name, last_name, username, email_address, password, deleted, role_id)
values ('Waiter', 'Ana', 'Kokic', 'ana', 'ana@gmail.com', '1234', false, 7);


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
values ('MenuItem', 'piletina', 'sastojci', 'slika', 'opis', 1, 100.0, 1);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time,price_id)
values ('MenuItem', 'svinjsko', 'sastojci', 'slika', 'opis', 1, 100.0, 2);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('DrinkCardItem', 'Kola', 'sastojci', 'slika', 'opis', 1, 0.0, 3);

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
