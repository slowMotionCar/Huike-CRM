package com.huike.report.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description TODO
 * @Author itheima
 * @Date 2023-07-11 01:08
 */
@Data
@ApiModel("渠道统计数据对象")
public class ReportChannelStatisticsData {

    @ApiModelProperty("渠道名称")
    private String channel;

    @ApiModelProperty("数量")
    private Integer num;
}
