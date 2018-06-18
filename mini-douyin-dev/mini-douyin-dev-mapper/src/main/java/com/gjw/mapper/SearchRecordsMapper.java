package com.gjw.mapper;

import com.gjw.pojo.SearchRecords;
import com.gjw.utils.MyMapper;

import java.util.List;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {

    public List<String> getHotwords();
}