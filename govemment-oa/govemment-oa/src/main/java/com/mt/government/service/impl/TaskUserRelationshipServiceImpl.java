package com.mt.government.service.impl;

import com.github.pagehelper.PageHelper;
import com.mt.government.mapper.CommonTaskInfoMapper;
import com.mt.government.model.vo.MyTaskListVo;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.service.TaskUserRelationshipService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskUserRelationshipServiceImpl implements TaskUserRelationshipService {
    @Resource
    private CommonTaskInfoMapper commonTaskInfoMapper;

    /**
     * 根据用户ID获取任务列表
     *
     * @param
     * @return
     */
    @Override
    public PagedResult TaskUserList(String userId, Integer status, String title, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<MyTaskListVo> list = commonTaskInfoMapper.selectMyTaskListVo(userId, status, title);
        return PagedResult.commonResult(list);
    }
}
