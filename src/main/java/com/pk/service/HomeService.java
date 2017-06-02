package com.pk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pk.dao.UserServiceRepositoryImpl;
import com.pk.model.ServiceProvider;

@Service
public class HomeService {

	private static final String APPLICATION_NAME = "Happy Services"; 
	private final UserServiceRepositoryImpl userServiceRepositoryImpl;
	
	public HomeService(UserServiceRepositoryImpl userServiceRepositoryImpl) {
		this.userServiceRepositoryImpl = userServiceRepositoryImpl;

	}	
	public String returnMessage() {
		return APPLICATION_NAME;
	}

	public List<ServiceProvider> searchService(String city, String location, String serviceType) {
		return userServiceRepositoryImpl.getServiceList(city, location, serviceType);
	}
	
	public List<ServiceProvider> fetchAllServiceProviders() {
		return userServiceRepositoryImpl.getServiceList();
	}
}
