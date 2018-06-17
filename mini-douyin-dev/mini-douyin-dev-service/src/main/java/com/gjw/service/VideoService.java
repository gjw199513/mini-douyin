package com.gjw.service;

import com.gjw.pojo.Bgm;
import com.gjw.pojo.Videos;

import java.util.List;

/**
 * Created by gjw19 on 2018/6/16.
 */
public interface VideoService {

    /**
     * 保存视频
     * @param video
     * @return
     */
    public String saveVideo(Videos video);

    /**
     * 修改视频的封面
     * @param videoId
     * @param converPath
     * @return
     */
    public void updateVideo(String videoId, String converPath);
}
