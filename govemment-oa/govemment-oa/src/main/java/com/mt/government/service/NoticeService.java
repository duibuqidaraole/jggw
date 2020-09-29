package com.mt.government.service;

import com.mt.government.model.Notice;
import com.mt.government.model.vo.PagedResult;
import lombok.NoArgsConstructor;


/**
 * 通知业务层接口
 *
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/17 11:51
 * @update fuzhigang
 * @date 2019-10-16
 */
public interface NoticeService {

    /**
     * 查看通知详情
     *
     * @param id     通知id
     * @param userId 用户id
     * @return 查询结果
     */
    Notice findOne(Integer id, String userId);

    /**
     * 发布通知
     *
     * @param notice 通知内容
     * @return 新增行数
     */
    int addNotice(Notice notice);

    /**
     * 更新通知
     *
     * @param notice
     * @return
     */
    int updateNotice(Notice notice);

    /**
     * 分页查看通知列表
     *
     * @param page     当前页
     * @param pageSize 每页显示数量
     * @param userId   用户id
     * @param status   已读状态
     * @param title    通知标题
     * @return 查询结果
     */
    PagedResult noticeList(Integer page, Integer pageSize, String userId, Integer status, String title);

    /**
     * 删除通知
     *
     * @param id 通知id
     * @return 影响行数
     */
    int delete(Integer id);

    /**
     * 统计未读通知数量
     *
     * @param userId 用户id
     * @return
     */
    long countUnread(String userId);
}
