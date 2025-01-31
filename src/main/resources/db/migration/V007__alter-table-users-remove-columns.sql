alter table orders drop constraint fk_orders_users;
alter table orders add constraint fk_orders_clients foreign key (credential) references clients (credential);

alter table users rename column credential to id;
alter table users modify column id bigint NOT NULL auto_increment;

alter table users drop column salary;
alter table users drop column balance;
alter table users drop column name;