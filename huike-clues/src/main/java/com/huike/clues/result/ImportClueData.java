package com.huike.clues.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description TODO
 * @Author itheima
 * @Date 2023-07-10 10:15
 */

@ApiModel("批量导入线索结果汇总数据对象")
@Data
public class ImportClueData {

    @ApiModelProperty("批量导入成功条数")
    private Integer successNum;

    @ApiModelProperty("批量导入失败条数")
    private Integer failureNum;
}
