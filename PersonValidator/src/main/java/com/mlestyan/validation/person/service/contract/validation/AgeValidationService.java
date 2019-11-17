package com.mlestyan.validation.person.service.contract.validation;

import java.util.Date;

public interface AgeValidationService {
	public boolean validateAge(Date birthDate);
}
