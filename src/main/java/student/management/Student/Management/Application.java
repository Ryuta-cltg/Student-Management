package student.management.Student.Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@Transactional

public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

