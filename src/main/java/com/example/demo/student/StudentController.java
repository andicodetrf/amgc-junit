package com.example.demo.student;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/students")
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public void addStudent(@Valid @RequestBody Student student) {
        studentService.addStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(
            @PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }
    
    public String helloStudentUnitTest(String name) {
    	return String.format("Hello, %s", name);
    }
    
    @GetMapping("/hello")
    public String justHello(String name) {
    	return "hello!";
    }
        
    
    @GetMapping("/helloInt")
    public String helloStudentIntTest(@RequestParam(name = "name", defaultValue = "Stranger") String name) {
    	return String.format("Hello, %s", name);
    }
    
    @GetMapping("/helloJson")
    public Map<String, String> sayHello() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("foo", "bar");
        map.put("aa", "bb");
        return map;
    }
    
    @PostMapping("/postJson")
    public ResponseEntity<Student> helloPostJson(@RequestBody Student student) {
    	System.out.println(">>> " + student.getName());
    	return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }
}
