package com.yuning.learning.english.dto;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "dict_word_relation")
public class DictWordRelation {
	private Integer wordId;
	private Integer dictionaryId;
}
