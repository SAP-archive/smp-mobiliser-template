update MOB_PREFERENCES set STR_VALUE = './configuration/com.sap.mobile.platform.server.mobiliser.core/keys/mobiliser.jks' where STR_VALUE = '${mobiliser.home}/conf/keys/mobiliser.jks';
update MOB_PREFERENCES set STR_VALUE = './configuration/com.sap.mobile.platform.server.mobiliser.core/keys/mobiliser_pub.jks' where STR_VALUE = '${mobiliser.home}/conf/keys/mobiliser_pub.jks';
update MOB_PREFERENCES set STR_VALUE = './configuration/com.sap.mobile.platform.server.mobiliser.core/sld_template.xml' where STR_VALUE = '${mobiliser.home}/conf/sld_template.xml';
update MOB_PREFERENCES set STR_VALUE = '${MOBILISER_HOST}/mobiliser/crystalrpt' where STR_VALUE = '${MOBILISER_HOST}/crystalrpt';

