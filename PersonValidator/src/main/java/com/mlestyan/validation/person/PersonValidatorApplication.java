package com.mlestyan.validation.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.mlestyan.validation.person.service.contract.StateReadingService;
import com.mlestyan.validation.person.service.contract.StateService;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class PersonValidatorApplication implements CommandLineRunner {
	@Autowired
	private StateReadingService stateReadingService;
	@Autowired
	private StateService stateService;

	public static void main(String[] args) {
		SpringApplication.run(PersonValidatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Read the available states from the JSON file and store it in the in-memory database.
		// This way we don't have to read the JSON file all the time we want to validate states.
		// Also it's more convenient to use a repository and the application will work faster.
		this.stateService.saveStates(stateReadingService.readStates());
	}
}
