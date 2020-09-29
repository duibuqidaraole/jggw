package com.mt.government.service.impl;

import com.github.pagehelper.PageHelper;
import com.mt.government.mapper.DeductedConfigMapper;
import com.mt.government.mapper.DeductedMapper;
import com.mt.government.mapper.TaskSubmitInfoMapper;
import com.mt.government.model.Deducted;
import com.mt.government.model.DeductedConfig;
import com.mt.government.model.TaskSubmitInfo;
import com.mt.government.model.vo.DeductedDetailVo;
import com.mt.government.model.vo.DeductedListVo;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.service.DeductedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 扣分业务实现
 *
 * @author g
 * @date 2019-11-11 15:06
 */
@Service
public class DeductedServiceImpl implements DeductedService {
    @Autowired
    private DeductedMapper deductedMapper;
    @Autowired
    private DeductedConfigMapper deductedConfigMapper;
    @Autowired
    private TaskSubmitInfoMapper taskSubmitInfoMapper;

    @Override
    public List<DeductedListVo> listDeductedListVo(Date startDay, Date endDay,Integer orgId, String orgName) {
        return deductedMapper.selectDeductedListVo(startDay, endDay, orgId, orgName);
    }

    @Override
    public PagedResult listDeductedByUser(Integer page, Integer pageSize, String userId) {
        // 开启分页
        PageHelper.startPage(page, pageSize);
        List<DeductedDetailVo> list = deductedMapper.selectDeductedDetailVo(userId);

        return PagedResult.commonResult(list);
    }

    @Override
    public int add(Deducted deducted) {
        if(deducted.getType()!=0){
            // 更新提交任务评分状态
            Example example = new Example(TaskSubmitInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("commonTaskId" , deducted.getTaskId());
            criteria.andEqualTo("userId" , deducted.getUserId());
            TaskSubmitInfo submitInfo = taskSubmitInfoMapper.selectByExample(example).get(0);
            submitInfo.setDeducted(1);
            taskSubmitInfoMapper.updateByPrimaryKey(submitInfo);
        }
        return deductedMapper.insert(deducted);
    }

    @Override
    public int update(Deducted deducted) {
        return deductedMapper.updateByPrimaryKey(deducted);
    }

    @Override
    public int delete(Long id) {
        return deductedMapper.deleteByPrimaryKey(id);
    }


    @Override
    public int saveDeductedConfig(DeductedConfig deductedConfig) {
        // 获取id判断是新增还是更新
        if (deductedConfig.getId() == null) {
            deductedConfig.setCreateTime(new Date());
            deductedConfig.setUpdateTime(deductedConfig.getCreateTime());
            return deductedConfigMapper.insertSelective(deductedConfig);
        } else {
            deductedConfig.setUpdateTime(new Date());
            return deductedConfigMapper.updateByPrimaryKeySelective(deductedConfig);
        }
    }

    @Override
    public DeductedConfig getDeductedConfig() {
        List<DeductedConfig> list = deductedConfigMapper.selectByExample(null);
        if (list.size() > 0) {
            return list.get(0);
        }
        return new DeductedConfig();
    }

}
