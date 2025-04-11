package com.yuning.learning.english.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuning.learning.english.dto.DictWordRelation;
import com.yuning.learning.english.dto.Word;
import com.yuning.learning.english.mapper.DictWordRelationMapper;
import com.yuning.learning.english.mapper.WordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordService {
	@Autowired
	private DictWordRelationMapper dictWordRelationMapper;

	@Autowired
	private WordMapper wordMapper;

	public Page<Word> getWordsByDictionaryId(Integer dictionaryId, Integer pageSize, Integer pageNum) {
		// Create a page object for pagination
		Page<DictWordRelation> relationPage = new Page<>(pageNum, pageSize);

		// Query dict_word_relation table to get word IDs by dictionary ID
		QueryWrapper<DictWordRelation> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("dictionary_id", dictionaryId);
		Page<DictWordRelation> relations = dictWordRelationMapper.selectPage(relationPage, queryWrapper);

		// Extract word IDs from the result
		List<Integer> wordIds = relations.getRecords().stream()
			.map(DictWordRelation::getWordId)
			.collect(Collectors.toList());

		// Query words table to get word details by word IDs
		Page<Word> wordPage = new Page<>(pageNum, pageSize);
		if (!wordIds.isEmpty()) {
			queryWrapper.clear();
			queryWrapper.in("id", wordIds);
			return wordMapper.selectPage(wordPage, queryWrapper);
		} else {
			return wordPage; // Return an empty page
		}
	}
}
