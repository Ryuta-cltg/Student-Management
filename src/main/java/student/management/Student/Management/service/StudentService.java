package student.management.Student.Management.service;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;
import student.management.Student.Management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }
  public List<Student> searchStudentList() {
    // 検索処理でリポジトリからデータ取得
    List<Student> students = repository.search();
    // 年齢が30代（30～39）の学生を絞り込む
    return students.stream()
        .filter(student -> student.getAge() >= 30 && student.getAge() <= 39)
        .collect(Collectors.toList());
  }

  public List<StudentCourses> searchStudentCoursesList() {
    // 検索処理でリポジトリからデータ取得
    List<StudentCourses> courses = repository.searchStudentCourses();

    // 「Javaコース」の情報を絞り込む
    return courses.stream()
        .filter(course -> "Java".equals(course.getCourseName()))
        .collect(Collectors.toList());
  }
}

