package com.yuning.learning.english.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuning.learning.english.dto.Word;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WordMapper extends BaseMapper<Word> {
}
