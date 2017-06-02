package com.pk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.pk.constants.ServiceQueries;
import com.pk.model.ServiceProvider;

public class ProviderServiceRepositoryImpl {
	Logger logger = Logger.getLogger(ProviderServiceRepositoryImpl.class);

	private final DataSource dataSource;
	private Connection con;
	public ProviderServiceRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<ServiceProvider> getProviderDetails(String emailId) {

		List<ServiceProvider> providerList = new ArrayList<>();
		try {
			initializeConnection();
			PreparedStatement ps = con.prepareStatement(ServiceQueries.GET_PROVIDER_DETAILS);
			ps.setString(1, emailId);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				ServiceProvider provider = new ServiceProvider();
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
				providerList.add(provider);
			}
		} catch (SQLException se) {
			throw new IllegalArgumentException(se.getMessage());
		}
		return providerList;
	}

	private Connection initializeConnection() throws SQLException {
		if(con == null) 
			con = dataSource.getConnection();
		return con;
	}

}
