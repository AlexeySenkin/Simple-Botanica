
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
                         `role_id` int NOT NULL AUTO_INCREMENT,
                         `role_name` varchar(45) NOT NULL,
                         `is_active` tinyint(1) DEFAULT '1',
                         PRIMARY KEY (`role_id`),
                         UNIQUE KEY `UNIQUE` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `user_id` int NOT NULL AUTO_INCREMENT,
                         `user_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `user_password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `is_banned` tinyint(1) NOT NULL DEFAULT '0',
                         `is_active` tinyint(1) NOT NULL DEFAULT '1',
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `users_UN` (`user_name`,`user_password`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE`users_roles` (
                             user_id    bigint not null references users (user_id),
                             role_id    bigint not null references roles (role_id),
                             primary key (user_id, role_id)
#                              KEY `fk_user_role_user` (`user_id`),
#                              CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
#                              KEY `fk_user_role_role` (`role_id`),
#                              CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `users` VALUES (1,'Bigcat82','$2y$10$5SfrazcWo8d0ZTyyiae7ceubFKbG3je9WaGdMVzgxJF.th7tBeK2G',0,1);
INSERT INTO `users` VALUES (2,'Alexsenkin','$2y$10$5SfrazcWo8d0ZTyyiae7ceubFKbG3je9WaGdMVzgxJF.th7tBeK2G',0,1);
INSERT INTO `users` VALUES (3,'Artmuz42','$2y$10$5SfrazcWo8d0ZTyyiae7ceubFKbG3je9WaGdMVzgxJF.th7tBeK2G',0,1);
INSERT INTO `users` VALUES (4,'Vitavtasbuzas','$2y$10$5SfrazcWo8d0ZTyyiae7ceubFKbG3je9WaGdMVzgxJF.th7tBeK2G',0,1);


INSERT INTO `roles` VALUES (1,'ROLE_ADMIN',1);
INSERT INTO `roles` VALUES (2,'ROLE_USER',1);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1), (2, 1), (3, 1), (4, 1);