-- courses（この順でID=1..7が振られます）
INSERT INTO courses (course_name) VALUES
('WM'),('Java'),('WEBマーケティング'),('WP'),('AWS'),('フロントエンド'),('デザイン');

-- students（7件）
INSERT INTO students (full_name, furigana, nickname, email, region, age, gender, remark, is_deleted) VALUES
('高橋一美','たかはしかずみ','かみ','takahashi@example.com','北海道',33,'女性',' ',FALSE),
('伊藤 健','いとう たけし','タケシ','ito@example.com','福岡',25,'男性','',FALSE),
('中村 優子','なかむら ゆうこ','ユウ','nakamura@example.com','広島',28,'女性',NULL,FALSE),
('木村 太郎','きむら たろう','タロー','kimura@example.com','東京',40,'男性',NULL,FALSE),
('佐々木 花子','ささき はなこ','ハナ','sasaki@example.com','沖縄',19,'女性',NULL,FALSE),
('本田　圭介','ほんだ　けいすけ','ケイ','honda@example.com','東京',38,'男性','',FALSE),
('井上 渚','いのうえ なぎさ','なぎ','inoue@example.com','千葉',38,'男性','',FALSE);

-- students_courses（コースIDは上の並びに対応）
INSERT INTO students_courses (student_id, course_id, start_date, end_date) VALUES
(1, 1, TIMESTAMP '2025-02-01 00:00:00', TIMESTAMP '2025-08-31 00:00:00'),
(2, 2, TIMESTAMP '2023-03-01 00:00:00', TIMESTAMP '2025-06-30 00:00:00'),
(3, 3, TIMESTAMP '2025-01-15 00:00:00', NULL),
(4, 4, TIMESTAMP '2024-04-01 00:00:00', TIMESTAMP '2025-01-31 00:00:00'),
(5, 5, TIMESTAMP '2025-03-01 00:00:00', NULL),
(6, 6, TIMESTAMP '2025-02-01 00:00:00', NULL),
(7, 2, TIMESTAMP '2025-08-27 00:32:52', TIMESTAMP '2026-08-27 00:32:52');