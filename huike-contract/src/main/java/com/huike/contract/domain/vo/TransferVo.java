package com.huike.contract.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("转派对象")
public class TransferVo {


    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("手机号")
    private String phonenumber;
    @ApiModelProperty("线索数量")
    private int clueNum;
    @ApiModelProperty("商机数量")
    private int businessNum;

    @ApiModelProperty("用户创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getClueNum() {
        return clueNum;
    }

    public void setClueNum(int clueNum) {
        this.clueNum = clueNum;
    }

    public int getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(int businessNum) {
        this.businessNum = businessNum;
    }
}
