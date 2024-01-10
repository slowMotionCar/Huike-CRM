package com.huike.report.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 线索转化龙虎榜对象
 * @Author itheima
 * @Date 2023-07-10 19:32
 */
@Data
@ApiModel("转化龙虎榜对象")
public class ReportIndexStatisticsData {

    @ApiModelProperty("用户姓名")
    private String  create_by;

    @ApiModelProperty("转换个数")
    private Integer num;

    @ApiModelProperty("部门")
    private String deptName;

    @ApiModelProperty("转换率")
    private BigDecimal radio;
}
