INSERT INTO public.users (id, version, birthdate, email, firstname, lastaccess, lastname, login, password, phone, role) VALUES (1, 1, '1986-09-25 00:00:00', 'jocelyn.ntakpe@sopragroup.com', 'Jocelyn', '1986-09-25 00:00:00', 'N''TAKPE', 'jOSS', 'lolilol', '51394', 'ROLE_ADMIN');
INSERT INTO public.users (id, version, birthdate, email, firstname, lastaccess, lastname, login, password, phone, role) VALUES (2, 0, '1988-10-25 00:00:00', 'julien.guerrin@sopragroup.com', 'Julien', '1988-10-25 00:00:00', 'GUERRIN', 'JujuPiwi', 'lolilol', '51365', 'ROLE_USER');
INSERT INTO public.users (id, version, birthdate, email, firstname, lastaccess, lastname, login, password, phone, role) VALUES (3, 0, '1986-05-25 00:00:00', 'charles-eric.giraud@sopragroup.com', 'Charles-Eric', '1986-05-25 00:00:00', 'GIRAUD', 'Selrak', 'lolilol', '51525', 'ROLE_USER');
INSERT INTO public.users (id, version, birthdate, email, firstname, lastaccess, lastname, login, password, phone, role) VALUES (4, 0, '2013-04-30 23:51:09', 'natacha.berges@sopragroup.com', 'Natacha', '1986-05-08 00:00:00', 'BERGES', 'nberges', 'lolilol', '51333', 'ROLE_USER');
INSERT INTO public.users (id, version, birthdate, email, firstname, lastaccess, lastname, login, password, phone, role) VALUES (5, 0, '2013-04-30 23:51:09', 'arnaud.pradelles@sopragroup.com', 'Arnaud', '1978-01-01 00:00:00', 'PRADELLES', 'Piwi', 'lolilol', '51345', 'ROLE_USER');

INSERT INTO public.quiz (id, version, createdate, execnumber, title, creator_id) VALUES (1, 0, '2013-05-10 16:19:11', 0, 'Java basics', 1);

INSERT INTO public.question (id, version, correctanswer, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, duration, quiz_id) VALUES (1, 0, 3, 'Java n''est pas fait pour faire de la programmation fonctionnelle contrairement � Lisp, Haskell, Scala, etc...', 'interpr�t�', 'orient� objet', 'Java n''est pas un langage :', 0, 'compil�', 'fonctionnel', 20, 1);
INSERT INTO public.question (id, version, correctanswer, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, duration, quiz_id) VALUES (2, 0, 4, 'Si aucun constructeur n''est d�fini la JVM en cr��ra un par d�faut. Ensuite, on peut en cr�er autant que l''on souhaite', '1', '0 ou plusieurs', 'Combien de constructeur peut-on d�finir pour une classe ?', 1, '0 ou 1', '1 ou plusieurs', 30, 1);
INSERT INTO public.question (id, version, correctanswer, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, duration, quiz_id) VALUES (3, 0, 1, 'L''h�ritage multiple n''est pas support� en Java mais on peut impl�menter plusieurs interfaces', 'Une classe peut impl�menter plusieurs interfaces mais doit �tendre une seule classe ', 'Une classe doit impl�menter une seule interface et �tendre une seule classe', 'Quelle proposition est la bonne ?', 2, 'Une classe peut impl�menter plusieurs classes mais doit �tendre une seule interface ', 'Une classe peut impl�menter plusieurs classes et peut �tendre plusieurs interfaces', 40, 1);
INSERT INTO public.question (id, version, correctanswer, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, duration, quiz_id) VALUES (4, 0, 2, 'Le garbage collector passe quand la heap memory est presque pleine', 'il peut engendrer une fuite m�moire', '', 'Lorsqu''un objet n''est plus r�f�renc� ...', 3, 'il peut d�truit ou non, c''est au bon vouloir de la JVM', 'il imm�diatement d�truit par la JVM', 30, 1);
