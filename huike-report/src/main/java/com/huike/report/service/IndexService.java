package com.huike.report.service;

import com.huike.clues.domain.vo.VulnerabilityMapVOnew;
import com.huike.report.domain.vo.ChangeStatisticsVO;
import com.huike.report.domain.vo.IndexBaseInfoVO;
import com.huike.report.domain.vo.IndexTodayInfoVO;
import com.huike.report.domain.vo.IndexTodoInfoVO;

import java.util.List;

/**
 * @Description IndexService
 * @Author Zhilin
 * @Date 2023-10-17
 */
public interface IndexService {

    /**
     * 首页简报
     * @return
     */
    IndexTodayInfoVO getTodayInfo();

    /**
     * 基础数据统计
     * @return
     */
    IndexBaseInfoVO getBaseInfo(String beginCreateTime, String endCreateTime);

    /**
     * 首页--获取待办数据
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    IndexTodoInfoVO getTodoInfo(String beginCreateTime, String endCreateTime);

    List<ChangeStatisticsVO> businessChangeStatistics(String beginCreateTime, String endCreateTime);

    List<ChangeStatisticsVO> salesStatistic(String beginCreateTime, String endCreateTime);

    VulnerabilityMapVOnew getVulnerabilityMap(String beginCreateTime, String endCreateTime);
}
