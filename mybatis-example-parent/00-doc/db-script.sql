drop table if exists user;
create table user(
	id int not null auto_increment comment '主键id',
	username varchar(30) not null comment '用户名称',
	birthday timestamp default CURRENT_TIMESTAMP comment '用户出生日期',
	sex varchar(2) not null comment '用户性别',
	address varchar(50) default null comment '用户住址',
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';
--初始化数据
insert into user(username, birthday, sex, address) values('张三', '1993-03-03', '男', 'A0-B1-C2');
insert into user(username, birthday, sex, address) values('李四', '1994-04-04', '男', 'X0-Y1-Z2');
insert into user(username, birthday, sex, address) values('王五', '1995-05-06', '女', 'I0-II1-III2');