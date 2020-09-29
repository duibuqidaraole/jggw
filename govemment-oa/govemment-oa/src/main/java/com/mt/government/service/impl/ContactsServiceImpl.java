package com.mt.government.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mt.government.mapper.ContactsMapper;
import com.mt.government.model.Contacts;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 联系人业务层实现
 *
 * @author fuzhigang
 * @date 2019-10-16
 */
@Service
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    private ContactsMapper contactsMapper;

    @Override
    public Contacts findOne(Integer id) {
        return contactsMapper.selectByPrimaryKey(id);
    }

    @Override
    public PagedResult findByUserId(Integer page, Integer pageSize, String userId, String linkman) {
        // 开启分页
        PageHelper.startPage(page, pageSize);
        // 创建查询条件
        Example example = new Example(Contacts.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId" , userId);
        if (StrUtil.isNotEmpty(linkman)) {
            criteria.andLike("linkman" , "%" + linkman + "%");
        }
        List<Contacts> list = contactsMapper.selectByExample(example);
        // 设置分页数据
        return PagedResult.commonResult(list);
    }

    @Override
    public List<Contacts> findAll() {
        return contactsMapper.selectAll();
    }

    @Override
    public int add(Contacts contacts) {
        contacts.setCreateTime(new Date());
        contacts.setUpdateTime(contacts.getCreateTime());
        return contactsMapper.insert(contacts);
    }

    @Override
    public int update(Contacts contacts) {
        contacts.setUpdateTime(new Date());
        return contactsMapper.updateByPrimaryKeySelective(contacts);
    }

    @Override
    public int delete(Integer id) {
        return contactsMapper.deleteByPrimaryKey(id);
    }
}
