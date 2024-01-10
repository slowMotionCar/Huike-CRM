package com.huike.web.controller.clues;

import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.dto.TbActivityDTO;
import com.huike.clues.result.TableDataInfoActivityList;
import com.huike.clues.service.TbActivityService;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.PageDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 活动管理Controller
 * @date 2023-04-01
 */
@RestController
@RequestMapping("/clues/activity")
@Slf4j
public class TbActivityController {
    @Autowired
    private TbActivityService tbActivityService;

    /**
     * 新增活动管理
     * @param dto
     * @return
     */
    @PostMapping()
    public AjaxResult insert(@RequestBody TbActivityDTO dto) {
        log.info("\ndto{}\n", dto);
        tbActivityService.insert(dto);
        return AjaxResult.success();
    }

    /**
     * 修改活动管理
     * @return
     */
    @PutMapping()
    public AjaxResult update(@RequestBody TbActivityDTO dto){
        log.info("\n{}\n", dto);
        tbActivityService.updateActivity(dto);
        return AjaxResult.success();
    }

    /**
     * 查询活动管理列表
     *
     * @return
     */
    // todo 未完成 参数部分时间不知道怎么处理
    @GetMapping("/list")
    public TableDataInfoActivityList<List<TbActivity>> list(PageDomain domain, String code, Integer channel) {
        return tbActivityService.find(domain, code, channel);
    }

    /**
     * 获取状态为为2的渠道活动列表
     * @return
     */
    @GetMapping("/listselect/{channel}")
    public AjaxResult<List<TbActivity>> findStatus(@PathVariable(name = "channel") Integer status) {
        List<TbActivity> list = tbActivityService.findStatus(status);
        return AjaxResult.success(list);
    }

    /**
     * 删除活动管理
     * @return
     */
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable(name = "ids") Long ids) {
        tbActivityService.delete(ids);
        return AjaxResult.success("删除成功");
    }

    /**
     * 获取活动管理详细信息
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult<TbActivity> findById(@PathVariable(name = "id") Long id) {
        TbActivity tbActivity = tbActivityService.findById(id);
        return AjaxResult.success(tbActivity);
    }
}
