DROP TABLE IF EXISTS next_gen_usr;
CREATE TABLE next_gen_usr (
	user_id INT AUTO_INCREMENT  PRIMARY KEY,
	user_name VARCHAR(16) NOT NULL,
	pw_hash VARCHAR(64) NOT NULL,
	active BOOLEAN DEFAULT TRUE,
	PRIMARY KEY(user_id),
	CONSTRAINT unique_user_name UNIQUE(user_name)
);

DROP TABLE IF EXISTS User_profile;
CREATE TABLE User_profile (
	user_id INT  NOT NULL,
	first_name VARCHAR(16) NOT NULL,
	last_name VARCHAR(16) NOT NULL,
	birthdate DATETIME DEFAULT NOW(),
	email VARCHAR(34) NOT NULL,
	ssn VARCHAR(26),
	PRIMARY KEY(user_id),
	CONSTRAINT unique_email UNIQUE(email),
	FOREIGN KEY (user_id) REFERENCES next_gen_usr(user_id)
);


DROP TABLE IF EXISTS preference_key_lookup;
CREATE TABLE preference_key_lookup (
	preference_key VARCHAR(36) NOT NULL,
	default_value VARCHAR(256) NOT NULL,
	data_type VARCHAR(16) DEFAULT 'String',
	is_custom_value BOOLEAN DEFAULT false,
	description VARCHAR(256),
	PRIMARY KEY (preference_key),
	CONSTRAINT unique_pref_key UNIQUE(preference_key)
);

DROP TABLE IF EXISTS preference_value_lookup;
CREATE TABLE preference_value_lookup (
	preference_key VARCHAR(36) NOT NULL,
	preference_value VARCHAR NOT NULL DEFAULT '',
	data_type VARCHAR(16) DEFAULT 'String',
	description VARCHAR(256),
	PRIMARY KEY (preference_key, preference_value)
);

DROP TABLE IF EXISTS user_preference;
CREATE TABLE user_preference (
	user_id INT NOT NULL,
	preference_key VARCHAR(36) NOT NULL,
	preference_value VARCHAR(256),
	data_type VARCHAR(16) NOT NULL,
	is_custom_value BOOLEAN DEFAULT false,
	PRIMARY KEY(user_id, preference_key),
	FOREIGN KEY (preference_key) REFERENCES preference_key_lookup(preference_key)
);

DROP TABLE IF EXISTS security_role;
CREATE TABLE security_role (
	role_id INT AUTO_INCREMENT,
	role_name VARCHAR(32) NOT NULL,
	role_description VARCHAR(126),
	PRIMARY KEY(role_id),
	CONSTRAINT unique_role_name UNIQUE(role_name)
);

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
	role_id INT NOT NULL,
	user_id INT NOT NULL,
	PRIMARY KEY(role_id, user_id),
	FOREIGN KEY (role_id) REFERENCES security_role(role_id),
	FOREIGN KEY (user_id) REFERENCES next_gen_usr(user_id)
);

DROP TABLE IF EXISTS security_permissions;
CREATE TABLE security_permissions (
	permission_id INT AUTO_INCREMENT,
	permission_name VARCHAR(36) NOT NULL,
	permission_description VARCHAR(80),
	PRIMARY KEY (permission_id),
	CONSTRAINT unique_permission_name UNIQUE(permission_name)
);

DROP TABLE IF EXISTS role_permission;
CREATE TABLE role_permission (
	role_id INT NOT NULL,
	permission_id INT NOT NULL,
	PRIMARY KEY (role_id, permission_id),
	FOREIGN KEY (role_id) REFERENCES security_role(role_id),
	FOREIGN KEY (permission_id) REFERENCES security_permissions(permission_id)
);


DROP TABLE IF EXISTS application_history;
CREATE TABLE application_history (
	history_id INT AUTO_INCREMENT,
	table_name VARCHAR(60) NOT NULL,
	history_type VARCHAR(16) NOT NULL,
	change_type VARCHAR(10),
	old_value VARCHAR(120),
	new_value VARCHAR(120),
	history_date TIMESTAMP DEFAULT NOW(),
	by_user VARCHAR(16) NOT NULL,
	PRIMARY KEY(history_id),
	FOREIGN KEY (by_user) REFERENCES next_gen_usr(user_name)
);

DROP TABLE IF EXISTS task_status;
CREATE TABLE task_status (
	status_id INT AUTO_INCREMENT,
	status_name VARCHAR(16) NOT NULL,
	status_value INT,
	PRIMARY KEY (status_id)
);

DROP TABLE IF EXISTS task_priority;
CREATE TABLE task_priority (
	priority_id INT AUTO_INCREMENT,
	priority_name VARCHAR(16) NOT NULL,
	priority_value INT,
	PRIMARY KEY (priority_id)
);

DROP TABLE IF EXISTS task_list;
CREATE TABLE task_list (
	task_id INT AUTO_INCREMENT,
	status_id INT NOT NULL,
	task_name VARCHAR(80) NOT NULL,
	due_date DATETIME NOT NULL,
	created_by VARCHAR(16) NOT NULL,
	task_owner VARCHAR(16),
	date_completed DATETIME,
	priority INT DEFAULT 1,
	active BOOLEAN DEFAULT TRUE,
	PRIMARY KEY(task_id),
	FOREIGN KEY (status_id) REFERENCES task_status(status_id),
	FOREIGN KEY (priority) REFERENCES task_priority(priority_id)
);