use ImoProject;

Insert into Users(username, passW, email, name) values('bart', 'q1w2e3', 'first_user@gmail.com', 'Bart');
Insert into Users(username, passW, email, name) values('lisa', 'qwertyui', 'second_user@gmail.com', 'Lisa');
Insert into Users(username, passW, email, name) values('Bia', '12345', 'Bia_user@gmail.com', 'Beatriz Patusco');
Insert into Users(username, passW, email, name) values('Monica', 'z1x2c3v4', 'M_user@hotmail.com', 'Mónia Dias');

Insert into Properties(imoType, imoDesc, price, location, ownerName) values('apartamento', 'perto do metro', 500.50, 'Algarve, Tavira', 'bart');
Insert into Properties(imoType, imoDesc, price, location, ownerName) values('moradia', 'muito espaçosa', 12000.00, 'Porto, viseu', 'bart');
Insert into Properties(imoType, imoDesc, price, location, ownerName) values('apartamento', 'sem ningéum por perto', 450.00, 'Lisboa, Catanheira', 'Monica');
Insert into Properties(imoType, imoDesc, price, location, ownerName) values('vivenda', 'optimo estado', 1000.00, 'Norte, Ameais de baixo', 'Bia');


Insert into Rentals(idProp,username, yearRen, weekRen, stateRen,dateRequest) values (1,'bart', 2014, 20, 'pending', '2014-04-27');


