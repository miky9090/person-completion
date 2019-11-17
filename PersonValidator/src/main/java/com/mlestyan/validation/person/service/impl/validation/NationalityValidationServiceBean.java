package com.mlestyan.validation.person.service.impl.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlestyan.validation.person.service.contract.StateService;
import com.mlestyan.validation.person.service.contract.validation.NationalityValidationService;

@Service
public class NationalityValidationServiceBean implements NationalityValidationService {
	@Autowired
	private StateService stateService;

	/**
	 * Checks if the provided nationality is in the database.
	 * @param nationality the input nationality code
	 * @return true if the nationality code could be found, false otherwise
	 * */
	@Override
	public boolean validateNationality(String nationality) {
		return nationality != null && this.stateService.getStateById(nationality).isPresent();
	}

}
