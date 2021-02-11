alter table orders add column side varchar;
create index on orders(side);