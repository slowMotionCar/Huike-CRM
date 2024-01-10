package com.huike.business.domain.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TbBusinessGainDTO
 * @Author FangYiHeng
 * @Date 2023-10-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbBusinessGainDTO {
    private Integer[] ids;
    private Integer userId;
}
