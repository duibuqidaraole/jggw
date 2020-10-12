package com.mt.government.service.impl;

import cn.hutool.core.date.DateUtil;
import com.mt.government.mapper.QuantitativeMapper;
import com.mt.government.mapper.UserMapper;
import com.mt.government.model.Quantitative;
import com.mt.government.service.QuantitativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 换届信息业务实现类
 *
 * @author fuzhigang
 * @date 2019-10-15
 */
@Service
public class QuantitativeServiceImpl implements QuantitativeService {
    @Autowired
    private QuantitativeMapper quantitativeMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public Quantitative findOne(Integer id) {
        return quantitativeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Quantitative> findAll() {
        return quantitativeMapper.selectAll();
    }

    @Override
    public List<Quantitative> findByUserId(String userId, String partySecretary) {
        Example example = new Example(Quantitative.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        if (!StringUtils.isEmpty(partySecretary)) {
            criteria.andLike("partySecretary", "%" + partySecretary + "%");
        }
        return quantitativeMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public int add(Quantitative quantitative) {
        quantitative.setCreateTime(new Date());
        quantitativeMapper.insert(quantitative);
        // 更新换届提醒状态
        updateQuantitativeFlag(quantitative.getUserId());
        return 1;
    }

    @Override
    @Transactional
    public int update(Quantitative quantitative) {
        quantitativeMapper.updateByPrimaryKeySelective(quantitative);
        // 更新换届提醒状态
        updateQuantitativeFlag(quantitative.getUserId());
        return 1;
    }

    @Override
    public int delete(Integer id) {
        return quantitativeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新换届提醒状态
     *
     * @param userId 用户id
     */
    public int updateQuantitativeFlag(String userId) {
        // 更新换届提醒状态
        Date endDay = quantitativeMapper.selectEndDayByUserId(userId);
        Date now = new Date();
        // 判断和当前日期的时间差，需求6个月
        if (endDay != null && DateUtil.betweenMonth(now, endDay, false) < 6) {
            // 如果小于6个月，更新用户换届提醒状态
            return userMapper.updateQuantitativeFlag(1, userId);
        } else {
            return userMapper.updateQuantitativeFlag(0, userId);
        }
    }
}
