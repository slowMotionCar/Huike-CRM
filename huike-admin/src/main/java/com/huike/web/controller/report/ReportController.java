package com.huike.web.controller.report;

import com.huike.clues.service.SysDeptService;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.page.TableDataInfo;
import com.huike.contract.domain.TbContractCount;
import com.huike.contract.domain.TbContractReport;
import com.huike.contract.domain.TbContract;
import com.huike.contract.service.TbContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Description 统计分析相关接口
 * @Date 2023-10-10
 */
@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController extends BaseController {

    @Autowired
    private TbContractService tbContractService;
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * ## 渠道统计-活动统计
     *
     * @return
     */
    @GetMapping("/contractStatisticsList")
    public TableDataInfo pageList(Date beginCreateTime,
                                  Date endCreateTime,
                                  String createBy,
                                  Long deptId,
                                  Long channel
    ) {
        List<Long> ids = sysDeptService.findBydeptId(deptId);
        List<TbContract> list = tbContractService.pageList(beginCreateTime, endCreateTime, createBy, ids, channel);
        return getDataTable(list);
    }

    /**
     *客户统计新增客户数报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/contractStatistics/{beginCreateTime}/{endCreateTime}")
    public TbContractReport<Integer> contractStatistics(@PathVariable Date beginCreateTime,
                                               @PathVariable Date endCreateTime) {
        return tbContractService.contractStatistics(beginCreateTime, endCreateTime);
    }

    /**
     *## 线索统计新增线索数量报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/salesStatistics/{beginCreateTime}/{endCreateTime}")
    public TbContractReport<Double> salesStatistics(@PathVariable Date beginCreateTime,
                                            @PathVariable Date endCreateTime) {
        return tbContractService.salesStatistics(beginCreateTime, endCreateTime);
    }

    /**
     * ## 销售统计归属部门明细列表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/deptStatisticsList/{beginCreateTime}/{endCreateTime}")
    public TableDataInfo deptStatisticsList(Date beginCreateTime,
                                               Date endCreateTime) {

        List<TbContractCount> list = tbContractService.pageListByDept(beginCreateTime, endCreateTime);
        return getDataTable(list);
    }

    /**
     * ## 销售统计归属渠道明细列表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/channelStatisticsList/{beginCreateTime}/{endCreateTime}")
    public TableDataInfo channelStatisticsList(Date beginCreateTime,
                                               Date endCreateTime) {

        List<TbContractCount> list = tbContractService.channelStatisticsList(beginCreateTime, endCreateTime);
        return getDataTable(list);
    }

    /**
     * ## ## 销售统计归属人报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/ownerShipStatisticsList/{beginCreateTime}/{endCreateTime}")
    public TableDataInfo ownerShipStatisticsList(Date beginCreateTime,
                                               Date endCreateTime) {

        List<TbContractCount> list = tbContractService.ownerShipStatisticsList(beginCreateTime, endCreateTime);
        return getDataTable(list);
    }

    @GetMapping("/activityStatistics/{beginCreateTime}/{endCreateTime}")
    public TableDataInfo activityStatistics() {
        return null;
    }
}
