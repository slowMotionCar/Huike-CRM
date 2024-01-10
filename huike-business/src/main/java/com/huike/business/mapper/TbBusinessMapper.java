package com.huike.business.mapper;

import com.huike.business.domain.TbBusiness;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
* @author 93238
* @description 针对表【tb_business(商机)】的数据库操作Mapper
* @createDate 2023-10-12 06:08:37
* @Entity com.huike.business.domain.TbBusiness
*/
@Mapper
public interface TbBusinessMapper extends BaseMapper<TbBusiness> {

    /**
     * 根据id查询商机表
     * @param id
     * @return
     */
    TbBusiness selectBusinessById(String id);

    void updateBusinessEndTimeById(Long assignId, Date endDate);
}




