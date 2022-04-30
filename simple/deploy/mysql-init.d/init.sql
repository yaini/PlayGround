CREATE USER 'simple'@'localhost' IDENTIFIED BY 'simple';
CREATE USER 'simple'@'%' IDENTIFIED BY 'simple';

GRANT ALL PRIVILEGES ON *.* TO 'simple'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'simple'@'%';

CREATE DATABASE SIMPLE;