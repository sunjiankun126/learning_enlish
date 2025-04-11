package com.yuning.learning.english.mapper;

import com.yuning.learning.english.config.mybatis.MysqlBaseMapper;
import com.yuning.learning.english.dto.Dictionary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictionaryMapper extends MysqlBaseMapper<Dictionary> {
}
