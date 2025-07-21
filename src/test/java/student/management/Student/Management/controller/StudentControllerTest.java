package student.management.Student.Management.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.domein.StudentDetail;
import student.management.Student.Management.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @SuppressWarnings("removal")
  @MockBean
  private StudentService service;

  @SuppressWarnings("resource")
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  // 一覧取得（正常系）
  // 受講生一覧の取得APIで 200 OK が返り、service が1回呼ばれることを確認
  @Test
  void 受講生詳細_一覧検索ができて空のリストが返ってくること() throws Exception{

    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk()) ;

    verify(service, times(1)).searchStudentList();
  }

  // バリデーション正常系
  // Studentエンティティの各項目に適切な値を設定したとき、バリデーション違反が発生しないことを確認
  @Test
  void 受講生詳細_受講生で適切な値を入力したときに入力チェックに異常が発生しないこと(){
    Student student = new Student();

    //バリデーション対応項目と値
    student.setId("1");
    student.setFullname("高橋一美");
    student.setFurigana("たかはしかずみ");
    student.setNickname("かみ");
    student.setEmail("takahasi@example.com");
    student.setRegion("北海道");
    student.setGender("女性");
    student.setAge(33);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    Assertions.assertEquals(0, violations.size());
  }

  // バリデーション異常
  // Studentエンティティに不正なID（数字以外）を設定したとき、バリデーション違反が発生することを確認
  @Test
  void 受講生詳細_受講生でIDに数字以外が入力された時に入力チェックがかかること(){
    Student student = new Student();
        student.setId("テストです");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

    Assertions.assertEquals(7, violations.size());
  }

  //個別取得(正常)
  //StudentにIDとフルネームを設定し、それをテスト用に用意したdetailに返す。
  @Test
  void 受講生詳細_IDを指定することで受講生情報が帰ること()throws Exception{
    Student student = new Student();
    student.setId("1");
    student.setFullname("高橋一美");

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);

    given(service.searchStudent("1")).willReturn(detail);

    mockMvc.perform(get("/student/{id}","1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.student.id").value("1"))
        .andExpect(jsonPath("$.student.fullname").value("高橋一美"));
  }
  //個別取得(異常系)

}