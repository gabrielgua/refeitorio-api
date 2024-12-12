set foreign_key_checks = 0;

delete from usuarios;
delete from atendimentos;
delete from produtos;
delete from atendimentos_produtos;

set foreign_key_checks = 1;

alter table atendimentos auto_increment = 1;
alter table produtos auto_increment = 1;

insert into usuarios (credential, email, name, role, salary, password) values
("13445", "admin@refeitorio.com", "Admin", "ADMIN", null, "$2a$12$0u7.zYTIbdoyqHHj.poKtuMsXIhaBBNG5Y1gB9HRyBDw8bKM71qk6"),
("43926", "refeitorio@refeitorio.com", "Refeitorio Auto Atentimento", "SELF_SERVICE", null, "$2a$12$8N.KRIqNEXL9bdZGjXobieapdlIrWm9NMaowWWAx96EZjcFUqVnYW"),
("87855", "estoque@refeitorio.com", "Estoque Refeitorio", "STOCK", null, "$2a$12$zsfECuMGujmYKVkFqRpPYONb3.Lopmf.MvX2UeEOCfmgy7RKWEYoW"),
("87230", "tesouraria@refeitorio.com", "Tesouraria", "TREASURY", null, "$2a$12$jvPNzMOyg99uQffOrxAkj.Rovm5bPHKRiXChjBZLtWRmTNfUDzll6"),
("64444", "rafael@refeitorio.com", "Rafael Guaitanele Niszczak", "EMPLOYEE", 1000, "$2a$12$6egieKlh5hlUbtiAawZfm.KcXsNmqjbYw/j0NMSBfvDXQupKN4Tqy"),
("45789", "gabriel@refeitorio.com", "Gabriel Guaitanele Niszczak", "TERTIARY", 2400, "$2a$12$TX/TVyFKIfrgBCrthQ.FOetRVjXbVnT.EAOfXgOI.8eegPWNftx2W");

insert into atendimentos (name, time_start, time_end, created_at) values
("Café da Manhã", "06:00:00", "10:00:00", utc_timestamp),
("Almoço", "12:00:00", "14:00:00", utc_timestamp),
("Lanche da Tarde", "16:00:00", "17:30:00", utc_timestamp),
("Jantar", "19:00:00", "21:00:00", utc_timestamp);

insert into produtos (code, name, price, price_type) values
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

insert into atendimentos_produtos (atendimento_id, produto_id) values
(1, 3), (1, 4), (1, 11), (1, 13),
(2, 1),
(3, 3), (3, 4), (3, 5), (3, 6), (3, 7), (3, 8), (3, 9), (3, 10), (3, 11), (3, 12), (3, 13), (3, 14),
(4, 2);