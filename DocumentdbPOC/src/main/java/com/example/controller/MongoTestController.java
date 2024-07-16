package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.documentDb.model.Employee;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


@RestController

@RequestMapping(value = "mongo")
public class MongoTestController {

	@Autowired
	private MongoDatabase database;
	
	
	@GetMapping("/employee")
	  List<Employee> all() {
		List<Employee> result = new ArrayList<Employee>();
		System.out.println("Getting all the employees");
    	MongoCollection<Employee> collection = database.getCollection("employee",Employee.class);
    	MongoCursor<Employee> cursor = collection.find().cursor();
    	while(cursor.hasNext()) {
    		result.add(cursor.next());
    	}
    	return result;
				
	  }
	  // end::get-aggregate-root[]

	  @PostMapping("/employee")
	  void newEmployee(@RequestBody Employee newEmployee) {
	    	System.out.println("Saving employee " + newEmployee);
	    	MongoCollection<Employee> collection = database.getCollection("employee",Employee.class);
	       collection.insertOne(newEmployee);

	  }

	  // Single item
	  
//	  @GetMapping("/employees/{name}")
//	  Employee one(@PathVariable String name) {
//	    
//	   
//	    	System.out.println(" Getting Employee by name : " + name);
//	    	Employee employee = datastore.find(Employee.class).filter(
//	    		Filters.eq("name", name)).iterator(new FindOptions().limit(1))
//	    		.tryNext();
//	    	System.out.println(employee);
//	    	return employee;
//	    }
//	  
//
//
//	  @DeleteMapping("/employees")
//	  void deleteEmployee() {
//		  datastore.find(Employee.class)
//			.delete(new DeleteOptions().multi(true));
//		System.out.println("All Employees deleted.");
//	  }
}
