#FLUSH Privileges
 
drop database system;
create database system DEFAULT CHARACTER SET utf8;
use system;
create table user (
       id int AUTO_INCREMENT not null PRIMARY key,
       username varchar(128) not null,
       password varchar(128) not null,
       type varchar(16),
       status varchar(16),
       modifyDate timestamp DEFAULT  now(),
       createDate timestamp
);

create table student(
       id int AUTO_INCREMENT not null primary key,
       name varchar(32) not null,
       sex char(6) not null,
       age date,
       address varchar(128),
       contact varchar(64),
       mobilePhone varchar(32),
       email varchar(64),
       telephone varchar(32),#it contains multi pthone number
       qq varchar(32),
       dataFrom varchar(128),
       status varchar(128),
       doesBill boolean,
       remark varchar(512),
       school varchar(128),
       modifyDate timestamp,
       createDate timestamp, #DEFAULT  now(),,
       uid int,
       otherTelephone varchar(256),
       accountBalance float,
       foreign key(uid) REFERENCES user(id) on delete  CASCADE
);


create table class(
       id int AUTO_INCREMENT not null primary key,
       className varchar(128) not null,
       classNotice varchar(512),
       teacher varchar(64),
       payAmount float ,
       modifyDate timestamp DEFAULT  now(),
       createDate timestamp
);

#drop table payway;
create table payway(
       id int auto_increment not null primary key,
       payway  varchar(512),
       discount float,
       modifyDate timestamp DEFAULT  now(),
       createDate timestamp, 
       cid int,
       foreign key(cid) REFERENCES class(id) on delete  CASCADE
);


#drop table stu_class;
create table stu_class(
       sid int,
       cid int,
       discount float default 0.0,# add this discount , it says one student have a discount at one class, it has a default value.
       isDeleteDiscount boolean default false,
       primary key (sid, cid),
       foreign key(sid) REFERENCES student(id) on delete  CASCADE ,
       foreign key(cid) REFERENCES class(id) on delete  CASCADE
);



#drop table communication;
create table communication(
       id int auto_increment not null primary key,
       content varchar(4096),
       createDate timestamp default now(),
       communicationDate timestamp,
       sid int,
       username varchar(128),
       uid int,
       status int default 1,#1 is normal, 0 is delete
       foreign key(sid) REFERENCES student(id) on delete  CASCADE,
       foreign key(uid) REFERENCES user(id) on delete  CASCADE
);
#use system;

#drop table payment; 
create TABLE payment(
       id int auto_increment not null primary key,
       payAmount float,
       purpose varchar(128),
       doesNeedReminder boolean ,
       startDate timestamp,
       endDate timestamp,
       cashOrCard varchar(128), #cash or card 
       selectedClass varchar(128),
       selectedPayway varchar(128),#when the status is -1,then this field store the student name
       remark varchar(256),
       payDate timestamp,
       useBalance float,
       discount float,
       sid int,
       uid int,
       payer varchar(64),
       receiver varchar(64),
       pwayid int,
       status int DEFAULT 1, #1 is norml, -1 is refund, 0 delete
       foreign key(sid) REFERENCES student(id) on delete  CASCADE,
       foreign key(uid) REFERENCES user(id) on delete  CASCADE
);

#select auto_increment from information_schema.`TABLES` where table_name='user';
insert into user(username,password,type,status,createDate)values('a',password('a'),'管理员','可用',now());
update user set modifyDate=now() where id = 1
#select @@IDENTITY

#SELECT last_insert_id() ;
#select @@IDENTITY

#select * from class
#select * from class where id =2;

#REPLACE  into stu_class(sid,cid) values(1,1) where not exists (select * from stu_class where sid = 1 and cid = 1)
#select sum(2+3+null) from dual;
#update student set modifyDate =now(), accountBalance = accountBalance+ 4  where (id = 6)
#update student set modifyDate =now(), accountBalance = 0 where (id = 6)