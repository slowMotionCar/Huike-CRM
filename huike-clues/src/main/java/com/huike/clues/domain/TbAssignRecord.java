package com.huike.clues.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分配记录表
 * @TableName tb_assign_record
 */
@TableName(value ="tb_assign_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TbAssignRecord implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联id
     */
    private Long assignId;

    /**
     * 所属人用户id
     */
    private Long userId;

    /**
     * 所属人名称
     */
    private String userName;

    /**
     * 所属人所属组织
     */
    private Long deptId;

    /**
     * 分配时间
     */
    private Date createTime;

    /**
     * 分配人
     */
    private String createBy;

    /**
     * 是否当前最新分配人
     */
    private String latest;

    /**
     * 类型0 线索 1 商机
     */
    private String type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}