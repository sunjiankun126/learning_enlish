package com.yuning.learning.english;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InsertDictWordRelation {
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

							// Get dictionary ID based on dictionary name
							int dictionaryId = getDictionaryId(conn, dictionaryName);

							if (dictionaryId == -1) {
								System.out.println("Dictionary not found for: " + dictionaryName);
								return;
							}

							// Read and parse JSON file
							ObjectMapper objectMapper = new ObjectMapper();
							objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
							List<Word> words = objectMapper.readValue(filePath.toFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, Word.class));

							// Insert word IDs and dictionary ID into dict_word_relation table
							insertDictWordRelation(conn, words, dictionaryId);
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

	private static int getDictionaryId(Connection conn, String dictionaryName) throws Exception {
		String query = "SELECT id FROM dictionary WHERE name = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, dictionaryName);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
		}
		return -1; // Dictionary not found
	}

	private static void insertDictWordRelation(Connection conn, List<Word> words, int dictionaryId) throws Exception {
		String query = "INSERT IGNORE dict_word_relation (word_id, dictionary_id) VALUES (?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			for (Word word : words) {
				int wordId = getWordId(conn, word.getName());
				if (wordId != -1) {
					stmt.setInt(1, wordId);
					stmt.setInt(2, dictionaryId);
					stmt.addBatch();
				}
			}
			stmt.executeBatch();
		}
	}

	private static int getWordId(Connection conn, String wordName) throws Exception {
		String query = "SELECT id FROM words WHERE LOWER(name) = LOWER(?)";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, wordName);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
		}
		System.out.println("Word not found: " + wordName);
		return -1; // Word not found
	}

	static class Word {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
