ALTER TABLE sell_products
    add COLUMN picture_url varchar;

ALTER TABLE sell_products drop column picture_count;