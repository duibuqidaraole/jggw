package com.mt.government.service;

import com.mt.government.common.exception.UserException;
import com.mt.government.model.User;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.model.vo.ReceiversVo;
import com.mt.government.model.vo.TreeVo;

import java.util.List;
import java.util.Map;

/**
 * 用户业务层接口
 *
 * @author fuzhigang
 */
public interface UserService {


    /**
     * 根据id查询
     *
     * @param userId
     * @return
     */
    User findById(String userId);

    /**
     * 查询所有用户
     *
     * @return
     */
    List<User> findAll();

    /**
     * 获取用户id和名称
     *
     * @return
     */
    List<Map<String, String>> findUserIdAndUserName();


    /**
     * 分页查询
     *
     * @param page     当前页
     * @param pageSize 每页显示数量
     * @return 查询结果
     */
    PagedResult findByPage(int page, int pageSize);

    /**
     * 添加
     *
     * @param user
     * @return
     */
    int add(User user) throws UserException;

    /**
     * 删除账号
     *
     * @param userId
     * @return
     */
    int delete(String userId);

    /**
     * 批量删除
     *
     * @param userIds 用户id
     * @return
     */
    int delete(String[] userIds);

    /**
     * 更新
     *
     * @param user
     * @return
     */
    int update(User user) throws UserException;

    /**
     * 根据单位查询账号信息
     *
     * @param orgId 单位id
     * @return
     */
    List<User> findByOrg(Integer orgId);

    /**
     * 获取账号所属单位和其下属单位
     *
     * @param userId   用户id
     * @param userRole 用户角色
     * @return 树形结构查询结果
     */
    List<TreeVo> getByUserRole(String userId, Integer userRole);

    /**
     * 查询可选的任务接收人
     *
     * @param userId   当前用户id
     * @param userRole 用户角色
     * @return 查询结果
     */
    List<ReceiversVo> findReceivers(String userId, Integer userRole);

    /**
     * 用户自行修改密码
     *
     * @param userId      用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    int updatePassword(String userId, String oldPassword, String newPassword) throws UserException;

    /**
     * 系统管理员修改密码
     *
     * @param userId   用户id
     * @param password 新密码
     * @return
     */
    int resetPassword(String userId, String password);
}
