package com.pk.controller;

import static com.pk.util.HappyServicesUtil.generateJson;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.model.ServiceProvider;
import com.pk.service.ProviderService;

@Controller
@EnableWebMvc
public class ProviderController {

	Logger logger = Logger.getLogger(ProviderController.class);
	private final ProviderService providerService;

	public ProviderController(ProviderService providerService) {
		this.providerService = providerService;
	}

	@RequestMapping(value="/edit", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void registerUser(@RequestBody ServiceProvider userDetail) {
		logger.info("Inside Register method");


	}

	@RequestMapping(value="/provider", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> fetchProviderDetails(@RequestBody String json) {
		logger.info("Inside Provider method");

		ObjectMapper mapper = new ObjectMapper();
		ServiceProvider userDetail = new ServiceProvider();
		try {
			JsonNode obj = mapper.readTree(json);
			String emailId = obj.get("emailId").textValue();

			userDetail = providerService.getProviderDetails(emailId);
			logger.info(userDetail);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return new ResponseEntity<String>(generateJson(userDetail, "providers"), HttpStatus.OK);
	}

}
