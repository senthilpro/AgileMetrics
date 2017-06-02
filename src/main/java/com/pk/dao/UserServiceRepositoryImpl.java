package com.pk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.pk.constants.ServiceConstants;
import com.pk.constants.ServiceQueries;
import com.pk.model.ServiceProvider;
import com.pk.model.UserFeedback;
import com.pk.util.HappyServicesUtil;

public class UserServiceRepositoryImpl {
	Logger logger = Logger.getLogger(UserServiceRepositoryImpl.class);

	private final DataSource dataSource;
	private Connection con;
	public UserServiceRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<ServiceProvider> getServiceList(String city, String location, String serviceType) {

		List<ServiceProvider> serviceProviderList = new ArrayList<>();
		try {
			initializeConnection();
			PreparedStatement ps = con.prepareStatement(ServiceQueries.GET_SERVICES);
			ps.setString(1, city);
			ps.setString(2, location);
			ps.setString(3, serviceType);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				ServiceProvider provider = new ServiceProvider();
				provider.setProviderName(rs.getString(1));
				provider.setBusinessName(rs.getString(2));
				provider.setBusinessAddress(rs.getString(3));
				provider.setEmailId(rs.getString(4));
				provider.setMobileNumber(rs.getString(5));
				serviceProviderList.add(provider);
			}
		} catch (SQLException se) {
			throw new IllegalArgumentException(se.getMessage());
		}
		return serviceProviderList;
	}

	public List<ServiceProvider> getServiceList() {

		List<ServiceProvider> serviceProviderList = new ArrayList<>();
		try {
			initializeConnection();
			PreparedStatement ps = con.prepareStatement(ServiceQueries.GET_ALL_SERVICE_PROVIDERS);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				ServiceProvider provider = new ServiceProvider();
				provider.setProviderName(rs.getString(1));
				provider.setBusinessName(rs.getString(2));
				provider.setBusinessAddress(rs.getString(3));
				provider.setEmailId(rs.getString(4));
				provider.setMobileNumber(rs.getString(5));
				provider.setIsActive(rs.getString(6));
				serviceProviderList.add(provider);
			}
		} catch (SQLException se) {
			throw new IllegalArgumentException(se.getMessage());
		}
		return serviceProviderList;
	}

	public boolean registerUser(ServiceProvider provider) {
		boolean isRegistered = false;
		try {
			String[] serviceTypes = provider.getServiceType().split(",");
			for(String serviceType : serviceTypes) {
				if(!checkIfProviderExists(provider.getEmailId(), provider.getCity(), serviceType)) {
					initializeConnection();
					PreparedStatement ps = con.prepareStatement(ServiceQueries.REGISTER_SERVICE_PROVIDER);
					ps.setString(1, provider.getProviderName());
					ps.setString(2, provider.getBusinessName());
					ps.setString(3, provider.getLocation());
					ps.setString(4, serviceType);
					ps.setString(5, provider.getMobileNumber());
					ps.setString(6, provider.getEmailId());
					ps.setString(7, HappyServicesUtil.getHashedPassword(provider.getPassword()));
					ps.setString(8, provider.getCity());
					ps.setString(9, provider.getBusinessAddress());

					int returnVal = ps.executeUpdate();
					isRegistered = returnVal > 0 ? true : false;
				} else {
					throw new IllegalArgumentException("Service Provider already exists with the same emailId.");
				}
			}
		} catch(SQLException se) {
			isRegistered= false;
			throw new IllegalArgumentException("Could not register user." + se.getMessage());
		}
		return isRegistered;
	}

	public boolean loginUser(String emailId, String password, String userType) {
		String query = (userType != null && ServiceConstants.ADMIN.equals(userType)) ? ServiceQueries.VALIDATE_ADMIN_CREDENTIALS : ServiceQueries.VALIDATE_USER_CREDENTIALS;
		boolean isValidUser = false;
		try {
			initializeConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, emailId);
			ps.setString(2, HappyServicesUtil.getHashedPassword(password));

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if(ServiceConstants.ONE.equals(rs.getString(ServiceConstants.VALID_USER)))
					isValidUser = true;
			}
		} catch (SQLException se) {
			throw new IllegalArgumentException(se.getMessage());
		}
		logger.info("User is valid/invalid" + isValidUser);
		return isValidUser;
	}

	public ServiceProvider updateUser(ServiceProvider provider) {
		ServiceProvider serviceProvider = new ServiceProvider();
		try {
			initializeConnection();
			PreparedStatement ps = con.prepareStatement(userUpdateQuery(provider));

			int returnVal = ps.executeUpdate();
			if(returnVal > 0) {
				serviceProvider = getProviderDetails(provider.getEmailId());
			}
		} catch(SQLException se) {
			throw new IllegalArgumentException("Could not register user." + se.getMessage());
		}
		return serviceProvider;
	}

	public List<ServiceProvider> activateOrDeactivateUser(ServiceProvider provider) {
		List<ServiceProvider> serviceProviderList = new ArrayList<>();
		try {
			initializeConnection();
			PreparedStatement ps = con.prepareStatement(ServiceQueries.ACTIVATE_OR_DEACTIVATE_SERVICE_PROVIDER);
			String flag = "Y".equals(provider.getIsActive()) ? "N" : "Y";
			ps.setString(1, flag);
			ps.setString(2, provider.getEmailId());

			int returnVal = ps.executeUpdate();
			if(returnVal > 0) {
				serviceProviderList = getServiceList();
			}
		} catch(SQLException se) {
			throw new IllegalArgumentException("Could not register user." + se.getMessage());
		}
		return serviceProviderList;
	}

	public ServiceProvider getProviderDetails(String emailId) {

		ServiceProvider provider = new ServiceProvider();
		try {
			initializeConnection();
			PreparedStatement ps = con.prepareStatement(ServiceQueries.GET_PROVIDER_DETAILS);
			ps.setString(1, emailId);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				provider.setProviderName(rs.getString(1));
				provider.setBusinessName(rs.getString(2));
				provider.setLocation(rs.getString(3));
				provider.setServiceType(rs.getString(4));
				provider.setMobileNumber(rs.getString(5));
				provider.setEmailId(rs.getString(6));
				provider.setCity(rs.getString(7));
				provider.setBusinessAddress(rs.getString(8));
				provider.setPassword(rs.getString(9));
				provider.setIsActive(rs.getString(10));
			}
		} catch (SQLException se) {
			throw new IllegalArgumentException(se.getMessage());
		}
		return provider;
	}
	private boolean checkIfProviderExists(String emailId, String city, String serviceType) {
		boolean isExists = false;
		try {
			initializeConnection();
			PreparedStatement ps = con.prepareStatement(ServiceQueries.CHECK_IF_SERVICE_EXISTS);
			ps.setString(1, city);
			ps.setString(2, emailId);
			ps.setString(3, serviceType);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int serviceExists = Integer.parseInt(rs.getString("SERVICEEXISTS"));
				isExists = serviceExists == 1 ? true : false; 
			}
		} catch (SQLException se) {
			throw new IllegalArgumentException(se.getMessage());
		}
		return isExists;

	}

	private String userUpdateQuery(ServiceProvider provider) {
		String sql = "UPDATE service_provider SET ";
		if(provider.getProviderName() != null) {
			sql = sql.concat("providerName = '" + provider.getProviderName() + "',");
		}
		if(provider.getBusinessName() != null) {
			sql = sql.concat(" businessName = '" + provider.getBusinessName() + "',");
		}
		if(provider.getLocation() != null) {
			sql = sql.concat(" location = '" + provider.getLocation() + "',");
		}
		if(provider.getCity() != null) {
			sql = sql.concat(" city = '" + provider.getCity() + "',");
		}
		if(provider.getServiceType() != null) {
			sql = sql.concat("serviceType = '" + provider.getServiceType() + "',");
		}
		if(provider.getMobileNumber() != null) {
			sql = sql.concat(" mobileNumber = '" + provider.getMobileNumber() + "',");
		}
		if(provider.getPassword() != null) {
			sql = sql.concat(" password = '" + HappyServicesUtil.getHashedPassword(provider.getPassword()) + "',");
		}
		if(provider.getBusinessAddress() != null) {
			sql = sql.concat(" businessAddress = '" + provider.getBusinessAddress() + "',");
		}
		sql = sql.concat(" updateTime = now()");
		sql = sql.concat(" WHERE emailId = '" + provider.getEmailId() + "'");
		return sql;
	}

	public boolean saveFeedback(UserFeedback feedback) {
		boolean isFeebackSaved = false;
		try {
			initializeConnection();
			PreparedStatement ps = con.prepareStatement(ServiceQueries.INSERT_FEEDBACK);
			ps.setString(1, feedback.getUserName());
			ps.setString(2, feedback.getUserEmail());
			ps.setString(3, feedback.getUserContact());
			ps.setString(4, feedback.getFeedback());
			ps.setString(5, feedback.getServiceProvider());

			int returnVal = ps.executeUpdate();
			if(returnVal > 0) {
				isFeebackSaved = true;
			}
		} catch(SQLException se) {
			throw new IllegalArgumentException(se.getMessage());
		}

		return isFeebackSaved;
	}

	private Connection initializeConnection() throws SQLException {
		if(con == null) 
			con = dataSource.getConnection();
		return con;
	}

}
