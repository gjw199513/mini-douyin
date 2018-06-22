package com.gjw.service;


import com.gjw.pojo.Bgm;
import com.gjw.utils.PagedResult;

/**
 * Created by Administrator on 2018/6/22.
 */
public interface VideoService {

    public void addBgm(Bgm bgm);

    public PagedResult queryBgmList(Integer page, Integer pageSize);

    public void deleteBgm(String id);
}
