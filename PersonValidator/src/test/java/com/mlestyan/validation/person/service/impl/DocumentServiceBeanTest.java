package com.mlestyan.validation.person.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mlestyan.validation.person.configuration.property.DocumentApiProperties;
import com.mlestyan.validation.person.model.domain.DocumentResponse;
import com.mlestyan.validation.person.model.domain.OkmanyDTO;

@ExtendWith(MockitoExtension.class)
@DisplayName("Document service bean tests")
class DocumentServiceBeanTest {
	@InjectMocks
	private DocumentServiceBean documentServiceBean;
	
	@Mock
	private RestTemplate documentRestTemplate;
	
	@Mock
	private DocumentApiProperties documentApiProperties;

	@Test
	void testCompleteDocumentNull() {
		OkmanyDTO document = null;
		assertThrows(NullPointerException.class, () -> documentServiceBean.completeDocument(document));
	}

	@Test
	void testCompleteDocumentContainsNullElement() {
		// Mock that the api path is "/test"
		Mockito.when(documentApiProperties.getDocumentCompletePath()).thenReturn("/test");
		
		// Create a response that the documentRestTemplate will return
		DocumentResponse response = new DocumentResponse();
		ResponseEntity<DocumentResponse> responseEntity = new ResponseEntity<DocumentResponse>(response, HttpStatus.ACCEPTED);
		
		// Mock that when we call "/test", with POST method, with any <HttpEntity<OkmanyDTO>> object, and with the expected return type DocumentResponse, we return responseEntity
		Mockito.when(documentRestTemplate.exchange(
	            ArgumentMatchers.eq("/test"),
	            ArgumentMatchers.eq(HttpMethod.POST),
	            ArgumentMatchers.<HttpEntity<OkmanyDTO>>any(),
	            ArgumentMatchers.eq(DocumentResponse.class))
	        ).thenReturn(responseEntity);
		
		DocumentResponse actualResponse = documentServiceBean.completeDocument(new OkmanyDTO());
		
		assertEquals(response, actualResponse);
	}
}
