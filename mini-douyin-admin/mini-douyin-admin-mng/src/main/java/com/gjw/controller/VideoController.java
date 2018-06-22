package com.gjw.controller;

import com.gjw.bean.AdminUser;
import com.gjw.pojo.Bgm;
import com.gjw.pojo.Users;
import com.gjw.service.UsersService;
import com.gjw.service.VideoService;
import com.gjw.utils.IMoocJSONResult;
import com.gjw.utils.PagedResult;
import com.gjw.web.util.ZKCurator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Controller
@RequestMapping("video")
public class VideoController {

    @Autowired
    private VideoService videoService;



    @GetMapping("/showAddBgm")
    public String showList() {
        return "video/addBgm";
    }

    @PostMapping("/addBgm")
    @ResponseBody
    public IMoocJSONResult addBgm(Bgm bgm) {
        videoService.addBgm(bgm);
        return IMoocJSONResult.ok();
    }

    @GetMapping("/showBgmList")
    public String showBgmList() {
        return "video/bgmList";
    }


    @PostMapping("/queryBgmList")
    @ResponseBody
    public PagedResult queryBgmList(Integer page) {
        return videoService.queryBgmList(page, 10);
    }

    @PostMapping("/delBgm")
    @ResponseBody
    public IMoocJSONResult delBgm(String bgmId) {
        videoService.deleteBgm(bgmId);
        return IMoocJSONResult.ok();
    }

    @PostMapping("/bgmUpload")
    @ResponseBody
    public IMoocJSONResult bgmUpload(@RequestParam("file") MultipartFile[] files) throws Exception {

        // 文件保存的命名空间
//        String fileSpace = File.separator+"java程序"+File.separator+"mini-douyin"+File.separator+"upload";
        String fileSpace = "D:" + File.separator + "java程序" + File.separator + "mini-douyin" + File.separator + "upload" + File.separator + "mvc-bgm";
        // 保存到数据库中的相对路径
        String uploadPathDB = File.separator + "bgm";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            if (files != null && files.length > 0) {

                // 获取文件名
                String filenName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(filenName)) {
                    // 文件上传的最终保存路径
                    String finalPath = fileSpace + uploadPathDB + File.separator + filenName;
                    // 设置数据库保存的路径
                    uploadPathDB += (File.separator + filenName);

                    // 判断该文件的目录是否存在
                    File outFile = new File(finalPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    // 将文件输出到指定路径
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                } else {
                    return IMoocJSONResult.errorMsg("上传出错...");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return IMoocJSONResult.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        return IMoocJSONResult.ok(uploadPathDB);
    }


}
