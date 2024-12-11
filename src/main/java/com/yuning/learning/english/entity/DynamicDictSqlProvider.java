package com.yuning.learning.english.entity;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class DynamicDictSqlProvider {
    public String getSelectSql(@Param("tableName") String tableName,
                               @Param("pageSize") Integer pageSize,
                               @Param("pageNum") Integer pageNum
                               ) {
        return new SQL()
                .SELECT("*")
                .FROM(tableName)
                .ORDER_BY("name")
                .toString();
    }
}
