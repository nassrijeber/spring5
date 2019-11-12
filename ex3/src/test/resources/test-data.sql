INSERT INTO category (name) VALUES ('Essai');
INSERT INTO category (name) VALUES ('Roman');
INSERT INTO category (name) VALUES ('Poésie');
INSERT INTO category (name) VALUES ('Biographie');



INSERT INTO author (firstname, lastname) VALUES ('J.R', 'Tokien');
INSERT INTO author (firstname, lastname) VALUES ('Emile', 'Zola');
INSERT INTO author (firstname, lastname) VALUES ('Ernesto', 'Guevara');
INSERT INTO author (firstname, lastname) VALUES ('Henry David', 'Thoreau');
INSERT INTO author (firstname, lastname) VALUES ('Albert', 'Cohen');
INSERT INTO author (firstname, lastname) VALUES ('Arthur', 'Rimbaud');
INSERT INTO author (firstname, lastname) VALUES ('Miguel', 'de Cervantes');
INSERT INTO author (firstname, lastname) VALUES ('Ernest', 'Hemingway');
INSERT INTO author (firstname, lastname) VALUES ('Leon', 'Tolstoi');
INSERT INTO author (firstname, lastname) VALUES ('Alexandre', 'Dumas');
INSERT INTO author (firstname, lastname) VALUES ('Charles', 'Baudelaire');
INSERT INTO author (firstname, lastname) VALUES ('Franz', 'Kafka');
INSERT INTO author (firstname, lastname) VALUES ('Stefan', 'Zweig');
INSERT INTO author (firstname, lastname) VALUES ('Jack', 'London');
INSERT INTO author (firstname, lastname) VALUES ('Joseph', 'Kessel');
INSERT INTO author (firstname, lastname) VALUES ('Thomas', 'Mann');
INSERT INTO author (firstname, lastname) VALUES ('Jorge', 'Semprun');
INSERT INTO author (firstname, lastname) VALUES ('Gabriel', 'Garcia Marquez');
INSERT INTO author (firstname, lastname) VALUES ('William', 'Faulkner');
INSERT INTO author (firstname, lastname) VALUES ('Giuseppe Tomasi', 'di Lampedusa');
INSERT INTO author (firstname, lastname) VALUES ('Joseph', 'Conrad');
INSERT INTO author (firstname, lastname) VALUES ('Romain', 'Gary');
INSERT INTO author (firstname, lastname) VALUES ('Victor', 'Hugo');
INSERT INTO author (firstname, lastname) VALUES ('Walt', 'Withman');
INSERT INTO author (firstname, lastname) VALUES ('Fedor', 'Dostoievski');
INSERT INTO author (firstname, lastname) VALUES ('James', 'Joyce');
INSERT INTO author (firstname, lastname) VALUES ('Vladimir', 'Nabokov');
INSERT INTO author (firstname, lastname) VALUES ('Charles', 'Dickens');
INSERT INTO author (firstname, lastname) VALUES ('Marcel', 'Proust');
INSERT INTO author (firstname, lastname) VALUES ('Honoré', 'de Balzac');



INSERT INTO book (title, author_id, category_id) VALUES ('Walden', 4, 1);
INSERT INTO book (title, author_id, category_id) VALUES ('L''ecriture ou la vie', 17, 1);
INSERT INTO book (title, author_id, category_id) VALUES ('Cent ans de solitude', 18, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Don Quichotte', 7, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Illusions perdues', 30, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('La recherche du temps perdu', 29, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('De grandes espérances', 28, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Lolita', 27, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Ulysse', 26, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Les frères Karamazov', 25, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Les misérables', 23, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Les racines du ciel', 22, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Au coeur des ténèbres', 21, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Le bruit et la fureur', 19, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('La montagne magique', 16, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Les cavaliers', 15, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Le procès', 12, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Le comte de Monte Christo', 10, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Guerre et paix', 9, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Le vieil homme et la mer', 8, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Belle du seigneur', 5, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Germinal', 2, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Le seigneur des anneaux', 1, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Feuilles d''herbe', 24, 3);
INSERT INTO book (title, author_id, category_id) VALUES ('Les fleurs du mal', 11, 3);
INSERT INTO book (title, author_id, category_id) VALUES ('Une saison en enfer', 6, 3);
INSERT INTO book (title, author_id, category_id) VALUES ('Le guépard', 20, 4);
INSERT INTO book (title, author_id, category_id) VALUES ('Martin Eden', 14, 4);
INSERT INTO book (title, author_id, category_id) VALUES ('Magellan', 13, 4);
INSERT INTO book (title, author_id, category_id) VALUES ('Carnets de voyage', 3, 4);

insert into Member(firstname, lastname, username, password) values('John', 'Doe', 'jdoe', '$2a$10$ncssTAEhxN658TbX6WMx4.Lck7v3GQp2WbBE4qk8wAY8n4otwEbCa');