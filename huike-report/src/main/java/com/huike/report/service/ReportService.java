package com.huike.report.service;

import com.huike.report.domain.vo.PageListVO;

import java.util.Date;
import java.util.List;

/**
 * @Description ReportService
 * @Author Leezi
 * @Date 2023-10-18
 */
public interface ReportService {
    /**
     * ## 渠道统计-活动统计
     *
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    List<PageListVO> pageList(Date beginCreateTime, Date endCreateTime);
}
