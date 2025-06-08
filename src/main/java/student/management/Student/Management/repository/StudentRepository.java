package student.management.Student.Management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;


@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一蘭(全件)
   */

  //@Select("SELECT * FROM students")
  List<Student> search();

  /**
   * 受講生の検索を行います。
   * @param id　受講生ID
   * @return 受講生
   */

  //@Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報(全件)
   */

  //@Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生
   */

  //@Select("SELECT * FROM students_courses WHERE student_id = #{student_id}")
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   *
   * @param student 受講生
   */

  //@Insert("INSERT INTO students(full_name, furigana, nickname, email, region, age, gender, remark, is_deleted)"
  //+"VALUES(#{fullname}, #{furigana}, #{nickname}, #{email}, #{region}, #{age}, #{gender}, #{remark}, false)")
  //@Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
   *
   * @param studentCourse 受講生コース情報
   */

  //@Insert("INSERT INTO students_courses(student_id, course_name, start_date, end_date)"
  //+"VALUES(#{studentId}, #{courseName}, #{startDate}, #{endDate}) ")
  //@Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   *
   * @param student　受講生
   */

  //@Update("UPDATE students SET full_name = #{fullname}, furigana = #{furigana}, nickname = #{nickname}, email = #{email},"
    //     +"region = #{region}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);


  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース
   */

  //@Update("UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id}")
  void updateStudentCourse(StudentCourse studentCourse);
}
