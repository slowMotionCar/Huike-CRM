package com.huike.report.service.impl;

import com.huike.contract.mapper.TbContractMapper;
import com.huike.report.domain.vo.PageListVO;
import com.huike.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description ReportServiceImpl
 * @Author Leezi
 * @Date 2023-10-18
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private TbContractMapper contractMapper;
    @Override
    public List<PageListVO> pageList(Date beginCreateTime, Date endCreateTime) {
        return null;
    }
}
