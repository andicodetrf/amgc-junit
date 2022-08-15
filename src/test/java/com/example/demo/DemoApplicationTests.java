package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class DemoApplicationTests {
	
	Calculator calc = new Calculator();

	//given, when, then - BDD format
	
	@Test
	void itShouldAddTwoNumbers() {
		//given
		int numberOne = 20;
		int numberTwo = 35;
		
		//when
		int result = calc.add(numberOne, numberTwo);
		
		//then
		int expected = 55;
		assertThat(result).isEqualTo(expected);
	}
	
	class Calculator {
		int add(int a, int b) {
			return a + b;
		}
		
	}

}
