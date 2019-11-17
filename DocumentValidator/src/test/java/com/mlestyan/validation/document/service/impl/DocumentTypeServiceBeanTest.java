package com.mlestyan.validation.document.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mlestyan.validation.document.model.domain.DocumentType;
import com.mlestyan.validation.document.repository.DocumentTypeRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Document type service bean tests")
class DocumentTypeServiceBeanTest {
	
	@InjectMocks
	private DocumentTypeServiceBean documentTypeServiceBean;
	
	@Mock
	private DocumentTypeRepository documentTypeRepository;

	@Test
	void testSaveDocumentTypesNull() {
		Iterable<DocumentType> documentTypes = null;
		Mockito.when(documentTypeRepository.saveAll(ArgumentMatchers.isNull())).thenThrow(IllegalArgumentException.class);
		
		assertThrows(IllegalArgumentException.class, () -> {
			documentTypeServiceBean.saveDocumentTypes(documentTypes);
		});
	}
	
	@Test
	void testDocumentTypesContainsElements() {
		DocumentType documentType1 = Mockito.mock(DocumentType.class);
		DocumentType documentType2 = Mockito.mock(DocumentType.class);
		List<DocumentType> documentTypes = Arrays.asList(documentType1, documentType2);
		
		Mockito.when(documentTypeRepository.saveAll(documentTypes)).thenReturn(documentTypes);
		
		Iterable<DocumentType> resultDocumentTypes = documentTypeServiceBean.saveDocumentTypes(documentTypes);
		
		assertEquals(documentTypes, resultDocumentTypes);
	}

	@Test
	void testGetDocumentTypeByIdNull() {
		String id = null;
		Mockito.when(documentTypeRepository.findById(ArgumentMatchers.isNull())).thenThrow(IllegalArgumentException.class);
		
		assertThrows(IllegalArgumentException.class, () -> {
			documentTypeServiceBean.getDocumentTypeById(id);
		});
	}
	
	@Test
	void testGetDocumentTypeByIdValidId() {
		String id = "1";
		Optional<DocumentType> optionalDocumentType = Optional.of(new DocumentType());
		Mockito.when(documentTypeRepository.findById(id)).thenReturn(optionalDocumentType);
		
		Optional<DocumentType> resultDocumentType = documentTypeServiceBean.getDocumentTypeById(id);
		
		assertEquals(optionalDocumentType, resultDocumentType);
	}
	
	@Test
	void testGetDocumentTypeByIdInvalidId() {
		String id = "1";
		Mockito.when(documentTypeRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<DocumentType> resultDocumentType = documentTypeServiceBean.getDocumentTypeById(id);
		
		assertEquals(Optional.empty(), resultDocumentType);
	}
}
