package student.management.Student.Management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.domein.StudentDetail;

/**
 * Serviceから取得したオブジェクトをControllerにとって都合の良い形に変換するクラス
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生情報コースをマッピングする。
   * 受講生コース情報は受講生に対して複数存在するのでループを回して受講生情報を組み立てる。
   * @param studentList 受講生一蘭
   * @param studentCourseList 受講生コース情報のリスト
   * @return 受講生詳細情報のリスト
   */

  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentCourse> studentCourseList) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setStudentCourseList(convertStudentCourseList);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
