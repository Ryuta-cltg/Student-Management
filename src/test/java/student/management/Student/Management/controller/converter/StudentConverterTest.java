package student.management.Student.Management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.domein.StudentDetail;

class StudentConverterTest {
  private final StudentConverter converter = new StudentConverter();

  @DisplayName("StudentConverterの変換処理テスト")
  //正常系
  @Test
  void 受講生詳細_StudentとCourse情報が正しくマッピングされること(){

    //Arrange : 受講生2人を用意(全フィールドを設定)
    Student student1 = new Student();
    student1.setId("1");
    student1.setFullname("高橋一美");
    student1.setFurigana("たかはしかずみ");
    student1.setNickname("かみ");
    student1.setEmail("takahashi@example.com");
    student1.setRegion("北海道");
    student1.setAge(33);
    student1.setGender("女性");
    student1.setRemark("1人目の備考");
    student1.setDeleted(false);

    Student student2 = new Student();
    student2.setId("2");
    student2.setFullname("伊藤　健");
    student2.setFurigana("いとうたけし");
    student2.setNickname("タケシ");
    student2.setEmail("ito@example.com");
    student2.setRegion("福岡");
    student2.setAge(25);
    student2.setGender("男性");
    student2.setRemark("2人目の備考");
    student2.setDeleted(false);

    //コース1 : student1に紐づく
    StudentCourse course1 = new StudentCourse();
    course1.setStudentId("1");
    course1.setCourseName("WM");
    course1.setStartDate(LocalDateTime.of(2025, 2, 1, 0, 0));
    course1.setEndDate(LocalDateTime.of(2025, 8, 31, 0, 0));

    //コース2 : student2に紐づく
    StudentCourse course2 = new StudentCourse();
    course2.setStudentId("2");
    course2.setCourseName("Java");
    course2.setStartDate(LocalDateTime.of(2025, 3, 1, 0, 0));
    course2.setEndDate(LocalDateTime.of(2025, 6, 30, 0, 0));

    // コース3 : 存在しないstudentIdに紐づく（＝マッチしてはいけない）
    StudentCourse course3 = new StudentCourse();
    course3.setStudentId("9999");
    course3.setCourseName("未登録");

    List<Student> studentList = List.of(student1,student2);
    List<StudentCourse> studentCourseList =List.of(course1,course2,course3);

    //Act : converterの呼び出し
    List<StudentDetail> result = converter.convertStudentDetails(studentList,studentCourseList);

    //Assert : 受講生詳細が2件生成されていること
    Assertions.assertEquals(2,result.size());

    // detail1（student1）の全値の検証
    StudentDetail detail1 = result.getFirst();
    Student s1 = detail1.getStudent();
    StudentCourse c1 = detail1.getStudentCourseList().getFirst();

    assertThat(s1.getId()).isEqualTo("1");
    assertThat(s1.getFullname()).isEqualTo("高橋一美");
    assertThat(s1.getFurigana()).isEqualTo("たかはしかずみ");
    assertThat(s1.getNickname()).isEqualTo("かみ");
    assertThat(s1.getEmail()).isEqualTo("takahashi@example.com");
    assertThat(s1.getRegion()).isEqualTo("北海道");
    assertThat(s1.getAge()).isEqualTo(33);
    assertThat(s1.getGender()).isEqualTo("女性");
    assertThat(s1.getRemark()).isEqualTo("1人目の備考");
    assertThat(s1.isDeleted()).isFalse();

    assertThat(c1.getStudentId()).isEqualTo("1");
    assertThat(c1.getCourseName()).isEqualTo("WM");
    assertThat(c1.getStartDate()).isEqualTo(LocalDateTime.of(2025, 2, 1, 0, 0));
    assertThat(c1.getEndDate()).isEqualTo(LocalDateTime.of(2025, 8, 31, 0, 0));

    // detail2（student2）に関する検証
    StudentDetail detail2 = result.get(1);
    Student s2 = detail2.getStudent();
    StudentCourse c2 = detail2.getStudentCourseList().getFirst();

    assertThat(s2.getId()).isEqualTo("2");
    assertThat(s2.getFullname()).isEqualTo("伊藤　健");
    assertThat(s2.getFurigana()).isEqualTo("いとうたけし");
    assertThat(s2.getNickname()).isEqualTo("タケシ");
    assertThat(s2.getEmail()).isEqualTo("ito@example.com");
    assertThat(s2.getRegion()).isEqualTo("福岡");
    assertThat(s2.getAge()).isEqualTo(25);
    assertThat(s2.getGender()).isEqualTo("男性");
    assertThat(s2.getRemark()).isEqualTo("2人目の備考");
    assertThat(s2.isDeleted()).isFalse();

    assertThat(c2.getStudentId()).isEqualTo("2");
    assertThat(c2.getCourseName()).isEqualTo("Java");
    assertThat(c2.getStartDate()).isEqualTo(LocalDateTime.of(2025, 3, 1, 0, 0));
    assertThat(c2.getEndDate()).isEqualTo(LocalDateTime.of(2025, 6, 30, 0, 0));

    // 追加検証："未登録" コースがどこにも含まれていないことを確認
   assertThat(
       result.stream()
        .flatMap(detail -> detail.getStudentCourseList().stream())
        .map(StudentCourse::getCourseName)
        .collect(Collectors.toList())

       // "未登録" のコースは出力に含まれていないこと
        ).doesNotContain("未登録");
  }

  //異常系 : 空のリスト
  @Test
  void 受講生詳細_空のリストを渡すと空のリストが返る(){

    //Arrange : 空のリストの作成
    List<Student>studentList = new ArrayList<>();
    List<StudentCourse>courseList =new ArrayList<>();

    //Act : 空のリストを渡して変換
    List<StudentDetail> result = converter.convertStudentDetails(studentList,courseList);

    //Assert : 空のリストが返ることを確認
    assertThat(result).isEmpty();
  }

  //異常系 : StudentListがnull
  /*@Test
  void 受講生詳細_studentListがnullならNullPointerExceptionがスローされる(){
    List<StudentCourse> courseList =new ArrayList<>();
    assertThrows(NullPointerException.class,() -> {
      converter.convertStudentDetails(null, courseList);
    });
  }

  //異常系 : StudentCourseListがnull 現状はnullを渡すようにはなっていないため、今後必要であれば
  @Test
  　void 受講生詳細_studentCourseListがnullならNullPointerExceptionがスローされる() {
    List<Student> studentList = new ArrayList<>();
    assertThrows(NullPointerException.class, () -> {
      converter.convertStudentDetails(studentList, null);
    });
  }*/
}