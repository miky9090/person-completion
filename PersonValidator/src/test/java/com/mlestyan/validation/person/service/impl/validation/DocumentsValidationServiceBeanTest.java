package com.mlestyan.validation.person.service.impl.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mlestyan.validation.person.model.domain.OkmanyDTO;

@ExtendWith(MockitoExtension.class)
@DisplayName("Documents validation service tests")
class DocumentsValidationServiceBeanTest {
	
	@InjectMocks
	private DocumentsValidationServiceBean documentsValidationService;

	@Test
	void testValidateDocumentsMoreThanOneValidPerType() {
		OkmanyDTO document1 = Mockito.mock(OkmanyDTO.class);
		OkmanyDTO document2 = Mockito.mock(OkmanyDTO.class);
		
		Mockito.when(document1.isErvenyes()).thenReturn(true);
		Mockito.when(document2.isErvenyes()).thenReturn(true);
		
		Mockito.when(document1.getOkmTipus()).thenReturn("1");
		Mockito.when(document2.getOkmTipus()).thenReturn("1");
		
		boolean result = documentsValidationService.validateDocuments(Arrays.asList(document1, document2));
		
		assertFalse(result);
	}
	
	@Test
	void testValidateDocumentsOneValidPerType() {
		OkmanyDTO document1 = Mockito.mock(OkmanyDTO.class);
		OkmanyDTO document2 = Mockito.mock(OkmanyDTO.class);
		
		Mockito.when(document1.isErvenyes()).thenReturn(true);
		Mockito.when(document2.isErvenyes()).thenReturn(false);
		
		Mockito.when(document1.getOkmTipus()).thenReturn("1");
		
		boolean result = documentsValidationService.validateDocuments(Arrays.asList(document1, document2));
		
		assertTrue(result);
	}
	
	@Test
	void testValidateDocumentsNotValids() {
		OkmanyDTO document1 = Mockito.mock(OkmanyDTO.class);
		OkmanyDTO document2 = Mockito.mock(OkmanyDTO.class);
		
		Mockito.when(document1.isErvenyes()).thenReturn(false);
		Mockito.when(document2.isErvenyes()).thenReturn(false);
		
		boolean result = documentsValidationService.validateDocuments(Arrays.asList(document1, document2));
		
		assertTrue(result);
	}
	
	@Test
	void testValidateDocumentsEachValidDifferentType() {
		OkmanyDTO document1 = Mockito.mock(OkmanyDTO.class);
		OkmanyDTO document2 = Mockito.mock(OkmanyDTO.class);
		
		Mockito.when(document1.isErvenyes()).thenReturn(true);
		Mockito.when(document2.isErvenyes()).thenReturn(true);
		
		Mockito.when(document1.getOkmTipus()).thenReturn("1");
		Mockito.when(document2.getOkmTipus()).thenReturn("2");
		
		boolean result = documentsValidationService.validateDocuments(Arrays.asList(document1, document2));
		
		assertTrue(result);
	}

}
