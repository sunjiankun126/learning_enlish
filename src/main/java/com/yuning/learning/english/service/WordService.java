package com.yuning.learning.english.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuning.learning.english.dto.DictWordRelation;
import com.yuning.learning.english.dto.Word;
import com.yuning.learning.english.mapper.DictWordRelationMapper;
import com.yuning.learning.english.mapper.WordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WordService {
	@Autowired
	private DictWordRelationMapper dictWordRelationMapper;

	@Autowired
	private WordMapper wordMapper;

	public List<Word> getWordsByDictionaryId(Integer dictionaryId, Integer pageSize, Integer pageNum) {
		// 获取关联的 wordIds 列表
		Page<DictWordRelation> page = new Page<>(pageNum, pageSize);
		Page<DictWordRelation> dictWordRelationResult = dictWordRelationMapper.selectPage(page,
			new LambdaQueryWrapper<DictWordRelation>().eq(DictWordRelation::getDictionaryId, dictionaryId).orderBy(true, true, DictWordRelation::getWordId)
		);
		List<Integer> wordIds = dictWordRelationResult.getRecords().stream().map(DictWordRelation::getWordId).collect(Collectors.toList());

		// 检查 wordIds 是否为空
		if (wordIds.isEmpty()) {
			return new ArrayList<>();
		}


		LambdaQueryWrapper<Word> queryWrapper =  new LambdaQueryWrapper();
		queryWrapper.in(Word::getId, wordIds);

		try {
			return wordMapper.selectList(queryWrapper);
		} catch (Exception e) {
			log.error("Failed to fetch words by dictionary ID: {}", dictionaryId, e);
			throw new RuntimeException("Failed to fetch words", e);
		}
	}
}
