SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Datenbank: `usr_web580_1`
--
CREATE DATABASE `usr_web580_1` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `usr_web580_1`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `rebecca_user`
--
CREATE TABLE IF NOT EXISTS `rebecca_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `rights` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Daten für Tabelle `rebecca_user`
--
INSERT INTO `rebecca_user` (`id`, `username`, `password`, `rights`) VALUES
(1, 'bastian', 'c6a13823c41f53f008c7b4828b0c973f', 1),
(2, 'rebecca', 'e358bf645a205cf15efa983b5517d945', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `rebecca_vokabeln`
--
CREATE TABLE IF NOT EXISTS `rebecca_vokabeln` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deutsch` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `englisch` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `spanisch` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=257 ;

--
-- Daten für Tabelle `rebecca_vokabeln`
--
INSERT INTO `rebecca_vokabeln` (`id`, `deutsch`, `englisch`, `spanisch`) VALUES
(2, 'Tschüss! Auf Wiedersehen', 'bye', '¡Adiós!'),
(3, 'Ach!', '', '¡ah!'),
(4, 'naja', '', '¡bah!'),
(5, 'Guten Morgen! Guten Tag!', 'Good morning; good day', '¡Buenos días!'),
(6, 'he! Was? Wie?', 'What?', '¡eh!'),
(7, 'ich bin ganz kaputt, müde, fertig', '', '¡Estoy hecho/-a polvo'),
(8, 'hervorragend', 'brilliant', '¡Estupendo!'),
(9, 'furchtbar, fatal', 'horrible', '¡Fatal!'),
(10, 'fabelhaft, super!', 'fabulous', '¡Fenomenal!'),
(11, 'Bis nachher! Auf Wiedersehen!', 'Good bye!', '¡Hasta luego!'),
(12, 'Bis morgen', '', '¡Hasta mañana!'),
(13, 'Hallo!', 'Hello!', '¡Hola!'),
(14, 'schlecht', 'bad', '¡Mal!'),
(15, 'Schau mal, Sieh mal! (Befehlsform)', '', '¡Mirar!'),
(16, 'so lala, einigermaßen, weder….noch', '', '¡Ni du ni fa!'),
(17, 'Ich weiß nicht!', 'I don&#039;t know!', '¡No sé!'),
(18, 'Hör mal', '', '¡Oye!'),
(19, 'Wie schade!', '', '¡Qué pena!'),
(20, 'Ganz und gar nicht!', '', '¡Qué va?'),
(21, 'es geht', 'it&#039;s okay', '¡Regular!'),
(22, 'es geht, (so) einigermaßen', '', '¡Tirando!'),
(23, 'Woher...?', '', '¿ De dónde…?'),
(24, 'Wo...?', 'Where...?', '¿ Dónde…?'),
(25, 'Wie heißt du?', 'What&#039;s your name?', '¿Cómo te llamas?'),
(26, 'Wie?', 'How?', '¿Cómo?'),
(27, 'nicht wahr?', 'not sure?', '¿no?'),
(28, 'Was gibt’s?', '', '¿Qué hay?'),
(29, 'Wie geht’s?', 'How are you?', '¿Qué tal?'),
(30, 'Was?', 'What?', '¿qué?'),
(31, 'Wer?', 'Who?', '¿quién?'),
(32, 'Stimmt&#039;s?', 'Right?', '¿Verdad?'),
(33, 'und dir?', 'And you?', '¿Y tú?'),
(34, 'Worin...?', '', 'A qué…'),
(35, 'manchmal', 'sometime', 'veces'),
(36, 'Mal sehen!', '', 'a ver'),
(38, 'öffnen', 'open', 'abrir'),
(39, 'Oma', 'grandma', 'abuela'),
(40, 'Opa', 'grandpa', 'abuelo'),
(41, 'jetzt', 'now', 'ahora'),
(42, 'etwas', 'something', 'algo'),
(43, 'dort', 'there', 'allí'),
(44, 'Freund/in', 'boy-, girlfriend', 'amigo/a'),
(45, 'lernen', 'learn', 'aprender'),
(46, 'hier', 'here', 'aquí'),
(48, 'tanzen', 'dance', 'bailar'),
(49, 'ziemlich viel, genug', 'quite allot, enough', 'bastante'),
(50, 'trinken', 'drink', 'beber'),
(51, 'gut', 'good', 'bien'),
(52, 'belegtes Brötchen', 'sandwitch', 'bocadillo'),
(54, 'singen', 'sing', 'cantar'),
(55, 'Haus', 'house', 'casa'),
(56, 'in der Nähe von', 'in the near of', 'cerca'),
(57, 'plaudern', '', 'charlar'),
(58, 'Junge/Mädchen', 'boy/girl', 'chico/a'),
(59, 'kochen', 'cooking', 'cocinar'),
(60, 'essen', 'eat', 'comer'),
(61, 'ausführen', 'execute', 'complir'),
(62, 'kaufen', 'buy', 'comprar'),
(63, 'einkaufen', 'shopping', 'comprar'),
(64, 'verstehen', 'understand', 'comprender'),
(65, 'mit', 'with', 'con'),
(66, 'mit allen', '', 'con todo el mundo'),
(67, 'rennen, laufen', 'run, walk', 'correr'),
(69, 'von, aus', '', 'de'),
(70, 'müssen', 'must/have to', 'deber'),
(71, 'sich erholen', '', 'descansar'),
(72, 'nach, danach (zeitlich)', '', 'después'),
(73, 'schwer, schwierig', 'difficult', 'difícil'),
(75, 'der', 'the', 'el'),
(76, 'das Konzert', 'concert', 'el concierto'),
(77, 'Handy', 'mobile phone', 'el móvil'),
(78, 'das Dorf, das Volk', '', 'el pueblo'),
(79, 'die Weile', '', 'el rato'),
(80, 'antreiben', '', 'empujar'),
(81, 'in, auf, an', 'in; about; on', 'en'),
(82, 'bei jemandem: zu Hause', '', 'en casa'),
(83, 'dann, also', '', 'entonces'),
(84, 'er/sie ist', 'he/she is', 'es'),
(85, 'schreiben', 'write', 'escribir'),
(86, '(zu) hören', 'listen (to)', 'escuchar'),
(87, 'hören, (zu)hören', '', 'escuchar'),
(88, 'dieses hier, das hier', '', 'esto, ese'),
(89, 'lernen', 'learn', 'estudiar'),
(91, 'Party', 'party', 'fiesta'),
(92, 'rauchen', 'smoking', 'fumar'),
(94, 'Brille', 'glasses', 'gafas'),
(96, 'sprechen', 'talk', 'hablar'),
(97, 'bis', 'until', 'hasta'),
(98, 'es gibt, es ist, es sind', '', 'hay (=haber)'),
(100, 'Bruder/Schwester', 'brother/sister', 'hermano/a'),
(101, 'heute', 'today', 'hoy'),
(102, 'Gymnasium', '', 'instituto'),
(103, 'interessant', 'interesting', 'interesante'),
(105, 'die', 'the', 'la'),
(106, 'der Brief', 'the letter', 'la carta'),
(107, 'das Fest, die Party', 'party', 'la fiesta'),
(108, 'das Wort', 'the word', 'la palabra'),
(109, 'der Strand', 'the beach', 'la playa'),
(110, 'der Platz', 'the place', 'la plaza'),
(111, 'das Netz, Internet', 'the internet', 'la red, las redes'),
(112, 'lesen', 'read', 'leer'),
(113, 'heißen', 'call', 'llamar'),
(114, '(an)kommen', 'come (arrive)', 'llegar'),
(115, 'tragen', 'carry', 'llevar'),
(116, 'die', 'the', 'los/las'),
(117, 'nachher, später', 'later', 'luego'),
(119, 'Mutter', 'mum', 'madre'),
(120, 'morgen', 'tomorrow', 'mañana'),
(121, 'ich heiße', 'my name is', 'me llamo'),
(122, 'sehen', 'see', 'mirar'),
(123, 'etwas (an)schauen, (an)sehen', '', 'mirar'),
(124, 'Musik', 'music', 'música'),
(125, 'sehr', 'much', 'muy'),
(127, 'schwimmen', 'swim', 'nadar'),
(128, 'Nein', 'no', 'no'),
(129, 'nicht nur', 'not only', 'no sólo'),
(131, 'Büro', 'office', 'oficina'),
(132, 'befehlen, aufräumen', '', 'ordenar'),
(134, 'Vater', 'dad', 'padre'),
(135, 'aber', 'but', 'pero'),
(136, 'malen', 'draw', 'pintar'),
(137, 'Frage', 'question', 'pregunta'),
(138, 'fragen', 'ask', 'preguntar'),
(139, 'Lehrer/Lehrerin', 'teacher', 'professor/a'),
(140, 'na, also', '', 'pues'),
(142, 'bekommen', 'get', 'recibir'),
(144, 'springen', 'jump', 'saltar'),
(145, 'sein', '(to) be', 'ser'),
(146, 'Ja', 'yes', 'sí'),
(147, 'immer', 'allways', 'siempre'),
(148, 'nur', 'only', 'sólo'),
(149, 'sie sind', 'they are', 'son'),
(150, 'ansteigen, aufsteigen', 'rise, fall', 'subir'),
(152, 'auch', 'too', 'también'),
(153, 'spät', 'late', 'tarde'),
(154, 'du heißt', 'call you', 'te llamas'),
(155, 'Onkel/Tante', '', 'tío/a'),
(156, 'noch, immer noch', '', 'todavía'),
(157, 'jedermann, alle', 'everybody, everyone', 'todo el mundo'),
(158, 'nehmen (einen Saft trinken= nehmen)', 'take', 'tomar'),
(159, 'arbeiten', 'work', 'trabajar'),
(160, 'du', 'you', 'tú'),
(161, 'dein Bruder', 'your brother', 'tu hermano'),
(163, 'etwas', 'something', 'un poco'),
(164, 'ein/e', '', 'un/una'),
(166, 'OK, einverstanden', 'okay', 'vale'),
(167, 'verkaufen', 'sell', 'vender'),
(168, 'etwa: Ich sehe was, was du nicht siehst.', '', 'veo, veo'),
(169, 'etwas sehen', '', 'ver'),
(170, 'leben, wohnen', 'live', 'vivir'),
(171, 'und', 'and', 'y'),
(173, 'schon', '', 'bonita'),
(174, 'ich', 'I', 'yo'),
(175, 'ich bin', 'I am, I&#039;m', 'yo soy'),
(181, 'das', 'the', ''),
(182, 'Warum?', 'Why?', '¿Por qué?'),
(183, 'Welche?', 'Wich?', '¿cuál?'),
(184, 'Kannst?', '', '¿Puede(s)?'),
(185, 'Wie viel?', 'How much? How many?', '¿Cuánto/a?'),
(186, 'Wie alt bist du?', 'How old are you?', '¿Cuántos años tiene?'),
(187, 'Ich glaube...', 'I belive...', 'Creo que...'),
(188, 'logisch', 'logic', 'lógico'),
(189, 'Heft', '', 'el cuanderno'),
(190, 'Taschenrechner', 'calculator', 'la calculadora'),
(191, 'Schere', '', 'las tijeras'),
(192, '(Geo)Dreieck', '', 'la regla'),
(193, 'Bleistift', 'pencil', 'el lápiz'),
(194, 'Kugelschreiber', '', 'el poligrafo'),
(195, 'Radiergummi', 'rubber', 'la goma'),
(196, 'Tintenkiller', '', 'el bolígrafo'),
(197, 'Eltern', 'parents', 'padres'),
(198, 'Nachname', 'lastname', 'el apellido'),
(199, 'Enkelin', '', 'nieta'),
(200, 'Enkel', '', 'nieto'),
(201, 'Tochter', 'doughter', 'hija'),
(202, 'Sohn', 'son', 'hijo'),
(203, 'der Neffe', '', 'el sobrino'),
(204, 'die Nichte', '', 'la sobrina'),
(205, 'Meldung', 'message', 'mensaje'),
(206, 'älter', 'older', 'mayores'),
(207, 'kleiner', 'smaller', 'pequeños'),
(208, 'neugierig', '', 'curiosos'),
(209, 'Stadtviertel', '', 'barrio'),
(210, 'Stadt', 'city', 'ciudad'),
(211, 'ruhig', '', 'tranquilo'),
(212, 'mir gefällt', '', 'me gunta'),
(213, 'Auszeichnung', 'award', ''),
(214, 'Gemeinschaft', 'community', ''),
(215, 'sich für etwas halten', 'to fancy onself as somthing', ''),
(216, 'mit Übernachtung', 'rasidential', ''),
(217, 'den Sprung wagen', 'to take the plunge', ''),
(218, 'etwas zu nichte machen', 'to wipe out', ''),
(219, 'etwas nützen, zugute kommen', 'to benefit somthing', ''),
(220, 'gesund, tauglich, nicht behindert', 'able-bodied', ''),
(221, 'unabhängig von', 'regardless of', ''),
(222, 'der/die Richtige sein', 'to fit the bill', ''),
(223, 'gegeneinander kämpfen', 'to compete', ''),
(224, 'besagen, hinweisen auf', 'to indicate', ''),
(225, 'die These vertreten', 'to make the case', ''),
(226, 'Leistung: Leistung (schulisch)', 'preformance, achievement', ''),
(227, 'Kreuzzug', 'crusade', ''),
(228, 'umfassend', 'comprehensive', ''),
(229, 'bis heute', 'to date', ''),
(230, 'Studie, Untersuchung', 'resarch', ''),
(231, 'Schlleiter', 'hadmaster, principal', ''),
(232, 'eindämmen', 'to curb', ''),
(233, 'gängig, verbreitet', 'prevalent', ''),
(234, 'Widerstand', 'resistance', ''),
(235, 'es wird von jedem erwartet', 'to be required to', ''),
(236, 'gestallten, festlegen', 'to shape', ''),
(237, 'übermäßig', 'overly', ''),
(238, 'ausgebeult, herabhängend', 'baggy', ''),
(239, 'Kopftuch', 'scarf', ''),
(240, 'Nietenhalsband', 'dog collar', ''),
(241, 'Piercing', 'stud', ''),
(242, 'angeblich', 'supposedly', ''),
(243, 'Ruf', 'reputation', ''),
(244, 'Kleidungsstück', 'garment', ''),
(245, 'Ausdruck eines Modischen Stiels, Statement', 'fashion statement', ''),
(246, 'Ladendiebstahl', 'shoplifting', ''),
(247, 'nichts Gutes im Schilde führen', 'to be up to no good', ''),
(248, 'beleidigen', 'to insult', ''),
(249, 'umklammern', 'to clutch', ''),
(250, 'Ziel', 'target', ''),
(251, 'Annahme', 'assumption', ''),
(252, 'provezieren', 'to provoke', ''),
(253, 'verhöhnen', 'to taunt', ''),
(254, 'einschüchtern', 'to intimidate', ''),
(255, 'Teufelskreis', 'vicious circle', '');
