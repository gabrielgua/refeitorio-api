create table clients (
    credential varchar(10) not null,
    name varchar(255) not null,
    role varchar(255),
    salary decimal(10, 2) not null,
    balance decimal(10, 2) default 0,

    primary key (credential)
);