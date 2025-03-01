create table credential_ranges(
    id bigint not null auto_increment,
    name varchar(255) not null,
    min integer not null,
    max integer not null,
    payment_type varchar(100) not null,
    created_at timestamp not null,

    primary key (id)
);