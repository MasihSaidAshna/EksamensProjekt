DROP DATABASE IF EXISTS project_management_tool;
CREATE DATABASE IF NOT EXISTS project_management_tool;
USE project_management_tool;

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
    time_estimate INT,
    uid INT NOT NULL,
    PRIMARY KEY (pid),
    FOREIGN KEY (uid) REFERENCES user(uid) ON DELETE CASCADE
);


CREATE TABLE module (
    mid INT NOT NULL AUTO_INCREMENT,
    module_name VARCHAR(255) NOT NULL,
    deadline date NOT NULL,
    time_estimate INT NOT NULL,
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
    ('Admin', 'root', 'Ad@Min.com', 'ADMIN');

INSERT INTO user (name, password, email, role) VALUES
    ('Batman', 'bat', 'Bat@Man.com', 'MANAGER');

INSERT INTO user (name, password, email, role) VALUES
    ('Mojo', 'jojo', 'Mo@Jo.com', 'EMPLOYEE');

INSERT INTO user (name, password, email, role) VALUES
    ('Joe', 'joe', 'J@J.com', 'EMPLOYEE');

INSERT INTO project (project_name, deadline, uid) VALUES
    ('Fun program', STR_TO_DATE('2023-08-06','%Y-%m-%d'), (SELECT uid FROM user WHERE name = 'Batman'));

INSERT INTO project (project_name, deadline, uid) VALUES
    ('Clever program', STR_TO_DATE('2024-06-10','%Y-%m-%d'), (SELECT uid FROM user WHERE name = 'Batman'));

INSERT INTO project (project_name, deadline, uid) VALUES
    ('Program', STR_TO_DATE('2023-07-10', '%Y-%m-%d'), (SELECT uid FROM user WHERE name = 'Admin'));

INSERT INTO module (module_name, deadline, time_estimate, set_status, pid, uid) VALUES
    ('Module random', STR_TO_DATE('2023-06-18', '%Y-%m-%d'), 4, 'DOING', (SELECT pid FROM project WHERE project_name = 'Fun program'), (SELECT uid FROM user WHERE name = 'Batman'));

INSERT INTO module (module_name, deadline, time_estimate, set_status, pid, uid) VALUES
    ('Module random 2', STR_TO_DATE('2023-06-30', '%Y-%m-%d'), 4, 'DOING', (SELECT pid FROM project WHERE project_name = 'Fun program'), (SELECT uid FROM user WHERE name = 'Batman'));

INSERT INTO module (module_name, deadline, time_estimate, set_status, pid, uid) VALUES
    ('Some module', STR_TO_DATE('2023-05-31', '%Y-%m-%d'), 3, 'TO_DO', (SELECT pid FROM project WHERE project_name = 'Clever program'), (SELECT uid FROM user WHERE name = 'Batman'));

INSERT INTO module (module_name, deadline, time_estimate, set_status, pid, uid) VALUES
    ('Some admin module', STR_TO_DATE('2023-06-30', '%Y-%m-%d'), 5, 'DOING', (SELECT pid FROM project WHERE project_name = 'Program'), (SELECT uid FROM user WHERE name = 'Admin'));

-- Updating project_creator values
SET SQL_SAFE_UPDATES = 0;
UPDATE project JOIN user SET project_creator = user.name WHERE project.uid = user.uid;

-- Updating project.time_estimate to take the sum of module.time_estimate
UPDATE project SET project.time_estimate = (SELECT SUM(module.time_estimate) FROM module WHERE module.pid = project.pid);



SET SQL_SAFE_UPDATES = 1;



