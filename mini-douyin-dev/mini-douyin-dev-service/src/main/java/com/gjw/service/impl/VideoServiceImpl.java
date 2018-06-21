package com.gjw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gjw.mapper.*;
import com.gjw.pojo.*;
import com.gjw.pojo.vo.CommentsVO;
import com.gjw.pojo.vo.VideosVo;
import com.gjw.service.BgmService;
import com.gjw.service.VideoService;
import com.gjw.utils.PagedResult;
import com.gjw.utils.TimeAgoUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by gjw19 on 2018/6/16.
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private VideosMapperCustom videosMapperCustom;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private CommentsMapper commentMapper;

    @Autowired
    private CommentsMapperCustom commentMapperCustom;
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
        String userId= video.getUserId();
        if(isSaveRecord != null && isSaveRecord == 1){
            SearchRecords record = new SearchRecords();
            String recordId = sid.nextShort();
            record.setId(recordId);
            record.setContent(desc);
            searchRecordsMapper.insert(record);
        }

        PageHelper.startPage(page, pageSize);
        List<VideosVo> list = videosMapperCustom.queryAllVideos(desc,userId);

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
     * 查询我喜欢的视频列表
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVo> list = videosMapperCustom.queryMyLikeVideos(userId);

		PageInfo<VideosVo> pageList = new PageInfo<>(list);

		PagedResult pagedResult = new PagedResult();
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setPage(page);
		pagedResult.setRecords(pageList.getTotal());

		return pagedResult;
    }

    /**
     * 查询我关注的人的视频列表
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize) {
   		PageHelper.startPage(page, pageSize);
		List<VideosVo> list = videosMapperCustom.queryMyFollowVideos(userId);

		PageInfo<VideosVo> pageList = new PageInfo<>(list);

		PagedResult pagedResult = new PagedResult();
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setPage(page);
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void userLikeVideo(String userId, String videoId, String videoCreaterId) {
        // 1. 保存用户和视频的喜欢点赞关联关系表
        String likeId = sid.nextShort();
        UsersLikeVideos ulv = new UsersLikeVideos();
        ulv.setId(likeId);
        ulv.setUserId(userId);
        ulv.setVideoId(videoId);
        usersLikeVideosMapper.insert(ulv);

        // 2. 视频喜欢数量累加
        videosMapperCustom.addVideoLikeCount(videoId);

        // 3. 用户受喜欢数量的累加
        usersMapper.addReceiveLikeCount(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
        // 1. 删除用户和视频的喜欢点赞关联关系表
        Example example = new Example(UsersLikeVideos.class);
        Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        usersLikeVideosMapper.deleteByExample(example);

        // 2. 视频喜欢数量累减
        videosMapperCustom.reduceVideoLikeCount(videoId);

        // 3. 用户受喜欢数量的累减
        usersMapper.reduceReceiveLikeCount(userId);
    }

    /**
     * 保存用户评论
     *
     * @param comment
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(Comments comment) {
        String id = sid.nextShort();
        comment.setId(id);
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
    }

    /**
     * 留言分页
     *
     * @param videoId
     * @param page
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        PageHelper.startPage(page, pageSize);

        List<CommentsVO> list = commentMapperCustom.queryComments(videoId);

        for (CommentsVO c : list) {
            String timeAgo = TimeAgoUtils.format(c.getCreateTime());
            c.setTimeAgoStr(timeAgo);
        }

        PageInfo<CommentsVO> pageList = new PageInfo<>(list);

        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(list);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());

        return grid;
    }
}
