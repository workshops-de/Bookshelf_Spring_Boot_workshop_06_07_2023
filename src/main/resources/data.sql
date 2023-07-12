DELETE FROM author;
DELETE FROM book;

INSERT INTO book (id, isbn, title, description) VALUES (1, '978-0201633610', 'Design Patterns', 'Mit Design Patterns lassen sich wiederkehrende Aufgaben in der objektorientierten Softwareentwicklung effektiv lösen.');
INSERT INTO book (id, isbn, title, description) VALUES (2, '978-3826655487', 'Clean Code', 'Das einzige praxisnahe Buch, mit dem Sie lernen, guten Code zu schreiben!');
INSERT INTO book (id, isbn, title, description) VALUES (3, '978-3836211161', 'Coding for Fun', 'Dieses unterhaltsam geschriebene Buch führt Sie spielerisch durch die spektakuläre Geschichte unserer Blechkollegen.');

INSERT INTO author (id, name, book_id) VALUES (1, 'Erich Gamma', 1);
INSERT INTO author (id, name, book_id) VALUES (2, 'Robert C. Martin', 2);
INSERT INTO author (id, name, book_id) VALUES (3, 'Gottfried Wolmeringer', 3);
