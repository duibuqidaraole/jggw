package com.mt.government.controller;

import com.github.pagehelper.PageHelper;
import com.mt.government.mapper.ActivistMapper;
import com.mt.government.model.Activist;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/Activist")
@CrossOrigin
@EnableScheduling   // 2.开启定时任务
public class ActivistController extends BaseController {

    @Resource
    private ActivistMapper activistMapper;

    /**
     * 添加
     *
     * @param activist
     * @return
     */
    @PostMapping("add")
    public Result add(@RequestBody Activist activist) {
        if (StringUtils.isEmpty(activist.getOrgName())) {
            return ResultUtil.error("单位名称为空");
        }
        if (StringUtils.isEmpty(activist.getActivistName())) {
            return ResultUtil.error("姓名不能为空");
        }
        activist.setType(0);
        activist.setStatus(0);
        activistMapper.insert(activist);
        return ResultUtil.success();
    }

    /**
     * list列表
     *
     * @param activist
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("list")
    public Result list(Activist activist, Integer page, Integer pageSize) {
        if (StringUtils.isEmpty(page) || StringUtils.isEmpty(pageSize)) {
            return ResultUtil.error("分页参数为空");
        }
        Example example = new Example(Activist.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("id").desc();
        criteria.andIsNull("doTime");
        PageHelper.startPage(page, pageSize);
        List<Activist> model = activistMapper.selectByExample(example);
        return ResultUtil.success(PagedResult.commonResult(model));
    }

    /**
     * 定时任务
     *
     * @param
     * @return
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public Result update() {
        System.err.println("1111");
        return ResultUtil.success();
    }
}
