use library;

drop table author;
drop table book;
drop table if exists hibernate_sequence;

drop table author_many;
drop table book_many;
drop table author_book;

-- TODO task_04
create table author_many(
id_author int not null auto_increment primary key,
name varchar(45) not null,
last_name varchar(45) not null
                        );

INSERT INTO author_many
(name, last_name)
VALUES
    ('author_name_1','author_last_name_1'),
    ('author_name_2','author_last_name_2'),
    ('author_name_3','author_last_name_3');

create table book_many(
id_book int not null auto_increment primary key,
name_book varchar(45) not null
                      );

INSERT INTO book_many
(name_book)
VALUES
    ('book_many_1'),
    ('book_many_2'),
    ('book_many_3');

create table author_book (
    author_id INT(11) NOT NULL,
    book_id INT(11) NOT NULL,
    PRIMARY KEY (author_id, book_id),
    FOREIGN KEY (author_id) REFERENCES author_many(id_author) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book_many(id_book) ON DELETE CASCADE
);

INSERT INTO author_book
(author_id,book_id)
VALUES
    (1,1),
    (1,2),
    (1,3),
    (2,1),
    (2,2),
    (2,3);




