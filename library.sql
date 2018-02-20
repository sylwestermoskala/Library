DROP DATABASE library;
create database library;
\c library
create schema library;

--DROP TABLE library.book;
--DROP TABLE library.authors;
--DROP TABLE library.clients;
--DROP TABLE library.rental;

CREATE TABLE library.authors
(
  authorid serial PRIMARY KEY,
  name char(20) NOT NULL,
  surname char(20) NOT NULL
);

CREATE TABLE library.book (
    bookid serial PRIMARY KEY,
    title char(40) NOT NULL,
    authorid integer REFERENCES library.authors(authorid),
	genre char(20) NOT NULL,
	description char(40)
);


CREATE TABLE library.clients
(
  clientid serial PRIMARY KEY,
  name char(20) NOT NULL,
  surname char(20) NOT NULL
 );

CREATE TABLE library.rental
(
  rentalid serial PRIMARY KEY,
  clientid integer REFERENCES library.clients (clientid) ,
  bookid integer REFERENCES library.book (bookid) ,
  date date NOT NULL,
  returned boolean NOT NULL
);

INSERT INTO library.authors (name, surname) VALUES ('Henryk','Sienkiewicz'),('Jan','Kochanowski'),('Wislawa','Szymborska');
INSERT INTO library.book (title, authorid, genre) VALUES ('W pustyni i w puszczy',1,'adventure'),('Treny',2,'poetry'),('Sol',3,'poetry');
INSERT INTO library.clients (name, surname) VALUES ('Lidia','Kucab'),('Marcin','Kukla'),('Sylwester','Moskala');
