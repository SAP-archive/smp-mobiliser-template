/*==============================================================*/
/* Table: CUS_BLACKLIST_TYPES                                   */
/*==============================================================*/
create table CUS_BLACKLIST_TYPES (
   ID_BLACKLIST_TYPE       NUMBER(5)          not null,
   STR_BLACKLIST_TYPE      VARCHAR2(80 char)  not null,
   ID_ERROR_CODE           NUMBER(5),
   DAT_CREATION            TIMESTAMP,
   ID_CUSTOMER_CREATION    NUMBER(18),
   DAT_LAST_UPDATE         TIMESTAMP,
   ID_CUSTOMER_LAST_UPDATE NUMBER(18),
  constraint PK_BLACKLIST_TYPES primary key (ID_BLACKLIST_TYPE)
);

comment on table CUS_BLACKLIST_TYPES is
'Lookup table for the various different type of black list reasons';

comment on column CUS_BLACKLIST_TYPES.ID_BLACKLIST_TYPE is
'The ID of the blacklist type';

comment on column CUS_BLACKLIST_TYPES.STR_BLACKLIST_TYPE is
'The name of the blacklist type';

comment on column CUS_BLACKLIST_TYPES.ID_ERROR_CODE is
'The error code to use for failing a transaction due to blacklisting';


/*==============================================================*/
/* Table: CUS_BLACKLIST                                         */
/*==============================================================*/
create table CUS_BLACKLIST (
   ID_ENTITY               NUMBER(18)         not null,
   ID_BLACKLIST_TYPE       NUMBER(5)          not null,
   STR_NAME                VARCHAR2(200 char),
   DAT_CREATION            TIMESTAMP,
   ID_CUSTOMER_CREATION    NUMBER(18),
   DAT_LAST_UPDATE         TIMESTAMP,
   ID_CUSTOMER_LAST_UPDATE NUMBER(18),
  constraint PK_BLACKLIST primary key (ID_ENTITY)
);

comment on table CUS_BLACKLIST is
'Table holding names of blacklisted persons';

comment on column CUS_BLACKLIST.ID_ENTITY is
'The ID of the blacklist entry';

comment on column CUS_BLACKLIST.ID_BLACKLIST_TYPE is
'The type of blacklist entry ; reason for blacklist';

comment on column CUS_BLACKLIST.STR_NAME is
'The name of the blacklisted person';

create index IDX_BLACKLIST_NAME on CUS_BLACKLIST (
   STR_NAME ASC
);

alter table CUS_BLACKLIST_TYPES
   add constraint FK_BLACKLIST_TY_ERR_COD foreign key (ID_ERROR_CODE)
      references MOB_ERROR_CODES (ID_ERROR_CODE)
      not deferrable;

alter table CUS_BLACKLIST
   add constraint FK_BLACKLIST_BLACK_LST_TY foreign key (ID_BLACKLIST_TYPE)
      references CUS_BLACKLIST_TYPES (ID_BLACKLIST_TYPE)
      not deferrable;
