create table USER (
	USR_USER_NO bigint not null auto_increment, 
	USR_NAME_DESC varchar(100), 
	USR_PASSWORD_CODE varchar(100), 
	USR_CREATE_DATE datetime,
	USR_UPDATE_DATE datetime,
	primary key (USR_USER_NO)
) engine=InnoDB;

create table ACCOUNT (
	ACC_ACCOUNT_NO bigint not null auto_increment, 
	ACC_USER_NO bigint not null, 
	ACC_IBAN_CODE varchar(30),
	ACC_DESCRIPTION_DESC varchar(255),
	ACC_BIC_CODE varchar(20), 
	ACC_CURRENCY_CODE varchar(3), 
	ACC_CREATE_DATE datetime, 
	ACC_UPDATE_DATE datetime, 
	primary key (ACC_ACCOUNT_NO)
) engine=InnoDB;
	
create table TRANSACTION (
	TRA_TRANSACTION_NO bigint not null auto_increment, 
	TRA_ACCOUNT_NO bigint not null, 
	TRA_MUTATION_DATE date, 
	TRA_INTEREST_DATE date, 
	TRA_AMOUNT_DEC decimal(19,2),
	TRA_CURRENCY_CODE varchar(3),
	TRA_BALANCE_AFTER_DEC decimal(19,2), 
	TRA_COUNTERPARTY_IBAN_CODE varchar(30), 
	TRA_COUNTERPARTY_NAME_DESC varchar(255), 
	TRA_COUNTERPARTY_BIC_CODE varchar(20), 
	TRA_TYPE_CODE varchar(10), 
	TRA_DESCRIPTION_DESC varchar(255), 
	TRA_SOURCE_CODE varchar(10),
	TRA_IMPORT_CODE bigint, 
	TRA_CATEGORY_ID bigint, 
	TRA_CREATE_DATE datetime, 
	TRA_UPDATE_DATE datetime, 
	primary key (TRA_TRANSACTION_NO)
) engine=InnoDB;

create table TRANSACTION_CATEGORY (
	TRC_ID_NO bigint not null auto_increment,
	TRC_USER_NO bigint not null, 
	TRC_CATEGORY_NO bigint not null,
	TRC_DESCRIPTION_DESC varchar(255), 
	TRC_TYPE_CODE varchar(10), 
	TRC_ACTIVE_BOOL char(1), 
	TRC_CREATE_DATE datetime, 
	TRC_UPDATE_DATE datetime, 
	primary key (TRC_CATEGORY_NO)
) engine=InnoDB;

create table TRANSACTION_IMPORT (
	IMP_IMPORT_NO bigint not null auto_increment, 
	IMP_USER_NO bigint not null, 
	IMP_FILE_DESC varchar(255), 
	IMP_HASH_CODE char(32), 
	IMP_STATUS_CODE varchar(20), 
	IMP_IMPORTED_DATE datetime, 
	IMP_UPDATE_DATE datetime, 
	primary key (IMP_IMPORT_NO)
) engine=InnoDB;

alter table USER add constraint UK_USER_NAME unique (USR_NAME_DESC);
alter table ACCOUNT add constraint FK_ACCOUNT_USER foreign key (ACC_USER_NO) references USER (USR_USER_NO);
alter table TRANSACTION add constraint FK_TRANSACTION_ACCOUNT foreign key (TRA_ACCOUNT_NO) references ACCOUNT (ACC_ACCOUNT_NO);
alter table TRANSACTION add constraint FK_TRANSACTION_CATEGORY foreign key (TRA_CATEGORY_ID) references TRANSACTION_CATEGORY (TRC_ID_NO);
alter table TRANSACTION add constraint FK_TRANSACTION_IMPORT foreign key (TRA_IMPORT_CODE) references TRANSACTION_IMPORT (IMP_IMPORT_NO);
alter table TRANSACTION_CATEGORY add constraint FK_TRANSACTION_CATEGORY_USER foreign key (TRC_USER_NO) references USER (USR_USER_NO);
alter table TRANSACTION_IMPORT add constraint FK_TRANSACTION_IMPORT_USER foreign key (IMP_USER_NO) references USER (USR_USER_NO);
