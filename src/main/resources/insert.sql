INSERT INTO T_CONFERENCE (id, version, name, country, city, date) VALUES (2000, 1, 'Devoxx BE', 'Belgium', 'Antwerp', '2016-01-01');
INSERT INTO T_CONFERENCE (id, version, name, country, city, date) VALUES (2001, 1, 'Devoxx FR', 'France', 'Paris', '2016-01-01');
INSERT INTO T_CONFERENCE (id, version, name, country, city, date) VALUES (2002, 1, 'Devoxx UK', 'United Kingdom', 'London', '2016-01-01');
INSERT INTO T_CONFERENCE (id, version, name, country, city, date) VALUES (2003, 1, 'Devoxx MA', 'Morroco', 'Casablanca', '2016-01-01');
INSERT INTO T_CONFERENCE (id, version, name, country, city, date) VALUES (2004, 1, 'Devoxx PL', 'Poland', 'Krakow', '2016-01-01');
INSERT INTO T_CONFERENCE (id, version, name, country, city, date) VALUES (2005, 1, 'JavaOne', 'USA', 'San Francisco', '2016-01-01');


INSERT INTO T_USER (email, name, password, version) VALUES('costajlmpp@gmail.com', 'Joao Costa', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 1)
INSERT INTO T_USER_ROLES (userId, permissions) VALUES('costajlmpp@gmail.com', 'ADMIN');
INSERT INTO T_USER_ROLES (userId, permissions) VALUES('costajlmpp@gmail.com', 'USER');


INSERT INTO T_USER (email, name, password, version) VALUES('admin@gmail.com', 'Financial Master', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 1)
INSERT INTO T_USER_ROLES (userId, permissions) VALUES('admin@gmail.com', 'ADMIN');

INSERT INTO T_USER (email, name, password, version) VALUES('simplespeaker@gmail.com', 'Simple Speaker', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 1)
INSERT INTO T_USER_ROLES (userId, permissions) VALUES('simplespeaker@gmail.com', 'USER');
