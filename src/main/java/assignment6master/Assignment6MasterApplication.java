package assignment6master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "assignment6master.repositories")
@SpringBootApplication
public class Assignment6MasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(Assignment6MasterApplication.class, args);
	}

}
