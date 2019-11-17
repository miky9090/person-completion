package com.mlestyan.validation.person.configuration.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.mlestyan.validation.person.configuration.property.DocumentApiProperties;

@Configuration
@EnableConfigurationProperties(DocumentApiProperties.class)
public class RestTemplateConfiguration {
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Autowired
	private DocumentApiProperties documentApiProperties;

	/**
	 * Prepares a rest template bean to be used when communicating with the
	 * documents' API
	 */
	@Bean
	public RestTemplate documentRestTemplate() {
		return restTemplateBuilder.rootUri(documentApiProperties.getUrl()).build();
	}
}
