package com.huike.clues.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description TODO
 * @Author itheima
 * @Date 2023-07-10 02:44
 */
@Data
@ApiModel("活动状态统计对象")
public class TableDataInfoActivityParams {

    @ApiModelProperty("审核状态个数")
    private Integer toAudit;

    @ApiModelProperty("通过状态个数")
    private Integer pass;

    @ApiModelProperty("拒绝状态个数")
    private Integer reject;

    @ApiModelProperty("完成状态个数")
    private Integer finish;
}
