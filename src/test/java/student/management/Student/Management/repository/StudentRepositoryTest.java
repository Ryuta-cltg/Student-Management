package student.management.Student.Management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  @DisplayName("受講生_全件検索 : data.sql　の件数が返ること")
  void 受講生の全件検索が行えること(){
    List<Student> actual = sut.search();
    assertThat(actual).hasSize(7);
  }
  @Test
  @DisplayName("受講生_検索 : ID=1 の受講生が取得できること")
  void 受講生の検索が行えること() {
    Student student = sut.searchStudent("1");
    assertThat(student.getId()).isEqualTo("1");
    assertThat(student.getFullname()).isEqualTo("高橋一美");
  }

  @Test
  @DisplayName("受講生コース_全件検索 : 件数が返ること")
  void 受講生コースの全件検索が行えること() {
    List<StudentCourse> courses = sut.searchStudentCourseList();
    assertThat(courses).hasSize(7);
  }

  @Test
  @DisplayName("受講生コース_検索 : studentId=1 のコースが取得できること")
  void 受講生コースの検索が行えること() {
    List<StudentCourse> courses = sut.searchStudentCourse("1");
    assertThat(courses).isNotEmpty();
    assertThat(courses.getFirst().getCourseName()).isEqualTo("WM");
  }

  @Test
  @DisplayName("コース名_存在チェック : WM が存在すること")
  void コース名存在チェック() {
    Integer count = sut.existsCourseName("WM");
    assertThat(count).isEqualTo(1);
  }

  @Test
  @DisplayName("受講生_登録 : 必要項目を満たせば登録できること")
  void 受講生の登録が行えること(){
    Student s = new Student();
    s.setFullname("テスト太郎");
    s.setFurigana("てすとたろう");
    s.setNickname("たろ");
    s.setEmail("taro@example.com");
    s.setRegion("東京");
    s.setAge(20);
    s.setGender("男性");
    s.setRemark("");

    sut.registerStudent(s);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(8);
  }

  @Test
  @DisplayName("受講生更新 : full_nameが更新されること")
  void 受講生の更新が行えること(){
    Student student = sut.searchStudent("1");
    student.setFullname("田中修一");

    sut.updateStudent(student);

    Student updated = sut.searchStudent("1");
    assertThat(updated.getFullname()).isEqualTo("田中修一");
  }
}