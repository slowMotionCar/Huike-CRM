package com.huike.report.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description TODO
 * @Author itheima
 * @Date 2023-07-11 00:44
 */
@Data
@ApiModel("销售统计归属部门数据对象")
public class ReportSalesDeptStatisticsListData {

    @ApiModelProperty("部门id")
    private Long dept_id;

    @ApiModelProperty("合同数量")
    private Integer num;

    @ApiModelProperty("成交金额")
    private Float total_amount;

    @ApiModelProperty("部门名称")
    private String deptName;
}
