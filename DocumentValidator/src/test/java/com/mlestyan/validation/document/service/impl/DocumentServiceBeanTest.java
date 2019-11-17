package com.mlestyan.validation.document.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mlestyan.validation.document.model.domain.DocumentResponse;
import com.mlestyan.validation.document.model.domain.ErrorContext;
import com.mlestyan.validation.document.model.domain.OkmanyDTO;
import com.mlestyan.validation.document.service.contract.validation.DocumentImageValidationService;
import com.mlestyan.validation.document.service.contract.validation.DocumentNumberValidationService;
import com.mlestyan.validation.document.service.contract.validation.DocumentTypeValidationService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Document service bean tests")
class DocumentServiceBeanTest {
	
	@InjectMocks
	private DocumentServiceBean documentService;
	
	@Mock
	private DocumentTypeValidationService documentTypeValidationService;
	
	@Mock
	private DocumentNumberValidationService documentNumberValidationService;
	
	@Mock
	private DocumentImageValidationService documentImageValidationService;

	@Test
	void testCompleteDocumentNull() {
		OkmanyDTO document = null;
		
		assertThrows(NullPointerException.class, () -> {
			documentService.completeDocument(document);
		});
	}
	
	@Test
	void testCompleteDocumentNullAttributes() {
		OkmanyDTO document = new OkmanyDTO();
		
		DocumentResponse response = documentService.completeDocument(document);
		List<ErrorContext> errors = response.getErrors();
		OkmanyDTO completedDocument = response.getDocument();
		
		assertTrue(errors.size() == 3);
		assertEquals(document, completedDocument);
	}
	
	@Test
	void testCompleteDocumentWrongInputs() {
		OkmanyDTO document = new OkmanyDTO();
		document.setOkmTipus("1");
		document.setOkmanyKep(new byte[] {});
		
		
		Mockito.when(documentTypeValidationService.validateDocumentType(ArgumentMatchers.anyString())).thenReturn(false);
		Mockito.when(documentImageValidationService.validateDocumentImage(ArgumentMatchers.any())).thenReturn(false);
		
		DocumentResponse response = documentService.completeDocument(document);
		List<ErrorContext> errors = response.getErrors();
		
		assertTrue(errors.size() == 3);
	}
	
	@Test
	void testCompleteDocumentWrongInputsHasValidDocumentType() {
		OkmanyDTO document = new OkmanyDTO();
		document.setOkmTipus("1");
		//document.setOkmanySzam("123");
		document.setOkmanyKep(new byte[] {});
		
		
		Mockito.when(documentTypeValidationService.validateDocumentType(ArgumentMatchers.anyString())).thenReturn(true);
		Mockito.when(documentImageValidationService.validateDocumentImage(ArgumentMatchers.any())).thenReturn(false);
		
		DocumentResponse response = documentService.completeDocument(document);
		List<ErrorContext> errors = response.getErrors();
		
		assertTrue(errors.size() == 3);
	}
	
