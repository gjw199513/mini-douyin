package com.gjw.controller;

import com.gjw.pojo.Users;
import com.gjw.pojo.vo.PublisherVideo;
import com.gjw.pojo.vo.UsersVo;
import com.gjw.service.UserService;
import com.gjw.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Api(value = "用户相关业务的接口", tags = {"用户相关业务的controller"})
@RequestMapping("/user")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "用户上传头像", notes = "用户上传头像的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true,
            dataType = "String", paramType = "query")
    @PostMapping("/uploadFace")
    public IMoocJSONResult uploadFace(String userId,
                                      @RequestParam("file") MultipartFile[] files) throws Exception {
        if(StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("用户id不能为空");
        }

        // 文件保存的命名空间
        String fileSpace = FILE_SPACE;
        // 保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/face";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            if (files != null && files.length > 0) {

                // 获取文件名
                String filenName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(filenName)) {
                    // 文件上传的最终保存路径
                    String finalFacePath = fileSpace + uploadPathDB + "/" + filenName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + filenName);

                    // 判断该文件的目录是否存在
                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    // 将文件输出到指定路径
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }else{
                    return IMoocJSONResult.errorMsg("上传出错...");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return IMoocJSONResult.errorMsg("上传出错...");
        } finally {
            if(fileOutputStream != null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        // 更新用户信息
        Users user = new Users();
        user.setId(userId);
        user.setFaceImage(uploadPathDB);
        userService.updateUserInfo(user);

        return IMoocJSONResult.ok(uploadPathDB);
    }

    @ApiOperation(value = "查询用户信息", notes = "查询用户信息的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true,
            dataType = "String", paramType = "query")
    @PostMapping("/query")
    public IMoocJSONResult query(String userId) throws Exception{

        if(StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("用户id不能为空");
        }

        Users userInfo = userService.queryUserInfo(userId);
        UsersVo userVo = new UsersVo();
        BeanUtils.copyProperties(userInfo, userVo);
        return IMoocJSONResult.ok(userVo);
    }

//    @ApiOperation(value = "查询用户信息", notes = "查询用户信息的接口")
//    @ApiImplicitParam(name = "userId", value = "用户id", required = true,
//            dataType = "String", paramType = "query")
    @PostMapping("/queryPublisher")
    public IMoocJSONResult queryPublisher(String loginUserId, String videoId, String publishUserId) throws Exception{

        if(StringUtils.isBlank(publishUserId)){
            return IMoocJSONResult.errorMsg("");
        }

        // 1. 查询视频发布者的信息
        Users userInfo = userService.queryUserInfo(publishUserId);
        UsersVo publisher = new UsersVo();
        BeanUtils.copyProperties(userInfo, publisher);

        // 2. 查询当前登陆者和视频的点赞关系
        boolean userLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);

        PublisherVideo bean = new PublisherVideo();
        bean.setPublisher(publisher);
        bean.setUserLikeVideo(userLikeVideo);

        return IMoocJSONResult.ok(bean);
    }

//    @ApiOperation(value = "查询用户信息", notes = "查询用户信息的接口")
//    @ApiImplicitParam(name = "userId", value = "用户id", required = true,
//            dataType = "String", paramType = "query")
    @PostMapping("/beyourfans")
    public IMoocJSONResult beyourfans(String userId, String fanId) throws Exception{

        if(StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)){
            return IMoocJSONResult.errorMsg("");
        }

        userService.saveUserFanRelation(userId, fanId);
        return IMoocJSONResult.ok("关注成功...");
    }

//    @ApiOperation(value = "查询用户信息", notes = "查询用户信息的接口")
//    @ApiImplicitParam(name = "userId", value = "用户id", required = true,
//            dataType = "String", paramType = "query")
    @PostMapping("/dontbeyourfans")
    public IMoocJSONResult dontbeyourfans(String userId, String fanId) throws Exception{

        if(StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)){
            return IMoocJSONResult.errorMsg("");
        }

        userService.deleteUserFanRelation(userId, fanId);
        return IMoocJSONResult.ok("取消关注成功...");
    }
}
