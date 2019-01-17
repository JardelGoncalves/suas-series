package com.jardel.suasseries;

import javax.transaction.Transactional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages={"com.jardel.models"})
@ComponentScan(basePackages={"com.jardel.*"})
@EnableJpaRepositories(basePackages={"com.jardel.repository"})
@Transactional
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

