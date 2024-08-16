CREATE DATABASE  IF NOT EXISTS `asset_craft`;
USE `asset_craft`;

 DROP TABLE IF EXISTS `review`;
DROP TABLE IF EXISTS `product_image`;
DROP TABLE IF EXISTS `product`;
        DROP TABLE IF EXISTS `user_image`;
    DROP TABLE IF EXISTS `user`;

CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `price` decimal(5, 2) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `rating` decimal(2, 1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `product` VALUES 
	(1,'Sci-Fi Weapons', 67.39,'3D', 'Great pack of modeled wapons', 4.5),
	(2,'Button Masher - Game Template', 120.50,'Templates', 'Ready and customizable game template', 3),
	(3,'Dragon Model', 210.60,'3D', 'Cool looking asset to use in your game', 1.5),
	(4,'Pixel Icon Pack', 30.00,'2D', 'Pack of icons', 4),
	(5,'Monster Sound Pack', 102.85,'Audio', 'Sounds of monsters that I keep in my basement', 5);
    
    


CREATE TABLE `product_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `path` varchar(45) DEFAULT NULL,
  `product_id` int NOT NULL,
  FOREIGN KEY (`product_id`) REFERENCES product(`id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `product_image` VALUES 
	(1,'/images/products/product-image1.jpg',1),
	(2,'/images/products/product-image2.jpg',2),
	(3,'/images/products/product-image3.jpg',3),
	(4,'/images/products/product-image4.jpg',4),
	(5,'/images/products/product-image5.jpg',5);
    
    
    


CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
	-- TODO: Make encripted passwords --
  `password` varchar(45) DEFAULT NULL,
	-- TODO: Email veryfication--
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `user` VALUES 
	(1,'Leslie','1234','leslie@outlook.com'),
	(2,'Emma','0000','emma@gmail.com'),
	(3,'Avani','9876','avani@outlook.com'),
	(4,'Yuri','1111','yuri@outlook.com'),
	(5,'Juan','2222','juan@gmail.com');
    
    


CREATE TABLE `user_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `path` varchar(45) DEFAULT NULL,
  `user_id` int NOT NULL,
  FOREIGN KEY (`user_id`) REFERENCES user(`id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `user_image` VALUES 
	(1,'/images/users/user-image1.jpg',1),
	(2,'/images/users/user-image2.jpg',2),
	(3,'/images/users/user-image3.jpg',3),
	(4,'/images/users/user-image4.jpg',4),
	(5,'/images/users/user-image5.jpg',5);
    
    


CREATE TABLE `review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comment` varchar(250) DEFAULT NULL,
  `rating` decimal(2, 1) DEFAULT NULL,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`id`),
  FOREIGN KEY (`product_id`) REFERENCES product(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `review` VALUES 
	(1,'Leslie',5,1,1),
	(2,'Emma',4.5,2,2),
	(3,'Avani',3,3,3),
	(4,'Yuri',1,4,4),
	(5,'Juan',4.5,5,5);
    
    
   
    
    
