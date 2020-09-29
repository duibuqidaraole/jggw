package com.mt.government.controller;

import cn.hutool.core.util.StrUtil;
import com.mt.government.model.Notice;
import com.mt.government.service.NoticeService;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 通知控制层
 *
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/17 11:52
 * @update fuzhigang
 * @date 2019-10-16
 */
@CrossOrigin
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    /**
     * 发布通知
     *
     * @param notice 通知信息
     * @return 影响行数
     */
    @PostMapping("/add")
    public Result addNotice(@RequestBody Notice notice) {
        if (StrUtil.isEmpty(notice.getTitle())) {
            return ResultUtil.error("标题不能为空");
        }
        int ref = noticeService.addNotice(notice);
        if (ref < 0) {
            return ResultUtil.error("发布失败");
        }
        return ResultUtil.success("发布成功");
    }

    /**
     * 改变通知状态
     *
     * @param notice 通知信息
     * @return 更新行数
     */
    @PostMapping("/update")
    public Result updateNotice(@RequestBody Notice notice) {
        int ref = noticeService.updateNotice(notice);
        if (ref < 0) {
            return ResultUtil.success("修改失败");
        }
        return ResultUtil.success("修改成功");
    }

    /**
     * 通知列表
     *
     * @param page     当前页
     * @param pageSize 每页数量
     * @param userId   用户id
     * @param status   已读状态
     * @param title    通知标题
     * @return 查询结果
     */
    @GetMapping("/list")
    public Result noticeList(Integer page, Integer pageSize, String userId, Integer status, String title) {
        if (page == null) {
            page = 1;
        }
        return ResultUtil.success(noticeService.noticeList(page, pageSize, userId, status, title));
    }

    /**
     * 查看通知详情
     *
     * @param id     任务id
     * @param userId 当前用户id
     * @return 查询结果
     */
    @GetMapping("/findOne")
    public Result findOne(Integer id, String userId) {
        if (id == null || userId == null) {
            return ResultUtil.error("error");
        }
        return ResultUtil.success(noticeService.findOne(id, userId));
    }

    /**
     * 删除通知
     *
     * @param id 通知id
     * @return 影响行数
     */
    @GetMapping("/delete")
    public Result delete(Integer id) {
        return ResultUtil.success(noticeService.delete(id));
    }
}
