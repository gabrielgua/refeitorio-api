create table user (
    credential varchar(36) not null,
    name varchar(255) not null,
    surname varchar(255) not null,
    salary decimal(10, 2) not null,

    primary key (credential)
);

create table lunch(
    id bigint not null,
    user_credential varchar(36) not null,
    price decimal(10, 2),
    created_at datetime not null,

    primary key (id),
    constraint fk_user_lunch foreign key (user_credential) references user (credential)
);