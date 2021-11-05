Create table project(id INT PRIMARY KEY AUTO_INCREMENT , created_date DATE NULL, name varchar(255) NULL , status boolean NOT NULL, updated_date date NULL);

Create table version(id INT PRIMARY KEY AUTO_INCREMENT, created_date DATE NULL, deploy_date DATE NULL, description VARCHAR(255) NULL , gmud VARCHAR(255) NULL, `order` INT NULL, status BOOLEAN NULL, updated_date DATE NULL, version_clone_id INT NULL, version_number VARCHAR(255) NULL, project_id INT NULL, CONSTRAINT FOREIGN KEY(project_id) REFERENCES project(id));

Create table screen (id INT PRIMARY KEY AUTO_INCREMENT, active BOOLEAN NOT NULL, created_date DATE NULL, image VARCHAR(255) NULL, name VARCHAR(255) NULL, `order` INT NULL, updated_date DATE NULL, urlog VARCHAR(255) NULL, clone_version_id INT NULL , screen_father_id INT NULL , version_id INT NULL, CONSTRAINT FOREIGN KEY(clone_version_id) REFERENCES  version(id), CONSTRAINT FOREIGN KEY(screen_father_id) REFERENCES screen(id), CONSTRAINT FOREIGN KEY(version_id) REFERENCES version(id));

Create table event_type(id INT PRIMARY KEY AUTO_INCREMENT, created_date DATE NULL, name VARCHAR(255) NULL, `order` INT NULL, status BOOLEAN NOT NULL, updated_date DATE NULL);

Create table  event(id INT PRIMARY KEY AUTO_INCREMENT, active BOOLEAN NOT NULL, created_date DATE NULL, `order` INT NOT NULL, parameter VARCHAR(255) NULL, updated_date DATE NULL, event_type_id INT NULL, screen_id INT NULL, CONSTRAINT FOREIGN KEY(event_type_id) REFERENCES event_type(id), CONSTRAINT FOREIGN KEY(screen_id) REFERENCES screen(id));

Create table request(id INT PRIMARY KEY AUTO_INCREMENT, created_date DATE NULL, description VARCHAR(255) NULL, layer VARCHAR(255) NULL, `order` INT NULL, status BOOLEAN NOT NULL, updated_date DATE NULL, uri_homolog VARCHAR(255) NULL, uri_prod VARCHAR(255) NULL, event_id INT NULL, request_father_id INT NULL, CONSTRAINT FOREIGN KEY(event_id) REFERENCES event(id), CONSTRAINT FOREIGN KEY(request_father_id) REFERENCES request(id));

Create table request_property(id INT PRIMARY KEY AUTO_INCREMENT, created_date DATE NULL, key VARCHAR(255) NULL, `order` VARCHAR(255) NULL, updated_date DATE NULL, value INT NULL, request_id INT NULL, CONSTRAINT FOREIGN KEY(request_id) REFERENCES request(id));

Create table USER (ID BIGINT INT PRIMARY KEY AUTO_INCREMENT, EMAIL VARCHAR(255) NULL, NAME VARCHAR(255) NULL, PASS VARCHAR(255) NULL);