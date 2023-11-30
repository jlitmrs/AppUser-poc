
INSERT INTO preference_value_lookup (preference_key, preference_value, data_type, description) VALUES ('default-theme', 'LIGHT', 'String', 'Light theme'),('default-theme', 'DARK', 'String', 'Dark theme'),('default-theme', 'UGLY', 'String', 'Ugly theme');
INSERT INTO preference_value_lookup (preference_key, preference_value, data_type) VALUES ('default-font', 'Arial', 'String'),('default-font', 'Verdana ', 'String'),('default-font', 'Times New Roman', 'String');
INSERT INTO preference_value_lookup (preference_key, preference_value, data_type) VALUES ('show-name', 'true', 'Boolean'),('show-name', 'false', 'Boolean');
INSERT INTO preference_value_lookup (preference_key, preference_value, data_type) VALUES ('show-id', 'true', 'Boolean'),('show-id', 'false', 'Boolean');
INSERT INTO preference_value_lookup (preference_key, preference_value, data_type) VALUES ('show-username', 'true', 'Boolean'),('show-username', 'false', 'Boolean');
INSERT INTO preference_value_lookup (preference_key, preference_value, data_type) VALUES ('default-font-size', '12', 'Integer'),('default-font-size', '14 ', 'Integer'),('default-font-size', '16', 'Integer');
INSERT INTO preference_value_lookup (preference_key, preference_value, data_type) VALUES ('application-width', '800px', 'String');


INSERT INTO preference_key_lookup (preference_key, default_value, data_type, description) VALUES ('default-theme', 'LIGHT', 'String', 'The default theme of the application.  default-value is LIGHT.');
INSERT INTO preference_key_lookup (preference_key, default_value, data_type, description) VALUES ('default-font', 'Arial', 'String', 'Default font for the application.  defaul-value is Arial');
INSERT INTO preference_key_lookup (preference_key, default_value, data_type, description) VALUES ('show-name', 'false', 'Boolean', 'Show name in emails and in the application. default-value is false.');
INSERT INTO preference_key_lookup (preference_key, default_value, data_type, description) VALUES ('show-id', 'false', 'Boolean', 'Show user id in emails and in the application. default-value is false.');
INSERT INTO preference_key_lookup (preference_key, default_value, data_type, description) VALUES ('show-username', 'true', 'Boolean', 'Show user name in emails and in the application. default-value is true.');
INSERT INTO preference_key_lookup (preference_key, default_value, data_type, description) VALUES ('default-font-size', '12', 'Integer', 'Default font size for the application.  defaul-value is 12');
INSERT INTO preference_key_lookup (preference_key, default_value, data_type, is_custom_value, description) VALUES ('application-width', '800px', 'Integer', 'true', 'Default application width for the application.  defaul-value is 800px');


INSERT INTO app_usr (user_name, pw_hash) VALUES ('UserAdmin', '6EHcy36TapgXHnN6fM7cjg==');
INSERT INTO User_profile (user_id, first_name, last_name, birthdate, email, ssn) VALUES (1, 'TMRS','Administrator','2000-1-1','admin@tmrs.com', 'ZtDk2BGPPv7/ECNw/Ru+IA==');
INSERT INTO user_preference (user_id, preference_key, preference_value, data_type, is_custom_value) VALUES (1,'default-theme','LIGHT', 'String', 'false'),(1,'show-name','false', 'Boolean', 'false'),(1,'show-id','false', 'Boolean', 'false'),(1,'show-username','false', 'Boolean', 'false'),(1,'default-font','ARIAL', 'String', 'false'),(1,'default-font-size','12', 'Integer', 'false'),(1,'application-width','800px', 'Integer', 'true');

INSERT INTO app_usr (user_name, pw_hash) VALUES ('lweyrich', 'ktHO4WqlLQ612S7xKkFysA==');
INSERT INTO User_profile (user_id, first_name, last_name, birthdate, email, ssn) VALUES (2, 'Louis','Weyrich','1969-4-17','lweyrich@tmrs.com', 'yLScNWvoxrv7UNAMUCGUHA==');
INSERT INTO user_preference (user_id, preference_key, preference_value, data_type, is_custom_value) VALUES (2,'default-theme','DARK', 'String', 'false'),(2,'show-name','false', 'Boolean', 'false'),(2,'show-id','false', 'Boolean', 'true'),(2,'show-username','false', 'Boolean', 'false'),(2,'default-font','ARIAL', 'String', 'false'),(2,'default-font-size','12', 'Integer', 'false'),(2,'application-width','550px', 'Integer', 'true');

