package com.pk.constants;

public class ServiceQueries {

	public static final String GET_SERVICES = "SELECT PROVIDERNAME, BUSINESSNAME, BUSINESSADDRESS, EMAILID, MOBILENUMBER FROM SERVICE_PROVIDER " +
			" WHERE CITY = ? AND LOCATION = ? AND SERVICETYPE= ? AND ISACTIVE='Y'";

	public static final String GET_ALL_SERVICE_PROVIDERS = "SELECT DISTINCT PROVIDERNAME, BUSINESSNAME, BUSINESSADDRESS, EMAILID, MOBILENUMBER, ISACTIVE FROM SERVICE_PROVIDER GROUP BY PROVIDERNAME";

	public static final String REGISTER_SERVICE_PROVIDER = "INSERT INTO service_provider " +
			"(providerName, businessName, location, serviceType, mobileNumber, " +
			" emailId, password, city, businessAddress, updateTime) " + 
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, now())";

	public static final String VALIDATE_ADMIN_CREDENTIALS = "SELECT 1 AS VALIDUSER FROM adminusers WHERE emailId=? AND password=?";

	public static final String VALIDATE_USER_CREDENTIALS = "SELECT 1 AS VALIDUSER FROM service_provider WHERE emailId=? AND password=?";

	public static final String GET_PROVIDER_DETAILS = "SELECT  providerName,  businessName,  location,  serviceType,  " + 
			" mobileNumber, emailId, city,  businessAddress, password, isActive FROM userservice.service_provider" +
			" WHERE emailId = ?";

	public static final String CHECK_IF_SERVICE_EXISTS = "SELECT 1 AS SERVICEEXISTS FROM SERVICE_PROVIDER " +
			" WHERE CITY = ? AND EMAILID = ? AND SERVICETYPE= ? ";

	public static final String ACTIVATE_OR_DEACTIVATE_SERVICE_PROVIDER = "UPDATE service_provider SET isActive = ?, updateTime = now() WHERE emailId = ?";

	public static final String INSERT_FEEDBACK = "INSERT INTO userFeedback (username, useremailid, usercontact, feedback, serviceprovidername, updateTime) " +
			" VALUES (?, ?, ?, ?, ?, now())";
}
