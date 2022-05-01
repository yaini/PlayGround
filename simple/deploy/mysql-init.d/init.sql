CREATE USER 'simple'@'localhost' IDENTIFIED BY 'simple';
CREATE USER 'simple'@'%' IDENTIFIED BY 'simple';

GRANT ALL PRIVILEGES ON *.* TO 'simple'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'simple'@'%';

CREATE DATABASE simple DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;