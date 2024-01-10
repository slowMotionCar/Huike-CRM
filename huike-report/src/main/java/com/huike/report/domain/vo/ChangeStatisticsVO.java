package com.huike.report.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description ChangeStatisticsVO
 * @Author Zhilin
 * @Date 2023-10-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStatisticsVO {
    private String create_by;
    private String deptName;
    private Integer num;
    private Double radio;
}
