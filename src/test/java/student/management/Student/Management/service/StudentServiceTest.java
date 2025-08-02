package student.management.Student.Management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import student.management.Student.Management.controller.converter.StudentConverter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.domein.StudentDetail;
import student.management.Student.Management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  // モック化した依存クラス(RepositoryとConverter)
  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  // テスト対象のクラス（Service）を宣言
  private StudentService sut;

  // 各テストの前に、Serviceクラスにモックを注入してインスタンス生成
  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出されていること() {

    // Arrange（準備）: モックの戻り値として空のリストを定義
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    // Act（実行）: 一覧検索のメソッドを実行
    sut.searchStudentList();

    // verify（確認）: モックのメソッドが期待通りに(1回ずつ)呼ばれたかを確認
    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void 受講生の詳細検索_IDに基づいて受講生とコースを取得し詳細を返すこと() {
    //Arrange（準備）: モックが返す受講生とコースを定義
    Student student = new Student();
    student.setId("999");

    List<StudentCourse> courseList = List.of(new StudentCourse());

    when(repository.searchStudent("999")).thenReturn(student);
    when(repository.searchStudentCourse("999")).thenReturn(courseList);

    //Act（実行）:　受講生詳細検索を実行
    StudentDetail result = sut.searchStudent("999");

    // Assert（検証）: 結果の中身が期待通りであることを検証
    assertEquals(student, result.getStudent());
    assertEquals(courseList, result.getStudentCourseList());

    // Assert（確認）: モックが正しく呼び出されたかを検証
    verify(repository).searchStudent("999");
    verify(repository).searchStudentCourse("999");
  }

  @Test
  void 受講生登録_受講生と受講生コースが適切に登録されること() {
    //Arrange（準備）: テストの対象となる受講生と受講生コース情報を定義
    Student student = new Student();
    student.setId("999");

    StudentCourse course = new StudentCourse();
    List<StudentCourse> courseList = List.of(course);

    StudentDetail detail = new StudentDetail(student, courseList);

    // Act（実行）: 登録メソッドを呼び出し
    StudentDetail result = sut.registerStudent(detail);

    // Assert（検証）: 各リポジトリのメソッドが呼ばれたか確認
    verify(repository).registerStudent(student);
    verify(repository).registerStudentCourse(course);

    // Assert（間接検証）: initStudentCourse() による初期化内容の確認
    assertEquals("999", course.getStudentId());
    assertNotNull(course.getStartDate());
    assertNotNull(course.getEndDate());
    assertTrue(course.getEndDate().isAfter(course.getStartDate()));

    // 戻り値が同じオブジェクトか確認
    assertSame(detail, result);
  }

  @Test
  void 受講生更新_受講生とコース情報の更新が実行されること(){
    // Arrange（準備）: 受講生と複数の受講生コース情報を用意
    Student student = new Student();
    StudentCourse course = new StudentCourse();
    List<StudentCourse> courseList = List.of(course);

    StudentDetail detail = new StudentDetail(student,courseList);

    // Act（実行）: サービスの更新処理を実行
    sut.updateStudent(detail);

    // Assert（検証）: リポジトリのメソッドが正しく呼び出されたかを確認
    verify(repository).updateStudent(student);
    verify(repository).updateStudentCourse(course);
  }
}