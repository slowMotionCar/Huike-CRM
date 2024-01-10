package com.huike.contract.service;

import com.huike.common.core.page.TableDataInfo;
import com.huike.contract.domain.TbContract;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.contract.domain.TbContractCount;
import com.huike.contract.domain.TbContractReport;
import com.huike.contract.domain.dto.PageTbContractDTO;
import com.huike.contract.domain.dto.TbContractDTO;

import java.util.Date;
import java.util.List;

/**
* @author 93238
* @description 针对表【tb_contract(合同)】的数据库操作Service
* @createDate 2023-10-12 06:42:55
*/
public interface TbContractService extends IService<TbContract> {

    /**
     * 新增合同
     * @param dto
     */
    void insertContract(TbContractDTO dto);

    /**
     * 合同分页查询
     * @param dto
     * @return
     */
    TableDataInfo<List<TbContract>> selectTbContractList(PageTbContractDTO dto);

    /**
     * 根据id查询合同
     * @param id
     * @return
     */
    TbContract selectDetailById(Integer id);

    /**
     * 根据id查询合同2
     * @param id
     * @return
     */
    TbContract selectTbContractById(String id);

    /**
     * 商机转合同
     * @param id
     */
    void changeContract(Integer id, TbContractDTO dto);

    /**
     * ## 渠道统计-活动统计
     *
     * @param beginCreateTime
     * @param endCreateTime
     * @param createBy
     * @param ids
     * @param channel
     * @return
     */
    List<TbContract> pageList(Date beginCreateTime, Date endCreateTime, String createBy, List<Long> ids, Long channel);

    /**
     * 客户统计新增客户数报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    TbContractReport<Integer> contractStatistics(Date beginCreateTime, Date endCreateTime);

    /**
     * ## 线索统计新增线索数量报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    TbContractReport<Double> salesStatistics(Date beginCreateTime, Date endCreateTime);

    /**
     * ## 销售统计归属部门明细列表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    List<TbContractCount> pageListByDept(Date beginCreateTime, Date endCreateTime);

    /**
     * ## 销售统计归属渠道明细列表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    List<TbContractCount> channelStatisticsList(Date beginCreateTime, Date endCreateTime);

    /**
     * ## 销售统计归属人报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    List<TbContractCount> ownerShipStatisticsList(Date beginCreateTime, Date endCreateTime);
}
