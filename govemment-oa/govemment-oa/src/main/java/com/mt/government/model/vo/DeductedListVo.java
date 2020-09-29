package com.mt.government.model.vo;

import lombok.Data;

/**
 * 扣分列表视图
 *
 * @author g
 * @date 2019-11-11 14:18
 */
@Data
public class DeductedListVo {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 单位名称
     */
    private String orgName;
    /**
     * 总扣分
     */
    private Integer point;
}
