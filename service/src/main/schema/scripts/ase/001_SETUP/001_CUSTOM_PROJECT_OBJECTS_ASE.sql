--
-- Copyright (C) 2012-2015 SAP SE
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--         http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

/*==============================================================*/
/* Table: CUS_BLACKLIST_TYPES                                   */
/*==============================================================*/
create table CUS_BLACKLIST_TYPES (
   ID_BLACKLIST_TYPE       numeric(5)         not null,
   STR_BLACKLIST_TYPE      nvarchar(80)       not null,
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
   STR_NAME                nvarchar(200)      null,
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
