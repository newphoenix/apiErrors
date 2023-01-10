package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("persons")
public class PersonController {

	static Map<Integer, Person> personsMap = new HashMap<>();
	static {
		personsMap.put(1, Person.builder().id(1).name("John").build());
		personsMap.put(2, Person.builder().id(2).name("Emma").build());
	}
	
	
	@GetMapping("{id}")
	public ResponseEntity<Person> getPerson(@PathVariable Integer id){
		
		Person person = Optional.ofNullable(personsMap.get(id))
				.orElseThrow(()-> new NotFoundException("Person not found with id:"+ id));
		return ResponseEntity.ok(person);
	}
	
	
	@PostMapping
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person){
		
		personsMap.put(person.getId(), person);
		return new ResponseEntity<>(personsMap.get(person.getId()),HttpStatus.CREATED);
		
	}
	
}
