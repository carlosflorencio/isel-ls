use ImoProject;

-- Drops
IF( object_id('Rentals', 'U') IS NOT NULL) DROP TABLE Rentals;
IF( object_id('Properties', 'U') IS NOT NULL) DROP TABLE Properties;
IF( object_id('Users', 'U') IS NOT NULL) DROP TABLE Users;



Create Table Users(
	id        INT NOT NULL UNIQUE IDENTITY(1, 1), 
	username varchar(10) not null,
	passW varchar(100) not null,
	email varchar(150) unique not null,
	name varchar(150) not null,
	Primary Key(username)
);

Create Table Properties(
	id int not null identity(1,1),
	imoType varchar(50) not null,
	imoDesc varchar (250), 
	price Money  not null,
	location varchar(150) not null,
	ownerName varchar(10), 
	Primary key(id),
	Foreign key(ownerName) references Users
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


