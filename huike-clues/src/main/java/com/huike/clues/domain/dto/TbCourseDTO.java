package com.huike.clues.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.huike.common.annotation.Excel;
import com.huike.common.core.domain.BaseEntity;

/**
 * 课程管理对象 tb_course
 *
 * @author ruoyi
 * @date 2021-04-02
 */
@ApiModel("课程对象")
public class TbCourseDTO extends BaseEntity
{
   /* private Integer pageNum;

    private Integer pageSize;//手动添加的页码和页数*/

   /* public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }*/

    private static final long serialVersionUID = 1L;

    /** 课程id */
    @ApiModelProperty("课程id")
    private Long id;

    /** 课程名称 */
    @ApiModelProperty("课程名称")
    @Excel(name = "课程名称")
    private String name;

    /** 课程编码 */
    @ApiModelProperty("课程编码")
    private String code;

    /** 课程学科 */
    @ApiModelProperty("课程学科")
    @Excel(name = "课程学科")
    private String subject;

    /** 价格 */
    @ApiModelProperty("价格")
    @Excel(name = "价格")
    private Integer price;

    /** 适用人群 */
    @ApiModelProperty("适用人群")
    @Excel(name = "适用人群")
    private String applicablePerson;

    /** 课程描述信息 */
    @ApiModelProperty("课程描述信息")
    @Excel(name = "课程描述信息")
    private String info;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSubject()
    {
        return subject;
    }
    public void setPrice(Integer price)
    {
        this.price = price;
    }

    public Integer getPrice()
    {
        return price;
    }
    public void setApplicablePerson(String applicablePerson)
    {
        this.applicablePerson = applicablePerson;
    }

    public String getApplicablePerson()
    {
        return applicablePerson;
    }
    public void setInfo(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("subject", getSubject())
                .append("price", getPrice())
                .append("applicablePerson", getApplicablePerson())
                .append("info", getInfo())
                .append("createTime", getCreateTime())
                .toString();
    }
}