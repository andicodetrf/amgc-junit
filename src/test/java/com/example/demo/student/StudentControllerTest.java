package com.example.demo.student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	
	@Test
	void helloStudentUnit() {
		StudentController studentController = new StudentController(null);
		String response = studentController.helloStudentUnitTest("Andi");
//		assertEquals("Hello, Andi", response);
		assertThat(response).isEqualTo("Hello, Andi");
	}
	
	@Test
	void testJustHello() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/students/hello");
		MvcResult result = mvc.perform(request).andReturn();
		
		assertThat(result.getResponse().getContentAsString()).isEqualTo("hello!");
	}
	

	@Test
	void helloStudentIntWithoutParam() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/students/helloInt");
		MvcResult result = mvc.perform(request).andReturn();
		
		assertThat(result.getResponse().getContentAsString()).isEqualTo("Hello, Stranger");
//		assertEquals("Hello, Stranger", result.getResponse().getContentAsString());
	}
	
	
	@Test
	void helloStudentIntWithParam() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/students/helloInt?name=Joe");
		MvcResult result = mvc.perform(request).andReturn();
		
		assertThat(result.getResponse().getContentAsString()).isEqualTo("Hello, Joe");
	}
	
	@Test
	void testGetHelloJson() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/students/helloJson");
		mvc.perform(request)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("key1").value("value1"));
	}
	
	@Test
	void testPostAddJson() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/api/v1/students/postJson")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\": \"Andi\",  \"email\": \"a123@gmail.com\", \"gender\": \"FEMALE\"}");
		
		mvc.perform(request)
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("name").value("Andi"))
		.andExpect(MockMvcResultMatchers.jsonPath("email").value("a123@gmail.com"));
	}
	
	
	
	

}
