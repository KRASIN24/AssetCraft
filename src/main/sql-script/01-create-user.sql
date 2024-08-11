-- Drop user first if they exist
DROP USER if exists 'admin'@'%' ;

-- Now create user with prop privileges
CREATE USER 'admin'@'%' IDENTIFIED BY 'e92dd2a18183';

GRANT ALL PRIVILEGES ON * . * TO 'admin'@'%';