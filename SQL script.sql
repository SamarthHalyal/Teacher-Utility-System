show databases;
create database db;
use db;

drop table dept;
drop table semester;
drop table subjects;
drop table teacher;
drop table register;
drop table updates;

create table dept(did int,dname varchar(20),primary key(did));
create table semester(semid int,did int,sname varchar(10),primary key(semid),foreign key(did) references dept(did));
create table subjects(sid int,sname varchar(10),modules int,alloc_time int,primary key(sid));
create table teacher(tid int not null auto_increment,email varchar(35),pass varchar(15),primary key(email),foreign key(tid) references register(uid));
create table register(uid int not null auto_increment,tname varchar(20),email varchar(35),did int,semid int,sname varchar(10),pw varchar(20),primary key(uid),foreign key(did) references dept(did),foreign key(semid) references semester(semid));
alter table register auto_increment=1;
create table updates(did int,semid int,sid int,tname varchar(20),sname varchar(10),mod_rem int,time_rem int,foreign key(did) references dept(did),foreign key(semid) references semester(semid),foreign key(sid) references subjects(sid));
alter table teacher auto_increment=1;
create table scheduler(tname varchar(20),today_mod int,today_time int);


select * from dept;
select * from semester;
select * from subjects;
select * from teacher;
select * from register;
select * from scheduler;
select tname,did,semid,sname from register;
select * from updates;

insert into updates(did,semid,sid,tname,sname) values(,,,"","");
alter table register modify sem varchar(1);
insert into scheduler values("sam",1,2);
update scheduler set today_mod = 5 where tname="Samarth";
update scheduler set today_time = 5 where tname="Samarth";


desc register;
desc dept;
desc subjects;
desc teacher;
desc updates;
desc semester;

select did from dept where dname="Computer Science";
select semid from semester where sname="6CS1";

update teacher set email="abc" && pass="test";

insert into dept values(3,"Civil");
insert into subjects values(12,"6CV2",5,10);
insert into register(tname,email,did,semid,sname,pw) values("rushika","rushika@patil.com",1,1,"5CS1","toor");
insert into semester values(12,3,"6CV2");
insert into teacher values(1,"test.kle@project.com","hello");

call new_procedure;
show triggers;

truncate register;
truncate teacher;
truncate updates;
truncate scheduler;