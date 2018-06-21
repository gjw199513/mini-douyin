package com.gjw.service;

import com.gjw.pojo.Users;
import com.gjw.utils.PagedResult;

public interface UsersService {

    public PagedResult queryUsers(Users user, Integer page, Integer pageSize);

}
