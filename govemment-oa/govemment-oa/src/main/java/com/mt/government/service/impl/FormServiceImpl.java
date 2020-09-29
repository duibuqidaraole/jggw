package com.mt.government.service.impl;

import com.mt.government.mapper.FormMapper;
import com.mt.government.model.Form;
import com.mt.government.service.FormService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FormServiceImpl implements FormService {

    @Resource
    private FormMapper formMapper;

    /**
     * 添加表单记录
     *
     * @param form
     */
    @Override
    public void Insert(Form form) {
        formMapper.insert(form);
    }

    /**
     * 获取表单记录列表
     *
     * @return
     */
    public List<Form> List() {
        return formMapper.selectAll();
    }

    /**
     * 根据ID删除表单记录
     *
     * @param form
     */
    @Override
    public void Delete(Form form) {
        formMapper.deleteByPrimaryKey(form);
    }

    /**
     * 根据ID获取记录
     *
     * @param form
     * @return
     */
    @Override
    public Form GetById(Form form) {
        Example example = new Example(Form.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id" , form.getId());
        return formMapper.selectOneByExample(example);
    }
}
