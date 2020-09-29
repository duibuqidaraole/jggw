package com.mt.government.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mt.government.enums.NoticeStatusEnum;
import com.mt.government.mapper.NoticeMapper;
import com.mt.government.mapper.NoticeUserMapper;
import com.mt.government.mapper.UserMapper;
import com.mt.government.model.Notice;
import com.mt.government.model.NoticeUser;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * 发布通知业务层实现
 *
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/17 11:51
 * @update fuzhigang
 * @date 2019-10-16
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private NoticeUserMapper noticeUserMapper;

    @Override
    public Notice findOne(Integer id, String userId) {
        // 更新关联数据为已读
        Map<String, Object> map = new HashMap<>(2);
        map.put("noticeId", id);
        map.put("userId", userId);
        noticeUserMapper.updateStatusReaded(map);
        // 返回通知信息
        return noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addNotice(Notice notice) {
        int ref = 0;
        notice.setStatus(NoticeStatusEnum.SHOW.getCode());
        notice.setCreateTime(new Date());
        notice.setUpdateTime(notice.getCreateTime());
        ref += noticeMapper.insert(notice);
        // 插入关联数据
        List<Map<String, String>> list = userMapper.selectUserIdAndOrgName();
        NoticeUser noticeUser;
        List<NoticeUser> model = new ArrayList<>();
        for (Map<String, String> map : list) {
            noticeUser = new NoticeUser(null, notice.getId(), map.get("userId"), 0, new Date());
            model.add(noticeUser);
        }
        ref += noticeUserMapper.insertList(model);
        return ref;
    }

    @Override
    public int updateNotice(Notice notice) {
        notice.setUpdateTime(new Date());
        return noticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public PagedResult noticeList(Integer page, Integer pageSize, String userId, Integer status, String title) {
        // 开启分页
        PageHelper.startPage(page, pageSize);
        List<Notice> list = noticeMapper.selectAllNotice(userId, status, title);

        return PagedResult.commonResult(list);
    }

    @Override
    public int delete(Integer id) {
        int ref = 0;
        // 删除关联数据
        Example example = new Example(NoticeUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("noticeId", id);
        ref += noticeUserMapper.deleteByExample(example);
        return ref + noticeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public long countUnread(String userId) {
        Example example = new Example(NoticeUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("status", 0);
        return noticeUserMapper.selectCountByExample(example);
    }
}
