package com.huike.clues.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("首页-转换龙虎榜参数对象")
public class IndexStatisticsVo {

    @ApiModelProperty("开始日期")
    private String beginCreateTime;

    @ApiModelProperty("结束日期")
    private String endCreateTime;

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("部门id数组")
    private Long[] deptIds;

    public Long[] getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(Long[] deptIds) {
        this.deptIds = deptIds;
    }

    public String getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(String beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
