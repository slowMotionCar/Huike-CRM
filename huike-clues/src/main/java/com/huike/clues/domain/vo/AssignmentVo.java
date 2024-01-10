package com.huike.clues.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("分配记录请求参数对象")
public class AssignmentVo {

    @ApiModelProperty("分配的线索id数组")
    private Long[] ids;
    @ApiModelProperty("分配给目标用户id")
    private Long userId;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
