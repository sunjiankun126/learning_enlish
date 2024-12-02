
USE `learning_english`;

CREATE TABLE  IF NOT EXISTS users
(
    user_id      bigint PRIMARY KEY AUTO_INCREMENT ,                                 -- 唯一标识符
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
    word_id  int PRIMARY KEY AUTO_INCREMENT ,                                 -- 唯一标识符
    name VARCHAR(255) NOT NULL,
    trans VARCHAR(2048) NOT NULL,
    uk_phone VARCHAR(255) default NULL,
    us_phone VARCHAR(255) default NULL,
    unique key (name),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                            -- 创建时间
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间
    )ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用戶表' ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS user_words (
    user_id bigint,
    word_id INT,
    status ENUM('unlearned', 'learning', 'learned') DEFAULT 'unlearned',  -- 学习状态
    review_count INT DEFAULT 0,          -- 复习次数
    last_reviewed DATETIME,              -- 上次复习时间
    PRIMARY KEY (user_id, word_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (word_id) REFERENCES words(word_id)
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '学习进度表' ROW_FORMAT = Dynamic;


CREATE TABLE IF NOT EXISTS  dict (
       id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '唯一标识符',
       name VARCHAR(100) NOT NULL COMMENT '考试名称',
       description TEXT COMMENT '考试描述',
       category VARCHAR(100) COMMENT '类别',
       tags JSON COMMENT '标签',
       url VARCHAR(255) COMMENT '文件链接',
       length INT COMMENT '单词总数',
       translate_language VARCHAR(50) COMMENT '翻译语言',
       language VARCHAR(50) COMMENT '语言',
       type VARCHAR(50) COMMENT '字典类型'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典类型' ROW_FORMAT = Dynamic;
