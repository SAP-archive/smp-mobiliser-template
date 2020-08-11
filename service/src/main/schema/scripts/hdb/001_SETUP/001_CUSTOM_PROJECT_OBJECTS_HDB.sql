/*==============================================================*/
/* Table: CUS_BLACKLIST_TYPES                                   */
/*==============================================================*/
create column table CUS_BLACKLIST_TYPES (
   ID_BLACKLIST_TYPE       INTEGER           not null,
   STR_BLACKLIST_TYPE      NVARCHAR(80)      not null,
   ID_ERROR_CODE           INTEGER,
   DAT_CREATION            TIMESTAMP,
   ID_CUSTOMER_CREATION    BIGINT,
   DAT_LAST_UPDATE         TIMESTAMP,
   ID_CUSTOMER_LAST_UPDATE BIGINT
);

alter table CUS_BLACKLIST_TYPES add constraint PK_BLACKLIST_TYPES primary key (ID_BLACKLIST_TYPE);

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
create column table CUS_BLACKLIST(
   ID_ENTITY               BIGINT             not null,
   ID_BLACKLIST_TYPE       INTEGER            not null,
   STR_NAME                NVARCHAR(200),
   DAT_CREATION            TIMESTAMP,
   ID_CUSTOMER_CREATION    BIGINT,
   DAT_LAST_UPDATE         TIMESTAMP,
   ID_CUSTOMER_LAST_UPDATE BIGINT
);

alter table CUS_BLACKLIST add constraint PK_BLACKLIST primary key (ID_ENTITY);

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
      references mob_error_codes (ID_ERROR_CODE)
      on delete restrict on update cascade;

alter table CUS_BLACKLIST
   add constraint FK_BLACKLIST_BLACK_LST_TY foreign key (ID_BLACKLIST_TYPE)
      references CUS_BLACKLIST_TYPES (ID_BLACKLIST_TYPE)
      on delete restrict on update cascade;
