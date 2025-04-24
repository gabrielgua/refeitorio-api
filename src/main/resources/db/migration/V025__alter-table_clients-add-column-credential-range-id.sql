set foreign_key_checks = 0;
alter table clients
    add column credential_range_id bigint not null,
    add constraint fk_credential_ranges_clients
        foreign key (credential_range_id) references credential_ranges (id);
set foreign_key_checks = 1;