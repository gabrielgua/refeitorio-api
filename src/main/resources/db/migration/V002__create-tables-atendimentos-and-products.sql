create table atendimentos (
    id bigint not null auto_increment,
    name varchar(255) not null,
    time_start time not null,
    time_end time not null,
    created_at timestamp not null,

    primary key (id)
);

create table products (
    id bigint not null auto_increment,
    code varchar(255),
    name varchar(255) not null,
    price decimal(10, 2) not null,
    price_type varchar(100) not null,

    primary key (id)
);

create table atendimentos_products (
    atendimento_id bigint not null,
    product_id bigint not null,

    primary key (atendimento_id, product_id),
    constraint ap_atendimentos_fk foreign key (atendimento_id) references atendimentos (id),
    constraint ap_produtos_fk foreign key (product_id) references products (id)
);