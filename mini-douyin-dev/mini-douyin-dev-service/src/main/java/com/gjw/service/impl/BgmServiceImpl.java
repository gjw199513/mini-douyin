package com.gjw.service.impl;

import com.gjw.mapper.BgmMapper;
import com.gjw.mapper.UsersMapper;
import com.gjw.pojo.Bgm;
import com.gjw.pojo.Users;
import com.gjw.service.BgmService;
import com.gjw.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

/**
 * Created by gjw19 on 2018/6/16.
 */
@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Autowired
    private Sid sid;


    /**
     * 查询背景音乐列表
     *
     * @return
     */
    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }

    /**
     * 根据id查询bgm信息
     *
     * @param bgmId
     * @return
     */
    @Override
    public Bgm queryBgmById(String bgmId) {
        return bgmMapper.selectByPrimaryKey(bgmId);
    }


}
