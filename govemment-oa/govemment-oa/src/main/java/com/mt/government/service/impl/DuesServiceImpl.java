package com.mt.government.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mt.government.common.exception.GlobalException;
import com.mt.government.mapper.Due2Mapper;
import com.mt.government.mapper.DuesMapper;
import com.mt.government.model.Due2;
import com.mt.government.model.Dues;
import com.mt.government.service.DuesService;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 党费信息业务实现
 *
 * @author g
 * @date 2019-11-12 11:37
 */
@Service
public class DuesServiceImpl implements DuesService {

    @Autowired
    private Due2Mapper due2Mapper;
    @Value("${FILEPATH}")
    private String FILEPATH;

    @Override
    public int save(Due2 due2) {
        Date date = new Date();
        due2.setCreateTime(date);
        due2.setUpdateTime(date);
        return due2Mapper.insert(due2);
    }

    @Override
    public int update(Due2 due2) {
        due2.setUpdateTime(new Date());
        return due2Mapper.updateByPrimaryKey(due2);
    }

    @Override
    public int delete(Long id) {
        return due2Mapper.deleteByPrimaryKey(id);
    }

//    @Override
//    public Result getDuesInfoByUser(String userId) {
        // 获取文件地址
        /*Dues dues = duesMapper.selectLatestRecordByUserId(userId);
        if (dues == null) {
            return ResultUtil.error("该单位还未上传党费信息");
        }
        JSONObject jsonObject = JSON.parseArray(dues.getFiles()).getJSONObject(0);
        String filePath = jsonObject.getString("url");
        // 获取表格数据
        ExcelReader reader = ExcelUtil.getReader(FILEPATH + filePath);
        List<List<Object>> data = reader.read(2);
        Map<String, Object> map = new HashMap<>(4);
        map.put("dues" , dues);
        map.put("list" , data);
        reader.close();*/
//        Example example = new Example(Due2.class);
//        example.orderBy("createTime").asc();
//        example.createCriteria().andEqualTo("userId", userId);
//        List<Due2> due2s = due2Mapper.selectByExample(example);
//        return ResultUtil.success(due2s);
//    }

    @Override
    //@Transactional
    public int resolveExcelToDues(MultipartFile file, Integer type, String userId) throws IOException {
        int ref = 0;
        // 如果是覆盖，删除改单位原来的记录
        if (type.equals(2)) {
            Example example = new Example(Due2.class);
            example.createCriteria().andEqualTo("userId", userId);
            int d = due2Mapper.deleteByExample(example);
            ref += d;
        }

        // 读取文件
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<List<Object>> read = reader.read(2);
        // 初始化接收的list
        List<Due2> list = new ArrayList<>(read.size());
        Due2 due2;
        Date date = new Date();
        for (List l : read) {
            // 创建党费信息对象
            due2 = new Due2(userId, String.valueOf(l.get(0)), String.valueOf(l.get(1)),
                    String.valueOf(l.get(2)), String.valueOf(l.get(3)), String.valueOf(l.get(4)),
                    String.valueOf(l.get(5)), String.valueOf(l.get(6)), String.valueOf(l.get(7)),
                    String.valueOf(l.get(8)), String.valueOf(l.get(9)), String.valueOf(l.get(10)),
                    String.valueOf(l.get(11)), String.valueOf(l.get(12)), String.valueOf(l.get(13)),
                    String.valueOf(l.get(14)), String.valueOf(l.get(15)), String.valueOf(l.get(16)),
                    String.valueOf(l.get(17)), date, date);
            // 添加到集合中
            list.add(due2);
            System.err.println(due2);
        }
        reader.close();
        // 插入到数据库
        int i = due2Mapper.insertList(list);
        ref += i;
        return ref;
    }
}
