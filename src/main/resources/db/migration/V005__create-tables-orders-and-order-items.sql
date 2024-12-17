create table orders (
    id bigint not null auto_increment,
    number varchar(12) not null,
    credential varchar(5) not null,
    atendimento_id bigint not null,

    final_price decimal(10, 2) not null,
    original_price decimal(10, 2) not null,
    discounted_price decimal(10, 2) not null,
    created_at timestamp not null,

    primary key (id),

    constraint fk_orders_users foreign key (credential) references users (credential),
    constraint fk_orders_atendimentos foreign key (atendimento_id) references atendimentos (id)
);

create table order_items (
    id bigint not null auto_increment,
    order_id bigint not null,
    product_id bigint not null,

    quantity smallint not null,
    unit_price decimal(10, 2),
    total_price decimal(10, 2),

    primary key (id),
    unique key uk_order_items (order_id, product_id),
    constraint fk_order_items_orders foreign key (order_id) references orders (id),
    constraint fk_order_items_products foreign key (product_id) references products (id)
);