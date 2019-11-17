package com.mlestyan.validation.person.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mlestyan.validation.person.model.domain.DocumentResponse;
import com.mlestyan.validation.person.model.domain.OkmanyDTO;
import com.mlestyan.validation.person.model.domain.PersonResponse;
import com.mlestyan.validation.person.model.domain.State;
import com.mlestyan.validation.person.model.domain.SzemelyDTO;
import com.mlestyan.validation.person.service.impl.validation.AgeValidationServiceBean;
import com.mlestyan.validation.person.service.impl.validation.DocumentsValidationServiceBean;
import com.mlestyan.validation.person.service.impl.validation.GenderValidationServiceBean;
import com.mlestyan.validation.person.service.impl.validation.NameValidationServiceBean;

@ExtendWith(MockitoExtension.class)
@DisplayName("Person service bean tests")
class PersonServiceBeanTest {
	
	@InjectMocks
	private PersonServiceBean personService;
	
	@Mock
	private StateServiceBean stateService;
	
	@Mock
	private DocumentServiceBean documentService;
	
	@Mock
	private AgeValidationServiceBean ageValidationService;
	
	@Mock
	private DocumentsValidationServiceBean documentsValidationService;
	
	@Mock
	private NameValidationServiceBean nameValidationService;
	
	@Mock
	private GenderValidationServiceBean genderValidationService;
	
	private SzemelyDTO person;
	
	@BeforeEach
	private void setup() {
		this.person = new SzemelyDTO();
		this.person.setVisNev("Asd Asd");
		this.person.setaNev("Asd Asd");
		this.person.setSzulNev("Asd Asd");
		this.person.setSzulDat(new GregorianCalendar(1990, 1, 1).getTime());
		this.person.setNeme("F");
		this.person.setAllampKod("HUN");
		
		List<OkmanyDTO> documents = new ArrayList<>();
		this.person.setOkmLista(documents);
	}
	
	@Test
	void testCompletePersonNullInput() {
		SzemelyDTO person = null;
		
		assertThrows(NullPointerException.class, () -> {
			personService.completePerson(person);
		});
	}
	
	@Test
	void testCompletePersonValidInput() {
		SzemelyDTO person = this.person;
		
		String nationality = "MAGYAR ÁLLAMPOLGÁRSÁG";
		State mockedState = Mockito.mock(State.class);
		Mockito.when(mockedState.getAllampolgarsag()).thenReturn(nationality);
		
		Mockito.when(nameValidationService.validateName(ArgumentMatchers.anyString())).thenReturn(true);
		Mockito.when(ageValidationService.validateAge(person.getSzulDat())).thenReturn(true);
		Mockito.when(genderValidationService.validateGender(ArgumentMatchers.anyString())).thenReturn(true);
		Mockito.when(documentsValidationService.validateDocuments(ArgumentMatchers.any())).thenReturn(true);
		Mockito.when(stateService.getStateById(ArgumentMatchers.anyString())).thenReturn(Optional.of(mockedState));
		
		PersonResponse response = personService.completePerson(person);
		
		assertNotNull(response);
		assertNotNull(response.getPerson());
		assertNotNull(response.getErrors());
		assertTrue(response.getErrors().size() == 0);
		
		String decodedNationality = response.getPerson().getAllampDekod();
		assertEquals(nationality, decodedNationality);
		assertNotNull(decodedNationality);
	}
	
	@Test
	void testCompletePersonWrongInputs() {
		SzemelyDTO person = this.person;
		OkmanyDTO mockedDocument1 = Mockito.mock(OkmanyDTO.class);
		OkmanyDTO mockedDocument2 = Mockito.mock(OkmanyDTO.class);
		
		person.setOkmLista(Arrays.asList(mockedDocument1, mockedDocument2));
		
		DocumentResponse mockedDocumentResponse = Mockito.mock(DocumentResponse.class);
		Mockito.when(mockedDocumentResponse.getErrors()).thenReturn(Collections.emptyList());
		
		Mockito.when(nameValidationService.validateName(ArgumentMatchers.anyString())).thenReturn(false);
		Mockito.when(ageValidationService.validateAge(person.getSzulDat())).thenReturn(false);
		Mockito.when(genderValidationService.validateGender(ArgumentMatchers.anyString())).thenReturn(false);
		Mockito.when(documentsValidationService.validateDocuments(ArgumentMatchers.any())).thenReturn(false);
		Mockito.when(stateService.getStateById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		Mockito.when(documentService.completeDocument(ArgumentMatchers.any(OkmanyDTO.class))).thenReturn(mockedDocumentResponse);
		
		PersonResponse response = personService.completePerson(person);
		
		assertNotNull(response);
		assertNotNull(response.getPerson());
		assertNotNull(response.getErrors());
		assertTrue(response.getErrors().size() == 7);
		
		String decodedNationality = response.getPerson().getAllampDekod();
		assertNull(decodedNationality);
	}
	
	@Test
	void testCompletePersonNullInputs() {
		SzemelyDTO person = this.person;
		person.setOkmLista(null);
		person.setVisNev(null);
		person.setSzulNev(null);
		person.setaNev(null);
		person.setSzulDat(null);
		person.setNeme(null);
		person.setAllampKod(null);
		person.setOkmLista(null);
		
		PersonResponse response = personService.completePerson(person);
		
		assertNotNull(response);
		assertNotNull(response.getPerson());
		assertNotNull(response.getErrors());
		assertTrue(response.getErrors().size() == 7);
		
		String decodedNationality = response.getPerson().getAllampDekod();
		assertNull(decodedNationality);
	}
	
	@Test
	void testCompletePersonInvalidNationality() {
		SzemelyDTO person = this.person;
		person.setOkmLista(null);
		person.setVisNev(null);
		person.setSzulNev(null);
		person.setaNev(null);
		person.setSzulDat(null);
		person.setNeme(null);
		person.setAllampKod("HUNGARY");
		person.setOkmLista(null);
		
		PersonResponse response = personService.completePerson(person);
		
		assertNotNull(response);
		assertNotNull(response.getPerson());
		assertNotNull(response.getErrors());
		assertTrue(response.getErrors().size() == 7);
		
		String decodedNationality = response.getPerson().getAllampDekod();
		assertNull(decodedNationality);
	}

}
