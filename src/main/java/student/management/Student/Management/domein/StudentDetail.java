package student.management.Student.Management.domein;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;
//受講生の詳細情報クラス

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentCourses> studentCourses;

}
