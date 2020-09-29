package com.mt.government.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mt.government.common.exception.GlobalException;
import com.mt.government.common.exception.TaskException;
import com.mt.government.enums.ComStatusEnum;
import com.mt.government.enums.TuRaleStatusEnum;
import com.mt.government.mapper.CommonTaskInfoMapper;
import com.mt.government.mapper.TaskSubmitInfoMapper;
import com.mt.government.mapper.TaskUserRelationshipMapper;
import com.mt.government.mapper.UserMapper;
import com.mt.government.model.CommonTaskInfo;
import com.mt.government.model.TaskSubmitInfo;
import com.mt.government.model.TaskUserRelationship;
import com.mt.government.model.User;
import com.mt.government.model.dto.PublishTaskDto;
import com.mt.government.model.dto.Receivers;
import com.mt.government.model.vo.CommonTaskDetailVo;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.service.ComTaskService;
import com.mt.government.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 14:52
 * @update 2020-03-14 15:06 g 任务接收人改为使用 redis 存储，不再保存到任务信息中
 */
@Service
public class ComTaskServiceImpl implements ComTaskService {
    @Resource
    private CommonTaskInfoMapper commonTaskInfoMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private TaskUserRelationshipMapper taskUserRelationshipMapper;
    @Autowired
    private TaskSubmitInfoMapper taskSubmitInfoMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${FILEPATH}")
    private String FILEPATH;
    @Value("${ZIPFILEPATH}")
    private String ZIPFILEPATH;

    @Override
    @Transactional
    public int addComTask(CommonTaskInfo commonTaskInfo, Receivers[] receivers) {
        // 添加任务
        commonTaskInfo.setCommonTaskCreateTime(new Date());
        commonTaskInfo.setCommonTaskStatus(ComStatusEnum.OPEN.getCode());
        // 设置任务编号
        commonTaskInfo.setTaskNo(taskNoGenerator(new Date()));
        commonTaskInfo.setReceivers("");
        int ref = commonTaskInfoMapper.insert(commonTaskInfo);
        // 将任务接收人保存到 redis 中
        redisUtil.sSet("RECEIVERS:" + commonTaskInfo.getCommonTaskId(), receivers);

        TaskUserRelationship tuRela;
        // 遍历数组 并创建任务和接受者的关系表
        for (Receivers receiver : receivers) {
            tuRela = new TaskUserRelationship();
            tuRela.setReceiverId(receiver.getId());
            tuRela.setTaskId(commonTaskInfo.getCommonTaskId());
            tuRela.setReceiverStatus(TuRaleStatusEnum.UNREAD.getCode());
            ref += taskUserRelationshipMapper.insert(tuRela);
        }
        return ref;
    }

