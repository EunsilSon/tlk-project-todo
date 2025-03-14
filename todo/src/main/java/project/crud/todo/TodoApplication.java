package project.crud.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 여기에 repository, entity 몇 depth까지 내려가야 인식 안 되는지 테스트 후 알려주기
 *
 * ComponentScan은 어느 때에 달아줘야하는지 테스트 후 알려주기
 * - 프로젝트 최상단
 * - @SpringBootApplication 내에 내장되어 있어서 어노테이션이 붙은 클래스들 자동으로 빈 등록한대
 * - 빈이 다른 패키지에 있을 때, 특정 패키지만 스캔할 때 @ComponentScan 명시해서 사용
 */
@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

}
