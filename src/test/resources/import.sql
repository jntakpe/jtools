-- Table de paramétrage
INSERT INTO public.parameter (id, version, key, value) VALUES (0, 0, 'smtp.host', 'ptx.smtp.corp.sopra');
INSERT INTO public.parameter (id, version, key, value) VALUES (1, 0, 'smtp.port', '25');
INSERT INTO public.parameter (id, version, key, value) VALUES (2, 0, 'smtp.from', 'jtools@sopragroup.com');

-- Table des utilisateurs
INSERT INTO public.users (id, version, birthdate, email, firstname, lastaccess, lastname, login, password, phone, role) VALUES (3, 0, '1986-05-25 00:00:00', 'charles-eric.giraud@sopragroup.com', 'Charles-Eric', '1986-05-25 00:00:00', 'GIRAUD', 'Selrak', 'lolilol', '51525', 'ROLE_USER');
INSERT INTO public.users (id, version, birthdate, email, firstname, lastaccess, lastname, login, password, phone, role) VALUES (4, 0, '2013-04-30 23:51:09', 'natacha.berges@sopragroup.com', 'Natacha', '2013-04-30 23:51:09', 'BERGES', 'nberges', 'lolilol', '51333', 'ROLE_USER');
INSERT INTO public.users (id, version, birthdate, email, firstname, lastaccess, lastname, login, password, phone, role) VALUES (5, 0, '2013-04-30 23:51:09', 'arnaud.pradelles@sopragroup.com', 'Arnaud', '2013-04-30 23:51:09', 'PRADELLES', 'Piwi', 'lolilol', '51345', 'ROLE_USER');
INSERT INTO public.users (id, version, birthdate, email, firstname, lastaccess, lastname, login, password, phone, role) VALUES (1, 3, '1986-09-25 00:00:00', 'jocelyn.ntakpe@sopragroup.com', 'Jocelyn', '2013-05-23 21:42:00', 'N''TAKPE', 'jOSS', 'lolilol', '51394', 'ROLE_ADMIN');
INSERT INTO public.users (id, version, birthdate, email, firstname, lastaccess, lastname, login, password, phone, role) VALUES (2, 2, '1988-10-25 00:00:00', 'julien.guerrin@sopragroup.com', 'Julien', '2013-05-23 21:42:58', 'GUERRIN', 'JujuPiwi', 'lolilol', '51365', 'ROLE_USER');

-- Table des quiz
INSERT INTO public.quiz (id, version, createdate, execnumber, meanresult, title, creator_id) VALUES (1, 1, '2013-05-10 16:19:11', 1, 100, 'Java basics', 1);
INSERT INTO public.quiz (id, version, createdate, execnumber, meanresult, title, creator_id) VALUES (2, 2, '2013-05-23 21:37:25', 2, 83, 'Cinema basics', 2);
INSERT INTO public.quiz (id, version, createdate, execnumber, meanresult, title, creator_id) VALUES (3, 1, '2013-05-23 21:29:20', 1, 75, 'Foot Basics', 1);

-- Table de jointure entre un utilisateur et un quiz
INSERT INTO public.quiz_user (result, quiz_id, user_id) VALUES (100, 1, 1);
INSERT INTO public.quiz_user (result, quiz_id, user_id) VALUES (100, 2, 1);
INSERT INTO public.quiz_user (result, quiz_id, user_id) VALUES (66, 2, 2);
INSERT INTO public.quiz_user (result, quiz_id, user_id) VALUES (75, 3, 2);

