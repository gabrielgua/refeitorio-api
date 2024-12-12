create table usuarios (
    credential varchar(5) not null,
    email varchar(255) not null,
    name varchar(255) not null,
    role varchar(255) not null,
    salary decimal(10, 2),
    password varchar(255) not null,

    primary key (credential)
);