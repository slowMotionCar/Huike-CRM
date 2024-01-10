package com.huike.clues.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.SysNotice;
import com.huike.clues.mapper.SysNoticeMapper;
import com.huike.clues.service.SysNoticeService;
import com.huike.common.constant.HttpStatus;
import com.huike.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description SysNoticeServiceImpl
 * @Author Leezi
 * @Date 2023-10-16
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
    @Autowired
    private SysNoticeMapper sysNoticeMapper;

    /**
     * 获取系统提醒下拉列表
     * @param status
     * @return
     */
    @Override
    public List<SysNotice> findByStatus(Integer status) {
        QueryWrapper<SysNotice> qw = new QueryWrapper<>();
        qw.lambda().eq(SysNotice::getStatus, status);
        return sysNoticeMapper.selectList(qw);
    }


    /**
     * 获取系统提醒分页列表
     *
     * @param pageSize
     * @param pageNum
     * @param status
     * @return
     */
    @Override
    public TableDataInfo<List<SysNotice>> findPage(Integer status, Integer pageNum, Integer pageSize) {
        if (pageNum == null) {
            pageNum = 0;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SysNotice> page = new Page<>(pageNum, pageSize);

        QueryWrapper<SysNotice> qw = new QueryWrapper<>();
        qw.lambda().eq(SysNotice::getStatus, status);

        Page<SysNotice> sysNoticePage = sysNoticeMapper.selectPage(page, qw);
        TableDataInfo<List<SysNotice>> td = new TableDataInfo<>();
        td.setRows(sysNoticePage.getRecords());
        td.setTotal(sysNoticePage.getTotal());
        td.setCode(HttpStatus.SUCCESS);
        td.setMsg("success");
        return td;
    }


    /**
     * 获取详情信息
     * @param noticeId
     * @return
     */
    @Override
    public SysNotice findById(Long noticeId) {

        return sysNoticeMapper.selectById(noticeId);
    }

    /**
     * 未读变已读
     * @param noticeId
     */
    @Override
    public void updateStatus(Long noticeId) {
        SysNotice sysNotice = sysNoticeMapper.selectById(noticeId);
        sysNotice.setStatus("1");
        sysNoticeMapper.updateById(sysNotice);
    }
}
