CREATE TABLE categories(
id bigint auto_increment,
name varchar(255),
constraint pk_category primary key (id)
)
ENGINE = InnoDB;

CREATE TABLE spend(
id bigint auto_increment,
user_id bigint,
spend_date datetime,
spend_value bigint default 0,
category_id bigint,
constraint pk_spend primary key (id),
constraint fk_category_id foreign key (category_id) references categories(id),
constraint fk_spend_user_id foreign key (user_id) references users(id)
)
ENGINE = InnoDB;

CREATE TABLE income(
id bigint auto_increment,
user_id bigint,
income_date datetime,
income_value bigint default 0,
constraint pk_income primary key (id),
constraint fk_income_user_id foreign key (user_id) references users(id)
)
ENGINE = InnoDB;

INSERT INTO categories(name)
VALUES
('Alapértelmezett'),
('Szórakozás'),
('Élelmiszer'),
('Utazás');