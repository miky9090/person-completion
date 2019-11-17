package com.mlestyan.validation.person.service.impl.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Name validation service tests")
class NameValidationServiceBeanTest {
	@InjectMocks
	private NameValidationServiceBean nameValidationServiceBean;
	
	@Test
	void testValidateNameValid() {
		String name = "Lestyán Mihály";
		
		boolean result = nameValidationServiceBean.validateName(name);
		
		assertTrue(result);
	}
	
	@Test
	void testValidateNameNull() {
		String name = null;
		
		boolean result = nameValidationServiceBean.validateName(name);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateNameEmpty() {
		String name = "";
		
		boolean result = nameValidationServiceBean.validateName(name);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateNameNoSpace() {
		String name = "LestyánMihály";
		
		boolean result = nameValidationServiceBean.validateName(name);
		
		assertFalse(result);
	}

	@Test
	void testValidateNameWithDr() {
		String name = "Dr. Lestyán Mihály";
		
		boolean result = nameValidationServiceBean.validateName(name);
		
		assertTrue(result);
	}
	
	@Test
	void testValidateNameWithDash() {
		String name = "Lestyán-Molnár Mihály";
		
		boolean result = nameValidationServiceBean.validateName(name);
		
		assertTrue(result);
	}
	
	@Test
	void testValidateNameWithDrDash() {
		String name = "Dr. Lestyán-Molnár Mihály";
		
		boolean result = nameValidationServiceBean.validateName(name);
		
		assertTrue(result);
	}
	
	@Test
	void testValidateNameWithDrDashDr() {
		String name = "Dr. Lestyán-Molnár Mihály Dr.";
		
		boolean result = nameValidationServiceBean.validateName(name);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateNameWithSinglePart() {
		String name = "Mihály";
		
		boolean result = nameValidationServiceBean.validateName(name);
		
		assertFalse(result);
	}
}
