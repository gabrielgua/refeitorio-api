set foreign_key_checks = 0;

delete from users;
delete from clients;
delete from orders;
delete from stores;
delete from products;
delete from order_items;
delete from atendimentos;
delete from atendimentos_products;
delete from order_discount_rules;
delete from order_discount_strategies;
delete from credential_ranges;

set foreign_key_checks = 1;

alter table users auto_increment = 1;
alter table stores auto_increment =1;
alter table atendimentos auto_increment = 1;
alter table order_items auto_increment = 1;
alter table products auto_increment = 1;
alter table orders auto_increment = 1;
alter table credential_ranges auto_increment = 1;
alter table order_discount_rules auto_increment = 1;
alter table order_discount_strategies auto_increment = 1;

insert into stores (id, name) values
(1, 'Erasto Gaertner'),
(2, 'Hóspice'),
(3, 'Irati');

insert into users (email, role, password, store_id) values
("admin@refeitorio.com", "ADMIN", "$2a$12$0u7.zYTIbdoyqHHj.poKtuMsXIhaBBNG5Y1gB9HRyBDw8bKM71qk6", 1);

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




insert into atendimentos (name, time_start, time_end, created_at) values
("Café da Manhã", "06:00:00", "12:00:00",  utc_timestamp),
("Almoço", "12:00:00", "16:00:00",  utc_timestamp),
("Lanche da Tarde", "16:00:00", "19:00:00", utc_timestamp),
("Jantar", "19:00:00", "23:59:00", utc_timestamp);

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

insert into atendimentos_products (atendimento_id, product_id, is_mandatory) values
(1, 4, true),
(2, 1, true),
(3, 3, true),
(4, 2, true),
(1, 5, false),
(1, 6, false);

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