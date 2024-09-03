CREATE DATABASE  IF NOT EXISTS `asset_craft`;
USE `asset_craft`;

DROP TABLE IF EXISTS `review`;
DROP TABLE IF EXISTS `product_user`;
DROP TABLE IF EXISTS `product_image`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `user_image`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `price` decimal(5, 2) DEFAULT NULL,
  -- TODO: (LONG) (HARD) (stashed as stash@{0}) change to ENUM
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
	(5,'Monster Sound Pack', 102.85,'Audio', 'Sounds of monsters that I keep in my basement', 5),
    (6, 'Fantasy Sword', 120.00, '3D', 'A sharp sword from the fantasy world', 4.8),
	(7, 'Modern UI Pack', 45.50, 'Templates', 'Clean and modern UI templates', 4.2),
	(8, 'Epic Background Music', 99.99, 'Audio', 'Music for epic moments', 3.8),
	(9, 'Forest Environment', 180.00, '3D', 'Lush forest environment for games', 4.9),
	(10, 'Pixel Art Character', 15.75, '2D', 'Cute pixel art character sprite', 3.5),
	(11, 'Horror Sound Effects', 85.60, 'Audio', 'Terrifying sound effects', 4.7),
	(12, 'Space Adventure Pack', 199.00, '3D', 'Complete space adventure assets', 4.3),
	(13, 'Retro Game Template', 60.00, 'Templates', 'Retro-style game template', 3.6),
	(14, 'Alien Creature', 210.00, '3D', 'An alien creature model', 4.1),
	(15, 'Chiptune Music Pack', 50.00, 'Audio', '8-bit chiptune music pack', 4.4);
    


