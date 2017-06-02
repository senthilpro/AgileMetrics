package com.pk.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.pk.dao.UserServiceRepositoryImpl;
import com.pk.model.ServiceProvider;
import com.pk.model.UserFeedback;

public class UserService {
	Logger logger = Logger.getLogger(UserService.class);

	private final UserServiceRepositoryImpl repositoryImpl;

	public UserService(UserServiceRepositoryImpl repositoryImpl) {
		this.repositoryImpl = repositoryImpl;
	}

	public String registerUser(ServiceProvider provider) {
		logger.info("Registering service provider : " + provider);
		boolean isRegistered = repositoryImpl.registerUser(provider);
		String message = "";
		if(isRegistered)
			message = "Registration Succesful!";
		else
			message = "Registration Unsuccesful!";
		return message;
	}
	
	public ServiceProvider updateUser(ServiceProvider provider) {
		logger.info("Registering service provider : " + provider);
		ServiceProvider serviceProvider = repositoryImpl.updateUser(provider);
		if(serviceProvider!=null)
			logger.info("Registering service provider : " + provider + " successful.");
		return serviceProvider;
	}
	
	
	public List<ServiceProvider> activateOrDeactivateServiceProvider(ServiceProvider provider) {
		logger.info("Activating service provider : " + provider);
		List<ServiceProvider> serviceProviderList = repositoryImpl.activateOrDeactivateUser(provider);
		return serviceProviderList;
	}

	public String login(String emailId, String password, String userType) {
		logger.info("Loggin in " +userType  + " with emailId " + emailId);
		boolean validUser = repositoryImpl.loginUser(emailId, password, userType);
		String message = "";
		if(validUser)
			message = "Login Succesful!";
		else
			message = "Invalid or bad emailId and password.";
		return message;
	}
	
	public boolean saveFeedback(UserFeedback feedback) {
		logger.info("Registering user feedback : " + feedback);
		boolean isFeedbackRegistered = repositoryImpl.saveFeedback(feedback);
		if(isFeedbackRegistered)
			logger.info("Registering user feedback successful.");
		else
			logger.error("Feedback was not saved");
		return isFeedbackRegistered;
	}
}
