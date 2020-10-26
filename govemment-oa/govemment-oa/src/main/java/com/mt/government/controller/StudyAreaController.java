package com.mt.government.controller;

import com.github.pagehelper.PageHelper;
import com.mt.government.mapper.ActivistMapper;
import com.mt.government.mapper.StudyAreaMapper;
import com.mt.government.model.Activist;
import com.mt.government.model.StudyArea;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.utils.DateUtil;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 学习天地
 */
@RestController
@RequestMapping("/studyArea")
@CrossOrigin
public class StudyAreaController extends BaseController {

    @Resource
    private StudyAreaMapper studyAreaMapper;

    /**
     * 添加
     *
     * @param studyArea
     * @return
     */
    @PostMapping("add")
    public Result add(@RequestBody StudyArea studyArea) {

        studyArea.setAddTime(new Date());
        studyAreaMapper.insert(studyArea);
        return ResultUtil.success();
    }

    /**
     * list列表
     *
     * @param studyArea
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("list")
    public Result list(StudyArea studyArea, Integer page, Integer pageSize) {
        if (StringUtils.isEmpty(page) || StringUtils.isEmpty(pageSize)) {
            return ResultUtil.error("分页参数为空");
        }
        Example example = new Example(StudyArea.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("addTime").desc();
        if(!StringUtils.isEmpty(studyArea.getTitle())){
            criteria.andLike("title","%"+studyArea.getTitle()+"%");
        }

        if(!StringUtils.isEmpty(studyArea.getStatus())){
            criteria.andLike("status","%"+studyArea.getStatus()+"%");
        }
        PageHelper.startPage(page, pageSize);
        List<StudyArea> list = studyAreaMapper.selectByExample(example);

        return ResultUtil.success(PagedResult.commonResult(list));
    }


    /**
     * 根据ID删除记录
     *
     * @param studyArea
     * @return
     */
    @PostMapping("delete")
    public Result delete(@RequestBody StudyArea studyArea) {
        if (StringUtils.isEmpty(studyArea.getId())) {
            return ResultUtil.error("ID不能为空");
        }
        int delete = studyAreaMapper.delete(studyArea);
        return ResultUtil.success(delete);
    }




}
