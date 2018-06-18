package com.gjw.controller;

import com.gjw.enums.VideoStatusEnum;
import com.gjw.pojo.Bgm;
import com.gjw.pojo.Videos;
import com.gjw.service.BgmService;
import com.gjw.service.VideoService;
import com.gjw.utils.FetchVideoCover;
import com.gjw.utils.IMoocJSONResult;
import com.gjw.utils.MergeVideoMp3;
import com.gjw.utils.PagedResult;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/video")
@Api(value = "视频相关业务的接口", tags = {"视频相关业务的controller"})
public class VideoController extends BasicController {

    @Autowired
    private BgmService bgmService;

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "上传视频", notes = "上传视频的接口")
	@ApiImplicitParams({
            // query是以?传参，而小程序使用formData传参，需要paramType="form"
		@ApiImplicitParam(name="userId", value="用户id", required=true,
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="bgmId", value="背景音乐id", required=false,
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoSeconds", value="背景音乐播放长度", required=true,
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoWidth", value="视频宽度", required=true,
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoHeight", value="视频高度", required=true,
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="desc", value="视频描述", required=false,
				dataType="String", paramType="form")
	})
    // 限制服务器上传文件请求的类型
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public IMoocJSONResult upload(String userId,
                                  String bgmId, double videoSeconds, int videoWidth, int videoHeight,
                                  String desc,
                                  @ApiParam(value = "短视频", required = true)
                                  MultipartFile file) throws Exception {

        if(StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("用户id不能为空");
        }

        // 保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/video";
        String coverPathDB = "/" + userId + "/video";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        String finalVideoPath = "";
        try {
            if (file != null) {

                // 获取文件名
                String fileName = file.getOriginalFilename();
                // abc.mp4
                String fileNamePrefix = fileName.split("\\.")[0];

                if (StringUtils.isNotBlank(fileName)) {
                    // 文件上传的最终保存路径
                    finalVideoPath = FILE_SPACE + uploadPathDB + "/" + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    coverPathDB = coverPathDB + "/" + fileNamePrefix + ".jpg";

                    // 判断该文件的目录是否存在
                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    // 将文件输出到指定路径
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
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

        // 判断bgmId是否为空，如果不为空，
        // 那就查询bgm的信息，并且合并视频，生产新的视频
        if(StringUtils.isNotBlank(bgmId)){
            Bgm bgm = bgmService.queryBgmById(bgmId);
            String mp3InputPath = FILE_SPACE + bgm.getPath();

            MergeVideoMp3 tool = new MergeVideoMp3(FFMPEG_EXE);
            String videoInputPath = finalVideoPath;

            String videoOutputName = UUID.randomUUID().toString() + ".mp4";
            uploadPathDB = "/" + userId + "/video/" + videoOutputName;
            finalVideoPath = FILE_SPACE + uploadPathDB;
            tool.convertor(videoInputPath, mp3InputPath, videoSeconds, finalVideoPath);
        }
        System.out.println("uploadPathDB=" + uploadPathDB);
        System.out.println("finalVideoPath=" + finalVideoPath);

        // 对视频进行截图
		FetchVideoCover videoInfo = new FetchVideoCover(FFMPEG_EXE);
        videoInfo.getCover(finalVideoPath,FILE_SPACE + coverPathDB);


        // 保存视频信息到数据库
        Videos video = new Videos();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds((float) videoSeconds);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDB);
        video.setCoverPath(coverPathDB);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());

        String videoId = videoService.saveVideo(video);
        return IMoocJSONResult.ok(videoId);
    }


    // 由于小程序上传文件是String，不能上传多个内容，需要再写一个方法进行上传封面图
    @ApiOperation(value = "上传封面", notes = "上传封面的接口")
    @ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户id", required=true,
				dataType="String", paramType="form"),
        @ApiImplicitParam(name="videoId", value="视频主键id", required=true,
				dataType="String", paramType="form")})
    @PostMapping(value = "/uploadCover", headers = "content-type=multipart/form-data")
    public IMoocJSONResult uploadCover(String userId, String videoId,
                                  @ApiParam(value = "视频封面", required = true)
                                  MultipartFile file) throws Exception {

        if(StringUtils.isBlank(userId) || StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("用户id和视频主键id不能为空...");
        }

        // 保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/video";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        // 文件上传的最终保存路径
        String finalVideoPath = "";
        try {
            if (file != null) {

                // 获取文件名
                String filenName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(filenName)) {
                    // 文件上传的最终保存路径
                    finalVideoPath = FILE_SPACE + uploadPathDB + "/" + filenName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + filenName);

                    // 判断该文件的目录是否存在
                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    // 将文件输出到指定路径
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
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

        videoService.updateVideo(videoId, uploadPathDB);
        return IMoocJSONResult.ok(videoId);
    }


    /**
     * 分页和搜素查询视频列表
     * @param video
     * @param isSaveRecord： 1 - 需要保存
     *                      0 - 不需要保存，或者为空的时候
     * @param page
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/showAll")
    public IMoocJSONResult showAll(@RequestBody Videos video, Integer isSaveRecord,
                                   Integer page) throws Exception{

        if(page == null){
            page = 1;
        }
        PagedResult result = videoService.getAllVideos(video, isSaveRecord, page, PAGE_SIZE);
        return IMoocJSONResult.ok(result);
    }

    @PostMapping(value = "/hot")
    public IMoocJSONResult hot() throws Exception{

        return IMoocJSONResult.ok(videoService.getHotwords());
    }
}