	@Test
	void testCompleteDocumentWrongInputsHasValidDocumentTypeInvalidDocNumber() {
		OkmanyDTO document = new OkmanyDTO();
		document.setOkmTipus("1");
		document.setOkmanySzam("123");
		document.setOkmanyKep(new byte[] {});
		
		
		Mockito.when(documentTypeValidationService.validateDocumentType(ArgumentMatchers.anyString())).thenReturn(true);
		Mockito.when(documentImageValidationService.validateDocumentImage(ArgumentMatchers.any())).thenReturn(false);
		Mockito.when(documentNumberValidationService.validateDocumentNumber(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(false);
		
		DocumentResponse response = documentService.completeDocument(document);
		List<ErrorContext> errors = response.getErrors();
		
		assertTrue(errors.size() == 3);
	}
	
	@Test
	void testCompleteDocumentWrongInputsHasValidDocumentTypeValidDocNumber() {
		OkmanyDTO document = new OkmanyDTO();
		document.setOkmTipus("1");
		document.setOkmanySzam("123");
		document.setOkmanyKep(new byte[] {});
		
		
		Mockito.when(documentTypeValidationService.validateDocumentType(ArgumentMatchers.anyString())).thenReturn(true);
		Mockito.when(documentImageValidationService.validateDocumentImage(ArgumentMatchers.any())).thenReturn(false);
		Mockito.when(documentNumberValidationService.validateDocumentNumber(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
		
		DocumentResponse response = documentService.completeDocument(document);
		List<ErrorContext> errors = response.getErrors();
		
		assertTrue(errors.size() == 2);
	}
	
	@Test
	void testCompleteDocumentWrongInputsHasValidDocumentImage() {
		OkmanyDTO document = new OkmanyDTO();
		document.setOkmTipus("1");
		document.setOkmanySzam("123");
		document.setOkmanyKep(new byte[] {});
		
		
		Mockito.when(documentTypeValidationService.validateDocumentType(ArgumentMatchers.anyString())).thenReturn(true);
		Mockito.when(documentImageValidationService.validateDocumentImage(ArgumentMatchers.any())).thenReturn(true);
		Mockito.when(documentNumberValidationService.validateDocumentNumber(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
		
		DocumentResponse response = documentService.completeDocument(document);
		List<ErrorContext> errors = response.getErrors();
		
		assertTrue(errors.size() == 1);
	}
	
	@Test
	void testCompleteDocumentWrongInputsHasInvalidExpirationDate() {
		OkmanyDTO document = new OkmanyDTO();
		document.setOkmTipus("1");
		document.setOkmanySzam("123");
		Date expirationDate = Date.from(LocalDate.now().minus(50, ChronoUnit.YEARS).atStartOfDay(ZoneId.systemDefault()).toInstant());
		document.setLejarDat(expirationDate);
		document.setOkmanyKep(new byte[] {});
		
		
		Mockito.when(documentTypeValidationService.validateDocumentType(ArgumentMatchers.anyString())).thenReturn(true);
		Mockito.when(documentImageValidationService.validateDocumentImage(ArgumentMatchers.any())).thenReturn(true);
		Mockito.when(documentNumberValidationService.validateDocumentNumber(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
		
		DocumentResponse response = documentService.completeDocument(document);
		List<ErrorContext> errors = response.getErrors();
		OkmanyDTO completedDocument = response.getDocument();
		
		assertTrue(errors.size() == 0);
		assertFalse(completedDocument.isErvenyes());
	}

	@Test
	void testCompleteDocumentWrongInputsHasValidExpirationDate() {
		OkmanyDTO document = new OkmanyDTO();
		document.setOkmTipus("1");
		document.setOkmanySzam("123");
		Date expirationDate = Date.from(LocalDate.now().plus(50, ChronoUnit.YEARS).atStartOfDay(ZoneId.systemDefault()).toInstant());
		document.setLejarDat(expirationDate);
		document.setOkmanyKep(new byte[] {});
		
		
		Mockito.when(documentTypeValidationService.validateDocumentType(ArgumentMatchers.anyString())).thenReturn(true);
		Mockito.when(documentImageValidationService.validateDocumentImage(ArgumentMatchers.any())).thenReturn(true);
		Mockito.when(documentNumberValidationService.validateDocumentNumber(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
		
		DocumentResponse response = documentService.completeDocument(document);
		List<ErrorContext> errors = response.getErrors();
		OkmanyDTO completedDocument = response.getDocument();
		
		assertTrue(errors.size() == 0);
		assertTrue(completedDocument.isErvenyes());
	}
	
	@Test
	void testCompleteDocumentWrongInputsHasValidExpirationDateHasErrors() {
		OkmanyDTO document = new OkmanyDTO();
		document.setOkmTipus("1");
		document.setOkmanySzam("123");
		Date expirationDate = Date.from(LocalDate.now().plus(50, ChronoUnit.YEARS).atStartOfDay(ZoneId.systemDefault()).toInstant());
		document.setLejarDat(expirationDate);
		document.setOkmanyKep(new byte[] {});
		
		
		Mockito.when(documentTypeValidationService.validateDocumentType(ArgumentMatchers.anyString())).thenReturn(false);
		Mockito.when(documentImageValidationService.validateDocumentImage(ArgumentMatchers.any())).thenReturn(true);
		
		DocumentResponse response = documentService.completeDocument(document);
		List<ErrorContext> errors = response.getErrors();
		OkmanyDTO completedDocument = response.getDocument();
		
		assertTrue(errors.size() == 1);
		assertFalse(completedDocument.isErvenyes());
	}
}
