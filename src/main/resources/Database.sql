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


CREATE TABLE project (
    pid INT NOT NULL AUTO_INCREMENT,
    projectName VARCHAR(255) NOT NULL,
    uid INT NOT NULL,
    PRIMARY KEY (pid),
    FOREIGN KEY (uid) REFERENCES user(uid)
);


CREATE TABLE module (
    mid INT NOT NULL AUTO_INCREMENT,
    moduleName VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL,
    pid INT NOT NULL,
    PRIMARY KEY (mid),
    FOREIGN KEY (pid) REFERENCES project(pid)
);
