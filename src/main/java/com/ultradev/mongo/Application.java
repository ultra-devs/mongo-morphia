package com.ultradev.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ultradev.mongo.dao.MorphiaOperations;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	MorphiaOperations morphiaOperations;

	public static void main(String[] args) throws Exception {
		// disabled banner, don't want to see the spring logo
		SpringApplication app = new SpringApplication(Application.class);
		// app.setBannerMode(Banner.Mode.OFF);
		app.run(args);

	}

	@Override
	public void run(String... args) throws Exception {
		morphiaOperations.performMorphiaCrud();
		// existing
		System.exit(1);
	}
}
