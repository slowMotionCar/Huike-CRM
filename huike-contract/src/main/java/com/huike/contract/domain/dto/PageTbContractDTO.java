package com.huike.contract.domain.dto;

import com.huike.common.core.page.PageDomain;

/**
 * @Description PageTbContractDTO
 * @Author xhr
 * @Date 2023-10-15
 */
public class PageTbContractDTO extends PageDomain {
    private String name;
    private String contractNo;
    private String phone;
    private String subject;
    private Long courseId;



    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return contractNo
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * 设置
     * @param contractNo
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * 获取
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 设置
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 获取
     * @return courseId
     */
    public Long getCourseId() {
        return courseId;
    }

    /**
     * 设置
     * @param courseId
     */
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String toString() {
        return "PageTbContractDTO{name = " + name + ", contractNo = " + contractNo + ", phone = " + phone + ", subject = " + subject + ", courseId = " + courseId + "}";
    }
}