INSERT INTO app_usr (user_name, pw_hash, active) VALUES ('testUserOne', 'uYMc2eDt+W16mZdUxf+x1g==', true);
INSERT INTO User_profile (user_id, first_name, last_name, birthdate, email, ssn) VALUES (3, 'Test','UserOne', null,'testuserone@tmrs.com','pG7P4G6CiUOcMPc8J603LA==');
INSERT INTO user_preference (user_id, preference_key, preference_value, data_type, is_custom_value) VALUES (3,'default-theme','UGLY', 'String', 'false'),(3,'show-name','true', 'Boolean', 'false'),(3,'show-id','false', 'Boolean', 'false'),(3,'show-username','false', 'Boolean', 'false'),(3,'default-font','ARIAL', 'String', 'false'),(3,'default-font-size','12', 'Integer', 'false'),(3,'application-width','400px', 'Integer', 'true');


INSERT INTO security_role (role_name, role_description) VALUES('ADMINISTRATOR', 'Administrator of application data.'); --1
INSERT INTO security_role (role_name, role_description) VALUES('USER_ADMIN', 'Can add, update, and delete users.'); --2
INSERT INTO security_role (role_name, role_description) VALUES('USER_VIEWER', 'Read-Only privilages for user data'); --3
INSERT INTO security_role (role_name, role_description) VALUES('APP_USER', 'Can add, update, and deactive their own account'); --4
INSERT INTO security_role (role_name, role_description) VALUES('TASK_VIEWER', 'Can view taks list'); --5
INSERT INTO security_role (role_name, role_description) VALUES('TASK_EDITOR', 'Can edit and deactive tasks'); --6
INSERT INTO security_role (role_name, role_description) VALUES('TASK_OWNER', 'Can own a task'); --7
INSERT INTO security_role (role_name, role_description) VALUES('ADD_TASK', 'Can add a task'); --8
INSERT INTO security_role (role_name, role_description) VALUES('HISTORY_VIEWER', 'Can view application history'); --9


INSERT INTO user_role(role_id, user_id) VALUES (1,1);
INSERT INTO user_role(role_id, user_id) VALUES (2,1);
INSERT INTO user_role(role_id, user_id) VALUES (3,1);
INSERT INTO user_role(role_id, user_id) VALUES (5,1);
INSERT INTO user_role(role_id, user_id) VALUES (6,1);
INSERT INTO user_role(role_id, user_id) VALUES (9,1);
INSERT INTO user_role(role_id, user_id) VALUES (2,2);
INSERT INTO user_role(role_id, user_id) VALUES (3,2);
INSERT INTO user_role(role_id, user_id) VALUES (5,2);
INSERT INTO user_role(role_id, user_id) VALUES (6,2);
INSERT INTO user_role(role_id, user_id) VALUES (3,3);
INSERT INTO user_role(role_id, user_id) VALUES (4,3);
INSERT INTO user_role(role_id, user_id) VALUES (5,3);
INSERT INTO user_role(role_id, user_id) VALUES (6,3);
INSERT INTO user_role(role_id, user_id) VALUES (7,3);
INSERT INTO user_role(role_id, user_id) VALUES (8,3);

