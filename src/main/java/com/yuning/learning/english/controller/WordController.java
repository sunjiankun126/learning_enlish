package com.yuning.learning.english.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yuning.learning.english.entity.Word;
import com.yuning.learning.english.service.DynamicTableService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/word")
@Slf4j
@Api(tags = {"单词控制器"})
public class WordController {

    @Autowired
    private DynamicTableService dynamicTableService;

    @GetMapping("/query/words")
    public ResponseEntity<List<Word>> queryWords(
            @RequestParam String userId,
            @RequestParam String tableName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        IPage<Word> wordIPage = dynamicTableService.queryTableWithPagination(tableName, page, size);
        return ResponseEntity.ok(wordIPage.getRecords());
    }

}
