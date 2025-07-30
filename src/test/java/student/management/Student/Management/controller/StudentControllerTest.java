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

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

  @Test
  @DisplayName("正常系：受講生一覧を取得し、空のリストが返ること")
  void 受講生詳細_一覧検索ができて空のリストが返ってくること() throws Exception {

    //Act & Assert : GETリクエストを実行し、ステータス200とサービス呼び出し回数を検証
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  @DisplayName("正常系：受講生に適切な値を入力するとバリデーションエラーが発生しない")
  void 受講生詳細_受講生で適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();

    //Arrange : 正しい項目値をセット
    student.setId("1");
    student.setFullname("高橋一美");
    student.setFurigana("たかはしかずみ");
    student.setNickname("かみ");
    student.setEmail("takahasi@example.com");
    student.setRegion("北海道");
    student.setGender("女性");
    student.setAge(33);

    //Act : バリデーションの実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //Assert : エラー件数が0であることを検証
    Assertions.assertEquals(0, violations.size());
  }

  @Test
  @DisplayName("異常系：受講生IDに不正な文字列を入力するとバリデーションエラーが発生する")
  void 受講生詳細_受講生でIDに数字以外が入力された時に入力チェックがかかること() {
    //Arrange : 不正なIDをセット
    Student student = new Student();
    student.setId("テストです");

    //Act : バリデーションを実行
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    //Assert : エラー件数が期待値と一致することを検証
    Assertions.assertEquals(7, violations.size());
  }

  @Test
  @DisplayName("正常系：ID指定で受講生詳細が取得できること")
  void 受講生詳細_IDを指定することで受講生情報が返ること() throws Exception {

    //Arrange ： サービスの戻り値を設定
    Student student = new Student();
    student.setId("1");
    student.setFullname("高橋一美");

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);

    given(service.searchStudent("1")).willReturn(detail);

    //Act & Assert ： GET実行後のレスポンスを検証
    mockMvc.perform(get("/student/{id}", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.student.id").value("1"))
        .andExpect(jsonPath("$.student.fullname").value("高橋一美"));
  }

  @Test
  @DisplayName("異常系：存在しないIDを指定するとMyExceptionが返る")
  void 受講生詳細_存在しないIDを指定するとMyExceptionが返ること() throws Exception {
    // Arrange：例外をスローするようモック設定
    given(service.searchStudent("999")).willThrow(new MyException("存在しないIDです。"));

    // Act & Assert：GET時に400エラーとメッセージを検証
    mockMvc.perform(get("/student/999"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("存在しないIDです。"));
  }

  @Test
  @DisplayName("異常系：IDの桁数超過により400エラーが返る")
  void 受講生詳細_IDが長すぎる場合は400が返ってくること() throws Exception {
    // Act & Assert：GET /exception 実行で例外ハンドリングを確認
    mockMvc.perform(get("/student/9999"))
        .andExpect(status().isBadRequest());

  }

  @Test
  @DisplayName("正常系：正しい入力で受講生登録が成功すること")
  void 受講生登録_正しい入力で登録が成功すること() throws Exception {

    //Arrange ： 登録用の正常なリクエストボディ（JSON）
    String json = """
  {
    "student": {
      "id": "1",
      "fullname": "高橋一美",
      "furigana": "たかはしかずみ",
      "nickname": "かみ",
      "email": "takahasi@example.com",
      "region": "北海道",
      "gender": "女性",
      "age": 33,
      "remark": "備考",
      "deleted": false
    },
    "studentCourseList": [
      {
        "courseName": "WM"
      }
    ]
  }
  """;

    // Arrange : MockしたStudentService が正常に登録処理を行い、空のStudentDetailを返すように設定
    given(service.registerStudent(any())).willReturn(new StudentDetail());

    // Act & Assert : 登録APIに対してPOSTリクエストを送信し、HTTPステータス200（OK）が返されることを検証
    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("異常系：登録時にMyExceptionが発生すると400エラーが返る")
  void 受講生登録_サービスでMyExceptionが発生した場合400番エラーが返ること() throws Exception {

    //Arrange : バリデーションエラー用の不正なJSON（メール形式不正）
    String json = """
  {
    "student": {
      "id": "1",
      "fullname": "高橋一美",
      "furigana": "たかはしかずみ",
      "nickname": "かみ",
      "email": "takahasi@examplecom",
      "region": "北海道",
      "gender": "女性",
      "age": 33,
      "remark": "備考",
      "deleted": false
    },
    "studentCourseList": [
      {
        "courseName": "デザインコース"
      }
    ]
  }
  """;

    // Arrange : サービス層で MyException をスローするようモック設定
    given(service.registerStudent(any()))
        .willThrow(new MyException("登録失敗"));

    //Act & Assert : 登録APIをPOSTリクエストで呼び出し、HTTP 400 BadRequest が返されることを検証
    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isBadRequest());

  }

  @Test
  @DisplayName("正常系：受講生情報の更新が成功すること")
  void 受講生更新_正しい入力で更新成功すること()throws Exception{

    //Arrange ： 更新用の正常なリクエストボディ（JSON）
    String json = """
  {
    "student": {
      "id": "1",
      "fullname": "高橋一美",
      "furigana": "たかはしかずみ",
      "nickname": "かみ",
      "email": "takahasi@example.com",
      "region": "北海道",
      "gender": "女性",
      "age": 33,
      "remark": "備考",
      "deleted": false
    },
    "studentCourseList": [
      {
        "courseName": "WM"
      }
    ]
  }
  """;

    //Act & Assert : 更新APIに対して PUT リクエストを送信し、HTTPステータス200（OK）と「更新処理が成功しました。」というメッセージが返ることを検証
    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("更新処理が成功しました。")));
  }

  @Test
  @DisplayName("異常系：更新時にMyExceptionが発生すると400エラーが返る")
  void 受講生更新_サービスでMyExceptionが発生した場合400番エラーが返ってくること()throws Exception{

    //Arrange : 更新用のJSONリクエストと、ServiceがMyExceptionをスローするモック設定
    String json = """
  {
    "student": {
      "id": "1",
      "fullname": "高橋一美",
      "furigana": "たかはしかずみ",
      "nickname": "かみ",
      "email": "takahasi@example.com",
      "region": "北海道",
      "gender": "女性",
      "age": 33,
      "remark": "備考",
      "deleted": false
    },
    "studentCourseList": [
      {
        "courseName": "Javaコース"
      }
    ]
  }
  """;

    doThrow(new MyException("更新失敗")).when(service).updateStudent(any());

    //Act & Assert : 更新APIに対してPUTリクエストを送信し、HTTPステータス400（Bad Request）とメッセージ「更新失敗」が返ることを検証
    mockMvc.perform(put("/updateStudent")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("更新失敗"));

  }

  @Test
  @DisplayName("異常系：例外発生時に400エラーが返る")
  void 例外テストポイント_例外が発生して400番エラーが返ること()throws Exception{
    // Act & Assert：GET /exception 実行で例外ハンドリングを確認
    mockMvc.perform(get("/exception"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("これはテスト用の例外です。"));
  }
}