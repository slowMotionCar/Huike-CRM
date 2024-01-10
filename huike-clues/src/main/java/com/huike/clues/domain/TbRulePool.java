package com.huike.clues.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.huike.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 线索池规则
 * @TableName tb_rule_pool
 */
@TableName(value ="tb_rule_pool")
@Data
public class TbRulePool implements Serializable {
    /**
     * 线程池规则
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 回收时限
     */
    private Integer limitTime;

    /**
     * 回收时限字典
     */
    private String limitTimeType;

    /**
     * 警告时间
     */
    private Integer warnTime;

    /**
     * 警告时间字典单位类型
     */
    private String warnTimeType;

    /**
     * 重复捞取时间
     */
    private Integer repeatGetTime;

    /**
     * 重复捞取时间字典
     */
    private String repeatType;

    /**
     * 最大保有量
     */
    private Integer maxNunmber;

    /**
     * 0 线索 1 商机
     */
    private String type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}