package com.mlestyan.validation.person.service.impl.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mlestyan.validation.person.configuration.property.PersonValidationProperties;
import com.mlestyan.validation.person.service.impl.validation.AgeValidationServiceBean;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.WARN)
@DisplayName("Age validation service tests")
class AgeValidationServiceBeanTest {

	@InjectMocks
	private AgeValidationServiceBean ageValidationService;
	
	@Mock
	private PersonValidationProperties personValidationProperties;
	
	/*@BeforeEach
	void setupMocks() {
		when(personValidationProperties.getMinAge()).thenReturn(18);
		when(personValidationProperties.getMaxAge()).thenReturn(120);
	}*/
	
	@Test
	void testValidateAgeTooOld() {
		Date d1 = Date.from(LocalDate.now().minus(150, ChronoUnit.YEARS).atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		when(personValidationProperties.getMaxAge()).thenReturn(120);
		boolean result = ageValidationService.validateAge(d1);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateAgeTooYoung() {
		Date d1 = Date.from(LocalDate.now().minus(10, ChronoUnit.YEARS).atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		when(personValidationProperties.getMinAge()).thenReturn(18);
		boolean result = ageValidationService.validateAge(d1);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateAgeCorrect() {
		Date d1 = Date.from(LocalDate.now().minus(50, ChronoUnit.YEARS).atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		when(personValidationProperties.getMinAge()).thenReturn(18);
		when(personValidationProperties.getMaxAge()).thenReturn(120);
		boolean result = ageValidationService.validateAge(d1);
		
		assertTrue(result);
	}
	
	@Test
	void testValidateAgeOneDayTo18() {
		Date d1 = Date.from(LocalDate.now().minus(18, ChronoUnit.YEARS).plus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		when(personValidationProperties.getMinAge()).thenReturn(18);
		
		boolean result = ageValidationService.validateAge(d1);
		assertFalse(result);
	}
	
	@Test
	void testValidateAgeOneDayAfter120() {
		Date d1 = Date.from(LocalDate.now().minus(120, ChronoUnit.YEARS).atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		when(personValidationProperties.getMaxAge()).thenReturn(120);
		
		boolean result = ageValidationService.validateAge(d1);
		assertFalse(result);
	}

}
