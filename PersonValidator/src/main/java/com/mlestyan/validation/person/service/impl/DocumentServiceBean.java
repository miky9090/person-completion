package com.mlestyan.validation.person.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mlestyan.validation.person.configuration.property.DocumentApiProperties;
import com.mlestyan.validation.person.model.domain.DocumentResponse;
import com.mlestyan.validation.person.model.domain.OkmanyDTO;
import com.mlestyan.validation.person.service.contract.DocumentService;

@Service
@EnableConfigurationProperties(DocumentApiProperties.class)
public class DocumentServiceBean implements DocumentService {
	private DocumentApiProperties documentApiProperties;
	private RestTemplate documentRestTemplate;
	
	@Autowired
	public DocumentServiceBean(RestTemplate documentRestTemplate, DocumentApiProperties documentApiProperties) {
		this.documentRestTemplate = documentRestTemplate;
		this.documentApiProperties = documentApiProperties;
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
		ResponseEntity<DocumentResponse> response = this.documentRestTemplate.exchange(documentApiProperties.getDocumentCompletePath(), HttpMethod.POST, new HttpEntity<OkmanyDTO>(document), DocumentResponse.class);
		return response.getBody();
	}

}
