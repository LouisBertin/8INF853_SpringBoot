insert into role (id, name) values (1, "CUSTOMER");
insert into role (id, name) values (2, "WORKER");
insert into role (id, name) values (3, "ADMIN");

insert into user (name, email, username, password, role_id) values ("louis", "bertin.louis7@gmail.com", "admin", "admin", 3);
insert into user (name, email, username, password, role_id) values ("test", "test@gmail.com", "test", "test", 1);
insert into user (name, email, username, password, role_id) values ("worker", "test@gmail.com", "worker", "worker", 2);

insert into categorie (nom) values ("Disney");
insert into categorie (nom) values ("Super-Héros");
insert into categorie (nom) values ("Figurines divers");

insert into marque (nom) values ("Hot Toys");
insert into marque (nom) values ("Art of War");

insert into figurine (date_parution, description, hauteur, largeur, longueur, nb_exemplaires,nom, poids, prix_ttc, quantite_magasin, quantite_stock, reference, categorie_id,image_id, marque_id) values ("2018-06-03", "Captain Marvel en version Deluxe",29,10,2,100,"Captain Marvel",0.25,300, 4, 5, "HOT904311", 2,1, 1);


insert into image (nom, extension) values ("captain_marvel",".png");
