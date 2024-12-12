set foreign_key_checks = 0;

delete from users;

set foreign_key_checks = 1;

insert into users (credential, email, name, role, salary, password) values
("13445", "admin@refeitorio.com", "Admin", "ADMIN", null, "$2a$12$0u7.zYTIbdoyqHHj.poKtuMsXIhaBBNG5Y1gB9HRyBDw8bKM71qk6"),
("43926", "refeitorio@refeitorio.com", "Refeitorio Auto Atentimento", "SELF_SERVICE", null, "$2a$12$8N.KRIqNEXL9bdZGjXobieapdlIrWm9NMaowWWAx96EZjcFUqVnYW"),
("87855", "estoque@refeitorio.com", "Estoque Refeitorio", "STOCK", null, "$2a$12$zsfECuMGujmYKVkFqRpPYONb3.Lopmf.MvX2UeEOCfmgy7RKWEYoW"),
("87230", "tesouraria@refeitorio.com", "Tesouraria", "TREASURY", null, "$2a$12$jvPNzMOyg99uQffOrxAkj.Rovm5bPHKRiXChjBZLtWRmTNfUDzll6"),
("64444", "rafael@refeitorio.com", "Rafael Guaitanele Niszczak", "EMPLOYEE", 1000, "$2a$12$6egieKlh5hlUbtiAawZfm.KcXsNmqjbYw/j0NMSBfvDXQupKN4Tqy"),
("45789", "gabriel@refeitorio.com", "Gabriel Guaitanele Niszczak", "TERCIARY", 2400, "$2a$12$TX/TVyFKIfrgBCrthQ.FOetRVjXbVnT.EAOfXgOI.8eegPWNftx2W");