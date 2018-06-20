package com.gjw.service;

import com.gjw.pojo.Bgm;
import com.gjw.pojo.Videos;
import com.gjw.utils.PagedResult;

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

    /**
     * 分页查询视频列表
     * @param page
     * @param pageSize
     * @return
     */
    public PagedResult getAllVideos(Videos video, Integer isSaveRecord,
                                    Integer page, Integer pageSize);


    /**
     * 查询我喜欢的视频列表
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
	public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize);

    /**
     * 查询我关注的人的视频列表
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
	public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize);

    /**
     * 获取热搜词列表
     * @return
     */
    public List<String> getHotwords();

    /**
     * 用户喜欢视频/点赞视频
     * @param userId
     * @param videoId
     * @param videoCreaterId
     */
    public void userLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * 用户不喜欢视频/取消点赞视频
     * @param userId
     * @param videoId
     * @param videoCreaterId
     */
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId);
}
