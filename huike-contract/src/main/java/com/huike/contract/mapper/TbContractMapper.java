package com.huike.contract.mapper;

import com.huike.contract.domain.TbContract;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.contract.domain.TbContractCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
* @author 93238
* @description 针对表【tb_contract(合同)】的数据库操作Mapper
* @createDate 2023-10-12 06:42:55
* @Entity com.huike.contract.domain.TbContract
*/
@Mapper
public interface TbContractMapper extends BaseMapper<TbContract> {
    /**
     * ## 渠道统计-活动统计
     * @param beginCreateTime
     * @param endCreateTime
     * @param ids
     * @param channel
     * @param createBy
     * @return
     */
    List<TbContract> selectContract(String beginCreateTime, String endCreateTime, List<Long> ids, Long channel, String createBy);

    /**
     *## 销售统计归属部门明细列表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    List<TbContractCount> selectCountByDept(Date beginCreateTime, Date endCreateTime);


    /**
     *## 销售统计归属部门明细列表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    List<TbContractCount> selectCountByActivity(Date beginCreateTime, Date endCreateTime);

    /**
     *## 销售统计归属人报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    List<TbContractCount> ownerShipStatisticsList(Date beginCreateTime, Date endCreateTime);

}




