package com.huike.clues.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Description SysQueryforOperLogDTO
 * @Author Zhilin
 * @Date 2023-10-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysQueryforOperLogDTO {

    private Integer pageNum;
    private Integer pageSize;
    private String keyWord;
    private Map<String,String> params;

}
