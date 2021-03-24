alter table sell_products add column if not exists original_volume int;
alter table sell_products_aud add column if not exists original_volume int;

update sell_products set original_volume = d.volume from (
    select distinct(id) as _id, volume from sell_products_aud
    where revtype = 0
) as d where id = d._id;

alter table buy_products add column if not exists original_volume int;
alter table buy_products_aud add column if not exists original_volume int;

update buy_products set original_volume = d.volume from (
    select distinct(id) as _id, volume from buy_products_aud
    where revtype = 0
) as d where id = d._id;