package com.mlestyan.validation.person.integration;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlestyan.validation.person.model.domain.DocumentResponse;
import com.mlestyan.validation.person.model.domain.ErrorContext;
import com.mlestyan.validation.person.model.domain.OkmanyDTO;
import com.mlestyan.validation.person.model.domain.SzemelyDTO;

@SpringBootTest(properties = { "person.application.document.api.url=http://localhost:8888", "person.application.document.api.document-complete-path=/complete-doc" })
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CompletePersonIT {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RestTemplate documentRestTemplate;

	private MockRestServiceServer mockServer;

	@BeforeEach
	public void init() {
		mockServer = MockRestServiceServer.createServer(documentRestTemplate);
	}

	@Test
	public void personCompletionWorksThroughAllLayers() throws Exception {
		OkmanyDTO document1 = new OkmanyDTO();
		document1.setOkmTipus("1");
		document1.setOkmanySzam("123456AB");
		document1.setOkmanyKep(new byte[] {});
		document1.setLejarDat(new GregorianCalendar(2020, 5, 12).getTime());
		document1.setErvenyes(false);
		
		List<OkmanyDTO> documents = new ArrayList<>();
		documents.add(document1);
		
		SzemelyDTO person = new SzemelyDTO();
		person.setAllampKod("HUN");
		person.setaNev("Horváth Mária");
		person.setSzulNev("Kiss József");
		person.setVisNev("Kiss József");
		person.setSzulDat(new GregorianCalendar(1990, 5, 5).getTime());
		person.setNeme("F");
		person.setOkmLista(documents);

		DocumentResponse documentResponse = new DocumentResponse();
		documentResponse.setDocument(document1);
		documentResponse.setErrors(new ArrayList<>());
        mockServer.expect(ExpectedCount.once(),
          requestTo(new URI("http://localhost:8888/complete-doc")))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withStatus(HttpStatus.OK)
              .contentType(MediaType.APPLICATION_JSON)
              .body(objectMapper.writeValueAsString(documentResponse)));
        
        mockMvc.perform(post("/complete-person")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.errors", emptyCollectionOf(ErrorContext.class)))
                .andExpect(jsonPath("$.person.allampDekod", notNullValue()));

	}
}
