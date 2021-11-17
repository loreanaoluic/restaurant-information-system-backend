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


INSERT INTO price (value, start_date, end_date)
values (1000.0, 1637177653457, 0);

INSERT INTO price (value, start_date, end_date)
values (1500.0, 1637177653457, 0);


INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('MenuItem', 'piletina', 'sastojci', 'slika', 'opis', 1, 100.0, 1);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time,price_id)
values ('MenuItem', 'svinjsko', 'sastojci', 'slika', 'opis', 1, 100.0, 2);


INSERT INTO reports (id, income, expense)
values (7, 1234, 150);

INSERT INTO reports (id, income, expense)
values (8, 2200, 1500);

INSERT INTO reports (id, income, expense)
values (9, 2200, 1500);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id)
values (2, 'poruka', 0, 1);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id)
values (1, 'poruka2', 0, 2);