package com.mt.government.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author administered
 * @date 2020-03-14 09:10
 */
@Data
@NoArgsConstructor
public class Receivers implements Serializable {
    private String id;
    private String name;
}
