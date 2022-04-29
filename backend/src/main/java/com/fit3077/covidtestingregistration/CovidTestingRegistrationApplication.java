package com.fit3077.covidtestingregistration;

import com.fit3077.covidtestingregistration.testingsite.TestingSiteCollection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CovidTestingRegistrationApplication {

	public static void main(String[] args) {
		TestingSiteCollection test = new TestingSiteCollection();
		test.updateTestingSiteList("DRIVETHROUGH",null)
		System.out.println(test.getTestingSiteList());

		SpringApplication.run(CovidTestingRegistrationApplication.class, args);
	}

}
