DROP DATABASE IF EXISTS productManagementToolDatabase;
CREATE DATABASE IF NOT EXISTS productManagementToolDatabase;
USE productManagementToolDatabase;

CREATE TABLE user (
    uid INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(50) NOT NULL,
    PRIMARY KEY (uid)
);


