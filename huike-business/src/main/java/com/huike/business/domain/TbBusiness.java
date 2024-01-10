package com.huike.business.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商机
 * @TableName tb_business
 */
@TableName(value = "tb_business")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbBusiness implements Serializable {
    @TableField(exist = false)
    private Integer falseCount;
    @TableField(exist = false)
    private String owner;
    /**
     * 商机id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 省
     */
    private String provinces;

    /**
     * 区
     */
    private String city;

    /**
     * 性別
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 微信
     */
    private String weixin;

    /**
     * qq
     */
    private String qq;

    /**
     * 意向等级
     */
    private String level;

    /**
     * 意向学科
     */
    private String subject;

    /**
     * 课程
     */
    private Long courseId;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 学历
     */
    private String education;

    /**
     * 在职情况
     */
    private String job;

    /**
     * 薪资
     */
    private String salary;

    /**
     * 专业
     */
    private String major;

    /**
     * 希望薪资
     */
    private String expectedSalary;

    /**
     * 学习原因
     */
    private String reasons;

    /**
     * 职业计划
     */
    private String plan;

    /**
     * 计划时间
     */
    private Date planTime;

    /**
     * 其他意向
     */
    private String otherIntention;

    /**
     * 状态(已分配1  进行中2  回收3)
     */
    private String status;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date nextTime;

    /**
     *
     */
    private Date lastUpdateTime;

    /**
     *
     */
    private Long clueId;

    /**
     * 是否转派
     */
    private String transfer;

    /**
     * 备注
     */
    private String remark;

    /**
     * 回收时间
     */
    private Date endTime;

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
        TbBusiness other = (TbBusiness) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
                && (this.getChannel() == null ? other.getChannel() == null : this.getChannel().equals(other.getChannel()))
                && (this.getActivityId() == null ? other.getActivityId() == null : this.getActivityId().equals(other.getActivityId()))
                && (this.getProvinces() == null ? other.getProvinces() == null : this.getProvinces().equals(other.getProvinces()))
                && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
                && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
                && (this.getAge() == null ? other.getAge() == null : this.getAge().equals(other.getAge()))
                && (this.getWeixin() == null ? other.getWeixin() == null : this.getWeixin().equals(other.getWeixin()))
                && (this.getQq() == null ? other.getQq() == null : this.getQq().equals(other.getQq()))
                && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
                && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
                && (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
                && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getOccupation() == null ? other.getOccupation() == null : this.getOccupation().equals(other.getOccupation()))
                && (this.getEducation() == null ? other.getEducation() == null : this.getEducation().equals(other.getEducation()))
                && (this.getJob() == null ? other.getJob() == null : this.getJob().equals(other.getJob()))
                && (this.getSalary() == null ? other.getSalary() == null : this.getSalary().equals(other.getSalary()))
                && (this.getMajor() == null ? other.getMajor() == null : this.getMajor().equals(other.getMajor()))
                && (this.getExpectedSalary() == null ? other.getExpectedSalary() == null : this.getExpectedSalary().equals(other.getExpectedSalary()))
                && (this.getReasons() == null ? other.getReasons() == null : this.getReasons().equals(other.getReasons()))
                && (this.getPlan() == null ? other.getPlan() == null : this.getPlan().equals(other.getPlan()))
                && (this.getPlanTime() == null ? other.getPlanTime() == null : this.getPlanTime().equals(other.getPlanTime()))
                && (this.getOtherIntention() == null ? other.getOtherIntention() == null : this.getOtherIntention().equals(other.getOtherIntention()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getNextTime() == null ? other.getNextTime() == null : this.getNextTime().equals(other.getNextTime()))
                && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()))
                && (this.getClueId() == null ? other.getClueId() == null : this.getClueId().equals(other.getClueId()))
                && (this.getTransfer() == null ? other.getTransfer() == null : this.getTransfer().equals(other.getTransfer()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getChannel() == null) ? 0 : getChannel().hashCode());
        result = prime * result + ((getActivityId() == null) ? 0 : getActivityId().hashCode());
        result = prime * result + ((getProvinces() == null) ? 0 : getProvinces().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getAge() == null) ? 0 : getAge().hashCode());
        result = prime * result + ((getWeixin() == null) ? 0 : getWeixin().hashCode());
        result = prime * result + ((getQq() == null) ? 0 : getQq().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getCourseId() == null) ? 0 : getCourseId().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getOccupation() == null) ? 0 : getOccupation().hashCode());
        result = prime * result + ((getEducation() == null) ? 0 : getEducation().hashCode());
        result = prime * result + ((getJob() == null) ? 0 : getJob().hashCode());
        result = prime * result + ((getSalary() == null) ? 0 : getSalary().hashCode());
        result = prime * result + ((getMajor() == null) ? 0 : getMajor().hashCode());
        result = prime * result + ((getExpectedSalary() == null) ? 0 : getExpectedSalary().hashCode());
        result = prime * result + ((getReasons() == null) ? 0 : getReasons().hashCode());
        result = prime * result + ((getPlan() == null) ? 0 : getPlan().hashCode());
        result = prime * result + ((getPlanTime() == null) ? 0 : getPlanTime().hashCode());
        result = prime * result + ((getOtherIntention() == null) ? 0 : getOtherIntention().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getNextTime() == null) ? 0 : getNextTime().hashCode());
        result = prime * result + ((getLastUpdateTime() == null) ? 0 : getLastUpdateTime().hashCode());
        result = prime * result + ((getClueId() == null) ? 0 : getClueId().hashCode());
        result = prime * result + ((getTransfer() == null) ? 0 : getTransfer().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", channel=").append(channel);
        sb.append(", activityId=").append(activityId);
        sb.append(", provinces=").append(provinces);
        sb.append(", city=").append(city);
        sb.append(", sex=").append(sex);
        sb.append(", age=").append(age);
        sb.append(", weixin=").append(weixin);
        sb.append(", qq=").append(qq);
        sb.append(", level=").append(level);
        sb.append(", subject=").append(subject);
        sb.append(", courseId=").append(courseId);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", occupation=").append(occupation);
        sb.append(", education=").append(education);
        sb.append(", job=").append(job);
        sb.append(", salary=").append(salary);
        sb.append(", major=").append(major);
        sb.append(", expectedSalary=").append(expectedSalary);
        sb.append(", reasons=").append(reasons);
        sb.append(", plan=").append(plan);
        sb.append(", planTime=").append(planTime);
        sb.append(", otherIntention=").append(otherIntention);
        sb.append(", status=").append(status);
        sb.append(", nextTime=").append(nextTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", clueId=").append(clueId);
        sb.append(", transfer=").append(transfer);
        sb.append(", remark=").append(remark);
        sb.append(", endTime=").append(endTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}