package student.management.Student.Management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourses;

@Mapper
public interface StudentRepository {
  //名前で検索
  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentCourses> searchStudentCourses();

  @Insert("INSERT INTO students(full_name, furigana, nickname, email, region, age, gender, remark, is_deleted)"
  +"VALUES(#{fullname}, #{furigana}, #{nickname}, #{email}, #{region}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);
}
