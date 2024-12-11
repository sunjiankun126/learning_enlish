package com.yuning.learning.english.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuning.learning.english.entity.Word;
import com.yuning.learning.english.mapper.DynamicTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicTableService {

    @Autowired
    private DynamicTableMapper dynamicTableMapper;

    public IPage<Word> queryTableWithPagination(String tableName, int page, int size) {
        Page<Word> pageRequest = new Page<>(page, size);
        return dynamicTableMapper.selectFromTable(pageRequest, tableName);
    }
}
