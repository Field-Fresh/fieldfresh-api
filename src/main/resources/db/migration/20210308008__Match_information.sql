CREATE TABLE matches
(
    id               character(25) DEFAULT fieldfresh_id('mi'::text) NOT NULL PRIMARY KEY,
    quantity float,
    unit_price_cents    bigint,
    sell_product_id  char(26) references sell_products (id),
    buy_product_id   char(26) references buy_products (id),
    round            bigint,
    created_at       timestamp,
    updated_at       timestamp
);

CREATE index on matches (round);
CREATE index on matches (quantity);
CREATE index on matches (unit_price_cents);