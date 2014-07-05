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
telephone varchar(32),
dataFrom varchar(128),
status varchar(128),
doesBill boolean,
modifyDate timestamp,
createDate timestamp,
uid int,
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
       cid int ,
        primary key (sid, cid),
       foreign key(sid) REFERENCES student(id) on delete  CASCADE ,
      foreign key(cid) REFERENCES class(id) on delete  CASCADE
);



#drop table communication;
create table communication(
id int auto_increment not null primary key,
content varchar(4096),
communicationDate timestamp,
sid int,
foreign key(sid) REFERENCES student(id) on delete  CASCADE
);

 
create TABLE payment(
id int auto_increment not null primary key,
payAmount float,
purpose varchar(128),
doesNeedReminder boolean ,
startDate timestamp,
endDate timestamp,
remark varchar(256),
payDate timestamp,
sid int,
uid int,
payer varchar(64),
receiver varchar(64),
pwayid int,
foreign key(sid) REFERENCES student(id) on delete  CASCADE,
foreign key(uid) REFERENCES user(id) on delete  CASCADE
);

 
#select auto_increment from information_schema.`TABLES` where table_name='user';
insert into user(username,password,type,status,createDate)values('a',password('a'),'admin','useable',now());

insert into user(username,password,type,status,createDate)values('b',password('b'),'super','useable',now());

update user set modifyDate=now() where id = 1
#select @@IDENTITY

#SELECT last_insert_id() ;
#select @@IDENTITY

#select * from class
#select * from class where id =2;

#REPLACE  into stu_class(sid,cid) values(1,1) where not exists (select * from stu_class where sid = 1 and cid = 1)
insert into student (name,sex,age) values("ss","2",'2002-10-2');
insert into student (name,sex,age) values("ss","2",'2003-10-2');
insert into student (name,sex,age) values("ss","2",'2004-10-2');
insert into student (name,sex,age) values("ss","2",'2005-10-2');
 
 select * from student where age > '2001-1-1' and age < '2013-1-1';
  select * from student where name = '23' and age BETWEEN '2001-1-1' and  '2013-1-1';
  select * from  student where 1=1  and name like '%wsx%'
  select * from student where 1=1  and name like '%wsx%' 
select * from student where 1=1  and name like '%wsx%'  and age between '2013-10-1' and '2013-10-1' and  doesBill = 1

delete from student where 1=1

select from payment, student where student.id = payment.sid;
