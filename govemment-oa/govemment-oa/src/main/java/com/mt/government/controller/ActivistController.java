package com.mt.government.controller;

import com.github.pagehelper.PageHelper;
import com.mt.government.mapper.ActivistMapper;
import com.mt.government.model.Activist;
import com.mt.government.model.Due2;
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
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
        if (StringUtils.isEmpty(activist.getUpdateSqloTime())) {
            return ResultUtil.error("时间不能为空");
        }
        activist.setType(0);
        activist.setStatus(0); // 提交入党状态
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
        criteria.andEqualTo("status",activist.getStatus());
        PageHelper.startPage(page, pageSize);
        List<Activist> model = activistMapper.selectByExample(example);

        // 遍历数据，根据不同状态决定是否显示转入为下一状态
        for (int i = 0; i < model.size(); i++) {
            int a = DateUtil.diffMonth(model.get(i).getUpdateSqloTime(),new Date());
            System.out.println("-------------"+a+"-----------------------------");
            if(model.get(i).getStatus().equals(0) && DateUtil.diffMonth(model.get(i).getUpdateSqloTime(),new Date()) >= 6  ){ // 为零,大于半年
                model.get(i).setType(1); // 可以被手动变成下一个状态

            }
            if(model.get(i).getStatus().equals(1) && DateUtil.diffMonth(model.get(i).getTempTime(),new Date()) >= 12  ){ // 为零,大于1年
                model.get(i).setType(1); // 可以被手动变成下一个状态
//                model.get(i).setTempTime(new Date()); // 更新时间
            }

            if(model.get(i).getStatus().equals(2)){ // 为2,无限制
                model.get(i).setType(1); // 可以被手动变成下一个状态
//                model.get(i).setTempTime(new Date()); // 更新时间临时时间
            }

            if(model.get(i).getStatus().equals(3)  && DateUtil.diffMonth(model.get(i).getTempTime(),new Date()) >= 12){ // 为3,一年
                model.get(i).setType(1); // 可以被手动变成下一个状态
//                model.get(i).setTempTime(new Date()); // 更新时间临时时间
            }
        }
        return ResultUtil.success(PagedResult.commonResult(model));
    }



    /**
     * 更新各种状态时间（进入下一阶段）
     *
     * @param activist
     * @return
     */
    @PostMapping("updateTime")
    public Result updateTime(@RequestBody Activist activist) {

        Integer status = activist.getStatus();
        if(status < 3){ // 有进入下一状态的按钮
            status = status + 1;
            activist.setStatus(status);
            activist.setType(0);
            activist.setTempTime(new Date()); // 更新时间临时时间
        }
        else { // 4：已经入党了，将状态转成没有按钮的状态
            status = status+1;
            activist.setStatus(status);
            activist.setType(0);
            activist.setTempTime(new Date()); // 更新时间临时时间
        }
        int i = activistMapper.updateByPrimaryKeySelective(activist);
        return ResultUtil.success(1);
    }


    /**
     * 根据ID删除记录
     *
     * @param activist
     * @return
     */
    @PostMapping("delete")
    public Result delete(@RequestBody Activist activist,HttpServletRequest request) {
        if (StringUtils.isEmpty(activist.getId())) {
            return ResultUtil.error("ID不能为空");
        }
        // 获取一下用户的id
//        String userId = getCurrentUser(request).getUserId();
//        if(StringUtils.isEmpty(userId)){ // 用户id不能为空
//            return ResultUtil.error("用户id不能为空");
//        }
//        if(userId.equals("admin")){ // 是总账号 ，有权限删除
            int delete = activistMapper.delete(activist);
            return ResultUtil.success(delete);
//        }
//        else {
//            return ResultUtil.error("没有删除权限哦！！！");
//        }
    }




//    /**
//     * 定时任务
//     *
//     * @param
//     * @return
//     */
//    @Scheduled(cron = "0/5 * * * * ?")
//    public Result update() {
//        System.err.println("1111");
//        return ResultUtil.success();
//    }
}
