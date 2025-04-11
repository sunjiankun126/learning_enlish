package com.yuning.learning.english.controller;

//import com.yuning.learning.english.service.DynamicTableService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuning.learning.english.common.response.Result;
import com.yuning.learning.english.dto.Word;
import com.yuning.learning.english.service.WordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/word")
@Slf4j
@Api(tags = {"单词控制器"})
public class WordController {


	@Autowired
	private WordService wordService;

	@GetMapping("/dictionary/{dictionaryId}")
	@ApiOperation("根据字典ID分页查询单词信息")
	public Result getWordsByDictionaryId(
		@PathVariable Integer dictionaryId,
		@RequestParam(defaultValue = "10") Integer pageSize,
		@RequestParam(defaultValue = "1") Integer pageNum) {
		Result result = Result.success();
		List<Word> words = wordService.getWordsByDictionaryId(dictionaryId, pageSize, pageNum);
		result.put("data", words);
		return result;
	}

}
