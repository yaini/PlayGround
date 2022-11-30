CREATE USER 'batch'@'localhost' IDENTIFIED BY 'batch';
CREATE USER 'batch'@'%' IDENTIFIED BY 'batch';

GRANT ALL PRIVILEGES ON *.* TO 'batch'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'batch'@'%';
