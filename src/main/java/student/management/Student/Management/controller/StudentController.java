package student.management.Student.Management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import student.management.Student.Management.controller.converter.StudentConverter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;
import student.management.Student.Management.domein.StudentDetail;
import student.management.Student.Management.service.StudentService;

//コントローラークラス　制御層

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  //studentの受講生一覧表示
  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentCourses> studentCourses = service.searchStudentCoursesList();

    model.addAttribute("studentList",converter.convertStudentDetails(students,studentCourses));
    return "studentList";
}
   //student_coursesListの一覧表示
  @GetMapping("/student_coursesList")
  public List<StudentCourses> getStudentCoursesList() {
    return service.searchStudentCoursesList();
  }
   //受講生登録画面表示
  @GetMapping ("/newStudent")
  public String newStudent(Model model){
    model.addAttribute("studentDetail",new StudentDetail());
    return "registerStudent";
  }

@PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
  if (result.hasErrors()) {
    return "registerStudent";
  }
  service.registerStudent(studentDetail);
  return "redirect:/studentList";

    }
}
