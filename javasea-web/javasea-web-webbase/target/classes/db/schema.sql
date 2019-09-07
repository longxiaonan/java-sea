create table if not exists ACCOUNT (
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name varchar(30) NOT NULL,
  balance double(15) NOT NULL DEFAULT '0.000'
);

-- create table if not exists ACCOUNT (
--     USE_ID int not null primary key auto_increment,
--     USE_NAME varchar(100),
--     USE_SEX varchar(1),
--     USE_AGE NUMBER(3),
--     USE_ID_NO VARCHAR(18),
--     USE_PHONE_NUM VARCHAR(11),
--     USE_EMAIL VARCHAR(100),
--     CREATE_TIME DATE,
--     MODIFY_TIME DATE,
--     USE_STATE VARCHAR(1));