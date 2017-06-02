package com.pk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pk.dao.ProviderServiceRepositoryImpl;
import com.pk.model.ServiceProvider;

@Service
public class ProviderService {

	private final ProviderServiceRepositoryImpl providerServiceRepositoryImpl;
	
	public ProviderService(ProviderServiceRepositoryImpl providerServiceRepositoryImpl) {
		this.providerServiceRepositoryImpl = providerServiceRepositoryImpl;
	}

	public ServiceProvider getProviderDetails(String emailId) {
		 List<ServiceProvider> providerList = providerServiceRepositoryImpl.getProviderDetails(emailId);
		 ServiceProvider provider = providerList.get(0);
		 String serviceType = "";
		 for(ServiceProvider serviceProvider : providerList) {
			  serviceType = serviceType.concat(serviceProvider.getServiceType()+ ",");
		 }
		 serviceType = serviceType.substring(0, serviceType.length()-1);
		 provider.setServiceType(serviceType);
		 return provider;
	}
}
