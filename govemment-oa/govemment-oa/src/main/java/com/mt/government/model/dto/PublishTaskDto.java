package com.mt.government.model.dto;

import com.mt.government.model.CommonTaskInfo;
import lombok.Data;

/**
 * @author administered
 * @date 2020-03-14 15:54
 */
@Data
public class PublishTaskDto {
    private CommonTaskInfo commonTaskInfo;
    private Receivers[] receivers;
}
