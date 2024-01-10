package com.huike.clues.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.SysNotice;
import com.huike.common.core.page.TableDataInfo;

import java.util.List;

/**
 * @Description SysNoticeService
 * @Author Leezi
 * @Date 2023-10-16
 */
public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 获取系统提醒下拉列表
     * @param status
     * @return
     */
    List<SysNotice> findByStatus(Integer status);

    /**
     * 获取系统提醒分页列表
     *
     * @param pageSize
     * @param pageNum
     * @param status
     * @return
     */
    TableDataInfo<List<SysNotice>> findPage(Integer status, Integer pageNum, Integer pageSize);

    /**
     * 通过id查询信息
     * @param noticeId
     * @return
     */
    SysNotice findById(Long noticeId);

    /**
     * 未读变已读
     * @param noticeId
     */
    void updateStatus(Long noticeId);
}
