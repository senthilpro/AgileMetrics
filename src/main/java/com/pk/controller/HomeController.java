package com.pk.controller;

import static com.pk.util.HappyServicesUtil.generateJson;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pk.model.ServiceProvider;
import com.pk.service.HomeService;

@Controller
public class HomeController {
	private Logger logger = Logger.getLogger(HomeController.class);

	private final HomeService homeService;

	public HomeController(HomeService homeService) {
		this.homeService = homeService;
	}

	@RequestMapping("/")
	public String happyServiceHome() {
		return "home";
	}

	@RequestMapping(value="/search", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> searchService(@RequestParam(value="city", required=true) String city, 
			@RequestParam(value="location", required=true) String location, @RequestParam(value="service", required=true) String serviceType) {
		logger.info(String.format("Searching services for location [%s], city [%s] and type [%s]", location, city, serviceType));

		List<ServiceProvider> serviceProviderList = new ArrayList<>();
		serviceProviderList = homeService.searchService(city, location, serviceType);
		String response = generateJson(serviceProviderList, "providers");
		
		logger.info(response);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/searchAll", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllServices() {
		logger.info(String.format("Fetchin all Services"));
		List<ServiceProvider> serviceProviderList = new ArrayList<>();
		serviceProviderList = homeService.fetchAllServiceProviders();
		String response = generateJson(serviceProviderList, "providers");
		
		logger.info(response);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}
