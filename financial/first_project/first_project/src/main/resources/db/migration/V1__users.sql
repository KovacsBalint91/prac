CREATE TABLE users(
    id bigint auto_increment,
    username varchar(50),
    name varchar(100),
    password varchar(200),
    wallet bigint default 0,
    role varchar(20) default 'ROLE_USER',
    enabled tinyint default 1,
    constraint pk_users primary key (id)
    )
    ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


insert into users(username, name, password, role)
    values
    ('admin', 'Admin', '$2a$10$pQuTYZtc8lQOPdlqgOTCF.hh170R1Own34jITOoUR9tVawg/6ci46', 'ROLE_ADMIN'),
    ('user', 'User', '$2a$10$u0A3Nd9gm6gjWnEksxBr9uV9wiabUHays5.nsOIAtG8c1DHMun4pO', 'ROLE_USER');