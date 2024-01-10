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
@ApiModel("销售统计归属人数据对象")
public class ReportSalesOwnerShipStatisticsListData {

    @ApiModelProperty("归属人")
    private String create_by;

    @ApiModelProperty("合同数量")
    private Integer num;

    @ApiModelProperty("成交金额")
    private Float total_amount;

}
