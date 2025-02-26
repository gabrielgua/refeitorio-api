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

insert into users (email, role, password) values
("admin@refeitorio.com", "ADMIN", "$2a$12$0u7.zYTIbdoyqHHj.poKtuMsXIhaBBNG5Y1gB9HRyBDw8bKM71qk6");

insert into clients (credential, name, role, salary, balance, free_of_charge) values
("64444", "João Maria do Santos", "Estagiário do RH", 1000.00, null, false),
("63412", "Pedro Maria do Santos", "Academico Administrativo", 1002.46, null, false),
("14928", "Gabriel John Doe", "Analista de Suporte N1", 2000, null, false),
("32456", "RFCC Teste", "RFCC", 2000, null, false),
("55908", "Corpo Clínico Teste", "Corpo Clínico", 2000, null, false),
("71893", "Redidentes Teste", "Residente e Especializando", 1670, null, false),
("00234", "SND Produção Teste", "Nutricionista N1", 3670, null, true),
("90090", "Terceirizado 2", "Terceirizado", 3670, 10.34, false),
("42877", "Terceiro", "Terceirizado", 2670, 3.12, false);


insert into atendimentos (name, code, time_start, time_end, price_type, created_at) values
("Café da Manhã", "CAFE_001", "06:00:00", "12:00:00", "PRICE_PER_UNIT", utc_timestamp),
("Almoço", "ALMOCO_002", "12:00:00", "16:00:00", "PRICE_PER_KG", utc_timestamp),
("Lanche da Tarde", "LANCHE_003","16:00:00", "19:00:00", "PRICE_PER_UNIT", utc_timestamp),
("Jantar", "JANTAR_004","19:00:00", "23:59:00", "PRICE_PER_KG", utc_timestamp);

insert into products (code, name, price, price_type, allow_multiple) values
("7891234567886", "Almoço", 16, "PRICE_PER_KG", false),
("7891234567887", "Jantar", 16, "PRICE_PER_KG", false),
("7891234567888", "Lanche da tarde", 5.84, "PRICE_PER_UNIT", true),
("7891234567889", "Café da Manhã", 5.84, "PRICE_PER_UNIT", true),
("7891234567890", "Coxinha", 5.99, "PRICE_PER_UNIT", true),
("7891234567891", "Pão de Queijo", 4.50, "PRICE_PER_UNIT", true),
("7891234567892", "Empada de Frango", 6.50, "PRICE_PER_UNIT", true),
("7891234567893", "Quibe", 3.80, "PRICE_PER_UNIT", true),
("7891234567894", "Pastel de Carne", 7.00, "PRICE_PER_UNIT", true),
("7891234567895", "Esfirra de Queijo", 5.50, "PRICE_PER_UNIT", true),
("7891234567896", "Bolo de Cenoura", 3.90, "PRICE_PER_UNIT", true),
("7891234567897", "Torta de Limão", 4.99, "PRICE_PER_UNIT", true),
("7891234567898", "Sanduíche Natural", 8.90, "PRICE_PER_UNIT", true),
("7891234567899", "Mini Pizza", 9.50, "PRICE_PER_UNIT", true);

insert into atendimentos_products (atendimento_id, product_id) values
(1, 3), (1, 4), (1, 11), (1, 13),
(2, 1),
(3, 3), (3, 4), (3, 5), (3, 6), (3, 7), (3, 8), (3, 9), (3, 10), (3, 11), (3, 12), (3, 13), (3, 14),
(4, 2);

insert into orders (id, number, credential, atendimento_id, final_price, discounted_price, original_price, created_at) values
(1, "202515559707", "14928", 2, 3.65, 3.65, 7.30, "2025-02-25 18:12:02"),
(2, "202594343248", "42877", 2, 10.50, 0, 10.5, "2025-02-25 18:12:12"),
(3, "202594833624", "90090", 2, 4.08, 0, 4.08, "2025-02-25 18:13:56"),
(4, "202586186626", "00234", 2, 0, 16.00, 16.00, "2025-02-25 18:12:56");

insert into order_items (order_id, product_id, quantity, unit_price, total_price, weight) values
(1, 1, 1, 16.00, 7.30, 0.456),
(2, 1, 1, 16.00, 10.50, 0.656),
(3, 1, 1, 16.00, 4.08, 0.255),
(4, 1, 1, 16.00, 16.00, 1.000);