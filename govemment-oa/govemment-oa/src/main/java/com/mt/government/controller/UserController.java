package com.mt.government.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mt.government.model.User;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.service.ComTaskService;
import com.mt.government.service.NoticeService;
import com.mt.government.service.UserService;
import com.mt.government.utils.RedisUtil;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制层
 *
 * @author fuzhigang
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private ComTaskService comTaskService;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${token.prefix}")
    private String TOKEN_PREFIX;

    /**
     * 查询所有用户
     *
     * @return 查询结果
     */
    @GetMapping("/findAll")
    public Result findAllUser() {
        return ResultUtil.success(userService.findAll());
    }

    /**
     * 根据id查找用户
     *
     * @param userId 用户id
     * @return 查询结果
     */
    @GetMapping("/findOne/{userId}")
    public Result findOne(@PathVariable String userId) {
        if (StrUtil.isEmpty(userId)) {
            return ResultUtil.error("用户名不能为空");
        }
        User user = userService.findById(userId);
        user.setPassword(null);
        return ResultUtil.success(user);
    }

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(String token, HttpServletRequest request) {
        // FIXME: NullPointException
        if (token == null) {
            token = request.getHeader("token");
        }
        // 获取用户id
        Object o = redisUtil.get(TOKEN_PREFIX + token);
        if (o != null) {
            User user = JSON.parseObject(o.toString(), User.class);
            return ResultUtil.success(user);
        }
        return ResultUtil.error("获取用户信息失败");
    }

    /**
     * 根据单位查询
     *
     * @param id 单位id
     * @return
     */
    @GetMapping("/findByOrg")
    public Result findByOrg(Integer id) {
        List<User> list = userService.findByOrg(id);
        if (list.size() > 0) {
            return ResultUtil.success(list.get(0));
        }
        return ResultUtil.success(null);
    }

    @GetMapping("/findUserId")
    public Result findUserIdAndUserName() {
        return ResultUtil.success(userService.findUserIdAndUserName());
    }

    /**
     * 分页查询用户
     *
     * @param page     当前页
     * @param pageSize 每页显示数
     * @return
     */
    @PostMapping("/findByPage")
    public Result findByPage(int page, int pageSize) {
        if (page == 0 || pageSize == 0) {
            return ResultUtil.error("请使用正确的访问方式");
        }
        return ResultUtil.success(userService.findByPage(page, pageSize));
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user, HttpServletRequest request) {
        // 参数校验
        if (StrUtil.isEmpty(user.getUserId()) || StrUtil.isEmpty(user.getPassword()) || user.getOrgId() == null) {
            return ResultUtil.error("请填写正确的数据");
        }
        User cur = getCurrentUser(request);
        if (cur.getUserRole() > 1) {
            return ResultUtil.error("暂无此权限");
        }
        // 设置用户级别为自己的下一级
        user.setUserRole(cur.getUserRole() + 1);

        int ref = userService.add(user);
        if (ref > 0) {
            return ResultUtil.success(ref);
        }
        return ResultUtil.error("添加失败");
    }

    /**
     * 更新用户
     *
     * @param user 修改用户信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        int ref = userService.update(user);
        if (ref > 0) {
            return ResultUtil.success(ref);
        }
        return ResultUtil.error("更新失败");
    }

    /**
     * 批量删除用户
     *
     * @param userIds 用户id数组
     * @return 修改结果
     */
    @PostMapping("/delete")
    public Result delete(String[] userIds) {
        if (StringUtils.isEmpty(userIds)) {
            return ResultUtil.error("参数不能为空");
        }
        int ref = userService.delete(userIds);
        if (ref > 0) {
            return ResultUtil.success();
        }
        return ResultUtil.error("删除失败");
    }

    /**
     * 获取下级已分配账号单位
     *
     * @param userId   用户id
     * @param userRole 用户级别
     * @return 查询结果
     */
    @GetMapping("/getByUserRole")
    public Result getByUserRole(String userId, Integer userRole) {
        return ResultUtil.success(userService.getByUserRole(userId, userRole));
    }

    /**
     * 获取相应的下级单位
     *
     * @param userId   当前用户id
     * @param userRole 用户角色
     * @return 查询结果
     */
    @GetMapping("/findReceivers")
    public Result findReceivers(String userId, Integer userRole) {
        return ResultUtil.success(userService.findReceivers(userId, userRole));
    }

    /**
     * 用户自行修改密码
     *
     * @param userId      用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    @PostMapping("/changePassword")
    public Result changePassword(String userId, String oldPassword, String newPassword) {
        if (StrUtil.isEmpty(userId) || StrUtil.isEmpty(oldPassword) || StrUtil.isEmpty(newPassword)) {
            return ResultUtil.error("请已正确的方式访问");
        }
        return ResultUtil.success(userService.updatePassword(userId, oldPassword, newPassword));
    }

    /**
     * 管理员修改密码
     *
     * @param userId   修改的用户id
     * @param password 新密码
     * @return 修改结果
     */
    @PostMapping("/resetPassword")
    public Result resetPassword(String userId, String password, HttpServletRequest request) {
        User currentUser = getCurrentUser(request);
        if (!currentUser.getUserRole().equals(0)) {
            return ResultUtil.error("您没有操作权限");
        }
        if (StrUtil.isEmpty(userId) || StrUtil.isEmpty(password)) {
            return ResultUtil.error("请已正确的方式访问");
        }
        return ResultUtil.success(userService.resetPassword(userId, password));
    }

    /**
     * 获取未读通知和未提交任务数量
     *
     * @param userId 用户id
     * @return 查询结果
     */
    @GetMapping("/getRemindInfo")
    public Result getRemindInfo(String userId) {
        long countNotice = noticeService.countUnread(userId);
        long countTask = comTaskService.countUnSubmit(userId);
        Map<String, Object> map = new HashMap<>(3);
        map.put("notice", countNotice);
        map.put("task", countTask);
        map.put("total", countNotice + countTask);
        return ResultUtil.success(map);
    }
}
