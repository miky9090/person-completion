package com.mlestyan.validation.person.service.impl;

import static com.mlestyan.validation.person.service.impl.ValidationMessageConstants.INVALID_AGE;
import static com.mlestyan.validation.person.service.impl.ValidationMessageConstants.INVALID_BIRTH_NAME;
import static com.mlestyan.validation.person.service.impl.ValidationMessageConstants.INVALID_DOCUMENTS_LIST;
import static com.mlestyan.validation.person.service.impl.ValidationMessageConstants.INVALID_GENDER;
import static com.mlestyan.validation.person.service.impl.ValidationMessageConstants.INVALID_MOTHERS_NAME;
import static com.mlestyan.validation.person.service.impl.ValidationMessageConstants.INVALID_NAME;
import static com.mlestyan.validation.person.service.impl.ValidationMessageConstants.INVALID_NATIONALITY;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlestyan.validation.person.model.domain.DocumentResponse;
import com.mlestyan.validation.person.model.domain.ErrorContext;
import com.mlestyan.validation.person.model.domain.OkmanyDTO;
import com.mlestyan.validation.person.model.domain.PersonResponse;
import com.mlestyan.validation.person.model.domain.State;
import com.mlestyan.validation.person.model.domain.SzemelyDTO;
import com.mlestyan.validation.person.service.contract.DocumentService;
import com.mlestyan.validation.person.service.contract.PersonService;
import com.mlestyan.validation.person.service.contract.StateService;
import com.mlestyan.validation.person.service.contract.validation.AgeValidationService;
import com.mlestyan.validation.person.service.contract.validation.DocumentsValidationService;
import com.mlestyan.validation.person.service.contract.validation.GenderValidationService;
import com.mlestyan.validation.person.service.contract.validation.NameValidationService;

@Service
public class PersonServiceBean implements PersonService {
	private StateService stateService;
	private DocumentService documentService;
	private AgeValidationService ageValidationService;
	private DocumentsValidationService documentsValidationService;
	private NameValidationService nameValidationService;
	private GenderValidationService genderValidationService;
	
	@Autowired
	public PersonServiceBean(StateService stateService, DocumentService documentService, AgeValidationService ageValidationService, DocumentsValidationService documentsValidationService, NameValidationService nameValidationService, GenderValidationService genderValidationService) {
		this.stateService = stateService;
		this.documentService = documentService;
		this.ageValidationService = ageValidationService;
		this.documentsValidationService = documentsValidationService;
		this.nameValidationService = nameValidationService;
		this.genderValidationService = genderValidationService;
	}

	/**
	 * Completes the provided {@link SzemelyDTO} object and collects the error codes in an array.
	 * @param person the input {@link SzemelyDTO}
	 * @return the {@link PersonResponse} containing the completed {@link SzemelyDTO} and the error codes
	 * */
	@Override
	public PersonResponse completePerson(SzemelyDTO person) {
		List<ErrorContext> errors = new ArrayList<>();
		PersonResponse response = new PersonResponse();
		
		this.processNames(person, errors);
		this.processAge(person, errors);
		this.processGender(person, errors);
		this.processNationality(person, errors);
		this.processDocumentsList(person, errors);
		
		response.setErrors(errors);
		response.setPerson(person);
		return response;
	}
	
	/**
	 * Checks if a {@link SzemelyDTO} has valid names and collects the error codes in the provided {@link List}.
	 * @param person the input person
	 * @param errors the input errors collector list
	 * */
	private void processNames(SzemelyDTO person, List<ErrorContext> errors) {
		if(person.getVisNev() == null || !nameValidationService.validateName(person.getVisNev())) {
			errors.add(new ErrorContext(INVALID_NAME));
		}
		if(person.getaNev() == null || !nameValidationService.validateName(person.getaNev())) {
			errors.add(new ErrorContext(INVALID_MOTHERS_NAME));
		}
		if(person.getSzulNev() == null || !nameValidationService.validateName(person.getSzulNev())) {
			errors.add(new ErrorContext(INVALID_BIRTH_NAME));
		}
	}
	
	/**
	 * Checks if a {@link SzemelyDTO} has valid age and collects the error codes in the provided {@link List}.
	 * @param person the input person
	 * @param errors the input errors collector list
	 * */
	private void processAge(SzemelyDTO person, List<ErrorContext> errors) {
		if(person.getSzulDat() == null || !ageValidationService.validateAge(person.getSzulDat())) {
			errors.add(new ErrorContext(INVALID_AGE));
		}
	}
	
	/**
	 * Checks if a {@link SzemelyDTO} has valid gender and collects the error codes in the provided {@link List}.
	 * @param person the input person
	 * @param errors the input errors collector list
	 * */
	private void processGender(SzemelyDTO person, List<ErrorContext> errors) {
		if(person.getNeme() == null || !genderValidationService.validateGender(person.getNeme())) {
			errors.add(new ErrorContext(INVALID_GENDER));
		}
	}
	
	/**
	 * Checks if a {@link SzemelyDTO} has valid nationality and collects the error codes in the provided {@link List}.
	 * @param person the input person
	 * @param errors the input errors collector list
	 * */
	private void processNationality(SzemelyDTO person, List<ErrorContext> errors) {
		if(person.getAllampKod() == null || person.getAllampKod().length() != 3) {
			errors.add(new ErrorContext(INVALID_NATIONALITY));
		}
		else {
			Optional<State> optionalState = stateService.getStateById(person.getAllampKod());
			if(optionalState.isPresent()) {
				person.setAllampDekod(optionalState.get().getAllampolgarsag());
			}
			else {
				errors.add(new ErrorContext(INVALID_NATIONALITY));
			}
		}
	}
	
	/**
	 * Checks if a {@link SzemelyDTO} has valid documents and collects the error codes in the provided {@link List}.
	 * @param person the input person
	 * @param errors the input errors collector list
	 * */
	private void processDocumentsList(SzemelyDTO person, List<ErrorContext> errors) {
		if(person.getOkmLista() == null) {
			errors.add(new ErrorContext(INVALID_DOCUMENTS_LIST));
		}
		else {
			List<OkmanyDTO> completedDocuments = person.getOkmLista().stream()
					.filter(Objects::nonNull) // Remove null elements
					.map(documentService::completeDocument) // Retrieve the completed documents (of type DocumentResponse) from documents api, with errors
					.peek(documentResponse -> errors.addAll(documentResponse.getErrors())) // Add all the errors from documents api to the errors list
					.map(DocumentResponse::getDocument) // Keep only the OkmanyDTO-s from the response
					.collect(Collectors.toList()); // Collect the OkmanyDTO-s into a list
			person.setOkmLista(completedDocuments);
			
			if(!documentsValidationService.validateDocuments(completedDocuments)) {
				errors.add(new ErrorContext(INVALID_DOCUMENTS_LIST));
			}
		}
	}

}
