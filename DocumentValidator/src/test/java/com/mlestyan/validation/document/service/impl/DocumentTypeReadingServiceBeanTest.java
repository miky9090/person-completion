package com.mlestyan.validation.document.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlestyan.validation.document.configuration.property.DocumentTypeReadingProperties;
import com.mlestyan.validation.document.model.domain.DocumentType;
import com.mlestyan.validation.document.model.domain.DocumentTypeDictionary;

@ExtendWith(MockitoExtension.class)
@DisplayName("Document type reading service bean tests")
class DocumentTypeReadingServiceBeanTest {
	
	@InjectMocks
	private DocumentTypeReadingServiceBean documentTypeReadingService;
	
	@Mock
	private DocumentTypeReadingProperties documentTypeReadingProperties;
	
	@Mock
	private ObjectMapper objectMapper;

	@Test
	void testReadDocumentTypesNullPath() {
		Mockito.when(documentTypeReadingProperties.getDocumentTypesJsonFilePath()).thenReturn(null);
		
		assertThrows(NullPointerException.class, () -> {
			documentTypeReadingService.readDocumentTypes();
		});
	}

	@Test
	void testReadDocumentTypesFileNotFound() {
		
		Mockito.when(documentTypeReadingProperties.getDocumentTypesJsonFilePath()).thenReturn("test-file-path");
		
		assertThrows(NullPointerException.class, () -> {
			documentTypeReadingService.readDocumentTypes();
		});
	}
	
	@Test
	void testReadDocumentTypesFileNullTypesInFile() {
		
		Mockito.when(documentTypeReadingProperties.getDocumentTypesJsonFilePath()).thenReturn("test-file-path");
		
		DocumentTypeDictionary mockedDocumentTypeDictionary = Mockito.mock(DocumentTypeDictionary.class);
		Mockito.when(mockedDocumentTypeDictionary.getDocumentTypes()).thenReturn(null);
		
		
		try {
			Mockito.when(objectMapper.readValue(ArgumentMatchers.any(File.class), ArgumentMatchers.eq(DocumentTypeDictionary.class))).thenReturn(mockedDocumentTypeDictionary);
			
			List<DocumentType> result = documentTypeReadingService.readDocumentTypes();
			
			assertNotNull(result);
			assertTrue(result.isEmpty());
			
		} 
		catch (IOException e) {
			fail("This shouldn't happen...");
		}
	}
	
	@Test
	void testReadDocumentTypesFileContainsNullTypesInFile() {
		
		Mockito.when(documentTypeReadingProperties.getDocumentTypesJsonFilePath()).thenReturn("test-file-path");
		
		DocumentType state1 = Mockito.mock(DocumentType.class);
		DocumentType state2 = null;
		
		DocumentTypeDictionary mockedDocumentTypeDictionary = Mockito.mock(DocumentTypeDictionary.class);
		Mockito.when(mockedDocumentTypeDictionary.getDocumentTypes()).thenReturn(Arrays.asList(state1, state2));
		
		
		try {
			Mockito.when(objectMapper.readValue(ArgumentMatchers.any(File.class), ArgumentMatchers.eq(DocumentTypeDictionary.class))).thenReturn(mockedDocumentTypeDictionary);
			
			List<DocumentType> result = documentTypeReadingService.readDocumentTypes();
			
			assertNotNull(result);
			assertTrue(result.size() == 1);
			assertEquals(state1, result.get(0));
		} 
		catch (IOException e) {
			fail("This shouldn't happen...");
		}
	}
	
	@Test
	void testReadDocumentTypesFileHasOnlyValidTypes() {
		
		Mockito.when(documentTypeReadingProperties.getDocumentTypesJsonFilePath()).thenReturn("test-file-path");
		
		DocumentType state1 = Mockito.mock(DocumentType.class);
		DocumentType state2 = Mockito.mock(DocumentType.class);
		
		DocumentTypeDictionary mockedDocumentTypeDictionary = Mockito.mock(DocumentTypeDictionary.class);
		Mockito.when(mockedDocumentTypeDictionary.getDocumentTypes()).thenReturn(Arrays.asList(state1, state2));
		
		
		try {
			Mockito.when(objectMapper.readValue(ArgumentMatchers.any(File.class), ArgumentMatchers.eq(DocumentTypeDictionary.class))).thenReturn(mockedDocumentTypeDictionary);
			
			List<DocumentType> result = documentTypeReadingService.readDocumentTypes();
			
			assertNotNull(result);
			assertTrue(result.size() == 2);
			assertEquals(state1, result.get(0));
			assertEquals(state2, result.get(1));
		} 
		catch (IOException e) {
			fail("This shouldn't happen...");
		}
	}
}
