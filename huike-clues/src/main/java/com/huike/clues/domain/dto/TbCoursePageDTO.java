package com.huike.clues.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class TbCoursePageDTO {
    private Integer pageNum=1;//测试先给默认值
    private Integer pageSize=10;//测试先给默认值
    private Integer code;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginCreateTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endCreateTime;
    private String name;
    private String applicablePerson;
    private String subject;
}
