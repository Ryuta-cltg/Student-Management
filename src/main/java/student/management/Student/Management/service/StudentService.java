package student.management.Student.Management.service;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;
import student.management.Student.Management.domein.StudentDetail;
import student.management.Student.Management.repository.StudentRepository;
    //処理のロジックを実装

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    repository.search();
    // 検索処理でリポジトリからデータ取得
    List<Student> students = repository.search();
    return repository.search();
  }
   public StudentDetail searchStudent(String id){
    Student student = repository.searchStudent(id);
     List<StudentCourses> studentCourses= repository.searchStudentCourses(student.getId());
     StudentDetail studentDetail = new StudentDetail();
     studentDetail.setStudent(student);
     studentDetail.setStudentCourses(studentCourses);
     return studentDetail;
   }

  public List<StudentCourses> searchStudentCoursesList() {
    return repository.searchStudentCoursesList();
  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentCourses studentCourses : studentDetail.getStudentCourses()) {
      studentCourses.setStudentId(studentDetail.getStudent().getId());
      studentCourses.setStartDate(LocalDateTime.now());
      studentCourses.setEndDate(LocalDateTime.now().plusYears(1));
      repository.registerStudentCourses(studentCourses);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentCourses studentCourses : studentDetail.getStudentCourses()) {
      repository.updateStudentCourses(studentCourses);
    }
  }
}