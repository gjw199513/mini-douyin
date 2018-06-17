package com.gjw.service.impl;

import com.gjw.mapper.BgmMapper;
import com.gjw.mapper.VideosMapper;
import com.gjw.pojo.Bgm;
import com.gjw.pojo.Videos;
import com.gjw.service.BgmService;
import com.gjw.service.VideoService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gjw19 on 2018/6/16.
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private Sid sid;

    /**
     * 保存视频
     * @param video
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveVideo(Videos video) {

        String id = sid.nextShort();
        video.setId(id);

        videosMapper.insertSelective(video);

        return id;
    }

    /**
     * 修改视频的封面
     *
     * @param videoId
     * @param converPath
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateVideo(String videoId, String converPath) {

        Videos video = new Videos();
        video.setId(videoId);
        video.setCoverPath(converPath);
        videosMapper.updateByPrimaryKeySelective(video);
    }
}
