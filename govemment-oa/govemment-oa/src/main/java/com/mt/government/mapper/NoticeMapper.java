package com.mt.government.mapper;

import com.mt.government.model.Notice;
import com.mt.government.common.config.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NoticeMapper extends MyMapper<Notice> {
    /**
     * 查询通知列表
     *
     * @param userId 用户id
     * @param status 已读状态
     * @param title  通知标题
     * @return 查询结果
     */
    List<Notice> selectAllNotice(@Param("userId") String userId, @Param("status") Integer status, @Param("title") String title);
}