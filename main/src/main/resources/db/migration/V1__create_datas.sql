Create table project(id INT(10) , created_date DATE , name varchar(60) , status boolean, updated_date date);

Create table version(id, created_date, deploy_date, description, gmud, order, status, updated_date, version_clone_id, version_number, project_id);

Create table screen (id, active, created_date, image, name, order, updated_date, urlog, clone_version_id, screen_father_id, version_id);

Create table event_type(id, created_date, name, order, status, updated_date);

Create table  event(id, active, created_date, order, parameter, updated_date, event_type_id, screen_id);

Create table request(id, created_date, descrption, layer, order, status, updated_date, uri_homolog, uri_prod, event_id, request_father_id);


Create table request_property(id, created_date, key, order, updated_date, value, request_id)

Create table USER (ID, EMAIL, NAME, PASS);