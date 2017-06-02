package com.pk.controller;

import static com.pk.util.HappyServicesUtil.generateJson;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.model.ServiceProvider;
import com.pk.model.UserFeedback;
import com.pk.service.UserService;

@Controller
@EnableWebMvc
public class UserController {

	Logger logger = Logger.getLogger(UserController.class);
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value="/register", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerUser(@RequestBody ServiceProvider userDetail) {
		logger.info("Inside Register method");
		String message = userService.registerUser(userDetail);
		return new ResponseEntity<String>(generateJson(message, "response"), HttpStatus.OK);
	}

	@RequestMapping(value="/login", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestBody String json, ModelMap model) {
		logger.info("Inside Login method");

		ObjectMapper mapper = new ObjectMapper();
		String message = "";
		try {
			JsonNode obj = mapper.readTree(json);
			String emailId = obj.get("emailId").textValue();
			String password = obj.get("password").textValue();
			String userType = obj.get("userType").textValue();

			message = userService.login(emailId, password, userType);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return new ResponseEntity<String>(generateJson(message, "response"), HttpStatus.OK);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateUser(@RequestBody ServiceProvider userDetail) {
		logger.info("Inside Update user method");
		ServiceProvider serviceProvider = userService.updateUser(userDetail);
		return new ResponseEntity<String>(generateJson(serviceProvider, "provider"), HttpStatus.OK);
	}
	
	@RequestMapping(value="/activateOrDeactivate", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> activateUser(@RequestBody ServiceProvider userDetail) {
		logger.info("Inside Activate user method");
		List<ServiceProvider> serviceProvider = userService.activateOrDeactivateServiceProvider(userDetail);
		return new ResponseEntity<String>(generateJson(serviceProvider, "providers"), HttpStatus.OK);
	}
	
	@RequestMapping(value="/feedback", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveFeedback(@RequestBody UserFeedback userFeedback) {
		logger.info("Inside Update user method");
		boolean serviceProvider = userService.saveFeedback(userFeedback);
		String response = "Feedback could not be saved.";
		if(serviceProvider) 
			response = "Feedback saved successfully";
		return new ResponseEntity<String>(generateJson(response, "response"), HttpStatus.OK);
	}
	
}
