package com.mlestyan.validation.document.service.impl.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mlestyan.validation.document.model.domain.DocumentType;
import com.mlestyan.validation.document.service.contract.DocumentTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Document type validation service tests")
class DocumentTypeValidationServiceBeanTest {
	
	@InjectMocks
	private DocumentTypeValidationServiceBean documentTypeValidationService;
	
	@Mock
	private DocumentTypeService documentTypeService;
	
	
	@Test
	void testValidateDocumentTypeValid() {
		String documentType = "1";
		DocumentType mockedDocumentType = Mockito.mock(DocumentType.class);
		Mockito.when(documentTypeService.getDocumentTypeById("1")).thenReturn(Optional.of(mockedDocumentType));
		
		boolean result = documentTypeValidationService.validateDocumentType(documentType);
		
		assertTrue(result);
	}

	@Test
	void testValidateDocumentTypeNull() {
		String documentType = null;

		boolean result = documentTypeValidationService.validateDocumentType(documentType);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateDocumentTypeEmpty() {
		String documentType = "";
		
		boolean result = documentTypeValidationService.validateDocumentType(documentType);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateDocumentTypeInvalid() {
		String documentType = "XYZ";
		
		boolean result = documentTypeValidationService.validateDocumentType(documentType);
		
		assertFalse(result);
	}
}
