package com.huike.clues.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("伪线索跟踪记录原因对象")
public class FalseClueVo {

    @ApiModelProperty("原因")
    private String reason;

    @ApiModelProperty("跟进记录")
    private String remark;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
