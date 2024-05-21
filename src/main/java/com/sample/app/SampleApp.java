package com.sample.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.sample.app.runtime.RuntimeHandler;

@SpringBootApplication
public class SampleApp {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
//		SpringApplication.run(SampleApp.class, args);
		
		ApplicationContext app = SpringApplication.run(SampleApp.class, args);
		
		RuntimeHandler.loadAllBeansFromControllers(app);
	}

}
