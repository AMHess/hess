BEGIN TRANSACTION;

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS medication;
DROP TABLE IF EXISTS caregiver;

DROP SEQUENCE IF EXISTS seq_user_id;
DROP SEQUENCE IF EXISTS seq_medication_id;
DROP SEQUENCE IF EXISTS seq_caregiver_id;

CREATE SEQUENCE seq_user_id
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;

CREATE SEQUENCE seq_medication_id
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;

CREATE SEQUENCE seq_caregiver_id
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;

CREATE TABLE users (
user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
username varchar(50) NOT NULL,
password_hash varchar(200) NOT NULL,
CONSTRAINT PK_users PRIMARY KEY (user_id)
);

CREATE TABLE medication (
medication_id int DEFAULT nextval('seq_medication_id'::regclass) NOT NULL,
user_id int NOT NULL,
medication_name varchar(50) NOT NULL,
medication_type varchar(50) NOT NULL,
frequency_taken int NOT NULL,
dosage_size int NOT NULL,
pill_count int NOT NULL,
CONSTRAINT PK_medication PRIMARY KEY (medication_id),
CONSTRAINT FK_medication_user_id FOREIGN KEY(user_id) REFERENCES users(user_id)
);

CREATE TABLE caregiver (
caregiver_id int DEFAULT nextval('seq_caregiver_id'::regclass) NOT NULL,
user_id int NOT NULL,
username varchar(50) NOT NULL,
CONSTRAINT PK_caregiver PRIMARY KEY (caregiver_id),
CONSTRAINT FK_caregiver_user_id FOREIGN KEY(user_id) REFERENCES users(user_id)
);

INSERT INTO users(username,password_hash) VALUES ('user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC');
INSERT INTO users(username,password_hash) VALUES ('admin', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC');

INSERT INTO medication(user_id, medication_name, medication_type, frequency_taken, dosage_size, pill_count)
                VALUES(1, 'Vraylar', 'Pill', 1, 2, 90);



COMMIT TRANSACTION;