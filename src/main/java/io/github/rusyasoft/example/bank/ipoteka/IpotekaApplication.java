package io.github.rusyasoft.example.bank.ipoteka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class IpotekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpotekaApplication.class, args);
	}

}
