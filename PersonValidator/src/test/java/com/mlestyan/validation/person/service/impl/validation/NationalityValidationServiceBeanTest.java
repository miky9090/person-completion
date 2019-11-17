package com.mlestyan.validation.person.service.impl.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mlestyan.validation.person.model.domain.State;
import com.mlestyan.validation.person.service.contract.StateService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Nationality validation service tests")
class NationalityValidationServiceBeanTest {
	
	@InjectMocks
	private NationalityValidationServiceBean nationalityValidationServiceBean;
	
	@Mock
	private StateService stateService;
	
	
	@Test
	void testValidateNationalityValid() {
		String nationality = "HUN";
		State mockedState = Mockito.mock(State.class);
		Mockito.when(stateService.getStateById("HUN")).thenReturn(Optional.of(mockedState));
		
		boolean result = nationalityValidationServiceBean.validateNationality(nationality);
		
		assertTrue(result);
	}

	@Test
	void testValidateNationalityNull() {
		String nationality = null;

		boolean result = nationalityValidationServiceBean.validateNationality(nationality);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateNationalityEmpty() {
		String nationality = "";
		Mockito.when(stateService.getStateById("")).thenReturn(Optional.empty());
		
		boolean result = nationalityValidationServiceBean.validateNationality(nationality);
		
		assertFalse(result);
	}
	
	@Test
	void testValidateNationalityInvalid() {
		String nationality = "XYZ";
		
		boolean result = nationalityValidationServiceBean.validateNationality(nationality);
		
		assertFalse(result);
	}
}
