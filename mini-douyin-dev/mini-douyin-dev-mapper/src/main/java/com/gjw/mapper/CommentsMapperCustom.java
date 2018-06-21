package com.gjw.mapper;

import java.util.List;

import com.gjw.pojo.vo.CommentsVO;
import com.gjw.pojo.Comments;
import com.gjw.utils.MyMapper;

public interface CommentsMapperCustom extends MyMapper<Comments> {

    public List<CommentsVO> queryComments(String videoId);
}