package student.management.Student.Management.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

//Studentコースのデータ　カラム名に依存

@Getter
@Setter
public class StudentCourses {
  private String id;
  private String studentId;
  private String courseName;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
