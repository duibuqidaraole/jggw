package com.mt.government.model.vo;

import com.mt.government.model.Deducted;
import lombok.Data;

/**
 * 扣分详情视图
 *
 * @author g
 * @date 2019-11-11 16:57
 */
@Data
public class DeductedDetailVo extends Deducted {
    /**
     * 任务编号
     */
    private String taskNo;
    /**
     * 扣分任务标题
     */
    private String taskTitle;
}
