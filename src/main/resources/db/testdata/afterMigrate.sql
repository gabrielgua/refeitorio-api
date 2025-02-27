set foreign_key_checks = 0;

delete from users;
delete from clients;
delete from orders;
delete from products;
delete from order_items;
delete from atendimentos;
delete from atendimentos_products;
delete from order_discount_rules;
delete from order_discount_strategies;
delete from credential_ranges;

set foreign_key_checks = 1;

alter table users auto_increment = 1;
alter table atendimentos auto_increment = 1;
alter table order_items auto_increment = 1;
alter table products auto_increment = 1;
alter table orders auto_increment = 1;
alter table credential_ranges auto_increment = 1;
alter table order_discount_rules auto_increment = 1;
alter table order_discount_strategies auto_increment = 1;

insert into users (email, role, password) values
("admin@refeitorio.com", "ADMIN", "$2a$12$0u7.zYTIbdoyqHHj.poKtuMsXIhaBBNG5Y1gB9HRyBDw8bKM71qk6");

insert into clients (credential, name, role, salary, balance, free_of_charge) values
("64444", "João Maria do Santos", "Estagiário do RH", 1000.00, 0, false),
("63412", "Pedro Maria do Santos", "Academico Administrativo", 1002.46, 1.00, false),
("14928", "Gabriel John Doe", "Analista de Suporte N1", 2000, 1.00, false),
("32456", "RFCC Teste", "RFCC", 2000, 0, false),
("55908", "Corpo Clínico Teste", "Corpo Clínico", 2000, 0, false),
("71893", "Redidentes Teste", "Residente e Especializando", 1670, 0, false),
("00234", "SND Produção Teste", "Nutricionista N1", 3670, 0, true),
("90090", "Teste Credencial", "Credencial Não Compatível com Tipo", 3670, 0, false);


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

insert into credential_ranges(name, min, max, payment_type, created_at) values
("range-cracha-funcionarios-clt-e-aprendizes", 1, 29999,  "PAYROLL_DEBIT", utc_timestamp),
("range-cracha-corpo-clinico", 50000, 59999, "BALANCE_DEBIT", utc_timestamp),
("range-cracha-corpo-estagiarios-e-acedemicos-cepep", 60000, 69999, "BALANCE_DEBIT", utc_timestamp);

insert into order_discount_strategies(credential_range_id, name, salary_min, salary_max, created_at) values
(1, "Funcionários CLT e Aprendizes - 1", 1, 1000, utc_timestamp),
(1, "Funcionários CLT e Aprendizes - 2", 1000.01, 2000, utc_timestamp),
(1, "Funcionários CLT e Aprendizes - 3", 2000.01, 5000, utc_timestamp),
(1, "Funcionários CLT e Aprendizes - 4", 5000.01, 100000, utc_timestamp),
(3, "Estagiários, Academicos CEPEP e Bolsistas", 0, 0, utc_timestamp);

insert into order_discount_rules(order_discount_strategy_id, product_id, discount_type, discount_value) values
(1, 4, "PERCENTAGE_VALUE", 0),
(1, 3, "PERCENTAGE_VALUE", 0),
(1, 1, "PERCENTAGE_VALUE", 0.7),
(1, 2, "PERCENTAGE_VALUE", 1),

(2, 4, "PERCENTAGE_VALUE", 0),
(2, 3, "PERCENTAGE_VALUE", 0),
(2, 1, "PERCENTAGE_VALUE", 0.5),
(2, 2, "PERCENTAGE_VALUE", 1),

(3, 4, "PERCENTAGE_VALUE", 0),
(3, 3, "PERCENTAGE_VALUE", 0),
(3, 1, "PERCENTAGE_VALUE", 0.2),
(3, 2, "PERCENTAGE_VALUE", 1),

(4, 4, "PERCENTAGE_VALUE", 0),
(4, 3, "PERCENTAGE_VALUE", 0),
(4, 1, "PERCENTAGE_VALUE", 0),
(4, 2, "PERCENTAGE_VALUE", 1),

(5, 4, "PERCENTAGE_VALUE", 1),
(5, 3, "PERCENTAGE_VALUE", 1),
(5, 1, "PERCENTAGE_VALUE", 1),
(5, 2, "PERCENTAGE_VALUE", 1);