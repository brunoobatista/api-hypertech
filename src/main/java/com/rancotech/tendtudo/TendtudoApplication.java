package com.rancotech.tendtudo;

import com.rancotech.tendtudo.config.property.TendtudoApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TendtudoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TendtudoApplication.class, args);
	}

	@Bean
	public TendtudoApiProperty tendtudoApiProperty() {
		return new TendtudoApiProperty();
	}

}
