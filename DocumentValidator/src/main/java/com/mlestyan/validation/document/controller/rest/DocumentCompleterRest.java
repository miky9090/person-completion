package com.mlestyan.validation.document.controller.rest;

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

import com.mlestyan.validation.document.model.domain.DocumentResponse;
import com.mlestyan.validation.document.model.domain.ErrorContext;
import com.mlestyan.validation.document.model.domain.OkmanyDTO;
import com.mlestyan.validation.document.service.contract.DocumentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Api("Document completion service")
@RestController
@RequestMapping("/")
public class DocumentCompleterRest {
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private MessageSource messageSource;
	
	@ApiOperation(value = "Validate and complete a Document object", response = DocumentResponse.class, consumes = "application/json", produces = "application/json", httpMethod = "POST")
	@PostMapping(value = "/complete-document", consumes = "application/json", produces = "application/json")
	public ResponseEntity<DocumentResponse> completeDocument(@ApiParam(required = true, value = "The document to be validated and completed") @RequestBody OkmanyDTO document, @ApiIgnore Locale locale) {
		DocumentResponse response = documentService.completeDocument(document);
		
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
