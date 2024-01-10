package com.huike.clues.domain.dto;

/**
 * @Description TbFalseClue
 * @Author Leezi
 * @Date 2023-10-17
 */
public class TbFalseClue {
    /**
     * 原因
     */
    private String reason;
    /**
     * 内容
     */
    private String remark;

    public TbFalseClue(String reason, String remark) {
        this.reason = reason;
        this.remark = remark;
    }

    public TbFalseClue() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
