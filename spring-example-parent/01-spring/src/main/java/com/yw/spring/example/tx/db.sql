drop table if exists account;
create table account (
	id int not null auto_increment comment '主键id',
	name varchar(30) not null comment '用户名称',
	money double not null default 0.0 comment '余额',
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账户表';