    @Override
    public List<CommonTaskInfo> findComTaskByUserId(User user) {
        // 查询用户任务关系表获取 任务id
        Example example = new Example(TaskUserRelationship.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("receiverId", user.getUserId());
        List<TaskUserRelationship> tuRelaList = taskUserRelationshipMapper.selectByExample(example);

        // 遍历集合 通过任务id获取任务详情
        List<CommonTaskInfo> commonTaskInfoList = new ArrayList<>();
        for (TaskUserRelationship tuRela : tuRelaList) {
            CommonTaskInfo model = new CommonTaskInfo();
            model.setCommonTaskId(tuRela.getTaskId());
            commonTaskInfoList.add(commonTaskInfoMapper.selectOne(model));
        }

        return commonTaskInfoList;
    }

    @Override
    public PagedResult List(CommonTaskInfo commonTaskInfo, Integer page, Integer pageSize) {
        // 设置任务过期状态
        List<CommonTaskInfo> list = commonTaskInfoMapper.selectByExample(null);
        for (CommonTaskInfo info : list) {
            if (info.getEndDay().getTime() < new Date().getTime()) {
                info.setCommonTaskStatus(2);
                commonTaskInfoMapper.updateByPrimaryKey(info);
            }
        }
        // 设置查询条件
        Example example = new Example(CommonTaskInfo.class);
        example.orderBy("commonTaskId").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("commonTaskStatus", commonTaskInfo.getCommonTaskStatus());
        criteria.andEqualTo("commonTaskPublisher", commonTaskInfo.getCommonTaskPublisher());
        if (StrUtil.isNotEmpty(commonTaskInfo.getCommonTaskTitle())) {
            criteria.andLike("commonTaskTitle", "%" + commonTaskInfo.getCommonTaskTitle() + "%");
        }
        // 获取分页数据
        PageHelper.startPage(page, pageSize);
        List<CommonTaskInfo> model = commonTaskInfoMapper.selectByExample(example);
        return PagedResult.commonResult(model);
    }

    @Override
    public int update(PublishTaskDto commonTaskInfo) {
        // 更新任务
        int ref = commonTaskInfoMapper.updateByPrimaryKeySelective(commonTaskInfo.getCommonTaskInfo());
        return ref;
    }

    @Transactional
    @Override
    public int delete(Integer id) {
        int ref = 0;
        // 删除关联表数据
        Example example = new Example(TaskUserRelationship.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId", id);
        ref += taskUserRelationshipMapper.deleteByExample(example);

        // 删除提交任务信息
        Example example1 = new Example(TaskSubmitInfo.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("commonTaskId", id);
        ref += taskSubmitInfoMapper.deleteByExample(example1);

        // 删除任务
        ref += commonTaskInfoMapper.deleteByPrimaryKey(id);
        return ref;
    }

    @Override
    public Map<String, Object> getById(Integer taskId) {
        // 获取任务详情
        CommonTaskInfo info = commonTaskInfoMapper.selectByPrimaryKey(taskId);
        // 获取接收单位列表
        Set<Object> receivers = redisUtil.sGet("RECEIVERS:" + info.getCommonTaskId());

        Map<String, Object> map = new HashMap<>(4);
        map.put("commonTaskInfo", info);
        map.put("receivers", receivers);
        return map;
    }


    @Override
    public List<CommonTaskDetailVo> findCommonTaskDetail(int taskId, int status) {
        // 执行查询
        return taskUserRelationshipMapper.findCommonTaskDetail(status, taskId);
    }

    @Override
    public File zipAttachment(Integer taskId) {
        // 获取选中的任务信息
        Example example = new Example(TaskSubmitInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("commonTaskId", taskId);
        List<TaskSubmitInfo> submitInfoList = taskSubmitInfoMapper.selectByExample(example);

        if (submitInfoList.size() == 0) {
            throw new TaskException("当前无人提交任务", -1);
        }
        // 获取附件信息
        List<Map<String, String>> files = new ArrayList<>(submitInfoList.size());
        List<Map<String, String>> list = null;
        for (TaskSubmitInfo ts : submitInfoList) {
            if (StrUtil.isNotEmpty(ts.getFiles()) || !ts.getFiles().equals("[]")) {
                // 将json转成集合
                list = JSON.parseObject(ts.getFiles(), new TypeReference<List<Map<String, String>>>() {
                });
                // 设置文件上传人
                for (Map<String, String> map : list) {
                    map.put("userId", ts.getUserId());
                }
                files.addAll(list);
            }
        }
        // 创建文件流
        FileInputStream[] fileInputStreams = new FileInputStream[files.size()];
        String paths[] = new String[files.size()];
        // 设置文件流和文件名
        FileInputStream in = null;
        Map<String, String> map;
        try {
            for (int i = 0; i < fileInputStreams.length; i++) {
                map = files.get(i);
                String fileName = map.get("userId") + "-" + map.get("name");
                paths[i] = fileName;
                String path = map.get("url");
                in = new FileInputStream(FILEPATH + path);
                fileInputStreams[i] = in;
            }
            // 创建压缩文件
            File zipFile = FileUtil.touch(ZIPFILEPATH + IdUtil.simpleUUID() + ".zip");

            return ZipUtil.zip(zipFile, paths, fileInputStreams);
        } catch (FileNotFoundException e) {
            throw new TaskException("附件打包下载失败", -1);
        } finally {
            IoUtil.close(in);
        }
    }

    @Override
    public ExcelWriter summaryData(Integer taskId) {
        // 获取发布任务信息
        CommonTaskInfo task = commonTaskInfoMapper.selectByPrimaryKey(taskId);
        // 获取文件名
        JSONArray jsonArray = JSON.parseArray(task.getFiles());
        int num = 0;
        if (jsonArray.size() > 1) {
            for (int i = 0; i < jsonArray.size(); i++) {
                if (jsonArray.getJSONObject(i).getString("name").contains("xls") || jsonArray.getJSONObject(i).getString("name").contains("xlsx")) {
                    num = i;
                }
            }
            //throw new TaskException("汇总功能目前只支持单文件任务", -1);
        }
        // 判断文件类型
        JSONObject json = jsonArray.getJSONObject(num);
        String fileName = json.getString("name");
        String ext = fileName.substring((fileName.lastIndexOf(".") + 1));
        if (!ext.equals("xls") && !ext.equals("xlsx")) {
            throw new TaskException("汇总任务文件只能是表格", -1);
        }

        // 获取所有提交文件的位置
        List<String> files = taskSubmitInfoMapper.findSubmitFilesByTaskId(taskId);
        if (files.size() == 0 || files.get(0).equals("[]")) {
            throw new TaskException("目前无人提交信息", -1);
        }
        // 读取所有数据
        ExcelReader reader = null;
        List<Map<String, Object>> list = new ArrayList<>();
        for (String filePath : files) {
            if (StrUtil.isNotEmpty(filePath)) {
                List<Map<String, Object>> fileList = JSON.parseObject(filePath, new TypeReference<List<Map<String, Object>>>() {
                });
                for (Map map : fileList) {
                    ext = (String) map.get("name");
                    ext = ext.substring(ext.lastIndexOf(".") + 1);
                    if (!ext.equals("xls") && !ext.equals("xlsx")) {
                        throw new TaskException("提交文件类型有误", -1);

                    }
                    String url = (String) map.get("url");
                    reader = ExcelUtil.getReader(FILEPATH + url);
                    List<Map<String, Object>> data = reader.readAll();
                    list.addAll(data);
                }
            }
        }
        // 将数据写入到writer中
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list);
        if (reader != null) {
            reader.close();
        }
        return writer;
    }

    @Override
    public long countUnSubmit(String userId) {
        Example example = new Example(TaskUserRelationship.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("receiverId", userId);
        criteria.andEqualTo("receiverStatus", 0);
        return taskUserRelationshipMapper.selectCountByExample(example);
    }

    /**
     * 根据日期生成编号
     * 类似 201912230001
     *
     * @return 编号
     */
    private String taskNoGenerator(Date date) {
        // 获取当前日期
        String currentDate = DateUtil.format(date, "yyyyMMdd");
        long sequence;
        // 判断是否存在key，存在直接取自增的值，不存在就创建新的，并设置自动过期时间为一天
        if (!redisUtil.hasKey(currentDate)) {
            sequence = redisUtil.incr(currentDate, 1L);
            redisUtil.expire(currentDate, 86400L);
        } else {
            sequence = redisUtil.incr(currentDate, 1L);
        }
        // 拼接序列号
        String no = String.valueOf(sequence);
        for (int i = no.length(); i < 4; i++) {
            no = "0" + no;
        }
        return currentDate + no;
    }
}