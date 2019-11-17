package com.mlestyan.validation.person.controller.rest;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlestyan.validation.person.model.domain.ErrorContext;
import com.mlestyan.validation.person.model.domain.PersonResponse;
import com.mlestyan.validation.person.model.domain.SzemelyDTO;
import com.mlestyan.validation.person.service.contract.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Api("Person completion service")
@RestController
@RequestMapping("/")
public class PersonCompleterRest {
	@Autowired
	private PersonService personService;
	
	@Autowired
	private MessageSource messageSource;

	@ApiOperation(value = "Validate and complete a Person object", response = PersonResponse.class, consumes = "application/json", produces = "application/json", httpMethod = "POST")
	@PostMapping(value = "/complete-person", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PersonResponse> completePerson(@ApiParam(required = true, value = "The person to be validated and completed") @RequestBody SzemelyDTO person, @ApiIgnore Locale locale) {
		PersonResponse response = personService.completePerson(person);
		
		// We shouldn't provide error messages directly from the business layer,
		// also there is no Locale information available there.
		// This leads us to process the data first in the business layer, 
		// then find the appropriate error messages in the presentation layer.
		// Another option is to create a component that resolves the error messages, 
		// but it's not worth it, since we have only 1 endpoint.
		List<ErrorContext> resolvedErrorMessages = response.getErrors().stream()
			.map(errorContext -> {
				if(errorContext.getResolvedMessage() == null) {
					errorContext.setResolvedMessage(messageSource.getMessage(errorContext.getErrorCode(), null, locale));
				}
				return errorContext;
			})
			.collect(Collectors.toList());
		
		response.setErrors(resolvedErrorMessages);
		
		return ResponseEntity.ok(response);
	}
}