package student.management.Student.Management.domein;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
//受講生の詳細情報クラス

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  private Student student;
  private List<StudentCourse> studentCourseList;

}
