
create table users
(
    id  IDENTITY     NOT NULL PRIMARY KEY,
    username NVARCHAR(255) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL
);
create TABLE Account
(
    id BIGINT NOT NULL,
    account_full_id VARCHAR(255),
    account_type VARCHAR(255) NOT NULL,
    bank_id INTEGER NOT NULL,
    client_id VARCHAR(255) NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    withdraw_allowed BIT NOT NULL,
    CONSTRAINT PK_Account PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES users (id)
);

CREATE TABLE Transaction
(
    id INTEGER AUTO_INCREMENT NOT NULL,
    date VARCHAR(255) NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    type_of_operation VARCHAR(255) NOT NULL,
    CONSTRAINT PK_Transaction PRIMARY KEY (id)
);

create table roles
(
    id   IDENTITY     NOT NULL PRIMARY KEY,
    name NVARCHAR(80) not null
);



create table users_roles
(
    user_id bigint not null,
    role_id integer not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)

);

insert into roles(name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');


