package com.card_saver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class CardSaverApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(CardSaverApplication.class, args);
	}

}