-- Table des questions
INSERT INTO public.question (id, version, correctanswer, duration, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, quiz_id) VALUES (1, 0, 3, 20, 'Java n''est pas fait pour faire de la programmation fonctionnelle contrairement à  Lisp, Haskell, Scala, etc...', 'interprété', 'orienté objet', 'Java n''est pas un langage :', 0, 'compilé', 'fonctionnel', 1);
INSERT INTO public.question (id, version, correctanswer, duration, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, quiz_id) VALUES (2, 0, 4, 30, 'Si aucun constructeur n''est défini la JVM en crééra un par défaut. Ensuite, on peut en créer autant que l''on souhaite', '1', '0 ou plusieurs', 'Combien de constructeur peut-on définir pour une classe ?', 1, '0 ou 1', '1 ou plusieurs', 1);
INSERT INTO public.question (id, version, correctanswer, duration, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, quiz_id) VALUES (3, 0, 1, 40, 'L''héritage multiple n''est pas supporté en Java mais on peut implémenter plusieurs interfaces', 'Une classe peut implémenter plusieurs interfaces mais doit étendre une seule classe ', 'Une classe doit implémenter une seule interface et étendre une seule classe', 'Quelle proposition est la bonne ?', 2, 'Une classe peut implémenter plusieurs classes mais doit étendre une seule interface ', 'Une classe peut implémenter plusieurs classes et peut étendre plusieurs interfaces', 1);
INSERT INTO public.question (id, version, correctanswer, duration, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, quiz_id) VALUES (4, 0, 2, 30, 'Le garbage collector passe quand la heap memory est presque pleine', 'il peut engendrer une fuite mémoire', '', 'Lorsqu''un objet n''est plus référencé ...', 3, 'il peut détruit ou non, c''est au bon vouloir de la JVM', 'il immédiatement détruit par la JVM', 1);
INSERT INTO public.question (id, version, correctanswer, duration, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, quiz_id) VALUES (5, 0, 2, 20, '10 joueurs de champ + 1 goal', '10', '13', 'Combien de joueurs il y a t-il sur le terrain pour chaque équipe', 0, '11', '12', 3);
INSERT INTO public.question (id, version, correctanswer, duration, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, quiz_id) VALUES (6, 0, 3, 20, 'L''ancien nom était 1ère division', '1ère division', 'Pro ligue', 'Comment s''appelle le championnat de France de football', 1, 'Top14', 'Ligue 1', 3);
INSERT INTO public.question (id, version, correctanswer, duration, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, quiz_id) VALUES (7, 0, 2, 20, 'Avec l''argent qu''ils ont dépensé le contraire aurait été grave', 'Marseille', 'Monaco', 'Qui a gagné le championnat de France pour la saison 2012/2013', 2, 'Paris', 'Lyon', 3);
INSERT INTO public.question (id, version, correctanswer, duration, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, quiz_id) VALUES (8, 0, 4, 20, 'Question facile', 'Real Madrid', 'Barcelone', 'Quel est le meilleur club du monde', 3, 'PSG', 'Manchester United', 3);
INSERT INTO public.question (id, version, correctanswer, duration, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, quiz_id) VALUES (9, 0, 3, 20, 'James Cameron est le réalisateur de Titanic', 'ET', 'Minority Report', 'Quel film n''a réalisé Steven Spielberg', 0, 'Il faut sauver le soldat Ryan', 'Titanic', 2);
INSERT INTO public.question (id, version, correctanswer, duration, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, quiz_id) VALUES (10, 0, 2, 20, 'Will Smith a refusé le rôle de Django', 'Men in black', 'Ali', 'Dans lequel de ces films Will Smith n''a pas joué', 1, 'Django unchained', 'Independance day', 2);
INSERT INTO public.question (id, version, correctanswer, duration, explanation, firstanswer, fourthanswer, label, number, secondanswer, thirdanswer, quiz_id) VALUES (11, 0, 1, 20, 'Bah ouais', 'La guerre des mondes', '', 'Dans quel film Bruce Willis n''a pas sauvé le monde', 2, 'Le cinquième élément', 'Armageddon', 2);

-- Table de jointure entre une question et un utilisateur
INSERT INTO public.question_user (answer, question_id, user_id) VALUES (3, 1, 1);
INSERT INTO public.question_user (answer, question_id, user_id) VALUES (4, 2, 1);
INSERT INTO public.question_user (answer, question_id, user_id) VALUES (1, 3, 1);
INSERT INTO public.question_user (answer, question_id, user_id) VALUES (2, 4, 1);
INSERT INTO public.question_user (answer, question_id, user_id) VALUES (3, 9, 1);
INSERT INTO public.question_user (answer, question_id, user_id) VALUES (2, 9, 2);
INSERT INTO public.question_user (answer, question_id, user_id) VALUES (2, 10, 2);
INSERT INTO public.question_user (answer, question_id, user_id) VALUES (2, 5, 2);
INSERT INTO public.question_user (answer, question_id, user_id) VALUES (3, 6, 2);
INSERT INTO public.question_user (answer, question_id, user_id) VALUES (2, 7, 2);
INSERT INTO public.question_user (answer, question_id, user_id) VALUES (3, 8, 2);
