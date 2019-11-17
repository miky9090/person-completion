package com.mlestyan.validation.person.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mlestyan.validation.person.client.DocumentServiceClient;
import com.mlestyan.validation.person.configuration.property.DocumentApiProperties;
import com.mlestyan.validation.person.model.domain.DocumentResponse;
import com.mlestyan.validation.person.model.domain.OkmanyDTO;
import com.mlestyan.validation.person.service.contract.DocumentService;

@Service
@Profile("discovery")
@EnableConfigurationProperties(DocumentApiProperties.class)
public class DocumentServiceBeanWithDiscovery implements DocumentService {
	private DocumentServiceClient documentServiceClient;
	
	@Autowired
	public DocumentServiceBeanWithDiscovery(DocumentServiceClient documentServiceClient) {
		this.documentServiceClient = documentServiceClient;
	}

	/**
	 * Calls document api to validate and fill the provided document object.
	 * @param document the input document
	 * @return the resolved {@link DocumentResponse}
	 * */
	@Override
	public DocumentResponse completeDocument(OkmanyDTO document) {
		if(document == null) {
			// If the document is null, we shouldn't send an HTTP request to the documents api.
			// We throw NullPointerException here manually to prevent unncessary calls to the other end.
			throw new NullPointerException("Unable to complete document! Document cannot be null!");
		}
		DocumentResponse response = this.documentServiceClient.completeocument(document);
		return response;
	}

}
