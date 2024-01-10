package com.huike.report.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel("折线图数据VO")
public class LineSeriesVo {

    @ApiModelProperty("统计名称，示例：新增客户数")
    private String name;

    @ApiModelProperty("数据列表，示例：每天数量的列表，1,2,3,...")
    private List<Object> data =new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
