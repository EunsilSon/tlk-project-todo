package project.crud.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 여기에 repository, entity 몇 depth까지 내려가야 인식 안 되는지 테스트 후 알려주기
 * ComponentScan은 어느 때에 달아줘야하는지 테스트 후 알려주기
 */
@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

}
