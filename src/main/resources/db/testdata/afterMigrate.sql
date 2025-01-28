set foreign_key_checks = 0;

delete from users;
delete from clients;
delete from orders;
delete from products;
delete from order_items;
delete from atendimentos;
delete from atendimentos_products;

set foreign_key_checks = 1;

alter table atendimentos auto_increment = 1;
alter table order_items auto_increment = 1;
alter table products auto_increment = 1;
alter table orders auto_increment = 1;
alter table clients auto_increment = 1;

insert into users (email, role, password) values
("admin@refeitorio.com", "ADMIN", "$2a$12$0u7.zYTIbdoyqHHj.poKtuMsXIhaBBNG5Y1gB9HRyBDw8bKM71qk6");

insert into clients (credential, name, role, salary, balance) values
("55555", "João Maria do Santos", "João", 1000.00, null),
("55554", "Pedro Maria do Santos", "Pedro", 1002.00, 1.00);


insert into atendimentos (name, time_start, time_end, type, created_at) values
("Café da Manhã", "06:00:00", "12:00:00", "UNIT", utc_timestamp),
("Almoço", "12:00:00", "16:00:00", "WEIGHT", utc_timestamp),
("Lanche da Tarde", "16:00:00", "19:00:00", "UNIT", utc_timestamp),
("Jantar", "19:00:00", "23:59:00", "WEIGHT", utc_timestamp);

insert into products (code, name, price, price_type) values
(null, "Almoço", 15, "PRICE_PER_KG"),
(null, "Jantar", 0, "PRICE_PER_KG"),
("7891234567888", "Suco Natural", 3.49, "PRICE_PER_UNIT"),
("7891234567889", "Café Preto", 2.99, "PRICE_PER_UNIT"),
("7891234567890", "Coxinha", 5.99, "PRICE_PER_UNIT"),
("7891234567891", "Pão de Queijo", 4.50, "PRICE_PER_UNIT"),
("7891234567892", "Empada de Frango", 6.50, "PRICE_PER_UNIT"),
("7891234567893", "Quibe", 3.80, "PRICE_PER_UNIT"),
("7891234567894", "Pastel de Carne", 7.00, "PRICE_PER_UNIT"),
("7891234567895", "Esfirra de Queijo", 5.50, "PRICE_PER_UNIT"),
("7891234567896", "Bolo de Cenoura", 3.90, "PRICE_PER_UNIT"),
("7891234567897", "Torta de Limão", 4.99, "PRICE_PER_UNIT"),
("7891234567898", "Sanduíche Natural", 8.90, "PRICE_PER_UNIT"),
("7891234567899", "Mini Pizza", 9.50, "PRICE_PER_UNIT");

insert into atendimentos_products (atendimento_id, product_id) values
(1, 3), (1, 4), (1, 11), (1, 13),
(2, 1),
(3, 3), (3, 4), (3, 5), (3, 6), (3, 7), (3, 8), (3, 9), (3, 10), (3, 11), (3, 12), (3, 13), (3, 14),
(4, 2);