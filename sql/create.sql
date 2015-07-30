
drop database if exists booksmarts;
create database booksmarts;

drop table if exists Users;
create table Users (
  username varchar(64) PRIMARY KEY,
  name varchar(64) NOT NULL,
  email varchar(64) NOT NULL UNIQUE,
  password varchar(64) NOT NULL
);

drop table if exists Books;
create table Books (
  isbn varchar(13) PRIMARY KEY,
  name varchar(64) NOT NULL
);


drop table if exists UserBookNotifications;
create table UserBookNotifications (
  username varchar(64) REFERENCES Users(username) ON DELETE CASCADE,
  book_isbn varchar(13) REFERENCES Books(isbn) ON DELETE CASCADE,
  date_notified timestamp,
  price NUMERIC(7,2) not null,
  date_set timestamp not null,
  PRIMARY KEY (username, book_isbn)
);


drop table if exists Quotes;
create table Quotes (
  id serial primary key,
  price NUMERIC(7,2) not null,
  quoted_at timestamp not null,
  url varchar(256) not null,
  UNIQUE (price, quoted_at, url)
);

drop table if exists BookQuotes;
create table BookQuotes (
  vendor_name varchar(256) not null REFERENCES Vendors(name),
  book_isbn varchar(13) not null REFERENCES Books(isbn),
  quote_id int not null REFERENCES Quotes(id),

  PRIMARY KEY (vendor_name, book_isbn, quote_id)
);


drop table if exists Vendors;
create table Vendors (
  name varchar(256) PRIMARY KEY,
  website varchar(256)
);


drop table if exists Publishers;
create table Publishers (
  name varchar(256) primary key
);

drop table if exists Authors;
create table Authors (
  id serial primary key,
  name varchar(256) not null,
  birthday date not null,
  UNIQUE (name, birthday)
);

drop table if exists Authored;
create table Authored (
  author_id int REFERENCES Authors(id),
  book_isbn varchar(13) REFERENCES Books(isbn),
  authored_at timestamp,

  PRIMARY KEY (author_id, book_isbn)
);

drop table if exists Published;
create table Published (
  book_isbn varchar(13) PRIMARY KEY REFERENCES Books(isbn),
  publisher_name varchar(256) not null REFERENCES Publishers(name),
  published_at timestamp not null
);

drop table if exists BookSale;
create table BookSale (
  id SERIAL not null PRIMARY KEY,
  username varchar(64) not null REFERENCES Users(username),
  book_isbn varchar(13) not null REFERENCES Books(isbn),
  sold_at timestamp not null,
  price NUMERIC(7,2) not null
);
