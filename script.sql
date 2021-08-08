 CREATE TABLE IF NOT EXISTS `clients`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` int NULL DEFAULT NULL,
  `NAME` varchar(50),
  `lastName` varchar(50),
  `userId` varchar(50),
  `password` varchar(50),
  `phoneNumber` varchar(50),
  `email` varchar(50),
  `status` int NULL DEFAULT NULL,
  `statusClient` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
 );