package student.management.Student.Management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import student.management.Student.Management.domein.StudentDetail;
import student.management.Student.Management.exception.MyException;
import student.management.Student.Management.service.StudentService;

@Tag(name = "受講生API", description = "受講生の検索・登録・更新などを提供するAPIです。")

@Validated
@RestController
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @Operation(summary = "受講生一覧の取得", description = "登録されている全ての受講生の一覧を返します。")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "一覧の取得に成功"),
      @ApiResponse(responseCode = "500", description = "サーバー内部エラー")
  })

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
   return service.searchStudentList();
  }

  @Operation(summary = "受講生の個別取得", description = "指定したIDに紐づく受講生の情報を返します。")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "受講生の取得に成功"),
      @ApiResponse(responseCode = "400", description = "無効なID"),
      @ApiResponse(responseCode = "404", description = "該当受講生が見つかりませんでした")
  })

  @GetMapping ("/student/{id}")
  public StudentDetail getStudent(
      @PathVariable @Valid @Size(min = 1,max = 3) String id){
    return service.searchStudent(id);
  }

  @Operation(summary = "受講生の登録", description = "新しい受講生の詳細情報を登録します。")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "登録に成功"),
      @ApiResponse(responseCode = "400", description = "バリデーションエラーによる登録失敗")
  })

  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "登録する受講生の詳細情報")
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
  return ResponseEntity.ok(responseStudentDetail);
  }

  @Operation(summary = "受講生の更新", description = "受講生の詳細情報を更新します。キャンセルフラグの削除も含みます。")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "更新に成功"),
      @ApiResponse(responseCode = "400", description = "バリデーションエラーまたは更新失敗")
  })

  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  @Operation(summary = "例外発生のテスト用", description = "このエンドポイントにアクセスすると例外がスローされます（動作確認用）")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "意図的に発生させた例外です")
  })

  @GetMapping("/exception")
  public void throwException() {
    throw new MyException("これはテスト用の例外です。");
  }

}
