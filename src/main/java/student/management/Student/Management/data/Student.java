package student.management.Student.Management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Studentのデータ　カラム名に依存
@Schema(description = "受講生情報")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {


  private String id;

  @NotBlank (message = "フルネームは入力必須項目です。")
  private String fullname;

  @NotBlank (message = "ふりがなは入力必須項目です。")
  @Pattern (regexp = "^[ぁ-んー]+$", message = "ふりがなはひらがなのみで入力してください")
  private String furigana;

  @NotBlank (message = "ニックネームは入力必須項目です。")
  private String nickname;

  @NotBlank (message = "メールアドレスは入力必須項目です。")
  @Email (message = "メールアドレスの形式で入力してください。")
  private String email;

  @NotBlank(message = "住所は入力必須項目です。")
  private String region;

  @NotNull(message = "年齢は入力必須項目です")
  @Min(value = 16, message = "年齢は16歳以上を入力してください")
  @Max(value = 70, message = "年齢は70歳以下を入力してください")
  private Integer age;

  @NotBlank (message = "性別は入力必須項目です")
  private String gender;

  private String remark;
  private boolean isDeleted;
}