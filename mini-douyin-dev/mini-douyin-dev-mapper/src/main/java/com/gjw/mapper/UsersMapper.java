package com.gjw.mapper;

import com.gjw.pojo.Users;
import com.gjw.utils.MyMapper;

public interface UsersMapper extends MyMapper<Users> {

    /**
     * 用户受喜欢数累加
     * @param userId
     */
    public void addReceiveLikeCount(String userId);

    /**
     * 用户受喜欢数累减
     * @param userId
     */
    public void reduceReceiveLikeCount(String userId);
}