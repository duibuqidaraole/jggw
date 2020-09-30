package com.mt.government.mapper;

import com.mt.government.model.User;
import com.mt.government.common.config.MyMapper;
import com.mt.government.model.vo.TreeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface UserMapper extends MyMapper<User> {
    /**
     * 获取用户id和名称
     *
     * @return
     */
    List<Map<String, String>> selectUserIdAndOrgName();

    /**
     * 根据单位获取用户
     *
     * @return 用户信息
     */
    User findUserByOrdId(Integer ordId);

    /**
     * 查询所有用户id
     *
     * @return
     */
    List<String> selectAllUserId();

    /**
     * 查询已分配账号的单位以树形结构返回
     *
     * @param parentId 上级单位id
     */
    List<TreeVo> selectAssignedOrgAsTree(@Param("parentId") Integer parentId);

    /**
     * 更新换届提醒
     *
     * @param flag 提醒状态
     * @param userId 更新用户的id
     * @return 更新结果
     */
    int updateQuantitativeFlag(@Param("flag") Integer flag, @Param("userId") String userId);

    /**
     *  查询出所有的每月党费信息
     * @return
     */
    List<Map> selectAllDue();

    String selectOrganIdByUserId(@Param("userId") String userId);
}
