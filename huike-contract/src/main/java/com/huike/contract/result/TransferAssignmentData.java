package com.huike.contract.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description TODO
 * @Author itheima
 * @Date 2023-07-10 14:57
 */
@Data
@ApiModel("转派处理信息对象")
public class TransferAssignmentData {

    @ApiModelProperty("转派处理结果是否成功")
    private Boolean flag;

    @ApiModelProperty("失败消息")
    private String msg;

}
