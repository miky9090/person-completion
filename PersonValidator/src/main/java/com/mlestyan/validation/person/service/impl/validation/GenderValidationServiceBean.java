package com.mlestyan.validation.person.service.impl.validation;

import org.springframework.stereotype.Service;

import com.mlestyan.validation.person.service.contract.validation.GenderValidationService;

@Service
public class GenderValidationServiceBean implements GenderValidationService {

	/**
	 * Validates if the provided gender is valid or not.
	 * @param gender the input gender
	 * @return true if the gender is either F(érfi) or N(ő), false otherwise
	 * */
	@Override
	public boolean validateGender(String gender) {
		return "F".equalsIgnoreCase(gender) || "N".equalsIgnoreCase(gender);
	}

}
