/*==============================================================*/
/* Table: CUS_BLACKLIST_TYPES                                   */
/*==============================================================*/
create table CUS_BLACKLIST_TYPES (
   ID_BLACKLIST_TYPE       numeric(5)         not null,
   STR_BLACKLIST_TYPE      varchar(80 char)   not null,
   ID_ERROR_CODE           numeric(5)         null,
   DAT_CREATION            datetime           null,
   ID_CUSTOMER_CREATION    numeric(18)        null,
   DAT_LAST_UPDATE         datetime           null,
   ID_CUSTOMER_LAST_UPDATE numeric(18)        null,
  constraint PK_BLACKLIST_TYPES primary key (ID_BLACKLIST_TYPE)
)
go

/*==============================================================*/
/* Table: CUS_BLACKLIST                                         */
/*==============================================================*/
create table CUS_BLACKLIST (
   ID_ENTITY               numeric(18)        not null,
   ID_BLACKLIST_TYPE       numeric(5)         not null,
   STR_NAME                varchar(200 char)  null,
   DAT_CREATION            datetime           null,
   ID_CUSTOMER_CREATION    numeric(18)        null,
   DAT_LAST_UPDATE         datetime           null,
   ID_CUSTOMER_LAST_UPDATE numeric(18)        null,
  constraint PK_BLACKLIST primary key (ID_ENTITY)
)
go

create index IDX_BLACKLIST_NAME on CUS_BLACKLIST (
   STR_NAME ASC
)
go

alter table CUS_BLACKLIST_TYPES
   add constraint FK_BLACKLIST_TY_ERR_COD foreign key (ID_ERROR_CODE)
      references MOB_ERROR_CODES (ID_ERROR_CODE)
go

alter table CUS_BLACKLIST
   add constraint FK_BLACKLIST_BLACK_LST_TY foreign key (ID_BLACKLIST_TYPE)
      references CUS_BLACKLIST_TYPES (ID_BLACKLIST_TYPE)
go
