package student.management.Student.Management.service;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import student.management.Student.Management.controller.converter.StudentConverter;
import student.management.Student.Management.data.Student;
import student.management.Student.Management.data.StudentCourse;
import student.management.Student.Management.domein.StudentDetail;
import student.management.Student.Management.exception.MyException;
import student.management.Student.Management.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentConverter converter;
  @Autowired
  public StudentService(StudentRepository repository,StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索です。
   * 全件検索を行うので、条件指定は行いません。。
   *
   * @return 受講生詳細一覧(全件)
   */

  public List<StudentDetail> searchStudentList() {
    List<Student> studentList =repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(studentList,studentCourseList);
  }

  /**
   * 受講生詳細検索です。
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id　受講生ID
   * @return 受講生詳細
   */

   public StudentDetail searchStudent(String id){
    Student student = repository.searchStudent(id);
    if (student == null){
      //404　Not Foundを返す
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,"受講生が見つかりません: "+id);
    }
     List<StudentCourse> studentCourse= repository.searchStudentCourse(student.getId());
     return new StudentDetail(student,studentCourse);
   }

  /**
   * 受講生詳細の登録を行います。
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値と日付情報(コース開始日など)を設定します。
   * 
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.registerStudent(student);

    studentDetail.getStudentCourseList().forEach(sc -> {
      ensureValidCourseName(sc.getCourseName());     // ★追加：存在チェック
      initStudentCourse(sc, student);                // 既存：初期値設定
      repository.registerStudentCourse(sc);          // 既存：登録
    });
    return studentDetail;
  }
  /**
   * 受講生詳細の更新を行います。
   * 受講生の情報と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());

    // コースごとに：存在チェック → 更新
    studentDetail.getStudentCourseList().forEach(sc -> {
      ensureValidCourseName(sc.getCourseName());
      repository.updateStudentCourse(sc);
    });
  }

  //呼び出し専用の補助メソッド
  /**
   * コース名がマスタに存在することを保証（存在しなければ MyException）
   */
  private void ensureValidCourseName(String courseName) {
    int count = repository.existsCourseName(courseName);
    if (count == 0) {
      throw new MyException("存在しないコースです: " + courseName);
    }
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定します。
   *
   * @param studentCourse 受講生コース情報
   * @param student 受講生
   */

  private  void initStudentCourse(StudentCourse studentCourse, Student student) {
    LocalDateTime now = LocalDateTime.now();
    studentCourse.setStudentId(student.getId());
    studentCourse.setStartDate(now);
    studentCourse.setEndDate(now.plusYears(1));
    }
  }