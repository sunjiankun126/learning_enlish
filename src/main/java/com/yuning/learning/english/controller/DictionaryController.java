package com.yuning.learning.english.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuning.learning.english.common.response.Result;
import com.yuning.learning.english.dto.DictWordRelation;
import com.yuning.learning.english.dto.Dictionary;
import com.yuning.learning.english.entity.enums.DictionaryClass;
import com.yuning.learning.english.mapper.DictionaryMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dictionary")
@Slf4j
@Api(tags = {"字典控制器"})
public class DictionaryController {

	@Resource
	private DictionaryMapper dictionaryMapper;

	@GetMapping("/page")
	@ApiOperation("分页查询字典")
	public Page<Dictionary> getDictionaries(@RequestParam int pageNum, @RequestParam int pageSize) {
		Page<Dictionary> page = new Page<>(pageNum, pageSize);
		QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
		return dictionaryMapper.selectPage(page, queryWrapper);
	}

	@GetMapping("/query/classes")
	@ApiOperation("查询字典的所有大类")
	public Result getDictionaryClasses() {
		List<String> dictionaryClasses = Arrays.stream(DictionaryClass.values()).map(DictionaryClass::name).collect(Collectors.toList());
		return Result.success(dictionaryClasses);
	}
}
