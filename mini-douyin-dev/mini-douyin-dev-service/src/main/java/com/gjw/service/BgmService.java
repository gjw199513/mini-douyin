package com.gjw.service;

import com.gjw.pojo.Bgm;
import com.gjw.pojo.Users;

import java.util.List;

/**
 * Created by gjw19 on 2018/6/16.
 */
public interface BgmService {

    /**
     * 查询背景音乐列表
     * @return
     */
    public List<Bgm> queryBgmList();


    /**
     * 根据id查询bgm信息
     * @param bgmId
     * @return
     */
    public Bgm queryBgmById(String bgmId);
}
