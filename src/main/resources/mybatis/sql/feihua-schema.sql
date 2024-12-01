
USE `feihua`;

CREATE TABLE  IF NOT EXISTS poems
(
    id           INT AUTO_INCREMENT ,                                 -- 唯一标识符
    title        VARCHAR(255) NOT NULL,                                          -- 诗歌标题
    author       VARCHAR(100) NOT NULL,                                          -- 诗歌作者
    rhythmic     VARCHAR(50),                                                    -- 诗歌体裁
    paragraphs   TEXT         NOT NULL,                                          -- 诗歌内容（存储所有段落）
    notes        TEXT,                                                           -- 诗歌注释（可选，存储解释或注释）
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                            -- 创建时间
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新时间
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_author`(`author`) USING BTREE,
    INDEX `idx_rhythmic`(`rhythmic`) USING BTREE,
    INDEX `idx_title`(`title`) USING BTREE,
    FULLTEXT INDEX `idx_paragraphs` (`paragraphs`)
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '诗词表' ROW_FORMAT = Dynamic;

