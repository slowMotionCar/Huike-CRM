package com.huike.web.controller.system;

import com.huike.clues.domain.SysNotice;
import com.huike.clues.service.SysNoticeService;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description SysNoticeController
 * @Author Leezi
 * @Date 2023-10-16
 */
@RestController
@RequestMapping("/system/notice")
@Slf4j
public class SysNoticeController {
    @Autowired
    private SysNoticeService sysNoticeService;

    /**
     * 获取系统提醒下拉列表
     * @return
     */
    @GetMapping("/list/{status}")
    public AjaxResult findByStatus(@PathVariable Integer status) {
        List<SysNotice> list =  sysNoticeService.findByStatus(status);
        return AjaxResult.success(list);
    }

    /**
     * 获取系统提醒分页列表
     * @return
     */
    @GetMapping("/pagelist/{status}")
    public TableDataInfo<List<SysNotice>> findPage(@PathVariable Integer status,
                                                   Integer pageNum, Integer pageSize) {
        return sysNoticeService.findPage(status, pageNum, pageSize);
    }

    /**
     * 通过id查询信息
     * @return
     */
    @GetMapping("/{noticeId}")
    public AjaxResult findById(@PathVariable Long noticeId) {
        SysNotice sysNotice = sysNoticeService.findById(noticeId);
        return AjaxResult.success(sysNotice);
    }

    /**
     * 未读变已读
     * @return
     */
    @PutMapping("/{noticeId}")
    public AjaxResult changeStatus(@PathVariable Long noticeId) {
        sysNoticeService.updateStatus(noticeId);
        return AjaxResult.success();
    }
}
