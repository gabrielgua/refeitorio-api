alter table order_items add column subtotal decimal(10, 2) not null;
alter table order_items add column discount decimal(10, 2) not null;
alter table order_items add column discounted_price decimal(10, 2) not null;