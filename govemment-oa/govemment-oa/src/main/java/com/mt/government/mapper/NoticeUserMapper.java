package com.mt.government.mapper;

import com.mt.government.model.NoticeUser;
import com.mt.government.common.config.MyMapper;

import java.util.Map;

public interface NoticeUserMapper extends MyMapper<NoticeUser> {
    /**
     * 更新关联记录状态为已读
     *
     * @param map 包含通知id和用户id的map
     * @return 影响行数
     */
    int updateStatusReaded(Map<String, Object> map);
}