CREATE TABLE `product_image` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `path` varchar(45) DEFAULT NULL,
  `product_id` BIGINT NOT NULL,
  FOREIGN KEY (`product_id`) REFERENCES product(`id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `product_image` VALUES 
	(1,'/images/products/img.png',1),
	(2,'/images/products/product-image2.jpg',2),
	(3,'/images/products/product-image3.jpg',3),
	(4,'/images/products/product-image4.jpg',4),
	(5,'/images/products/product-image5.jpg',5),
    (6, '/images/products/product-image6.jpg', 6),
	(7, '/images/products/product-image7.jpg', 7),
	(8, '/images/products/product-image8.jpg', 8),
	(9, '/images/products/product-image9.jpg', 9),
	(10, '/images/products/product-image10.jpg', 10),
	(11, '/images/products/product-image11.jpg', 11),
	(12, '/images/products/product-image12.jpg', 12),
	(13, '/images/products/product-image13.jpg', 13),
	(14, '/images/products/product-image14.jpg', 14),
	(15, '/images/products/product-image15.jpg', 15),
    (16, '/images/products/imag.png',1);
    
    
    


CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
	-- TODO: Make encripted passwords --
  `password` varchar(45) DEFAULT NULL,
	-- TODO: Email veryfication--
  `email` varchar(45) DEFAULT NULL,
  `active` BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `user` VALUES 
	(1,'Leslie','{noop}1234','leslie@outlook.com', TRUE),
	(2,'Emma','{noop}0000','emma@gmail.com', TRUE),
	(3,'Avani','{noop}9876','avani@outlook.com', TRUE),
	(4,'Yuri','{noop}1111','yuri@outlook.com', TRUE),
	(5,'Juan','{noop}2222','juan@gmail.com', TRUE),
    (6, 'Sophia', '{noop}5678', 'sophia@example.com', TRUE),
	(7, 'Liam', '{noop}abcd', 'liam@example.com', TRUE),
	(8, 'Olivia', '{noop}efgh', 'olivia@example.com', TRUE),
	(9, 'Noah', '{noop}ijkl', 'noah@example.com', TRUE),
	(10, 'Ava', '{noop}mnop', 'ava@example.com', TRUE),
	(11, 'Mason', '{noop}qrst', 'mason@example.com', TRUE),
	(12, 'Isabella', '{noop}uvwx', 'isabella@example.com', TRUE),
	(13, 'James', '{noop}yz12', 'james@example.com', TRUE),
	(14, 'Mia', '{noop}3456', 'mia@example.com', TRUE),
	(15, 'Benjamin', '{noop}7890', 'benjamin@example.com', TRUE);
    
    


CREATE TABLE `user_image` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `path` varchar(45) DEFAULT NULL,
  `user_id` BIGINT NOT NULL,
  FOREIGN KEY (`user_id`) REFERENCES user(`id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `user_image` VALUES 
	(1,'/images/users/user-image1.jpg',1),
	(2,'/images/users/user-image2.jpg',2),
	(3,'/images/users/user-image3.jpg',3),
	(4,'/images/users/user-image4.jpg',4),
	(5,'/images/users/user-image5.jpg',5),
    (6, '/images/users/user-image6.jpg', 6),
	(7, '/images/users/user-image7.jpg', 7),
	(8, '/images/users/user-image8.jpg', 8),
	(9, '/images/users/user-image9.jpg', 9),
	(10, '/images/users/user-image10.jpg', 10),
	(11, '/images/users/user-image11.jpg', 11),
	(12, '/images/users/user-image12.jpg', 12),
	(13, '/images/users/user-image13.jpg', 13),
	(14, '/images/users/user-image14.jpg', 14),
	(15, '/images/users/user-image15.jpg', 15);
    
    
	CREATE TABLE `role` (
    `user_id` BIGINT NOT NULL,
    `role_name` varchar(50) NOT NULL,
    UNIQUE KEY `autorieties5_idx_1` (`user_id`, `role_name`),
    CONSTRAINT `autorieties5_idx_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1;
    
    -- TODO: Populate role
    INSERT INTO `role` VALUES
    (1, 'USER');
    
    
CREATE TABLE `product_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `product_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `status` ENUM('OWNER', 'BUYER','WHISHLIST','CART') NOT NULL,
  FOREIGN KEY (`product_id`) REFERENCES product(`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `product_user` VALUES 
	(1, 1, 1, 'OWNER'),
	(2, 2, 2, 'OWNER'),
    (3, 3, 3, 'OWNER'),
    (4, 4, 4, 'OWNER'),
    (5, 5, 5, 'OWNER'),
    (6, 6, 6, 'OWNER'),
	(7, 7, 7, 'OWNER'),
	(8, 8, 8, 'OWNER'),
	(9, 9, 9, 'OWNER'),
	(10, 10, 10, 'OWNER'),
	(11, 11, 11, 'OWNER'),
	(12, 12, 12, 'OWNER'),
	(13, 13, 13, 'OWNER'),
	(14, 14, 14, 'OWNER'),
	(15, 15, 15, 'OWNER'),
    (16, 1, 2, 'CART'),
	(17, 1, 3, 'WHISHLIST'),
	(18, 2, 4, 'BUYER'),
	(19, 2, 5, 'CART'),
	(20, 3, 6, 'WHISHLIST'),
	(21, 3, 7, 'BUYER'),
	(22, 4, 8, 'CART'),
	(23, 4, 9, 'WHISHLIST'),
	(24, 5, 10, 'BUYER'),
	(25, 5, 11, 'CART'),
	(26, 6, 12, 'WHISHLIST'),
	(27, 6, 13, 'BUYER'),
	(28, 7, 14, 'WHISHLIST'),
	(29, 8, 15, 'WHISHLIST'),
	(30, 9, 1, 'CART'),
    (31, 2, 1, 'BUYER');


CREATE TABLE `review` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `comment` varchar(250) DEFAULT NULL,
  `rating` decimal(2, 1) DEFAULT NULL,
  `user_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`id`),
  FOREIGN KEY (`product_id`) REFERENCES product(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `review` VALUES 
	(1, 'Excellent quality and highly detailed!', 5, 1, 1),
	(2, 'Good value for the price, but could use more features.', 4.5, 2, 2),
	(3, 'Decent, but not exactly what I was looking for.', 3, 3, 3),
	(4, 'Terrible experience, not recommended.', 1, 4, 4),
	(5, 'Very satisfied with the purchase, will buy again!', 4.5, 5, 5),
	(6, 'Amazing asset, worked perfectly in my project.', 5, 6, 6),
	(7, 'The design is great, but the performance could be better.', 3.5, 7, 7),
	(8, 'Not bad, but it didn’t meet my expectations.', 3, 8, 8),
	(9, 'Fantastic! Exactly what I needed.', 5, 9, 9),
	(10, 'Disappointed with the quality, not worth the money.', 2.5, 10, 10),
	(11, 'Pretty good, but some elements are lacking detail.', 3.8, 11, 11),
	(12, 'The sounds are really immersive, great for my game!', 4.7, 12, 12),
	(13, 'Okay product, but could use some improvements.', 3.6, 13, 13),
	(14, 'Exceeded my expectations, would recommend!', 4.3, 14, 14),
	(15, 'Solid asset, very useful for my project.', 4.4, 15, 15);
    
    
   
    
    
