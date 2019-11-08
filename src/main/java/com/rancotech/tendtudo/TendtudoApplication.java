package com.rancotech.tendtudo;

import com.rancotech.tendtudo.config.property.TendtudoApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TendtudoApiProperty.class)
public class TendtudoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TendtudoApplication.class, args);
	}

/*	@Bean
	public TendtudoApiProperty tendtudoApiProperty() {
		return new TendtudoApiProperty();
	}*/

}
