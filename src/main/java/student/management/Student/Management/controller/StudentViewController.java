package student.management.Student.Management.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.domein.StudentDetail;
import student.management.Student.Management.service.StudentService;

@Controller
public class StudentViewController {

  private final StudentService studentService;

  public StudentViewController(StudentService studentService){
    this.studentService = studentService;
  }

  @GetMapping("/students/list")
  public String showStudentList(Model model) {
    List<StudentDetail> list = studentService.searchStudentList();
    model.addAttribute("studentList",list);
    return "studentList";  // → templates/studentList.html
  }

  @GetMapping("/students/register")
  public String showRegisterForm(Model model){
    StudentDetail dto = new StudentDetail();

    dto.setStudent(new Student());

    List<StudentCourse> courses = new ArrayList<>();
    courses.add(new StudentCourse());
    dto.setStudentCourseList(courses);

    model.addAttribute("studentDetail", dto);

    return "registerStudent";
  }

  @PostMapping("/students/register")
  public String registerFromView(@ModelAttribute StudentDetail studentDetail) {
    studentService.registerStudent(studentDetail);
    return "redirect:/students/list";
  }

  @GetMapping("/students/update/{id}")
  public String showUpdateForm(@PathVariable String id,Model model){
    StudentDetail dto = studentService.searchStudent(id);
    model.addAttribute("studentDetail",dto);
    return "updateStudent";
  }

  @PostMapping ("/update/students")
  public String updateStudent(@ModelAttribute StudentDetail studentDetail){
    studentService.updateStudent(studentDetail);
    return "redirect:/students/list";
  }
}