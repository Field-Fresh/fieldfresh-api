CREATE EXTENSION if not exists postgis;
-- This is necessary for any spacial operations on data

CREATE TABLE proxies (
    id                  character(25) DEFAULT fieldfresh_id('pr'::text) NOT NULL PRIMARY KEY,
    user_id             char(24) references users(id),
    display_name varchar,
    description varchar,
    street_address varchar,
    postal_code varchar,
    city varchar,
    province varchar,
    country varchar,
    longitude float not null,
    latitude float not null,
    geo geometry,
    created_at timestamp,
    updated_at timestamp
);

CREATE index on proxies (geo);
CREATE index on proxies (street_address);
CREATE index on proxies (postal_code);
CREATE index on proxies (city);
CREATE index on proxies (province);
CREATE index on proxies (country);
CREATE index on proxies (longitude, latitude);
CREATE index on proxies (created_at);
CREATE index on proxies (updated_at);

CREATE TABLE products (
    id                  character(24) DEFAULT fieldfresh_id('p'::text) NOT NULL PRIMARY KEY,
    type varchar,
    category varchar,
    family varchar,
    unit varchar,
    class_type varchar,
    created_at timestamp,
    updated_at timestamp
);

CREATE index on products (category);
CREATE index on products (family);
CREATE index on products (class_type);
CREATE index on products (created_at);
CREATE index on products (updated_at);

CREATE TABLE orders (
    id                  character(25) NOT NULL PRIMARY KEY,
    proxy_id             char(25) references proxies(id),
    status varchar,
    round_updated_timestamp timestamp,
    round integer,
    created_at timestamp,
    updated_at timestamp
);

CREATE index on orders (round);
CREATE index on orders (round_updated_timestamp);
CREATE index on orders (status);
CREATE index on orders (created_at);
CREATE index on orders (updated_at);

CREATE TABLE buy_orders (
    id                  character(25) DEFAULT fieldfresh_id('bo'::text) NOT NULL PRIMARY KEY,
    created_at timestamp,
    updated_at timestamp
);

CREATE index on buy_orders (created_at);
CREATE index on buy_orders (updated_at);

CREATE TABLE sell_orders (
    id                  character(25) DEFAULT fieldfresh_id('so'::text) NOT NULL PRIMARY KEY,
    created_at timestamp,
    updated_at timestamp
);

CREATE index on sell_orders (created_at);
CREATE index on sell_orders (updated_at);

CREATE TABLE buy_products (
    id                  character(26) DEFAULT fieldfresh_id('bup'::text) NOT NULL PRIMARY KEY,
    earliest_date timestamp ,
    latest_date timestamp ,
    max_price_cents bigint,
    volume float,
    buy_order_id char(25) references buy_orders(id),
    product_id char(24) references products(id),
    created_at timestamp,
    updated_at timestamp
);

CREATE index on buy_products (earliest_date);
CREATE index on buy_products (latest_date);
CREATE index on buy_products (max_price_cents);
CREATE index on buy_products (volume);
CREATE index on buy_products (created_at);
CREATE index on buy_products (updated_at);

CREATE TABLE sell_products (
    id                  character(26) DEFAULT fieldfresh_id('sup'::text) NOT NULL PRIMARY KEY,
    earliest_date timestamp ,
    latest_date timestamp ,
    min_price_cents bigint,
    volume float,
    picture_count integer,
    sell_order_id char(25) references sell_orders(id),
    product_id char(24) references products(id),
    created_at timestamp,
    updated_at timestamp
);

CREATE index on sell_products (earliest_date);
CREATE index on sell_products (latest_date);
CREATE index on sell_products (min_price_cents);
CREATE index on sell_products (volume);
CREATE index on sell_products (created_at);
CREATE index on sell_products (updated_at);


CREATE TABLE ratings (
    id                  character(24) DEFAULT fieldfresh_id('r'::text) NOT NULL PRIMARY KEY,
    rating_user_id char(24) references users(id),
    rating_value integer not null,
    comments varchar,
    order_id char(25) references orders(id),
    created_at timestamp,
    updated_at timestamp
);

CREATE index on ratings (rating_value);
CREATE index on ratings (created_at);
CREATE index on ratings (updated_at);
