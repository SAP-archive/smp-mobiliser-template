/*==============================================================*/
/* Table: CUS_BLACKLIST_TYPES                                   */
/*==============================================================*/
create table CUS_BLACKLIST_TYPES (
   ID_BLACKLIST_TYPE       numeric(5)         not null,
   STR_BLACKLIST_TYPE      nvarchar(80)       not null,
   ID_ERROR_CODE           numeric(5)         null,
   DAT_CREATION            datetime2          null,
   ID_CUSTOMER_CREATION    numeric(18)        null,
   DAT_LAST_UPDATE         datetime2          null,
   ID_CUSTOMER_LAST_UPDATE numeric(18)        null,
  constraint PK_BLACKLIST_TYPES primary key (ID_BLACKLIST_TYPE)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sys.sp_addextendedproperty 'MS_Description',
   'Lookup table for the various different type of black list reasons',
   'user', @CurrentUser, 'table', 'CUS_BLACKLIST_TYPES'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',.
   'The ID of the blacklist type',
   'user', @CurrentUser, 'table', 'CUS_BLACKLIST_TYPES', 'column', 'ID_BLACKLIST_TYPE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',.
   'The name of the blacklist type',
   'user', @CurrentUser, 'table', 'CUS_BLACKLIST_TYPES', 'column', 'STR_BLACKLIST_TYPE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',.
   'The error code to use for failing a transaction due to blacklisting',
   'user', @CurrentUser, 'table', 'CUS_BLACKLIST_TYPES', 'column', 'ID_ERROR_CODE'
go

/*==============================================================*/
/* Table: CUS_BLACKLIST                                         */
/*==============================================================*/
create table CUS_BLACKLIST (
   ID_ENTITY               numeric(18)        not null,
   ID_BLACKLIST_TYPE       numeric(5)         not null,
   STR_NAME                nvarchar(200)      null,
   DAT_CREATION            datetime2          null,
   ID_CUSTOMER_CREATION    numeric(18)        null,
   DAT_LAST_UPDATE         datetime2          null,
   ID_CUSTOMER_LAST_UPDATE numeric(18)        null,
  constraint PK_BLACKLIST primary key (ID_ENTITY)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sys.sp_addextendedproperty 'MS_Description',
   'Table holding names of blacklisted persons',
   'user', @CurrentUser, 'table', 'CUS_BLACKLIST'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',.
   'The ID of the blacklist entry',
   'user', @CurrentUser, 'table', 'CUS_BLACKLIST', 'column', 'ID_ENTITY'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',.
   'The type of blacklist entry ; reason for blacklist',
   'user', @CurrentUser, 'table', 'CUS_BLACKLIST', 'column', 'ID_BLACKLIST_TYPE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',.
   'The name of the blacklisted person',
   'user', @CurrentUser, 'table', 'CUS_BLACKLIST', 'column', 'STR_NAME'
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
