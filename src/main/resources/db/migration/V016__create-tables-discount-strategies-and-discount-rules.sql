create table order_discount_strategies(
    id bigint not null auto_increment,
    credential_range_id bigint not null,
    name varchar(255) not null,
    salary_min decimal(10,2) default 0,
    salary_max decimal(10,2) default 0,
    created_at timestamp not null,

    primary key (id),
    constraint fk_order_discount_strategy_credential_range foreign key (credential_range_id) references credential_ranges (id)
);

create table order_discount_rules(
    id bigint not null auto_increment,
    order_discount_strategy_id bigint not null,
    product_id bigint not null,
    discount_type varchar(255) not null,
    discount_value decimal(10,2) default 0,

    primary key (id),
    constraint fk_discount_rule_product foreign key (product_id) references products (id),
    constraint fk_discount_rule_discount_strategy foreign key (order_discount_strategy_id) references order_discount_strategies (id)
);