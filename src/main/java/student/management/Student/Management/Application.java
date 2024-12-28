package student.management.Student.Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}
 @GetMapping("/hello") //検索エンジンに追加入力するアドレスのようなもの
 public String hello() {
		return "Hello,World";  //実行結果　画面に出力されるもの
 }
}
