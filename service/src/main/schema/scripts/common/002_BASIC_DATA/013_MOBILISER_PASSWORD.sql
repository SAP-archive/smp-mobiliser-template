-- Updating the mobiliser password to an actual password - this script should be removed before publication on SAP Service Marketplace
UPDATE MOB_CUSTOMERS_CREDENTIALS SET STR_CREDENTIAL = '{SHA}euAiVLAW6SpS257g2qRaWNNo7l8=' WHERE ID_CUSTOMER = 100 AND ID_CREDENTIAL_TYPE = 1 AND BOL_IS_ACTIVE = 'Y';
DELETE FROM MOB_HISTORY WHERE STR_OLD_VALUE = 'REPLACE_ME';

-- setting the creation date of the credentials to now instead of having
-- them be expired
UPDATE MOB_CUSTOMERS_CREDENTIALS SET DAT_CREATION = #CURRENT_TIMESTAMP#;
