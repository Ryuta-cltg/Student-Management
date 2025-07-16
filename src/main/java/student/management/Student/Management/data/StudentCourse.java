package student.management.Student.Management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

//Studentコースのデータ　カラム名に依存

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  private String id;

  private String studentId;

  @NotBlank (message = "コース名は入力必須項目です。")
  private String courseName;

  private LocalDateTime startDate;

  private LocalDateTime endDate;
}
