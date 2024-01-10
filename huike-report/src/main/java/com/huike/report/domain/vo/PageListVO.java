package com.huike.report.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Description PageListVO
 * @Author Leezi
 * @Date 2023-10-18
 */
@Data
public class PageListVO {
    private Long activityId;
    private Long BusinessId;
    private String channel;
    private String courseId;
    private Double coursePrice;
    private String createBy;
    private Date createTime;
    private Long deptId;
    private String discountType;
    private String fileName;
    private Long id;
    private String name;
    private Integer order;
    private String phone;
    private String status;
    private String subject;
    private String updateBy;
    private Date updateTime;

}
