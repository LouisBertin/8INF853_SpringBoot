insert into role (id, name) values (1, "CUSTOMER");
insert into role (id, name) values (2, "WORKER");
insert into role (id, name) values (3, "ADMIN");

insert into user (name, email, username, password, role_id) values ("louis", "bertin.louis7@gmail.com", "admin", "admin", 3);
insert into user (name, email, username, password, role_id) values ("test", "test@gmail.com", "test", "test", 1);