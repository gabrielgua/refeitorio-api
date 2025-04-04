create table balance_movements(
    id bigint not null auto_increment,
    credential varchar(255) not null,

    amount decimal(10,2) not null,
    old_balance decimal(10,2) not null,
    new_balance decimal(10,2) not null,
    timestamp timestamp not null,
    type varchar(255) not null,

    primary key (id),
    constraint fk_balance_movements_clients foreign key (credential) references clients (credential)
);