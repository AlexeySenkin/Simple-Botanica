
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
                         `role_id` int NOT NULL AUTO_INCREMENT,
                         `role_name` varchar(45) NOT NULL,
                         `is_active` tinyint(1) DEFAULT '1',
                         PRIMARY KEY (`role_id`),
                         UNIQUE KEY `UNIQUE` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



LOCK TABLES `roles` WRITE;
INSERT INTO `roles` VALUES (1,'administrator',1);
INSERT INTO `roles` VALUES (2,'user',1);
UNLOCK TABLES;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `user_id` int NOT NULL AUTO_INCREMENT,
                         `user_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `user_password` char(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `is_banned` tinyint(1) NOT NULL DEFAULT '0',
                         `is_active` tinyint(1) NOT NULL DEFAULT '1',
                         `role_id` int NOT NULL,
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `users_UN` (`user_name`,`user_password`),
                         KEY `fk_user_role` (`role_id`),
                         CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES (1,'Bigcat82','root',0,1,1);
INSERT INTO `users` VALUES (2,'Alexsenkin','root',0,1,1);
INSERT INTO `users` VALUES (3,'Artmuz42','root',0,1,1);
INSERT INTO `users` VALUES (4,'Vitavtasbuzas','root',0,1,1);
UNLOCK TABLES;


