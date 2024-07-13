package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.documentDb.model.Employee;

import dev.morphia.Datastore;
import dev.morphia.DeleteOptions;
import dev.morphia.query.FindOptions;
import dev.morphia.query.experimental.filters.Filters;


@RestController
public class TestController {

	@Autowired
	private Datastore datastore;
	
	
	@GetMapping("/employees")
	  List<Employee> all() {
    	System.out.println("Getting all the employees");

		return datastore.find(Employee.class).iterator().toList();
	  }
	  // end::get-aggregate-root[]

	  @PostMapping("/employee")
	  Employee newEmployee(@RequestBody Employee newEmployee) {
	    	System.out.println("Saving employee " + newEmployee);

		  return datastore.save(newEmployee);
	  }

	  // Single item
	  
	  @GetMapping("/employees/{name}")
	  Employee one(@PathVariable String name) {
	    
	   
	    	System.out.println(" Getting Employee by name : " + name);
	    	Employee employee = datastore.find(Employee.class).filter(
	    		Filters.eq("name", name)).iterator(new FindOptions().limit(1))
	    		.tryNext();
	    	System.out.println(employee);
	    	return employee;
	    }
	  


	  @DeleteMapping("/employees")
	  void deleteEmployee() {
		  datastore.find(Employee.class)
			.delete(new DeleteOptions().multi(true));
		System.out.println("All Employees deleted.");
	  }
}
