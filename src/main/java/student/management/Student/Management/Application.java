package student.management.Student.Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	private String name = "Hiroishi Ryuta";
	private String age = "24";

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	//検索エンジンに追加入力するアドレスのようなもの
 @GetMapping("/StudentInfo")
 public String getStudentInfo() {

		//実行結果　画面に出力されるもの
		return name + "さん" + age + "歳";
 }
@PostMapping("/StudentInfo")
	public void setStudentInfo(String name, String age){
		this.name = name;
		this.age = age;
 }

 @PostMapping("StudentName")
	public void updateStudentName(String name){
		this.name = name;
 }
}
