package com.huike.business.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.huike.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 商机跟进记录
 * @TableName tb_business_track_record
 */
@TableName(value ="tb_business_track_record")
@Data
public class TbBusinessTrackRecord implements Serializable {
    /**
     * 任务id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商机id
     */
    private String businessId;

    /**
     * 跟进人
     */
    private String createBy;

    /**
     * 沟通重点
     */
    private String keyItems;

    /**
     * 沟通纪要
     */
    private String record;

    /**
     * 跟进时间
     */
    private Date createTime;

    /**
     * 跟进状态
     */
    private String trackStatus;

    /**
     * 
     */
    private Date nextTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TbBusinessTrackRecord other = (TbBusinessTrackRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBusinessId() == null ? other.getBusinessId() == null : this.getBusinessId().equals(other.getBusinessId()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getKeyItems() == null ? other.getKeyItems() == null : this.getKeyItems().equals(other.getKeyItems()))
            && (this.getRecord() == null ? other.getRecord() == null : this.getRecord().equals(other.getRecord()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getTrackStatus() == null ? other.getTrackStatus() == null : this.getTrackStatus().equals(other.getTrackStatus()))
            && (this.getNextTime() == null ? other.getNextTime() == null : this.getNextTime().equals(other.getNextTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBusinessId() == null) ? 0 : getBusinessId().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getKeyItems() == null) ? 0 : getKeyItems().hashCode());
        result = prime * result + ((getRecord() == null) ? 0 : getRecord().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getTrackStatus() == null) ? 0 : getTrackStatus().hashCode());
        result = prime * result + ((getNextTime() == null) ? 0 : getNextTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", businessId=").append(businessId);
        sb.append(", createBy=").append(createBy);
        sb.append(", keyItems=").append(keyItems);
        sb.append(", record=").append(record);
        sb.append(", createTime=").append(createTime);
        sb.append(", trackStatus=").append(trackStatus);
        sb.append(", nextTime=").append(nextTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}