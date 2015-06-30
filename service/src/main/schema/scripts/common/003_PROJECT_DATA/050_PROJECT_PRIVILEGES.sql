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

INSERT INTO MOB_UMGR_PRIVILEGES (ID_PRIVILEGE,STR_PRIVILEGE,DAT_CREATION,ID_CUSTOMER_CREATION) values ('WS_SPECIAL_AUTHORISATION','Allows to create a new attribute for a customer',null,0);
INSERT INTO MOB_UMGR_ROLE_PRIVILEGES (ID_ROLE, ID_PRIVILEGE, ID_CUSTOMER_CREATION) VALUES ('SYSTEM','WS_SPECIAL_AUTHORISATION', 0);

