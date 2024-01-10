package com.huike.clues.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 课程管理
 * @TableName tb_course
 */
@TableName(value ="tb_course")
@Data
public class TbCourse implements Serializable {
    /**
     * 课程id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 学科
     */
    private String subject;

    /**
     * 浠锋牸
     */
    private Integer price;

    /**
     * 
     */
    private String applicablePerson;

    /**
     * 课程描述信息
     */
    private String info;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 是否删除 1 是
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}