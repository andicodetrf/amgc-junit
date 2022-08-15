package com.example.demo.student;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


//annotation that would spinup db for us incl autowires. underTest will be null without this annotation
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentRepositoryTest {

	//run against h2-in-memory instead of actual db eg. psql 
	
	@Autowired
	private StudentRepository underTest;
	
	@AfterEach
	void tearDown() {
		underTest.deleteAll();
	}
	
	@Test
	@Order(1)  
	void itShouldCheckWhenStudentEmailExists() {
		
		
		//given
		String email = "a123@gmail.com";
		Student student = new Student("Andi", email, Gender.FEMALE);
		underTest.save(student);
		
		
		//when
		boolean exists = underTest.selectExistsEmail(email);
		
		
		//then
		assertThat(exists).isTrue();
	}
	
	
	@Test
	@Order(2)
	void itShouldCheckWhenStudentEmailDoesNotExist() {
		
		System.out.println("=============");
		
		//given
		String email = "a123@gmail.com";
		
		//when
		boolean exists = underTest.selectExistsEmail(email);
		
		//then
		assertThat(exists).isFalse();
	}

}
