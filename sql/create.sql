

create table Users (
	username varchar(64) PRIMARY KEY,
	name varchar(64) NOT NULL,
	email varchar(64) NOT NULL UNIQUE,
	password varchar(64) NOT NULL
);

create table Books (
	isbn varchar(13) PRIMARY KEY,
	name varchar(64) NOT NULL
);


create table UserBookNotifications (
	username varchar(64),
	book_isbn varchar(13),
	date_notified timestamp default null,
	price NUMERIC(7,2) not null,
	date_set timestamp not null
	FOREIGN KEY username REFERENCES Users ON DELETE CASCADE,
	FOREIGN KEY book_isbn REFERENCES Books ON DELETE CASCADE,
	PRIMARY KEY (username, book_isbn)
);


create table Quotes (
	id serial primary key,
	price NUMERIC(7,2) not null,
	quoted_at timestamp not null,
	url varchar(256) not null,
	UNIQUE (price, quoted_at, url)
);

create table BookQuotes (
	vendor_name varchar(256) not null,
	book_isbn varchar(13) not null,
	quote_id int not null
	PRIMARY KEY (vendor_name, book_isbn, quote_id)
);


create table Vendor (
	vendor_name varchar(256) PRIMARY KEY,
	vendor_website varchar(256)
);


create table Publisher (
	name varchar(256) primary key
);

create table Author (
	id serial primary key,
	name varchar(256) not null,
	birthday date not null,
	UNIQUE (name, birthday)
);

create table Authored (
	author_id int,
	book_isbn varchar(13),
	authored_at timestamp,
	
	PRIMARY KEY (author_id, book_isbn)
);

create table Published (
	book_isbn varchar(13) PRIMARY KEY,
	publisher_name varchar(256) not null,
	published_at timestamp not null
);

create table BookSale (
	id SERIAL not null PRIMARY KEY,
	username varchar(64) not null,
	book_isbn varchar(13) not null,
	sold_at timestamp not null,
	price NUMERIC(7,2) not null,
);
