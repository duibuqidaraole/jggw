package com.mt.government.model.vo;

import com.mt.government.model.TaskUserRelationship;
import lombok.Data;

import java.util.Date;

/**
 * 用户接收到的任务列表视图对象
 *
 * @author g
 * @date 2019-11-13 17:09
 */
@Data
public class MyTaskListVo extends TaskUserRelationship {
    private String taskNo;
    private String commonTaskTitle;
    private Date commonTaskCreateTime;
    private String commonTaskPublisher;
    private String orgName;
}
