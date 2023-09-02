drop database if exists page;
create database page;
use page;

create table page
(
   id int(4) not null auto_increment,

   title varchar(50),
   contents varchar(50),

   primary key (empno)

) ENGINE=InnoDB;