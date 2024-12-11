package com.yuning.learning.english.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuning.learning.english.entity.DynamicDictSqlProvider;
import com.yuning.learning.english.entity.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;


@Mapper
public interface DynamicTableMapper {
    @SelectProvider(type = DynamicDictSqlProvider.class, method = "getSelectSql")
    IPage<Word> selectFromTable(Page<?> page, @Param("tableName") String tableName);
}
