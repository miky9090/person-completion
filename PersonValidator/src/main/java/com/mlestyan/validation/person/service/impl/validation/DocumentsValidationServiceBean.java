package com.mlestyan.validation.person.service.impl.validation;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.mlestyan.validation.person.model.domain.OkmanyDTO;
import com.mlestyan.validation.person.service.contract.validation.DocumentsValidationService;

@Service
public class DocumentsValidationServiceBean implements DocumentsValidationService {

	/**
	 * Checks if any of the OkmanyDTO-s of the same type has more than one valid instance.
	 * @param documents the input documents
	 * @return true if there is no multiple valid OkmanyDTO-s per type, false otherwise
	 * */
	@Override
	public boolean validateDocuments(Iterable<OkmanyDTO> documents) {
		Map<String, Long> tooManyValidDocumentsMap = StreamSupport.stream(documents.spliterator(), false)
				.filter(document -> document.isErvenyes()) // Check only valid documents
				.collect(Collectors.groupingBy(OkmanyDTO::getOkmTipus, Collectors.counting())) // Group the documents by okmTipus and count them.
				.entrySet().stream()
					.filter(entry -> entry.getValue() > 1) // Filter out the ones that appear more than 1 time
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); // Collect them in a map
		
		// There is at least one type of document that has more than one valid instance
		if(tooManyValidDocumentsMap.size() > 0) {
			return false;
		}
		
		return true;
	}

}
