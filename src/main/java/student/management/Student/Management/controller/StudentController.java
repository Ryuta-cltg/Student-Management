package student.management.Student.Management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import student.management.Student.Management.controller.converter.StudentConverter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;
import student.management.Student.Management.domein.StudentDetail;
import student.management.Student.Management.service.StudentService;

//コントローラークラス　制御層

@RestController
public class StudentController {

  private final StudentService service;
  private final StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  //studentの受講生一覧表示
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentCourses> studentCourses = service.searchStudentCoursesList();
    return converter.convertStudentDetails(students,studentCourses);
}
  @GetMapping ("/student/{id}")
  public StudentDetail getStudent(@PathVariable String id){
    return service.searchStudent(id);
  }

   /*受講生登録画面表示 使わない
   @GetMapping ("/newStudent")
    public String newStudent(Model model){
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentCourses(Arrays.asList(new StudentCourses()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }*/

@PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
  return ResponseEntity.ok(responseStudentDetail);
  }


  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が正常に作動しました。");
  }
}
