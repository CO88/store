create table products (
  id bigint NOT NULL AUTO_INCREMENT,
  brand_id bigint NOT NULL,
  category_id bigint NOT NULL,
  price bigint,
  PRIMARY KEY (id)
);

create table brands (
   id bigint NOT NULL AUTO_INCREMENT,
   name varchar(150),
   PRIMARY KEY (id)
);

create table categories (
   id bigint NOT NULL AUTO_INCREMENT,
   name varchar(150),
   PRIMARY KEY (id)
);

create table category_price_summaries (
  id bigint NOT NULL AUTO_INCREMENT,
  category_id bigint NOT NULL,
  min_product_id bigint,
  max_product_id bigint,
  version Long default 0,
  PRIMARY KEY (id)
);

create table lowest_totals (
   id bigint NOT NULL AUTO_INCREMENT,
   brand_id bigint NOT NULL,
   total int,
   version Long default 0,
   PRIMARY KEY (id)
);

create table product_lowest_totals (
   id bigint NOT NULL AUTO_INCREMENT,
   lowest_total_id bigint NOT NULL,
   product_id bigint NOT NULL,
   PRIMARY KEY (id)
);