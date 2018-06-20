package com.gjw.service.impl;

import com.gjw.mapper.UsersLikeVideosMapper;
import com.gjw.mapper.UsersMapper;
import com.gjw.pojo.Users;
import com.gjw.pojo.UsersLikeVideos;
import com.gjw.service.UserService;
import com.gjw.utils.IMoocJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

/**
 * Created by gjw19 on 2018/6/16.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private Sid sid;
    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    //查询数据使用SUPPORTS
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {

        Users user = new Users();
        user.setUsername(username);

        // 根据条件查询user
        Users result = usersMapper.selectOne(user);

        return result == null ? false : true;
    }

    /**
     * 保存用户（用户注册）
     *
     * @param user
     */
    // 插入数据使用REQUIRED
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users user) {
        String userId = sid.nextShort();
        user.setId(userId);
        usersMapper.insert(user);
    }

    /**
     * 用户登录，根据用户名和密码查询用户
     *
     * @param username
     * @param password
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        // 通用的对象，可以创建任意类型
        Example userExample = new Example(Users.class);

        // 添加查询条件的通用方法
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password",password);
        Users result = usersMapper.selectOneByExample(userExample);
        return result;
    }

    /**
     * 用户修改信息
     *
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserInfo(Users user) {
                // 通用的对象，可以创建任意类型
        Example userExample = new Example(Users.class);

        // 添加查询条件的通用方法
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", user.getId());
        usersMapper.updateByExampleSelective(user, userExample);
    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Example userExample = new Example(Users.class);

        // 添加查询条件的通用方法
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", userId);
        Users user = usersMapper.selectOneByExample(userExample);
        return user;
    }

    /**
     * 查询用户是否喜欢点赞视频
     *
     * @param userId
     * @param videoId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)){
            return false;
        }

        Example example = new Example(UsersLikeVideos.class);
        Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        List<UsersLikeVideos> list = usersLikeVideosMapper.selectByExample(example);

        if(list != null && list.size()>0){
            return true;
        }
        return false;
    }


}
