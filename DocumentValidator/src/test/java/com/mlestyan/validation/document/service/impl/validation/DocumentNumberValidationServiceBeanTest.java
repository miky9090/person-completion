package com.mlestyan.validation.document.service.impl.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mlestyan.validation.document.configuration.property.DocumentValidationProperties;
import com.mlestyan.validation.document.model.domain.DocumentType;
import com.mlestyan.validation.document.service.contract.DocumentTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Document number validation service tests")
class DocumentNumberValidationServiceBeanTest {
	
	@InjectMocks
	private DocumentNumberValidationServiceBean documentNumberValidationService;
	
	@Mock
	private DocumentTypeService documentTypeService;
	
	@Mock
	private DocumentValidationProperties documentValidationProperties;
	
	@Test
	void testValidateDocumentNumberNullDocumentType() {
		String documentType = null;
		String documentNumber = "";
		
		boolean result = documentNumberValidationService.validateDocumentNumber(documentType, documentNumber);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateDocumentNumberNullDocumentNumber() {
		String documentType = "";
		String documentNumber = null;
		
		boolean result = documentNumberValidationService.validateDocumentNumber(documentType, documentNumber);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateDocumentNumberDocTypeNotFound() {
		String documentType = "";
		String documentNumber = "";
		
		Mockito.when(documentTypeService.getDocumentTypeById("")).thenReturn(Optional.empty());
		
		boolean result = documentNumberValidationService.validateDocumentNumber(documentType, documentNumber);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateDocumentNumberDocNumberTooLong() {
		String documentType = "";
		String documentNumber = "45645ADSASAD";
		Map<String, String> validationPatterns = new HashMap<>();
		
		Mockito.when(documentTypeService.getDocumentTypeById("")).thenReturn(Optional.of(Mockito.mock(DocumentType.class)));
		Mockito.when(documentValidationProperties.getDocumentNumberValidationPatterns()).thenReturn(validationPatterns);
		Mockito.when(documentValidationProperties.getMaxDocumentNumberLength()).thenReturn(1);
		
		boolean result = documentNumberValidationService.validateDocumentNumber(documentType, documentNumber);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateDocumentNumberDocNumberValid() {
		String documentType = "1";
		String documentNumber = "A1";
		Map<String, String> validationPatterns = new HashMap<>();
		validationPatterns.put("1", "A1");
		
		Mockito.when(documentTypeService.getDocumentTypeById("1")).thenReturn(Optional.of(Mockito.mock(DocumentType.class)));
		Mockito.when(documentValidationProperties.getDocumentNumberValidationPatterns()).thenReturn(validationPatterns);
		
		boolean result = documentNumberValidationService.validateDocumentNumber(documentType, documentNumber);
		
		assertTrue(result);
	}
	
	@Test
	void testValidateDocumentNumberPatternNotFoundDocNumberValid() {
		String documentType = "1";
		String documentNumber = "A1";
		Map<String, String> validationPatterns = new HashMap<>();
		
		Mockito.when(documentTypeService.getDocumentTypeById("1")).thenReturn(Optional.of(Mockito.mock(DocumentType.class)));
		Mockito.when(documentValidationProperties.getDocumentNumberValidationPatterns()).thenReturn(validationPatterns);
		Mockito.when(documentValidationProperties.getMaxDocumentNumberLength()).thenReturn(3);
		
		boolean result = documentNumberValidationService.validateDocumentNumber(documentType, documentNumber);
		
		assertTrue(result);
	}

}
