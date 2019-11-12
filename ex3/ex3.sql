create table Category (
    id serial not null primary key,
    name varchar(255)
);

create table Author (
    id serial not null primary key,
    firstname varchar(255),
    lastname varchar(255)
);

create table Book (
    id  serial not null primary key,
    title varchar(255),
    author_id int references Author(id),
    category_id int references Category(id)
);

create table Member (
    id  serial not null primary key,
    password varchar(255),
    username varchar(255),
    firstname varchar(255),
    lastname varchar(255)
);

create table Member_authorities(
	member_id int not null references Member(id),
	authority varchar(50)
);

create table Book_comments (
    Book_id int not null references Book(id),
    Member_id int references Member(id),
    date timestamp,
    text varchar(2000)
);

create view Member_comments as select * from Book_comments;

create table Member_Category (
    Member_id int not null references Member(id),
    preferences_id int not null references Category(id)
);

create table Reservation (
    id serial not null,
    pickupDate date,
    returnDate date,
    book_id int references Book(id),
    member_id int references Member(id)
);

create view authorities as select m.username, ma.authority from Member_authorities ma join Member m on ma.member_id=m.id;


insert into category (name) values ('Essai');
insert into category (name) values ('Roman');
insert into category (name) values ('Poésie');
insert into category (name) values ('Biographie');



insert into author (firstname, lastname) values ('J.R', 'Tokien');
insert into author (firstname, lastname) values ('Emile', 'Zola');
insert into author (firstname, lastname) values ('Ernesto', 'Guevara');
insert into author (firstname, lastname) values ('Henry David', 'Thoreau');
insert into author (firstname, lastname) values ('Albert', 'Cohen');
insert into author (firstname, lastname) values ('Arthur', 'Rimbaud');
insert into author (firstname, lastname) values ('Miguel', 'de Cervantes');
insert into author (firstname, lastname) values ('Ernest', 'Hemingway');
insert into author (firstname, lastname) values ('Leon', 'Tolstoi');
insert into author (firstname, lastname) values ('Alexandre', 'Dumas');
insert into author (firstname, lastname) values ('Charles', 'Baudelaire');
insert into author (firstname, lastname) values ('Franz', 'Kafka');
insert into author (firstname, lastname) values ('Stefan', 'Zweig');
insert into author (firstname, lastname) values ('Jack', 'London');
insert into author (firstname, lastname) values ('Joseph', 'Kessel');
insert into author (firstname, lastname) values ('Thomas', 'Mann');
insert into author (firstname, lastname) values ('Jorge', 'Semprun');
insert into author (firstname, lastname) values ('Gabriel', 'Garcia Marquez');
insert into author (firstname, lastname) values ('William', 'Faulkner');
insert into author (firstname, lastname) values ('Giuseppe Tomasi', 'di Lampedusa');
insert into author (firstname, lastname) values ('Joseph', 'Conrad');
insert into author (firstname, lastname) values ('Romain', 'Gary');
insert into author (firstname, lastname) values ('Victor', 'Hugo');
insert into author (firstname, lastname) values ('Walt', 'Withman');
insert into author (firstname, lastname) values ('Fedor', 'Dostoievski');
insert into author (firstname, lastname) values ('James', 'Joyce');
insert into author (firstname, lastname) values ('Vladimir', 'Nabokov');
insert into author (firstname, lastname) values ('Charles', 'Dickens');
insert into author (firstname, lastname) values ('Marcel', 'Proust');
insert into author (firstname, lastname) values ('Honoré', 'de Balzac');



insert into book (title, author_id, category_id) values ('Walden', 4, 1);
insert into book (title, author_id, category_id) values ('L''ecriture ou la vie', 17, 1);
insert into book (title, author_id, category_id) values ('Cent ans de solitude', 18, 2);
insert into book (title, author_id, category_id) values ('Don Quichotte', 7, 2);
insert into book (title, author_id, category_id) values ('Illusions perdues', 30, 2);
insert into book (title, author_id, category_id) values ('La recherche du temps perdu', 29, 2);
insert into book (title, author_id, category_id) values ('De grandes espérances', 28, 2);
insert into book (title, author_id, category_id) values ('Lolita', 27, 2);
insert into book (title, author_id, category_id) values ('Ulysse', 26, 2);
insert into book (title, author_id, category_id) values ('Les frères Karamazov', 25, 2);
insert into book (title, author_id, category_id) values ('Les misérables', 23, 2);
insert into book (title, author_id, category_id) values ('Les racines du ciel', 22, 2);
insert into book (title, author_id, category_id) values ('Au coeur des ténèbres', 21, 2);
insert into book (title, author_id, category_id) values ('Le bruit et la fureur', 19, 2);
insert into book (title, author_id, category_id) values ('La montagne magique', 16, 2);
insert into book (title, author_id, category_id) values ('Les cavaliers', 15, 2);
insert into book (title, author_id, category_id) values ('Le procès', 12, 2);
insert into book (title, author_id, category_id) values ('Le comte de Monte Christo', 10, 2);
insert into book (title, author_id, category_id) values ('Guerre et paix', 9, 2);
insert into book (title, author_id, category_id) values ('Le vieil homme et la mer', 8, 2);
insert into book (title, author_id, category_id) values ('Belle du seigneur', 5, 2);
insert into book (title, author_id, category_id) values ('Germinal', 2, 2);
insert into book (title, author_id, category_id) values ('Le seigneur des anneaux', 1, 2);
insert into book (title, author_id, category_id) values ('Feuilles d''herbe', 24, 3);
insert into book (title, author_id, category_id) values ('Les fleurs du mal', 11, 3);
insert into book (title, author_id, category_id) values ('Une saison en enfer', 6, 3);
insert into book (title, author_id, category_id) values ('Le guépard', 20, 4);
insert into book (title, author_id, category_id) values ('Martin Eden', 14, 4);
insert into book (title, author_id, category_id) values ('Magellan', 13, 4);
insert into book (title, author_id, category_id) values ('Carnets de voyage', 3, 4);

insert into Member(firstname, lastname, username, password) values('John', 'Doe', 'jdoe', '$2a$10$oZsxPNZFQmLO9HaJNAElJuCF1OPoRKMf2T2S1Of/MWRm1FWMyXELe');
insert into Member_authorities(member_id, authority) values(1, 'find-books');
insert into Member_authorities(member_id, authority) values(1, 'borrow-books');

insert into Book_comments(book_id, member_id, date, text) values(1,1, now(), 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."');


