package com.mt.government.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mt.government.enums.SubmitTaskStatusEnum;
import com.mt.government.enums.TuRaleStatusEnum;
import com.mt.government.common.exception.GlobalException;
import com.mt.government.mapper.*;
import com.mt.government.model.*;
import com.mt.government.model.vo.MySubmitListVo;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.service.SubmitTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 17:23
 */
@Service
public class SubmitTaskServiceImpl implements SubmitTaskService {
    @Resource
    private TaskSubmitInfoMapper tsInfoMapper;
    @Resource
    private TaskUserRelationshipMapper taskUserRelationshipMapper;
    @Resource
    private CommonTaskInfoMapper commonTaskInfoMapper;
    @Autowired
    private DeductedConfigMapper deductedConfigMapper;
    @Autowired
    private DeductedMapper deductedMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public PagedResult findByUserId(Integer page, Integer pageSize, String userId, String title) {
        // 开启分页
        PageHelper.startPage(page, pageSize);
        // 获取数据
        List<MySubmitListVo> list = tsInfoMapper.selectMySubmitList(userId, title);

        return PagedResult.commonResult(list);
    }

    @Override
    @Transactional
    public int addSubmitTask(TaskSubmitInfo taskSubmitInfo) {
        // 判断是否重复提交
        Example example = new Example(TaskSubmitInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId" , taskSubmitInfo.getUserId());
        criteria.andEqualTo("commonTaskId" , taskSubmitInfo.getCommonTaskId());
        int count = tsInfoMapper.selectCountByExample(example);
        if (count > 0) {
            throw new GlobalException("该任务已提交过，请勿重复提交" , -1);
        }

        // 获取对应任务信息
        CommonTaskInfo task = commonTaskInfoMapper.selectByPrimaryKey(taskSubmitInfo.getCommonTaskId());
        // 判断是否有标题，没有标题自动填充为 xx回复 xx任务
        if (StrUtil.isBlank(taskSubmitInfo.getSubmitTitle())) {
            StringBuilder sb = new StringBuilder();
            // 获取提交单位名
            User user = userMapper.selectByPrimaryKey(taskSubmitInfo.getUserId());
            // 拼接提交任务标题
            sb.append(user.getOrgName()).append(" ").append("回复").append(" ").append(task.getCommonTaskTitle());
            taskSubmitInfo.setSubmitTitle(sb.toString());
        }

        // 将任务状态修改为已提交
        List<TaskUserRelationship> taskUserRelationships = getTaskUserRelationships(taskSubmitInfo);
        TaskUserRelationship taskUserRelationship = taskUserRelationships.get(0);
        taskUserRelationship.setReceiverStatus(TuRaleStatusEnum.SUBMIT.getCode());
        taskUserRelationship.setReceiverTime(new Date());
        taskUserRelationshipMapper.updateByPrimaryKeySelective(taskUserRelationship);

        // 默认设置新提交的任务状态为未审批
        taskSubmitInfo.setStatus(SubmitTaskStatusEnum.UNAPPROVAL.getCode());
        // 设置提交时间
        taskSubmitInfo.setSubmitTime(taskUserRelationship.getReceiverTime());
        // 判断是否超时,如果超时自动扣分
        if (DateUtil.compare(taskSubmitInfo.getSubmitTime(), task.getEndDay()) > 0) {
            taskSubmitInfo.setIsOvertime(1);
            // 计算超时扣分
            float point = getDeductedPoint(task.getEndDay(), taskSubmitInfo.getSubmitTime());
            Date createTime = new Date();
            Deducted deducted = new Deducted(null, "system" , createTime, "system" , createTime, taskSubmitInfo.getUserId(), point, "超时自动扣分" , taskSubmitInfo.getCommonTaskId());

            deductedMapper.insert(deducted);
        }
        return tsInfoMapper.insertSelective(taskSubmitInfo);
    }

    @Override
    public TaskSubmitInfo findOne(int id) {
        return tsInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(TaskSubmitInfo taskSubmitInfo) {
        return tsInfoMapper.updateByPrimaryKeySelective(taskSubmitInfo);
    }


    /**
     * 查询关联记录
     *
     * @param taskSubmitInfo 提交任务信息
     * @return
     */
    private List<TaskUserRelationship> getTaskUserRelationships(TaskSubmitInfo taskSubmitInfo) {
        Example example = new Example(TaskUserRelationship.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId" , taskSubmitInfo.getCommonTaskId());
        criteria.andEqualTo("receiverId" , taskSubmitInfo.getUserId());

        return taskUserRelationshipMapper.selectByExample(example);
    }

    /**
     * 提交任务超时自动计算扣分
     *
     * @param endDay    任务结束时间
     * @param submitDay 提交时间
     * @return 扣分值
     */
    private float getDeductedPoint(Date endDay, Date submitDay) {
        // 获取配置信息
        DeductedConfig config = deductedConfigMapper.selectByExample(null).get(0);
        // 如果没有配置不扣分
        if (config == null) {
            return 0;
        }
        // 获取相差天数
        long day = DateUtil.betweenDay(endDay, submitDay, false);
        // 通过配置计算分数
        float points =  day * config.getPointsPerDay();
        // 如果扣分大于最大扣分数，返回最大扣分数
        if (config.getMaxDeductedPoints() != 0 && points > config.getMaxDeductedPoints()) {
            return config.getMaxDeductedPoints();
        }
        return points;
    }
}
