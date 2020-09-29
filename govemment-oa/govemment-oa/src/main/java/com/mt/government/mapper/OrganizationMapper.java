package com.mt.government.mapper;

import com.mt.government.model.Organization;
import com.mt.government.model.vo.ReceiversVo;
import com.mt.government.common.config.MyMapper;
import com.mt.government.model.vo.TreeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrganizationMapper extends MyMapper<Organization> {
    List<Organization> findByParentId(@Param("parentId") Integer parentId);

    /**
     * 获取任务接收人列表
     *
     * @param userId   任务id
     * @param parentId
     * @param userRole
     * @return
     */
    List<ReceiversVo> findUserIdAndChildren(String userId, Integer parentId, Integer userRole);

    /**
     * 查询下级单位
     *
     * @param userId
     * @param parentId
     * @param userRole
     * @return
     */
    List<ReceiversVo> selectByUserRole(@Param("userId") String userId, @Param("parentId") Integer parentId, @Param("userRole") Integer userRole);

    /**
     * 查询该单位下级没有分配账号的单位
     *
     * @param orgId 单位id
     * @return 包含当前单位的单位列表
     */
    List<Organization> selectNoAccountOrg(@Param("orgId") Integer orgId);

    /**
     * @param parentId
     * @return
     */
    List<TreeVo> selectByParentIdAsTreeNode(@Param("parentId") Integer parentId);
}