package com.huike.contract.domain;

import lombok.Data;

import java.util.List;

/**
 * @Description UserNumber
 * @Author Leezi
 * @Date 2023-10-18
 */
@Data
public class UserNumber<T> {
    private String name;
    private List<T>  data;
}
