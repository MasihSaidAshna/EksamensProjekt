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
    project_name VARCHAR(255) NOT NULL,
    project_creator VARCHAR(255),
    deadline date NOT NULL,
    uid INT NOT NULL,
    PRIMARY KEY (pid),
    FOREIGN KEY (uid) REFERENCES user(uid) ON DELETE CASCADE
);


CREATE TABLE module (
    mid INT NOT NULL AUTO_INCREMENT,
    module_name VARCHAR(255) NOT NULL,
    deadline date NOT NULL,
    set_status ENUM('TO_DO', 'DOING', 'DROPPED', 'DONE') NOT NULL,
    assign_user VARCHAR(255) default 'Unassigned',
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

INSERT INTO project (project_name, deadline, uid) VALUES
    ('Stupid program', STR_TO_DATE('2023-08-06','%Y-%m-%d'), (SELECT uid FROM user WHERE name = 'Batman'));

INSERT INTO project (project_name, deadline, uid) VALUES
    ('Clever program', STR_TO_DATE('2024-06-10','%Y-%m-%d'), (SELECT uid FROM user WHERE name = 'Batman'));

INSERT INTO project (project_name, deadline, uid) VALUES
    ('Program', STR_TO_DATE('2023-08-06', '%Y-%m-%d'), (SELECT uid FROM user WHERE name = 'Admin1'));

INSERT INTO module (module_name, deadline, set_status, pid, uid) VALUES
    ('Module random', STR_TO_DATE('2023-05-12', '%Y-%m-%d'), 'DOING', (SELECT pid FROM project WHERE project_name = 'Stupid program'), (SELECT uid FROM user WHERE name = 'Batman'));

INSERT INTO module (module_name, deadline, set_status, pid, uid) VALUES
    ('Some module', STR_TO_DATE('2023-05-31', '%Y-%m-%d'), 'TO_DO', (SELECT pid FROM project WHERE project_name = 'Clever program'), (SELECT uid FROM user WHERE name = 'Batman'));

INSERT INTO module (module_name, deadline, set_status, pid, uid) VALUES
    ('Some admin module', STR_TO_DATE('2023-06-30', '%Y-%m-%d'), 'DOING', (SELECT pid FROM project WHERE project_name = 'Program'), (SELECT uid FROM user WHERE name = 'Admin1'));

-- Updating project_creator values
UPDATE project JOIN user SET project_creator = user.name WHERE project.uid = user.uid;


