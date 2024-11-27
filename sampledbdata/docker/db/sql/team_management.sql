-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- ホスト: mysql
-- 生成日時: 2024 年 11 月 27 日 16:47
-- サーバのバージョン： 8.4.2
-- PHP のバージョン: 8.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- データベース: `team_management`
--

-- --------------------------------------------------------

--
-- テーブルの構造 `management`
--

CREATE TABLE `management` (
  `management_id` int NOT NULL,
  `user_id` int NOT NULL,
  `subject` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '科目',
  `link` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `status` int NOT NULL COMMENT '取り組み中など',
  `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL COMMENT '兼完了日',
  `completion_schedule` date NOT NULL COMMENT '完了予定日'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `management`
--

INSERT INTO `management` (`management_id`, `user_id`, `subject`, `link`, `status`, `create_at`, `update_at`, `delete_at`, `completion_schedule`) VALUES
(1, 5, 'モードで切り替わるオノを作成しました。次は、魔法の杖を追加します。', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2024-10-14 16:33:29', NULL, '2024-10-21 16:37:59', '2024-10-21'),
(2, 5, '魔法の杖を追加しました。次は、魔法系のアーマーの追加します', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2024-10-21 16:34:07', NULL, '2024-10-28 16:37:59', '2024-10-28'),
(3, 5, '魔法系のアーマー追加しました。次は、新しいエンチャントを追加します。', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2024-11-04 16:34:26', NULL, '2024-11-11 16:37:59', '2024-11-11'),
(4, 5, '引き続き飛行できるエンチャントを作成します。', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2024-10-28 16:34:46', NULL, '2024-12-04 16:37:59', '2024-12-04'),
(5, 4, 'part17-3進行中です。木の自然生成をコメントアウトしpart18に進みます', 'https://www.youtube.com/playlist?list=PLWwlnq188K5w95GuBYAEbgVFJ_BqSr8OW', 5, '2024-10-14 16:35:23', NULL, '2024-10-21 16:37:59', '2024-10-21'),
(6, 4, 'オリジナルアイテム”ランダムスキル付与の食べ物”の作成を行います', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2024-10-28 16:35:49', NULL, '2024-11-04 16:37:59', '2024-11-04'),
(7, 4, 'オリジナルアイテムの追加で一部完了したのですがエンチャントからの追加も作成します', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2024-11-11 16:36:10', NULL, '2024-11-11 16:37:59', '2024-11-25'),
(8, 6, 'オリジナルの武器の追加の途中まで進んでいるので引き続き進めていきます', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2024-10-28 16:36:56', NULL, '2024-11-04 16:37:59', '2024-11-04'),
(9, 6, 'オリジナルの武器の追加を進行中で、引き続き進めていきます', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2024-10-28 16:37:07', NULL, '2024-11-04 16:37:59', '2024-12-04'),
(10, 4, '１個目のオリジナルアイテムの動作確認後、アイテム紹介文の作成に入ります（ブランチは切っています）', '', 1, '2024-11-27 16:43:15', '2024-11-27 16:44:03', NULL, '2024-12-02'),
(11, 3, '今週中に動画パートを終わらせます。', '', 1, '2024-11-27 16:43:26', '2024-11-27 16:43:48', NULL, '2024-12-02'),
(12, 5, 'アイテムを経験値に変換するアイテム作成終わりました。\r\n次は、新しいチェストを追加します。特徴(容量増加、できれば整頓機能)を作成します。', '', 1, '2024-11-27 16:43:38', '2024-11-27 16:44:40', NULL, '2024-12-04'),
(13, 6, 'オリジナルの武器がもうすぐで終わるので終わらせてからチラシ裏の案の作成に入ります', '', 1, '2024-11-27 16:44:12', NULL, NULL, '2024-12-02'),
(14, 5, 'モードで切り替わるオノを作成しました。次は、魔法の杖を追加します。', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2023-10-14 16:33:29', NULL, '2023-10-21 16:37:59', '2023-10-21'),
(15, 5, '魔法の杖を追加しました。次は、魔法系のアーマーの追加します', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2023-10-21 16:34:07', NULL, '2023-10-28 16:37:59', '2023-10-28'),
(16, 5, '魔法系のアーマー追加しました。次は、新しいエンチャントを追加します。', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2023-11-04 16:34:26', NULL, '2023-11-11 16:37:59', '2023-11-11'),
(17, 5, '引き続き飛行できるエンチャントを作成します。', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2023-10-28 16:34:46', NULL, '2023-12-04 16:37:59', '2023-12-04'),
(18, 4, 'part17-3進行中です。木の自然生成をコメントアウトしpart18に進みます', 'https://www.youtube.com/playlist?list=PLWwlnq188K5w95GuBYAEbgVFJ_BqSr8OW', 5, '2023-10-14 16:35:23', NULL, '2023-10-21 16:37:59', '2023-10-21'),
(19, 4, 'オリジナルアイテム”ランダムスキル付与の食べ物”の作成を行います', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2023-10-28 16:35:49', NULL, '2023-11-04 16:37:59', '2023-11-04'),
(20, 4, 'オリジナルアイテムの追加で一部完了したのですがエンチャントからの追加も作成します', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2023-11-11 16:36:10', NULL, '2023-11-11 16:37:59', '2023-11-25'),
(21, 6, 'オリジナルの武器の追加の途中まで進んでいるので引き続き進めていきます', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2023-10-28 16:36:56', NULL, '2023-11-04 16:37:59', '2023-11-04'),
(22, 6, 'オリジナルの武器の追加を進行中で、引き続き進めていきます', 'https://github.com/Hanakawalab-Minecraft/UniqueItemsMod', 5, '2023-10-28 16:37:07', NULL, '2023-11-04 16:37:59', '2023-12-04');

-- --------------------------------------------------------

--
-- テーブルの構造 `user_table`
--

CREATE TABLE `user_table` (
  `user_id` int NOT NULL,
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `permission_level` mediumint NOT NULL COMMENT '1:admin , 2 :nomel user ,3 other',
  `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `user_table`
--

INSERT INTO `user_table` (`user_id`, `user_name`, `password`, `email`, `permission_level`, `create_at`, `update_at`, `delete_at`) VALUES
(1, '西野樹', '$2a$10$y6oOhq0D2PjcHjHHEwltaeN8Lg2OegeHoIXuMwaSAQ0nSlSOkulWW', 'sample@a.a', 1, '2024-11-27 16:29:01', NULL, NULL),
(2, '水吉晴也', '$2a$10$ZW1a3YzbrSxgUxdYFjkk7uT15F5/bIoJK8tWPusGMXArrFOAh70gK', 'mizuyoshi1@a.a', 2, '2024-11-27 16:29:29', NULL, NULL),
(3, '平尾勇望', '$2a$10$ubDzYOJD90r9WagMl5vjjuEPTgiN9E/38raSftjry7DEyCL967Ufy', 'hirao1@a.a', 2, '2024-11-27 16:29:58', NULL, NULL),
(4, '酒井晶', '$2a$10$J8izED2Pi8UbcpEYs.T.QuR1takhzGHC5KVMyX5AlEp3EpYEiAck.', 'sakai1@a.a', 2, '2024-11-27 16:30:11', NULL, NULL),
(5, '大山博久', '$2a$10$Y2fjdSeoTsDXT.pNIAjVRudGp/QRGWp7BgbvCn7FPMtLZ0qbFO7RC', 'ooyama1@a.a', 2, '2024-11-27 16:30:32', NULL, NULL),
(6, '河村一輝', '$2a$10$4.C3HdiiXF65arUQchW9o.jRwH5eV2398i6L5yd4HJRuBKNfJwNzq', 'kawamura1@a.a', 2, '2024-11-27 16:30:55', NULL, NULL),
(7, '浅野竜太郎', '$2a$10$IB434C01XPjMfiSkBTjznuTxHFM0qHXPHMLyObnBxlsFsL4mtH1LO', 'asano1@a.a', 1, '2024-11-27 16:31:53', NULL, NULL);

--
-- ダンプしたテーブルのインデックス
--

--
-- テーブルのインデックス `management`
--
ALTER TABLE `management`
  ADD PRIMARY KEY (`management_id`);

--
-- テーブルのインデックス `user_table`
--
ALTER TABLE `user_table`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `unique_email` (`email`) USING BTREE;

--
-- ダンプしたテーブルの AUTO_INCREMENT
--

--
-- テーブルの AUTO_INCREMENT `management`
--
ALTER TABLE `management`
  MODIFY `management_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- テーブルの AUTO_INCREMENT `user_table`
--
ALTER TABLE `user_table`
  MODIFY `user_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
