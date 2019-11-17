package com.mlestyan.validation.person.service.impl.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.mlestyan.validation.person.service.contract.validation.NameValidationService;

@Service
public class NameValidationServiceBean implements NameValidationService {
	
	// Not perfect. Needs improvements.
	private static final String NAME_VALIDATION_PATTERN = "(([A-ZÁÉÍÓÖŐÚÜŰÄ][a-záéíóöőúüűä.\\/\"\' -]*)+){2,}(?<=(.+[ ]+.+))(?<!(^Dr\\..*Dr\\.$))";
	
	/**
	 * Validates if the provided name is a valid name.
	 * @param name the input name
	 * @return true if the name is valid, false otherwise
	 * */
	@Override
	public boolean validateName(String name) {
		if(name == null) {
			return false;
		}
		Pattern documentTypePattern = Pattern.compile(NAME_VALIDATION_PATTERN);
		Matcher documentTypeMatcher = documentTypePattern.matcher(name);
		return documentTypeMatcher.matches();
	}
}
