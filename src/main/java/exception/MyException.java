package exception;

/**
 * アプリケーション内で発生する業務的な例外を表すカスタム例外クラスです。
 *
 * バリデーションやビジネスロジック違反など、明示的に検知・通知したいエラーに対して使用されます。
 * {@link GlobalExceptionHandler} にて処理され、クライアントに適切なメッセージを返却します。
 */

public class MyException extends RuntimeException {

  /**
   * 指定されたメッセージを持つ新しい例外を構築します。
   *
   * @param message エラーメッセージ
   */

  public MyException(String message) {
    super(message);
  }
}
