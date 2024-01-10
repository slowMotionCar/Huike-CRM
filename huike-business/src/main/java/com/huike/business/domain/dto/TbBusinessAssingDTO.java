package com.huike.business.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TbBusinessAssingDTO
 * @Author FangYiHeng
 * @Date 2023-10-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbBusinessAssingDTO {

    private Long[] ids;
    private Long userId;

}
