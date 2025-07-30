package student.management.Student.Management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.domein.StudentDetail;

class StudentConverterTest {
  private final StudentConverter converter = new StudentConverter();

  @Test
  void 受講生詳細_StudentとCourse情報が正しくマッピングされること(){
    //Arrange
    Student student1 = new Student();
    student1.setId("1");
    student1.setFullname("高橋一美");

    Student student2 = new Student();
    student2.setId("2");
    student2.setFullname("伊藤　健");

    StudentCourse course1 = new StudentCourse();
    course1.setStudentId("1");
    course1.setCourseName("WM");

    StudentCourse course2 = new StudentCourse();
    course2.setStudentId("2");
    course2.setCourseName("Java");

    List<Student> studentList = List.of(student1,student2);
    List<StudentCourse> studentCourseList =List.of(course1,course2);

    //Act
    List<StudentDetail> result = converter.convertStudentDetails(studentList,studentCourseList);

    //Assert
    Assertions.assertEquals(2,result.size());

    StudentDetail detail1 = result.getFirst();
    Assertions.assertEquals("1",detail1.getStudent().getId());
    Assertions.assertEquals(1,detail1.getStudentCourseList().size());

    StudentDetail detail2 = result.get(1);
    Assertions.assertEquals("2",detail2.getStudent().getId());
    Assertions.assertEquals(1,detail2.getStudentCourseList().size());
    Assertions.assertEquals("Java",detail2.getStudentCourseList().getFirst().getCourseName());
  }

  @Test
  void 受講生詳細_空のリストを渡すと空のリストが返る(){
    //Arrange
    List<Student>studentList = new ArrayList<>();
    List<StudentCourse>courseList =new ArrayList<>();
    //Act
    List<StudentDetail> result = converter.convertStudentDetails(studentList,courseList);
    //Assert
    assertThat(result).isEmpty();
  }
}