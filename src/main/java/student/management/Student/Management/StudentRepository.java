package student.management.Student.Management;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentRepository {
  //名前で検索
  @Select("SELECT * FROM student WHERE name = #{name}")
  Student searchByName(String name);
  //学生を登録
  @Insert("INSERT values (#{name}, #{age})")
  void registerStudent(String name,int age);
  //学生を更新
  @Update("UPDATE student SET age = #{age} WHERE name = #{name}")
  void updateStudent(String name,int age);
  //学生を削除
  @Delete("DELETE FROM student WHERE name = #{name}")
  void deleteStudent(String name);
}
