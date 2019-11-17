package com.mlestyan.validation.person.service.impl.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Gender validation service tests")
class GenderValidationServiceBeanTest {

	@InjectMocks
	private GenderValidationServiceBean genderValidationServiceBean;
	
	@Test
	void testValidateGenderNotValid() {
		String inputGender = "A";
		
		boolean result = genderValidationServiceBean.validateGender(inputGender);
		
		assertFalse(result);
	}

	@Test
	void testValidateGenderNull() {
		String inputGender = null;
		
		boolean result = genderValidationServiceBean.validateGender(inputGender);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateGenderMale() {
		String inputGender = "F";
		
		boolean result = genderValidationServiceBean.validateGender(inputGender);
		
		assertTrue(result);
	}
	
	@Test
	void testValidateGenderFemale() {
		String inputGender = "N";
		
		boolean result = genderValidationServiceBean.validateGender(inputGender);
		
		assertTrue(result);
	}
}
