package com.example.documentDb;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.example.controller.MongoTestController;
import com.example.controller.MorphiaTestController;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MorphiaApplication.class,MorphiaTestController.class,MongoTestController.class);
	}

}
