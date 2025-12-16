package com.prodapt.licensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LicensetrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicensetrackerApplication.class, args);
	}

}
