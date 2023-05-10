DROP DATABASE IF EXISTS productManagementToolDatabase;
CREATE DATABASE IF NOT EXISTS productManagementToolDatabase;
USE productManagementToolDatabase;

CREATE TABLE user (
    uid INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(50) NOT NULL,
    role ENUM('ADMIN', 'MANAGER', 'EMPLOYEE') NOT NULL,
    PRIMARY KEY (uid)
);


CREATE TABLE project (
    pid INT NOT NULL AUTO_INCREMENT,
    projectName VARCHAR(255) NOT NULL,
    deadline date NOT NULL,
    uid INT NOT NULL,
    PRIMARY KEY (pid),
    FOREIGN KEY (uid) REFERENCES user(uid) ON DELETE CASCADE
);


CREATE TABLE module (
    mid INT NOT NULL AUTO_INCREMENT,
    moduleName VARCHAR(255) NOT NULL,
    deadline date NOT NULL,
    setstatus ENUM('TO_DO', 'DOING', 'DROPPED', 'DONE') NOT NULL,
	pid INT NOT NULL,
    uid INT NOT NULL,
    PRIMARY KEY (mid),
    FOREIGN KEY (pid) REFERENCES project(pid) ON DELETE CASCADE,
    FOREIGN KEY (uid) REFERENCES user(uid) ON DELETE CASCADE
);


-- Add test data
INSERT INTO user (name, password, email, role) VALUES
    ('Admin1', 'root', 'Ad@Min.com', 'ADMIN');

INSERT INTO user (name, password, email, role) VALUES
    ('Batman', 'bat', 'Bat@Man.com', 'MANAGER');

INSERT INTO user (name, password, email, role) VALUES
    ('Mumu', 'popo', 'Mu@Mu.com', 'EMPLOYEE');

INSERT INTO project (projectName, deadline, uid) VALUES
    ('Stupid program', STR_TO_DATE('2023-08-06','%Y-%m-%d'), (SELECT uid FROM user WHERE name = 'Batman'));

INSERT INTO project (projectName, deadline, uid) VALUES
    ('Clever program', STR_TO_DATE('2024-06-10','%Y-%m-%d'), (SELECT uid FROM user WHERE name = 'Batman'));

INSERT INTO project (projectName, deadline, uid) VALUES
    ('Program', STR_TO_DATE('2023-08-06', '%Y-%m-%d'), (SELECT uid FROM user WHERE name = 'Admin1'));

INSERT INTO module (moduleName, deadline, setstatus, pid, uid) VALUES
    ('Module random', STR_TO_DATE('2023-05-12', '%Y-%m-%d'), 'DOING', (SELECT pid FROM project WHERE projectName = 'Stupid program'), (SELECT uid FROM user WHERE name = 'Batman'));

INSERT INTO module (moduleName, deadline, setstatus, pid, uid) VALUES
    ('Some module', STR_TO_DATE('2023-05-31', '%Y-%m-%d'), 'TO_DO', (SELECT pid FROM project WHERE projectName = 'Clever program'), (SELECT uid FROM user WHERE name = 'Batman'));



