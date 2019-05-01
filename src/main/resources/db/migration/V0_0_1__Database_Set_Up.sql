create table if not exists USER ( 
	USR_ID_CODE bigint(20) not null auto_increment, 
	USR_NAME_DESC varchar(100) not null unique, 
	USR_PASSWORD_CODE varchar(100) default null, 
	primary key (USR_ID_CODE)
) engine=innodb default charset=utf8;