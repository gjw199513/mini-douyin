package com.gjw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gjw.mapper.BgmMapper;
import com.gjw.mapper.SearchRecordsMapper;
import com.gjw.mapper.VideosMapper;
import com.gjw.mapper.VideosMapperCustom;
import com.gjw.pojo.Bgm;
import com.gjw.pojo.SearchRecords;
import com.gjw.pojo.Videos;
import com.gjw.pojo.vo.VideosVo;
import com.gjw.service.BgmService;
import com.gjw.service.VideoService;
import com.gjw.utils.PagedResult;
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
    private VideosMapperCustom videosMapperCustom;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

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

    /**
     * 分页查询视频列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PagedResult getAllVideos(Videos video, Integer isSaveRecord,
                                    Integer page, Integer pageSize) {

        // 保存热搜词记录
        String desc = video.getVideoDesc();
        if(isSaveRecord != null && isSaveRecord == 1){
            SearchRecords record = new SearchRecords();
            String recordId = sid.nextShort();
            record.setId(recordId);
            record.setContent(desc);
            searchRecordsMapper.insert(record);
        }

        PageHelper.startPage(page, pageSize);
        List<VideosVo> list = videosMapperCustom.queryAllVideos(desc);

        PageInfo<VideosVo> pageList = new PageInfo<>(list);

        // 为后台分页插件提供对应的参数
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    /**
     * 获取热搜词列表
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<String> getHotwords() {
        return searchRecordsMapper.getHotwords();
    }
}
