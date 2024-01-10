package com.huike.clues.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class TbCoursePageDTOTemp {
    private Integer pageNum=1;//测试先给默认值
    private Integer pageSize=10;//测试先给默认值
    private Integer code;
    private Map<String, String> params;
    private String name;
    private String applicablePerson;
    private String subject;
}
