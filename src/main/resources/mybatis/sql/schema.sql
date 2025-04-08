
USE `learning_english`;

CREATE TABLE  IF NOT EXISTS users
(
    id      int PRIMARY KEY AUTO_INCREMENT ,                                 -- 唯一标识符
    nick_name    VARCHAR(255) NOT NULL,                                          -- 用戶昵称
    openid       VARCHAR(255) NOT NULL,                                        -- 诗歌作者
    phone_num     VARCHAR(50),                                                    -- 诗歌体裁
    username VARCHAR(255) default NULL,
    password VARCHAR(255) default NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                            -- 创建时间
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用戶表' ROW_FORMAT = Dynamic;

CREATE TABLE  IF NOT EXISTS words
(
    id  MEDIUMINT PRIMARY KEY AUTO_INCREMENT ,                                 -- 唯一标识符
    name VARCHAR(255) NOT NULL,
    trans VARCHAR(2048) NOT NULL,
    uk_phone VARCHAR(255) default NULL,
    us_phone VARCHAR(255) default NULL,
    unique key (name),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                            -- 创建时间
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间
    )ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用戶表' ROW_FORMAT = Dynamic;


CREATE TABLE IF NOT EXISTS wrong_words (
          user_id int,
          word_id MEDIUMINT,
          review_count INT DEFAULT 0,          -- 复习次数
          last_reviewed DATETIME,              -- 上次复习时间
          PRIMARY KEY (user_id, word_id),
          FOREIGN KEY (user_id) REFERENCES users(id),
          FOREIGN KEY (word_id) REFERENCES words(id)
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '错题本' ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS favorite_words (
           user_id int,
           word_id MEDIUMINT,
           review_count INT DEFAULT 0,          -- 复习次数
           last_reviewed DATETIME,              -- 上次复习时间
           PRIMARY KEY (user_id, word_id),
           FOREIGN KEY (user_id) REFERENCES users(id),
           FOREIGN KEY (word_id) REFERENCES words(id)
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '收藏表' ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS  dictionary (
       id SMALLINT PRIMARY KEY AUTO_INCREMENT COMMENT '唯一标识符',
       name VARCHAR(100) NOT NULL COMMENT '考试名称',
        unique  key (name),
       created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典类型' ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS  dict_word_relation (
         word_id MEDIUMINT NOT NULL COMMENT '单词id',
         dictionary_id SMALLINT NOT NULL COMMENT '词典Id',
         PRIMARY KEY (word_id, dictionary_id),
         FOREIGN KEY (dictionary_id) REFERENCES dictionary(id),
         FOREIGN KEY (word_id) REFERENCES words(id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典单词表' ROW_FORMAT = Dynamic;

