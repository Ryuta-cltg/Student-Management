＃受講生管理システム

本アプリケーションは、受講生情報の登録・一覧表示・更新することのできる簡易的な受講生管理システムです。
バックエンドはSpring Boot、フロントはThymeleafを使用しています。

---
## 📌 使用技術

- Java 17
- Spring Boot 3.x
- Spring MVC
- MyBatis
- Thymeleaf
- MySQL
- Gradle
- IntelliJ IDEA
- Git / GitHub

---

## 🎯 主な機能

- 受講生一覧表示（Thymeleaf + REST）
- 受講生新規登録（バリデーション付き）
- 受講生情報の更新機能
- コース情報の紐付け（受講生1名に対して複数コース可）
- エラーハンドリング（未登録のコース名に対して例外）

---

## 🧪 テスト

- `StudentControllerTest`：MockMvcを使用したREST APIの結合テスト
- `StudentViewControllerTest`：Thymeleaf画面の遷移・バリデーションテスト
- `StudentServiceTest`：Mockitoによるユニットテスト（正常系・異常系含む）
- `StudentConverterTest`：DTO変換ロジックの単体テスト
- `StudentRepositoryTest`：MyBatisの動作確認（@MybatisTest使用）

---
## 🗃 ディレクトリ構成
```
├── controller/
│   ├── StudentController.java        # REST API用
│   └── StudentViewController.java    # Thymeleaf画面用

├── service/
│   └── StudentService.java

├── repository/
│   └── StudentRepository.java

├── model/
│   ├── Student.java
│   ├── StudentCourse.java
│   └── StudentDetail.java

├── converter/
│   └── StudentConverter.java

├── exception/
│   ├── GlobalExceptionHandler.java
│   └── MyException.java

├── test/
│   ├── controller/  （Controller系）
│   ├── service/     （Service層）
│   ├── converter/   （変換ロジック）
│   └── repository/  （MyBatisテスト）

└── templates/
    ├── studentList.html
    ├── registerStudent.html
    └── updateStudent.html
```
---
## ✅ 今後の拡張予定

- ログイン認証機能の追加（Spring Security）
- 検索条件の絞り込み（名前・地域など）
- 申込機能の追加
---