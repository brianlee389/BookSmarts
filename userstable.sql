create table Users (
	username varchar(64) PRIMARY KEY,
	name varchar(64) NOT NULL,
	email varchar(64) NOT NULL UNIQUE,
	password varchar(64) NOT NULL
);

create table Books (
	isbn int PRIMARY KEY,
	name varchar(64) NOT NULL
);
