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

INSERT INTO MOB_ERROR_CODES (ID_ERROR_CODE, ID_ERROR_LEVEL, STR_INFORMATION) VALUES (10101, 'INFO', 'NAME ON BLACKLIST:Name has been on blacklist');
INSERT INTO MOB_ERROR_CODES (ID_ERROR_CODE, ID_ERROR_LEVEL, STR_INFORMATION) VALUES (10102, 'INFO', 'NAME PROBABLE ON BLACKLIST: Name has a likely match on blacklist');

INSERT INTO CUS_BLACKLIST_TYPES (ID_BLACKLIST_TYPE, STR_BLACKLIST_TYPE) VALUES (0, 'OFAC');
