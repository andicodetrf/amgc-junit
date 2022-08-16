package com.example.demo.student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.student.exception.BadRequestException;



//either use autoCloseable or use ExtendWith(MockitoExtension.class) annotation to mock
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
	
	@Mock
	private StudentRepository studentRepository;
	
//	private AutoCloseable autoCloseable;
	private StudentService underTest;
	
	@BeforeEach
	void setUp() {
//		autoCloseable = MockitoAnnotations.openMocks(this);
		//since studentRepo is tested, we don't need to test it again here hence we can mock it. 
		underTest = new StudentService(studentRepository);
	}
	
//	@AfterEach
//	void tearDown() throws Exception {
//		autoCloseable.close();	
//	}

	@Test
	void canGetAllStudents() {
		//when
		underTest.getAllStudents();
		
		//then 
		//mockito.verify, mocking studentRepo & its methods (both defined & jpa's)
		verify(studentRepository).findAll();
		
	}

	@Test
	void canAddStudent() {
		//to test that studentRepository.save(student) in addStudent method is indeed invoked 
		//with the same arg passed into addStudent
		
		//given
		String email = "a123@gmail.com";
		Student student = new Student("Andi", email, Gender.FEMALE);
		
		//when
		underTest.addStudent(student);
		
		//then
		ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
		
		verify(studentRepository).save(studentArgumentCaptor.capture());
		
		Student capturedStudent = studentArgumentCaptor.getValue();

		assertThat(capturedStudent).isEqualTo(student);
	
	}
	
	@Test
	void willThrowWhenEmailIsTaken() {
		
		//given
		String email = "a123@gmail.com";
		Student student = new Student("Andi", email, Gender.FEMALE);
		
		//BDDMockito.given - to manipulate if (existsEmail) as true so that we can test the throwable block.
//		given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true); 
		given(studentRepository.selectExistsEmail(anyString())).willReturn(true); //alternative
		
		//when
		//then
		assertThatThrownBy(() -> underTest.addStudent(student))
		.isInstanceOf(BadRequestException.class)
		.hasMessageContaining("Email " + student.getEmail() + " taken");
		
		//since addStudent threw exception, studentRepository.save will never be invoked. below is to verify the mock method
		verify(studentRepository, never()).save(any());

	}

	@Test
	@Disabled
	void testDeleteStudent() {
	
	}

	
	@Test
	@Disabled
	void testToShowDisabledAnnotation() {
		
	}

}
