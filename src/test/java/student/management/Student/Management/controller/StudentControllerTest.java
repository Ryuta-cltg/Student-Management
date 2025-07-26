package student.management.Student.Management.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.domein.StudentDetail;
import student.management.Student.Management.exception.MyException;
import student.management.Student.Management.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  // StudentServiceのMockをDI。Controller単体テストのため。
  @SuppressWarnings("removal")
  @MockBean
  private StudentService service;

  // バリデーションチェック用のValidatorインスタンス生成
  @SuppressWarnings("resource")
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  // 一覧取得（正常系）
  // 受講生一覧の取得APIで 200 OK が返り、service が1回呼ばれることを確認
  @Test
  void 受講生詳細_一覧検索ができて空のリストが返ってくること() throws Exception {

    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  // バリデーション正常系
  // Studentエンティティの各項目に適切な値を設定したとき、バリデーション違反が発生しないことを確認
  @Test
  void 受講生詳細_受講生で適切な値を入力したときに入力チェックに異常が発生しないこと() {
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
  void 受講生詳細_受講生でIDに数字以外が入力された時に入力チェックがかかること() {
    Student student = new Student();
    student.setId("テストです");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    Assertions.assertEquals(7, violations.size());
  }

  //個別取得(正常)
  //StudentにIDとフルネームを設定し、それをテスト用に用意したdetailに返す。
  @Test
  void 受講生詳細_IDを指定することで受講生情報が帰ること() throws Exception {
    Student student = new Student();

    student.setId("1");
    student.setFullname("高橋一美");

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);

    given(service.searchStudent("1")).willReturn(detail);

    mockMvc.perform(get("/student/{id}", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.student.id").value("1"))
        .andExpect(jsonPath("$.student.fullname").value("高橋一美"));
  }

  //個別取得(異常系)
  // 存在しないIDを指定したとき、MyExceptionが発生して400が返る
  @Test
  void 受講生詳細_存在しないIDを指定するとMyExceptionが返ること() throws Exception {

    given(service.searchStudent("999")).willThrow(new MyException("存在しないIDです。"));
    mockMvc.perform(get("/student/999"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("存在しないIDです。"));
  }

  // IDの形式不正（桁数制限など）で400エラーが返ることを確認
  @Test
  void 受講生詳細_IDが長すぎる場合は400が返ってくること() throws Exception {
    mockMvc.perform(get("/student/9999"))
        .andExpect(status().isBadRequest());
  }

  @Autowired
  private ObjectMapper objectMapper;

  // 登録（正常系）
  // 正しいStudentDetailを渡すと200 OKが返る
  @Test
  void 受講生登録_正しい入力で登録が成功すること() throws Exception {
    Student student = new Student();

    student.setId("1");
    student.setFullname("高橋一美");
    student.setFurigana("たかはしかずみ");
    student.setNickname("かみ");
    student.setEmail("takahasi@exanple.com");
    student.setRegion("北海道");
    student.setGender("女性");
    student.setAge(33);
    student.setRemark("備考");
    student.setDeleted(false);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);

    given(service.registerStudent(any())).willReturn(studentDetail);

    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentDetail)))
            .andExpect(status().isOk());
  }

  // 登録（異常系）
  // サービス層でMyExceptionが発生した場合に400エラーが返ること
  @Test
  void 受講生登録_サービスでMyExceptionが発生した場合400番エラーが返ること() throws Exception {
    Student student = new Student();

    student.setId("1");
    student.setFullname("高橋一美");
    student.setFurigana("たかはしかずみ");
    student.setNickname("かみ");
    student.setEmail("takahasi@exanple.com");
    student.setRegion("北海道");
    student.setGender("女性");
    student.setAge(33);
    student.setRemark("備考");
    student.setDeleted(false);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);

    given(service.registerStudent(any())).willThrow(new MyException("登録失敗"));

    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(student)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("登録失敗"));
  }

  // 更新（正常系）
  // 正しい入力で更新が成功し、成功メッセージが返る
  @Test
  void 受講生更新_正しい入力で更新成功すること()throws Exception{
    Student student = new Student();

    student.setId("1");
    student.setFullname("高橋一美");
    student.setFurigana("たかはしかずみ");
    student.setNickname("かみ");
    student.setEmail("takahasi@exanple.com");
    student.setRegion("北海道");
    student.setGender("女性");
    student.setAge(33);
    student.setRemark("備考");
    student.setDeleted(false);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);

    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentDetail)))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("更新処理が成功しました。")));
  }

  // 更新（異常系）
  // サービスで例外が発生した場合に400が返る
  @Test
  void 受講生更新_サービスでMyExceptionが発生した場合400番エラーが返ってくること()throws Exception{
    Student student = new Student();

    student.setId("1");
    student.setFullname("高橋一美");
    student.setFurigana("たかはしかずみ");
    student.setNickname("かみ");
    student.setEmail("takahasi@exanple.com");
    student.setRegion("北海道");
    student.setGender("女性");
    student.setAge(33);
    student.setRemark("備考");
    student.setDeleted(false);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);

    doThrow(new MyException("更新失敗"))
        .when(service)
        .updateStudent(any());

    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentDetail)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("更新失敗"));
  }

  // 例外発生のシミュレーション（テスト用）
  @Test
  void 例外テストポイント_例外が発生して400番エラーが返ること()throws Exception{
    mockMvc.perform(get("/exception"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("これはテスト用の例外です。"));
  }
}