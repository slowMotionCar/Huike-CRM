package com.huike.contract.domain;

import lombok.Data;

import java.util.List;

/**
 * @Description Report
 * @Author Leezi
 * @Date 2023-10-18
 */
@Data
public class TbContractReport<T> {
    List<String> xAxis;
    List<UserNumber<T>> series;
}
