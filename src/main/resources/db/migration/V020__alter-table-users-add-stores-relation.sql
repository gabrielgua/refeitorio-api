set foreign_key_checks = 0;
alter table users add column store_id bigint not null;
alter table users add constraint fk_users_stores foreign key (store_id) references stores (id);
set foreign_key_checks = 1;