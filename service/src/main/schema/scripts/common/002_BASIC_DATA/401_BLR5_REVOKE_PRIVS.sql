-- This will create a role which is identical to the STANDARD_CONSUMER 
-- role with the exception of containing to receive money for an unknown 
-- customer.
-- This role is set as the revoke role for customers of type 5.
-- In case new privileges are added to the STANDARD_CONSUMER role they 
-- should also be added to this role otherwise they will not be revoked from customer with BLR5 (pending registration)
INSERT INTO MOB_UMGR_ROLES (ID_ROLE, STR_ROLE,DAT_CREATION,ID_CUSTOMER_CREATION) values ('ALL_BUT_RECEIVE', 'Used to revoke privileges for BLR 5 customers', #CURRENT_TIMESTAMP#,0);
INSERT INTO MOB_UMGR_ROLE_PRIVILEGES (ID_ROLE, ID_PRIVILEGE,DAT_CREATION,ID_CUSTOMER_CREATION) (SELECT 'ALL_BUT_RECEIVE', ID_PRIVILEGE, #CURRENT_TIMESTAMP#, 0 from MOB_UMGR_ROLE_PRIVILEGES where ID_ROLE = 'STANDARD_CONSUMER' and ID_PRIVILEGE <> 'UC_197_PAYEE_PRIVILEGE');
UPDATE MOB_BLACKLISTREASONS set STR_REVOKE_ROLE = 'ALL_BUT_RECEIVE' where ID_BLACKLISTREASON = 5;

