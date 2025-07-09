package exception;


import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   *バリデーションエラー用
   * （@Valid/@Validated）発生時に処理される例外ハンドラです。
   * リクエストボディ内のデータがバリデーション制約に違反した場合にこのメソッドが呼び出され、
   * 各フィールドごとのエラーメッセージをMap形式でレスポンスとして返却します。
   * @param ex バリデーション例外オブジェクト（MethodArgumentNotValidException）
   * @return 各フィールドとエラーメッセージを格納したMapを含むレスポンスエンティティ（HTTPステータス 400 Bad Request）
   */

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
    );

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  /**
   *
   * @param ex
   * @return
   */
  // 独自例外用
  @ExceptionHandler(MyException.class)
  public ResponseEntity<String> handleMyException(MyException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  // 予期しないエラー
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleOtherExceptions(Exception ex) {
    return new ResponseEntity<>("サーバー内部でエラーが発生しました", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  }