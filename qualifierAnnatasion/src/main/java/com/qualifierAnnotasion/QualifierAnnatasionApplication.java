package com.qualifierAnnotasion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QualifierAnnatasionApplication {

	public static void main(String[] args) {
		SpringApplication.run(QualifierAnnatasionApplication.class, args);
	}

	@Bean(name = "mahmut")
	public WordFileReader getWordFileReader(){
		return new WordFileReader();
	}
}
