package student.management.Student.Management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;
import student.management.Student.Management.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  //検索エンジンに追加入力するアドレスのようなもの
  @GetMapping("/studentList")
  public List<Student> getStudentList() {
    return service.searchStudentList();
  }
  @GetMapping("/student_coursesList")
  public List<StudentCourses> getStudentCoursesList() {
    return service.searchStudentCoursesList();
  }

}
