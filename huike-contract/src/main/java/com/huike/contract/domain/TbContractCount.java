package com.huike.contract.domain;

import lombok.Data;

/**
 * @Description TbContractCount
 * @Author Leezi
 * @Date 2023-10-18
 */
@Data
public class TbContractCount {
    private Integer deptId;
    private String channel;
    private Double total_Amount;
    private String deptName;
    private Integer num;
    private String createBy;
}