INSERT INTO security_permissions (permission_name) VALUES ('ADD_USER'); -- 1
INSERT INTO security_permissions (permission_name) VALUES ('EDIT_USER'); --2
INSERT INTO security_permissions (permission_name) VALUES ('EDIT_PROFILE'); --3
INSERT INTO security_permissions (permission_name) VALUES ('EDIT_USER_NAME'); --4
INSERT INTO security_permissions (permission_name) VALUES ('VIEW_USER'); --5
INSERT INTO security_permissions (permission_name) VALUES ('VIEW_PROFILE'); --6
INSERT INTO security_permissions (permission_name) VALUES ('VIEW_USERS'); --7
INSERT INTO security_permissions (permission_name) VALUES ('DEACTIVATE_USERS'); --8
INSERT INTO security_permissions (permission_name) VALUES ('DEACTIVATE_SELF'); --9
INSERT INTO security_permissions (permission_name) VALUES ('VIEW_SSN'); --10
INSERT INTO security_permissions (permission_name) VALUES ('EDIT_SSN'); --11
INSERT INTO security_permissions (permission_name) VALUES ('EDIT_USER_PREFERENCE'); --12
INSERT INTO security_permissions (permission_name) VALUES ('VIEW_USER_PREFERENCES'); --13
INSERT INTO security_permissions (permission_name) VALUES ('CREATE_PREFERENCE_KEY'); --14
INSERT INTO security_permissions (permission_name) VALUES ('EDIT_PREFERENCE_KEY'); --15
INSERT INTO security_permissions (permission_name) VALUES ('VIEW_PREFERENCE_KEYS'); --16
INSERT INTO security_permissions (permission_name) VALUES ('EDIT_PREFERENCE_VALUE'); --17
INSERT INTO security_permissions (permission_name) VALUES ('CREATE_PREFERENCE_VALUE'); --18
INSERT INTO security_permissions (permission_name) VALUES ('VIEW_PREFERENCE_VALUES'); --19
INSERT INTO security_permissions (permission_name) VALUES ('ADD_USER_SECURITY'); --20
INSERT INTO security_permissions (permission_name) VALUES ('EDIT_USER_SECURITY'); --21
INSERT INTO security_permissions (permission_name) VALUES ('VIEW_USER_SECURITIES'); --22
INSERT INTO security_permissions (permission_name) VALUES ('ADD_ROLE'); --23
INSERT INTO security_permissions (permission_name) VALUES ('EDIT_ROLE'); --24
INSERT INTO security_permissions (permission_name) VALUES ('VIEW_ROLES'); --25
INSERT INTO security_permissions (permission_name) VALUES ('ADD_PERMISSION'); --26
INSERT INTO security_permissions (permission_name) VALUES ('VIEW_TASK'); --27
INSERT INTO security_permissions (permission_name) VALUES ('ADD_TASK'); --28
INSERT INTO security_permissions (permission_name) VALUES ('EDIT_TASK'); --29
INSERT INTO security_permissions (permission_name) VALUES ('DEACTIVATE_TASK'); --30
INSERT INTO security_permissions (permission_name) VALUES ('OWN_TASK'); --31
INSERT INTO security_permissions (permission_name) VALUES ('VIEW_HISTORY'); --31


INSERT INTO role_permission (role_id, permission_id) VALUES (9,31);
INSERT INTO role_permission (role_id, permission_id) VALUES (8,28);
INSERT INTO role_permission (role_id, permission_id) VALUES (7,31);
INSERT INTO role_permission (role_id, permission_id) VALUES (6,30);
INSERT INTO role_permission (role_id, permission_id) VALUES (6,29);
INSERT INTO role_permission (role_id, permission_id) VALUES (5,27);

INSERT INTO role_permission (role_id, permission_id) VALUES (4,9);
INSERT INTO role_permission (role_id, permission_id) VALUES (4,2);
INSERT INTO role_permission (role_id, permission_id) VALUES (4,3);
INSERT INTO role_permission (role_id, permission_id) VALUES (4,4);
INSERT INTO role_permission (role_id, permission_id) VALUES (4,11);
INSERT INTO role_permission (role_id, permission_id) VALUES (4,12);

INSERT INTO role_permission (role_id, permission_id) VALUES (3,5);
INSERT INTO role_permission (role_id, permission_id) VALUES (3,6);
INSERT INTO role_permission (role_id, permission_id) VALUES (3,10);
INSERT INTO role_permission (role_id, permission_id) VALUES (3,13);


INSERT INTO role_permission (role_id, permission_id) VALUES (2,1);
INSERT INTO role_permission (role_id, permission_id) VALUES (2,2);
INSERT INTO role_permission (role_id, permission_id) VALUES (2,3);
INSERT INTO role_permission (role_id, permission_id) VALUES (2,7);
INSERT INTO role_permission (role_id, permission_id) VALUES (2,8);


INSERT INTO role_permission (role_id, permission_id) VALUES (1,14);
INSERT INTO role_permission (role_id, permission_id) VALUES (1,15);
INSERT INTO role_permission (role_id, permission_id) VALUES (1,16);
INSERT INTO role_permission (role_id, permission_id) VALUES (1,17);
INSERT INTO role_permission (role_id, permission_id) VALUES (1,18);
INSERT INTO role_permission (role_id, permission_id) VALUES (1,19);
INSERT INTO role_permission (role_id, permission_id) VALUES (1,23);
INSERT INTO role_permission (role_id, permission_id) VALUES (1,24);
INSERT INTO role_permission (role_id, permission_id) VALUES (1,25);
INSERT INTO role_permission (role_id, permission_id) VALUES (1,26);
INSERT INTO role_permission (role_id, permission_id) VALUES (1,27);

INSERT INTO task_list (task_name, status, due_date, date_completed, created_by, task_owner, priority) VALUES('Create Task List Feature', 'COMPLETE', '2023-10-30', '2023-10-01', 'AppAdmin', 'AppAdmin', 'NONE');