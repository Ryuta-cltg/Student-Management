package student.management.Student.Management.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * バリデーションエラー用 （@Valid/@Validated）発生時に処理される例外ハンドラです。 リクエストボディ内のデータがバリデーション制約に違反した場合にこのメソッドが呼び出され、
   * 各フィールドごとのエラーメッセージをMap形式でレスポンスとして返却します。
   *
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
   * 独自例外用
   * アプリケーション内で発生する業務的な例外 {@link MyException} を処理します。
   * この例外は、バリデーションやビジネスロジック上の明示的なエラーで使用され、
   * クライアントには HTTP 400（Bad Request） として返却されます。
   *
   * @param ex 捕捉された MyException インスタンス
   * @return エラーメッセージと HTTP 400 ステータスを含むレスポンスエンティティ
   */

  @ExceptionHandler(MyException.class)
  public ResponseEntity<String> handleMyException(MyException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  /**
   * 予期しないエラー用
   * アプリケーションで発生した予期しない例外（全ての {@link Exception}）を処理します。
   * ログ出力や通知の対象となり得る、開発者の想定外の例外です。
   * クライアントには一般的なエラーメッセージと HTTP 500（内部サーバーエラー）を返却します。
   *
   * @param ex 捕捉された Exception インスタンス
   * @return 汎用エラーメッセージと HTTP 500 ステータスを含むレスポンスエンティティ
   */

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleOtherExceptions(Exception ex) {
    return new ResponseEntity<>("サーバー内部でエラーが発生しました", HttpStatus.INTERNAL_SERVER_ERROR);
  }

}