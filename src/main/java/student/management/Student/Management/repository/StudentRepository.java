package student.management.Student.Management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;

@Mapper
public interface StudentRepository {
  //名前で検索
  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);
   //受講生情報(全件)を返す
  @Select("SELECT * FROM students_courses")
  List<StudentCourses> searchStudentCoursesList();
   //特定の受講生コース情報を返す
  @Select("SELECT * FROM students_courses WHERE student_id = #{student_id}")
  List<StudentCourses> searchStudentCourses(String id);

  @Insert("INSERT INTO students(full_name, furigana, nickname, email, region, age, gender, remark, is_deleted)"
  +"VALUES(#{fullname}, #{furigana}, #{nickname}, #{email}, #{region}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses(student_id, course_name, start_date, end_date)"
      +"VALUES(#{studentId}, #{courseName}, #{startDate}, #{endDate}) ")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourses(StudentCourses studentCourses);
  //生徒情報の更新
  @Update("UPDATE students SET full_name = #{fullname}, furigana = #{furigana}, nickname = #{nickname}, email = #{email},"
         +"region = #{region}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);
  //コース情報の更新
  @Update("UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id}")
  void updateStudentCourses(StudentCourses studentCourses);
}
