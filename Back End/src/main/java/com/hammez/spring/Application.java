package com.hammez.spring;

import com.hammez.spring.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	// Command Line Runner - allows execution of code before application fully starts. Good place to add demo data to database
	@Bean
	CommandLineRunner runner() {
		return args -> {
			// Owners
			Owner jamesOwner = new Owner("James", "Eln");
			Owner lilyOwner = new Owner("Lily", "Gutsy");
			ownerRepository.save(jamesOwner);
			ownerRepository.save(lilyOwner);
			// Cars
			carRepository.save(new Car(jamesOwner, "Ford", "Mustang", "Blue", "JKE-123", 2018, 65000));
			carRepository.save(new Car(jamesOwner, "Land Rover", "Defender", "Green", "APG-901", 2004, 15000));
			carRepository.save(new Car(lilyOwner, "Tesla", "Model S", "Black", "ELE-444", 2019, 85000));
			carRepository.save(new Car(jamesOwner, "Audi", "R8", "Silver", "FAS-998", 2016, 105000));
			// Users
			userRepository.save(new User("user", "$2a$10$TeNiwnHE12ub01/hD5uz6u3ocLaRr1YUg8XpIgE7fieFDPZTD6IRW", "USER"));
			userRepository.save(new User("admin", "$2a$10$X1KrwaQP7lu3xKOya9HH1e/SwXlZyZcHTewYtGwLEr9Yq78y5B/Ei", "ADMIN"));
		};
	}
}