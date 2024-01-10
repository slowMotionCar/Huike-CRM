package com.huike.clues.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description SysQueryforDictDataDTO
 * @Author Zhilin
 * @Date 2023-10-16
 */

@ApiModel("分页查询字典数据对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysQueryforDictDataDTO {

    private Integer pageNum;
    private Integer pageSize;
    private String dictType;
    private String dictLabel;
    private Integer status;



}
