package com.flas.service;

import org.springframework.boot.ssl.SslBundles;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
/*
 * Used to add ssl certificate
 */
@Service
public class ResourceService {
	
	private final RestTemplate restTemplate;

	public ResourceService(RestTemplateBuilder restTemplateBuilder, SslBundles sslBundles){
		this.restTemplate = restTemplateBuilder.setSslBundle(sslBundles.getBundle("server")).build();
	}

}