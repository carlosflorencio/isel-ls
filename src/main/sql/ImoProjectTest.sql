use ImoProjectTest;
-- Drops 
IF( Object_id('Rentals', 'U') IS NOT NULL ) DROP TABLE rentals; 
IF( Object_id('Properties', 'U') IS NOT NULL ) DROP TABLE properties; 
IF( Object_id('Users', 'U') IS NOT NULL ) DROP TABLE users;
IF( Object_id('sqlquerytest', 'U') IS NOT NULL ) DROP TABLE sqlquerytest;

CREATE TABLE Users 
  ( 
	 id        INT NOT NULL UNIQUE IDENTITY(1, 1), 
     username VARCHAR(10) NOT NULL, 
     passw    VARCHAR(100) NOT NULL, 
     email    VARCHAR(150) UNIQUE NOT NULL, 
     name     VARCHAR(150) NOT NULL, 
     PRIMARY KEY(username) 
  ); 

CREATE TABLE Properties 
  ( 
     id        INT NOT NULL IDENTITY(1, 1), 
     imotype   VARCHAR(50) NOT NULL, 
     imodesc   VARCHAR (250), 
     price     MONEY NOT NULL, 
     location  VARCHAR(150) NOT NULL, 
     ownername VARCHAR(10), 
     PRIMARY KEY(id), 
     FOREIGN KEY(ownername) REFERENCES users 
  ); 

Create Table Rentals(
	idProp int not null,
	yearRen int not null,
	weekRen int not null,
	username varchar(10) not null,
	stateRen varchar(30),
	dateRequest datetime,
	dateAccept datetime,
	foreign key(idProp) references Properties,
	foreign key(username) references Users,
	primary key(idProp, yearRen, weekRen),
	check (stateRen = 'pending' or stateRen = 'rented')
);

CREATE TABLE sqlquerytest 
  ( 
     id     INT NOT NULL IDENTITY(1, 1), 
     name   VARCHAR(50) NOT NULL, 
     age    INT,
	 accepted DATETIME,
     born   DATE, 
     weight DECIMAL(5, 2), 
     PRIMARY KEY(id), 
  ); 