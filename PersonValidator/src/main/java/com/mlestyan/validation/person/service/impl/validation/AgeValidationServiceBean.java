package com.mlestyan.validation.person.service.impl.validation;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.mlestyan.validation.person.configuration.property.PersonValidationProperties;
import com.mlestyan.validation.person.service.contract.validation.AgeValidationService;

@Service
@EnableConfigurationProperties(PersonValidationProperties.class)
public class AgeValidationServiceBean implements AgeValidationService {
	@Autowired
	private PersonValidationProperties personValidationProperties;

	/**
	 * Validates if the age of the provided date object is between the min. and max. age.
	 * @param birthDate The birth date of a person
	 * @return true if the person has appropriate age, false otherwise
	 * */
	@Override
	public boolean validateAge(Date birthDate) {
		LocalDate localBirthDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long age = Period.between(localBirthDate, LocalDate.now()).get(ChronoUnit.YEARS);
		return age >= this.personValidationProperties.getMinAge() && age < this.personValidationProperties.getMaxAge();
	}

}
