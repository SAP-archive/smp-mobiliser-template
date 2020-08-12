/*==============================================================*/
/* Table: CUS_BLACKLIST_TYPES                                   */
/*==============================================================*/
create table CUS_BLACKLIST_TYPES (
   ID_BLACKLIST_TYPE       NUMERIC(5)         not null,
   STR_BLACKLIST_TYPE      VARCHAR(80)        not null,
   ID_ERROR_CODE           NUMERIC(5),
   DAT_CREATION            TIMESTAMP,
   ID_CUSTOMER_CREATION    NUMERIC(18),
   DAT_LAST_UPDATE         TIMESTAMP,
   ID_CUSTOMER_LAST_UPDATE NUMERIC(18),
  constraint PK_BLACKLIST_TYPES primary key (ID_BLACKLIST_TYPE)
);

/*==============================================================*/
/* Table: CUS_BLACKLIST                                         */
/*==============================================================*/
create table CUS_BLACKLIST (
   ID_ENTITY               NUMERIC(18)        not null,
   ID_BLACKLIST_TYPE       NUMERIC(5)         not null,
   STR_NAME                VARCHAR(200),
   DAT_CREATION            TIMESTAMP,
   ID_CUSTOMER_CREATION    NUMERIC(18),
   DAT_LAST_UPDATE         TIMESTAMP,
   ID_CUSTOMER_LAST_UPDATE NUMERIC(18),
  constraint PK_BLACKLIST primary key (ID_ENTITY)
);

create index IDX_BLACKLIST_NAME on CUS_BLACKLIST (
   STR_NAME ASC
);

alter table CUS_BLACKLIST_TYPES
   add constraint FK_BLACKLIST_TY_ERR_COD foreign key (ID_ERROR_CODE)
      references MOB_ERROR_CODES (ID_ERROR_CODE);

alter table CUS_BLACKLIST
   add constraint FK_BLACKLIST_BLACK_LST_TY foreign key (ID_BLACKLIST_TYPE)
      references CUS_BLACKLIST_TYPES (ID_BLACKLIST_TYPE);
