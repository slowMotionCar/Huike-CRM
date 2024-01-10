package com.huike.clues.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * @Description SysQueryforDictTypeDTO
 * @Author Zhilin
 * @Date 2023-10-15
 */
@ApiModel("分页查询字典类型对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysQueryforDictTypeDTO {


    private Integer pageNum;
    private Integer pageSize;
    private String dictName;
    private String dictType;
    private String status;
    private Map<String,String> params;
    // private String  beginTime;
    // private String endTime;

}
