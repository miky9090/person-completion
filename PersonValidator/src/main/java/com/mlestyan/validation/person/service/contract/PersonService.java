package com.mlestyan.validation.person.service.contract;

import com.mlestyan.validation.person.model.domain.PersonResponse;
import com.mlestyan.validation.person.model.domain.SzemelyDTO;

public interface PersonService {
	public PersonResponse completePerson(SzemelyDTO person);
}
