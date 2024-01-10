package com.huike.clues.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 线索跟进记录
 * @TableName tb_clue_track_record
 */
@TableName(value ="tb_clue_track_record")
@Data
public class TbClueTrackRecord implements Serializable {
    /**
     * 任务id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 线索id
     */
    private Long clueId; //原来是String

    /**
     * 跟进人
     */
    private String createBy;

    /**
     * 意向等级
     */
    private String subject;

    /**
     * 跟进记录
     */
    private String record;

    /**
     * 意向等级
     */
    private String level;

    /**
     * 跟进时间
     */
    private Date createTime;

    /**
     * 0 正常跟进记录 1 伪线索
     */
    private String type;

    /**
     * 原因
     */
    private String falseReason;

    /**
     * 
     */
    private Date nextTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}