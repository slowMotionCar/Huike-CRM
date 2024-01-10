package com.huike.contract.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 合同
 * @TableName tb_contract
 */
@TableName(value ="tb_contract")
@Data
public class TbContract implements Serializable {
    /**
     * 合同id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 意向学科
     */
    private String subject;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 
     */
    private String channel;

    /**
     * 状态(待审核1，已通过2，已驳回3 全部完成4)
     */
    private String status;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 
     */
    private Long deptId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 完成时间
     */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finisTime;


    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 订单价格
     */
    private Double contractOrder;

    /**
     *
     */
    @TableField(exist = false)
    private Double order;

    /**
     * 折扣类型
     */
    private String discountType;

    /**
     * 课程价格
     */
    private Double coursePrice;

    /**
     * 
     */
    private String processInstanceId;

    /**
     * 
     */
    private Long businessId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}