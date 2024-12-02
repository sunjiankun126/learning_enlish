package com.yuning.learning.english;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonToSqlTest {
    @Test
    public  void jsonFile2Dictionary() throws IOException {
        // JSON 文件路径
        String jsonFilePath = "F:\\SJK\\learning_enlish\\learning_enlish\\src\\main\\resources\\dictionary\\base\\en2zh_CN.json";
        // SQL 输出文件路径
        String sqlFilePath = "F:\\SJK\\learning_enlish\\learning_enlish\\src\\main\\resources\\mybatis\\sql\\data\\base\\dictionary.sql";
        createFileIfNotExists(sqlFilePath);
        // 表名
        String tableName = "words";

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 读取 JSON 文件
            JsonNode root = objectMapper.readTree(new File(jsonFilePath));

            // 创建 SQL 输出文件
            FileWriter writer = new FileWriter(sqlFilePath);

            // 遍历 JSON 数组并生成 SQL 语句
            for (JsonNode node : root) {
                String name = node.get("name").asText();
                String trans = node.get("trans").toString().replace("[", "").replace("]", "").replace("\"", "");

                String ukPhone = node.has("ukphone") ? node.get("ukphone").asText() : null;
                String usPhone = node.has("usphone") ? node.get("usphone").asText() : null;

                // 防止 SQL 注入的处理（可选）
                name = name.replace("'", "''");
                trans = trans.replace("'", "''");

                // 构造 INSERT 语句
                String sql = String.format(
                        "INSERT INTO %s (name, trans, uk_phone, us_phone) VALUES ('%s', '%s', %s, %s);\n",
                        tableName,
                        name,
                        trans,
                        ukPhone != null ? "'" + ukPhone.replace("'", "''") + "'" : "NULL",
                        usPhone != null ? "'" + usPhone.replace("'", "''") + "'" : "NULL"
                );

                // 写入 SQL 文件
                writer.write(sql);
            }

            // 关闭写入流
            writer.close();
            System.out.println("SQL 文件已生成: " + sqlFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsonFile2Sql() throws IOException {
        String srcPath = "F:\\SJK\\learning_enlish\\learning_enlish\\src\\main\\resources\\dictionary"; // 替换为你的目录路径
        String destPath = "F:\\SJK\\learning_enlish\\learning_enlish\\src\\main\\resources\\mybatis\\sql\\data";
        List<String> fileNames = listFilesWithoutExtension(srcPath);
        for (String fileName : fileNames) {
            jsonFile2Dictionary(srcPath, destPath, fileName);
        }

    }

    public  void jsonFile2Dictionary(String srcPath, String destPath, String fileName) throws IOException {
        // JSON 文件路径
        String jsonFilePath = srcPath + "\\" + fileName + ".json";
        // SQL 输出文件路径
        String sqlFilePath = destPath + "\\" + fileName + ".sql";
        createFileIfNotExists(sqlFilePath);
        // 表名
        String tableName = fileName;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 读取 JSON 文件
            JsonNode root = objectMapper.readTree(new File(jsonFilePath));

            // 创建 SQL 输出文件
            FileWriter writer = new FileWriter(sqlFilePath);

            // 遍历 JSON 数组并生成 SQL 语句
            for (JsonNode node : root) {
                String name = node.get("name").asText();

                // 防止 SQL 注入的处理（可选）
                name = name.replace("'", "''");

                // 构造 INSERT 语句
                String sql = String.format(
                        "INSERT INTO %s (name,) VALUES ('%s');\n",
                        tableName,
                        name
                );

                // 写入 SQL 文件
                writer.write(sql);
            }

            // 关闭写入流
            writer.close();
            System.out.println("SQL 文件已生成: " + sqlFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> listFilesWithoutExtension(String directoryPath) {
        List<String> fileNamesWithoutExtension = new ArrayList<>();
        File directory = new File(directoryPath);

        // 确保路径是有效目录
        if (directory.exists() && directory.isDirectory()) {
            // 列出所有文件
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        // 获取文件名并移除后缀
                        String fileName = file.getName();
                        int dotIndex = fileName.lastIndexOf('.');
                        if (dotIndex > 0) {
                            fileName = fileName.substring(0, dotIndex); // 移除后缀
                        }
                        fileNamesWithoutExtension.add(fileName);
                    }
                }
            }
        } else {
            System.out.println("目录不存在或不是一个有效目录");
        }

        return fileNamesWithoutExtension;
    }


    public void createFileIfNotExists(String filePath) throws IOException {
        File file = new File(filePath);

        // 检查文件是否存在
        if (!file.exists()) {
            // 创建父目录（如果不存在）
            File parentDirectory = file.getParentFile();
            if (parentDirectory != null && !parentDirectory.exists()) {
                boolean dirsCreated = parentDirectory.mkdirs();
                if (dirsCreated) {
                    System.out.println("父目录创建成功: " + parentDirectory.getPath());
                }
            }

            // 创建文件
            boolean fileCreated = file.createNewFile();
            if (fileCreated) {
                System.out.println("文件创建成功: " + filePath);
            } else {
                System.out.println("文件创建失败: " + filePath);
            }
        } else {
            System.out.println("文件已存在: " + filePath);
        }
    }
}
