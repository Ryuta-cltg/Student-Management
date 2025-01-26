package student.management.Student.Management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Transactional

public class Application {
  @Autowired
  private StudentRepository repository;

	private String name = "Hiroishi Ryuta";
	private String age = "24";

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	//検索エンジンに追加入力するアドレスのようなもの
 @GetMapping("/Student")
 public String getStudent(@RequestParam String name) {
	 System.out.println("検索する名前: " + name);//デバック用
		Student student = repository.searchByName(name);
	 if (student == null) {
		 return "Student not found";
	 }
		//実行結果　画面に出力されるもの
		return student.getName() + " " + student.getAge() + "歳";
 }
@PostMapping("/Student")
	public void registerStudent(String name, int age){
		repository.registerStudent(name,age);
 }

 @PatchMapping ("/Student")
	public void updateStudent(String name,int age){
		repository.updateStudent(name,age);
	}

@DeleteMapping ("/Student")
	public void  deleteStudent(String name){
		repository.deleteStudent(name);
  }
}

