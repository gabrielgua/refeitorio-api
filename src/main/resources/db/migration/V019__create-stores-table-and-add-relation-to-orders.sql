create table stores (
    id bigint not null auto_increment,
    name varchar(255) not null unique,

    primary key (id)
);

# not quite sure why we need this, but it doesn't work without it
set foreign_key_checks = 0;
alter table orders add column store_id bigint not null;
alter table orders add constraint fk_orders_stores foreign key (store_id) references stores (id);
set foreign_key_checks = 1;