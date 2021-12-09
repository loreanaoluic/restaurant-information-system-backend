INSERT INTO expenses (text, value, date, deleted)
values ('nabavka salate', 2000,  1637193115, false);

INSERT INTO expenses (text, value, date, deleted)
values ('nabavka salate', 2000,  1637193115, true);

INSERT INTO menu (id)
values (1);

INSERT INTO price (value, start_date, end_date)
values (1000.0, 1637177653457, 0);

INSERT INTO item (dtype, name, ingredients, image, description, menu_id, preparation_time, price_id)
values ('MenuItem', 'piletina', 'sastojci', 'slika', 'opis', 1, 100.0, 1);

INSERT INTO receipts (id, issue_date)
values (1, 1637193115);

INSERT INTO receipt_item (quantity, additional_note, item_status, item_id, receipt_id)
values (2, 'poruka', 0, 1, 1);

INSERT INTO users (id,dtype,  name, last_name, username, email_address, password, deleted)
values (1,'Director', 'Nemanja', 'Milutinovic', 'nemanja', 'nemanja@gmail.com', '$2a$10$vnbp6TE0PEATtxRoxzzGHOUfb76RxBI.O9l8WAJAA.L.aZIE6O5ry', false);

INSERT INTO users (id,dtype,  name, last_name, username, email_address, password, deleted)
values (2,'Waiter', 'Milica', 'Mitrovic', 'milica', 'milica@gmail.com', '$2a$10$vnbp6TE0PEATtxRoxzzGHOUfb76RxBI.O9l8WAJAA.L.aZIE6O5ry', false);






