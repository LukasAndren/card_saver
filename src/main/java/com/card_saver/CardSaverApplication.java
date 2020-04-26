package com.card_saver;

import com.card_saver.parser.Parser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class CardSaverApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		Parser.parseAllCardNames();
		Parser.parseAllSetNames();
		SpringApplication.run(CardSaverApplication.class, args);
	}

}
