package com.yuning.learning.english;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InsertJson2Dictionary {

	private static final String DB_URL = "jdbc:mysql://172.171.15.10:32002/learning_english";
	private static final String USER = "admin";
	private static final String PASS = "ZZPass2019!";

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		try {
			// Establish database connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Directory containing JSON files
			Path directoryPath = Paths.get("E:\\personal\\learning_enlish\\src\\main\\resources\\dictionary");

			// Create an ExecutorService with a fixed thread pool size of 10
			ExecutorService executorService = Executors.newFixedThreadPool(16);

			// Traverse the directory and process each file concurrently
			Files.walk(directoryPath)
				.filter(Files::isRegularFile)
				.filter(filePath -> filePath.toString().endsWith(".json"))
				.forEach(filePath -> {
					executorService.submit(() -> {
						try {
							String fileName = filePath.toFile().getName();
							String dictionaryName = fileName.substring(0, fileName.lastIndexOf('.'));

							// 将dictionaryName做为表名，创建表；并将文件中的内容写入到该表中；
							createTable(conn, dictionaryName );


							// Read and parse JSON file
							ObjectMapper objectMapper = new ObjectMapper();
							objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
							List<Word> words = objectMapper.readValue(filePath.toFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, Word.class));

							// Insert word IDs and dictionary ID into dict_word_relation table
							insertDictWordRelation(conn, words, dictionaryName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				});

			// Shutdown the executor service after all tasks are submitted
			executorService.shutdown();

			// Wait for all threads to finish processing
			try {
				if (!executorService.awaitTermination(60, TimeUnit.MINUTES)) {
					executorService.shutdownNow();
				}
			} catch (InterruptedException e) {
				executorService.shutdownNow();
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total time taken: " + (endTime - startTime) + "ms");
	}

	public static void main1(String[] args) {
		long startTime = System.currentTimeMillis();
		try {
			// Establish database connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Directory containing JSON files
			Path directoryPath = Paths.get("E:\\personal\\learning_enlish\\src\\main\\resources\\dictionary");

			// Create an ExecutorService with a fixed thread pool size of 10
			ExecutorService executorService = Executors.newFixedThreadPool(10);

			// Traverse the directory and process each file concurrently
			Files.walk(directoryPath)
				.filter(Files::isRegularFile)
				.filter(filePath -> filePath.toString().endsWith(".json"))
				.forEach(filePath -> {
					executorService.submit(() -> {
						try {
							String fileName = filePath.toFile().getName();
							String dictionaryName = fileName.substring(0, fileName.lastIndexOf('.'));

							// 将dictionaryName做为表名，创建表；并将文件中的内容写入到该表中；
							createTable(conn, dictionaryName);


							// Read and parse JSON file
							ObjectMapper objectMapper = new ObjectMapper();
							objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
							List<Word> words = objectMapper.readValue(filePath.toFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, Word.class));

							// Insert word IDs and dictionary ID into dict_word_relation table
							insertDictWordRelation(conn, words, dictionaryName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				});

			// Shutdown the executor service after all tasks are submitted
			executorService.shutdown();

			// Wait for all threads to finish processing
			try {
				if (!executorService.awaitTermination(60, TimeUnit.MINUTES)) {
					executorService.shutdownNow();
				}
			} catch (InterruptedException e) {
				executorService.shutdownNow();
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total time taken: " + (endTime - startTime) + "ms");
	}


	private static void createTable(Connection conn, String tableName) throws SQLException {
		// 表名合法性校验（防止SQL注入）
//		if (!isValidTableName(tableName)) {
//			throw new IllegalArgumentException("Invalid table name: " + tableName);
//		}

		// 使用StringBuilder构建SQL语句
		StringBuilder sqlBuilder = new StringBuilder(256);
		sqlBuilder.append("CREATE TABLE IF NOT EXISTS `")
			.append(escapeIdentifier(tableName, conn))
			.append("` (\n")
			.append("    id MEDIUMINT PRIMARY KEY AUTO_INCREMENT,\n")
			.append("    name VARCHAR(255) NOT NULL,\n")
			.append("    UNIQUE KEY `idx_name` (name)\n")  // 显式命名唯一索引
			.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin\n")
			.append("ROW_FORMAT=DYNAMIC;");

		try (Statement stmt = conn.createStatement()) {
			stmt.execute(sqlBuilder.toString());
		}
	}

	// 表名安全处理方法
	private static String escapeIdentifier(String identifier, Connection conn) throws SQLException {
		if (identifier == null || identifier.isEmpty()) {
			throw new IllegalArgumentException("Identifier cannot be null or empty");
		}
		// 使用数据库元数据获取正确的转义字符
		DatabaseMetaData metaData = conn.getMetaData();
		return metaData.getIdentifierQuoteString().equals("`")
			? identifier
			: "`" + identifier + "`";
	}

	// 表名合法性校验（正则表达式）
	private static boolean isValidTableName(String name) {
		return name.matches("^[a-zA-Z_][a-zA-Z0-9_]*$");
	}

	private static void insertDictWordRelation(Connection conn, List<Word> words, String tableName) throws SQLException {
		String sql = "INSERT INTO " + tableName + " (name) VALUES (?) on duplicate key update name = VALUES(name)";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			conn.setAutoCommit(false); // 开启事务

			for (Word word : words) {
				stmt.setString(1, word.getName()); // 修正参数索引从1开始
				stmt.addBatch(); // 添加到批处理
			}

			stmt.executeBatch(); // 批量执行
			conn.commit(); // 提交事务
		} catch (SQLException e) {
			conn.rollback(); // 回滚事务（如果连接未关闭）
			throw e; // 重新抛出异常给上层处理
		} finally {
			conn.setAutoCommit(true); // 恢复自动提交模式
		}
	}

//	private static int getWordId(Connection conn, String wordName) throws Exception {
//		String query = "SELECT id FROM words WHERE LOWER(name) = LOWER(?)";
//		try (PreparedStatement stmt = conn.prepareStatement(query)) {
//			stmt.setString(1, wordName);
//			ResultSet rs = stmt.executeQuery();
//			if (rs.next()) {
//				return rs.getInt("id");
//			}
//		}
//		System.out.println("Word not found: " + wordName);
//		return -1; // Word not found
//	}

	public static class Word {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
