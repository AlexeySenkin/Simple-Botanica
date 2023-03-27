DROP TABLE IF EXISTS `care`;
CREATE TABLE `care` (
                        `care_id` int NOT NULL AUTO_INCREMENT,
                        `care_name` varchar(128) NOT NULL,
                        `is_active` tinyint(1) NOT NULL DEFAULT '1',
                        PRIMARY KEY (`care_id`),
                        UNIQUE KEY `care_id_UNIQUE` (`care_id`),
                        UNIQUE KEY `care_name_UNIQUE` (`care_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `care` WRITE;
INSERT INTO `care` VALUES (1,'полив',1);
INSERT INTO `care` VALUES (2,'внесение удобрений',1);
UNLOCK TABLES;


DROP TABLE IF EXISTS `plants`;
CREATE TABLE `plants` (
                          `plant_id` int NOT NULL AUTO_INCREMENT,
                          `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `family` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `genus` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `short_description` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `is_active` tinyint(1) NOT NULL DEFAULT '1',
                          PRIMARY KEY (`plant_id`),
                          UNIQUE KEY `plants_id_UNIQUE` (`plant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `plants` WRITE;
INSERT INTO `plants` VALUES (1,'Алоказия','Ароидные','Алоказия','Представители рода Алоказия — растения высотой от 40 см до двух метров и более, от небольшого до среднего размера, изредка древовидные и гигантские, вечнозелёные, изредка с опадающими на зиму листьями, выделяют прозрачный млечный сок.Стебель толстый, обычно вертикальный, изредка удлинённый и ползучий.Корневище толстое, короткое, часто клубневидное или столонообразное.','Растения высотой от 40 см до двух метров и более',1);
INSERT INTO `plants` VALUES (2,'Филодендрон','Ароидные','Филодендрон','Филодендроны — вечнозелёные многолетние растения. Отличаются от растений других родов семейства Ароидные разнообразием жизненных форм. Среди них есть эпифиты и полуэпифиты, или гемиэпифиты, хотя гемиэпифитов среди филодендронов очень немного. Преимущественно филодендроны эпифиты, то есть лазящие растения, прикрепляющиеся к опоре с помощью длинных воздушных корней-присосок. Некоторые виды могут совмещать в себе несколько жизненных форм, проявляя их в зависимости от условий произрастания. Филодендроны-гемиэпифиты могут быть двух типов: первичные и вторичные. Первичные гемиэпифиты начинают свой жизненный путь в пологе леса, где прорастает попавшее туда семя. Как только растение достигает достаточного размера и возраста, чтобы производить воздушные корни, оно прикрепляется ими к почве. После этого первичный гемиэпифит получает питательные вещества непосредственно из почвы. Вторичные гемиэпифиты начинают свой жизненный путь на поверхности почвы или на стволе дерева непосредственно у поверхности земли. У этих гемиэпифитов есть свои корни в начальный период их жизни, затем они постепенно поднимаются вверх по дереву, дают воздушные корни и теряют свои подземные корни, становясь эпифитами. Вторичный гемиэпифит не всегда прорастает вблизи дерева. Такой гемиэпифит будет расти вдоль поверхности земли, развивая длинные междоузлия, пока подходящее дерево не будет найдено. При этом ориентиром гемиэпифиту служит тень, отбрасываемая деревом. Это свойство называется скототропизмом. Когда дерево найдено, скототропик становится фототропиком, его междоузлия становятся короче и толще. Однако чаще всего семена филодендронов прорастают на деревьях.','Филодендроны — вечнозелёные многолетние растения.',1);
INSERT INTO `plants` VALUES (3,'Сциндапсус','Ароидные','Сциндапсус','Высокорослые, многолетние, травянистые, вечнозелёные лазящие лианы. Листья простые, овальные, без дырок, кожистые, очерёдные, тёмно-зелёные, в беловато-жёлтых точках. Соцветие — початок. Цветки обоеполые.','Высокорослые, многолетние, травянистые, вечнозелёные лазящие лианы.',1);
INSERT INTO `plants` VALUES (4,'Спатифиллюм','Ароидные','Спатифиллюм','Многолетние вечнозелёные растения. В основном наземные, но среди них встречаются эпифиты и хемиэпифиты. Корневище короткое. Стебель отсутствует — прикорневые листья образуют пучок прямо из почвы. Листья от овальных до ланцетовидных, цельные, с отчётливо различимой средней жилкой и тонкими параллельно идущими боковыми. Черешок удлинённый, снабжён влагалищем до середины длины или до основания листовой пластинки, с раздутыми сосудиками у основания листа. Цветоножка равна или превышает длину черешка. Соцветие — початок с покрывалом у основания. Покрывало продолговато-эллиптическое, длиннее початка, от зелёного до белого, не опадающее. Белое покрывало после отцветания быстро зеленеет. Початок от сидячего до снабжённого ножкой. Цветки обоеполые, заключены в два круга сросшихся лепестков околоцветника; тычинок 6; завязь трёхгнёздная с 1—7 семяпочками в каждом гнезде. Семена изогнутые, гладкие.','Многолетние вечнозелёные растения.',1);
UNLOCK TABLES;


DROP TABLE IF EXISTS `plant_care`;
CREATE TABLE `plant_care` (
                              `plant_care_id` int unsigned NOT NULL AUTO_INCREMENT,
                              `care_id` int NOT NULL,
                              `plant_id` int NOT NULL,
                              `care_count` int NOT NULL,
                              `care_volume` decimal(5,2) NOT NULL,
                              PRIMARY KEY (`plant_care_id`),
                              KEY `fk_plant_idx` (`plant_id`),
                              KEY `fk_care_idx` (`care_id`),
                              CONSTRAINT `fk_care` FOREIGN KEY (`care_id`) REFERENCES `care` (`care_id`),
                              CONSTRAINT `fk_plant` FOREIGN KEY (`plant_id`) REFERENCES `plants` (`plant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `plant_care` WRITE;
INSERT INTO `plant_care` VALUES (6,1,1,1,50.00);
INSERT INTO `plant_care` VALUES (7,2,1,2,50.00);
INSERT INTO `plant_care` VALUES (8,1,2,1,60.00);
INSERT INTO `plant_care` VALUES (9,2,2,2,60.00);
INSERT INTO `plant_care` VALUES (10,1,3,1,70.00);
INSERT INTO `plant_care` VALUES (11,2,3,2,70.00);
INSERT INTO `plant_care` VALUES (12,1,4,1,80.00);
INSERT INTO `plant_care` VALUES (13,2,4,2,80.00);
UNLOCK TABLES;



DROP TABLE IF EXISTS `plant_photos`;
CREATE TABLE `plant_photos` (
                                `plant_id` int NOT NULL,
                                `file_path` varchar(128) DEFAULT NULL,
                                PRIMARY KEY (`plant_id`),
                                UNIQUE KEY `file_path_UNIQUE` (`file_path`),
                                CONSTRAINT `plant_photos_FK` FOREIGN KEY (`plant_id`) REFERENCES `plants` (`plant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `plant_photos` WRITE;
INSERT INTO `plant_photos` VALUES (1,'Alocasia.jpg');
INSERT INTO `plant_photos` VALUES (2,'Philodendron.jpg');
INSERT INTO `plant_photos` VALUES (3,'Scindapsus.jpg');
INSERT INTO `plant_photos` VALUES (4,'Spatifilum.jpg');
UNLOCK TABLES;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `user_id` int NOT NULL AUTO_INCREMENT,
                         `first_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `last_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `email` varchar(45) NOT NULL,
                         `phone_number` varchar(45) NOT NULL,
                         `address` varchar(128) NOT NULL,
                         `reg_date` datetime NOT NULL,
                         `is_banned` tinyint(1) NOT NULL DEFAULT '0',
                         `is_active` tinyint(1) NOT NULL DEFAULT '1',
                         `user_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES (1,'Maria','Kuzmina','bigcat82@gmail.com','+79990000001','Ryazan','2026-03-20 23:00:00',0,1,'Bigcat82');
INSERT INTO `users` VALUES (2,'Alexey','Senkin','i@alexsenkin.ru','+79990000002','Ryazan','2026-03-20 23:00:00',0,1,'Alexsenkin');
INSERT INTO `users` VALUES (3,'Artur','Muzafarov','artmuz42@gmail.com','+79990000003','Ufa','2026-03-20 23:00:00',0,1,'Artmuz42');
INSERT INTO `users` VALUES (4,'Vitavtas','Buzas','vitavtasbuzas@gmail.com','+79990000004','Saint Petersburg','2026-03-20 23:00:00',0,1,'Vitavtasbuzas');
UNLOCK TABLES;



DROP TABLE IF EXISTS `user_plants`;
CREATE TABLE `user_plants` (
                               `user_plant_id` int NOT NULL AUTO_INCREMENT,
                               `user_id` int NOT NULL,
                               `plant_id` int NOT NULL,
                               `is_banned` tinyint(1) DEFAULT '0',
                               `is_active` tinyint(1) NOT NULL DEFAULT '1',
                               PRIMARY KEY (`user_plant_id`),
                               KEY `fk_user_plant` (`user_id`),
                               KEY `fk_plant_2` (`plant_id`),
                               CONSTRAINT `fk_plant_2` FOREIGN KEY (`plant_id`) REFERENCES `plants` (`plant_id`),
                               CONSTRAINT `fk_user_plant` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `user_plants` WRITE;
INSERT INTO `user_plants` VALUES (1,1,1,0,1);
INSERT INTO `user_plants` VALUES (2,2,2,0,1);
INSERT INTO `user_plants` VALUES (3,3,3,0,1);
INSERT INTO `user_plants` VALUES (4,4,4,0,1);
UNLOCK TABLES;


DROP TABLE IF EXISTS `user_care`;
CREATE TABLE `user_care` (
                             `user_care_id` int NOT NULL AUTO_INCREMENT,
                             `user_plant_id` int NOT NULL,
                             `event_date` datetime NOT NULL,
                             `care_id` int NOT NULL,
                             `care_volume` decimal(5,2) DEFAULT NULL,
                             `prim` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
                             PRIMARY KEY (`user_care_id`),
                             KEY `fk_user_care_user_plant` (`user_plant_id`),
                             KEY `fk_user_care_care` (`care_id`),
                             CONSTRAINT `fk_user_care_care` FOREIGN KEY (`care_id`) REFERENCES `care` (`care_id`),
                             CONSTRAINT `fk_user_care_user_plant` FOREIGN KEY (`user_plant_id`) REFERENCES `user_plants` (`user_plant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `user_care` WRITE;
INSERT INTO `user_care` VALUES (1,1,'2023-03-26 17:00:00',1,100.00,'');
INSERT INTO `user_care` VALUES (2,1,'2023-03-26 17:05:00',2,100.00,'');
INSERT INTO `user_care` VALUES (3,2,'2023-03-26 18:00:00',1,100.00,'');
INSERT INTO `user_care` VALUES (4,2,'2023-03-26 18:05:00',2,100.00,'');
INSERT INTO `user_care` VALUES (5,3,'2023-03-26 19:00:00',1,100.00,'');
INSERT INTO `user_care` VALUES (6,3,'2023-03-26 19:05:00',2,100.00,'');
INSERT INTO `user_care` VALUES (7,4,'2023-03-26 20:00:00',1,100.00,'');
INSERT INTO `user_care` VALUES (8,4,'2023-03-26 20:05:00',2,100.00,'');
UNLOCK TABLES;




DROP TABLE IF EXISTS `user_care_custom`;
CREATE TABLE `user_care_custom` (
                                    `user_care_custom_id` int NOT NULL AUTO_INCREMENT,
                                    `user_plant_id` int NOT NULL,
                                    `care_id` int NOT NULL,
                                    `user_care_count` int DEFAULT NULL,
                                    `user_care_volume` decimal(5,2) DEFAULT NULL,
                                    PRIMARY KEY (`user_care_custom_id`),
                                    UNIQUE KEY `UNIQUE` (`user_plant_id`,`care_id`),
                                    KEY `fk_user_care_custom_care` (`care_id`),
                                    CONSTRAINT `fk_user_care_custom_care` FOREIGN KEY (`care_id`) REFERENCES `care` (`care_id`),
                                    CONSTRAINT `fk_user_care_custom_user_plant` FOREIGN KEY (`user_plant_id`) REFERENCES `user_plants` (`user_plant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `user_care_custom` WRITE;
INSERT INTO `user_care_custom` VALUES (1,1,1,3,55.00);
INSERT INTO `user_care_custom` VALUES (2,1,2,3,55.00);
INSERT INTO `user_care_custom` VALUES (3,2,1,3,65.00);
INSERT INTO `user_care_custom` VALUES (4,2,2,3,65.00);
INSERT INTO `user_care_custom` VALUES (5,3,1,3,75.00);
INSERT INTO `user_care_custom` VALUES (6,3,2,3,75.00);
INSERT INTO `user_care_custom` VALUES (7,4,1,3,85.00);
INSERT INTO `user_care_custom` VALUES (8,4,2,3,85.00);
UNLOCK TABLES;

DROP TABLE IF EXISTS `user_photos`;
CREATE TABLE `user_photos` (
                               `user_id` int NOT NULL,
                               `file_path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                               PRIMARY KEY (`user_id`),
                               UNIQUE KEY `file_path_UNIQUE` (`file_path`),
                               CONSTRAINT `fk_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `user_photos` WRITE;
INSERT INTO `user_photos` VALUES (2,'Alexsenkin.png');
INSERT INTO `user_photos` VALUES (3,'Artmuz42.png');
INSERT INTO `user_photos` VALUES (1,'Bigcat82.png');
INSERT INTO `user_photos` VALUES (4,'Vitavtasbuzas.png');
UNLOCK TABLES;




