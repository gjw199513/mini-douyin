package com.gjw.controller;

import com.gjw.pojo.Users;
import com.gjw.pojo.vo.UsersVo;
import com.gjw.utils.IMoocJSONResult;
import com.gjw.utils.MD5Utils;
import com.gjw.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value = "用户注册登录的接口", tags = {"注册和登录的controller"})
public class RegistLoginController extends BasicController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "用户注册的接口")
    @PostMapping("/regist")
    public IMoocJSONResult regist(@RequestBody Users user) throws Exception {

        // 1. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return IMoocJSONResult.errorMsg("用户名和密码不能为空");
        }
        // 2. 判断用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());

        // 3. 保存用户，注册信息
        if (!usernameIsExist) {
            user.setNickname(user.getUsername());
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            user.setFansCounts(0);
            user.setReceiveLikeCounts(0);
            user.setFollowCounts(0);
            userService.saveUser(user);
        } else {
            return IMoocJSONResult.errorMsg("用户名已经存在，请换一个再试");
        }

        // 为了保证安全，将password设为空返回
        user.setPassword("");

//		// redis设置session
//		String uniqueToken = UUID.randomUUID().toString();
//		redis.set(USER_REDIS_SESSION + ":" + user.getId(), uniqueToken, 1000*60*30);
//
//		UsersVo userVo = new UsersVo();
//		// 将user的属性拷贝到uservo中
//		BeanUtils.copyProperties(user,userVo);
//		userVo.setUserToken(uniqueToken);
        UsersVo userVo = setUserRedisSessionToken(user);
        return IMoocJSONResult.ok(userVo);
    }

    /**
     * 通过redis设置session
     *
     * @param userModel
     */
    public UsersVo setUserRedisSessionToken(Users userModel) {
        // redis设置session
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 1000 * 60 * 30);

        UsersVo userVo = new UsersVo();
        // 将user的属性拷贝到uservo中
        BeanUtils.copyProperties(userModel, userVo);
        userVo.setUserToken(uniqueToken);
        return userVo;
    }

    @ApiOperation(value = "用户登录", notes = "用户登录的接口")
    @PostMapping("/login")
    public IMoocJSONResult login(@RequestBody Users user) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();

//		Thread.sleep(3000);
        // 1. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IMoocJSONResult.errorMsg("用户名和密码不能为空");
        }
        // 2. 判断用户是否存在
        Users userResult = userService.queryUserForLogin(user.getUsername(), MD5Utils.getMD5Str(password));

        // 3. 返回
        if (userResult != null) {
            userResult.setPassword("");
            UsersVo userVo = setUserRedisSessionToken(userResult);
            return IMoocJSONResult.ok(userVo);
        } else {
            return IMoocJSONResult.errorMsg("用户名或密码不正确，请重试");
        }
    }

    @ApiOperation(value = "用户注销", notes = "用户注销的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                        dataType = "String", paramType = "query")
    @PostMapping("/logout")
    public IMoocJSONResult logout(String userId) throws Exception {
        redis.del(USER_REDIS_SESSION + ":" + userId);
        return IMoocJSONResult.ok();
    }
}
