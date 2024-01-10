package com.huike.web.controller.business;

import com.huike.business.domain.dto.TbBusinessDTODTO;
import com.huike.business.domain.dto.TbBusinessTrackRecordDTO;
import com.huike.business.domain.vo.BusinessTrackVo;
import com.huike.business.service.TbBusinessTrackRecordService;
import com.huike.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 商机跟进记录Controller
 * @date 2023-04-28
 */
@RestController
@RequestMapping("/business/record")
@Api(tags = "商机相关接口")
@Slf4j
public class TbBusinessTrackRecordController {

    @Resource
    private TbBusinessTrackRecordService tbBusinessTrackRecordService;

    /**
     * 处理“新增商机跟进记录”请求
     * @param tbBusinessTrackRecordDTO
     * @return
     */
    @ApiOperation("新增商机跟进记录接口")
    @PostMapping
    public AjaxResult addBusinessTrackRecord(@RequestBody TbBusinessTrackRecordDTO tbBusinessTrackRecordDTO) {
        log.info("执行新增商机跟进记录接口：{}", tbBusinessTrackRecordDTO);
        tbBusinessTrackRecordService.addBusinessTrackRecord(tbBusinessTrackRecordDTO);
        return AjaxResult.success();
    }

    /**
     * 处理”查询商机跟进记录“请求
     * @param businessId
     * @return
     */
    @ApiOperation("查询商机跟进记录接口")
    @GetMapping("/list")
    public AjaxResult selectBusinessTrackRecord(Long businessId) {
        log.info("执行查询商机跟进记录接口：{}", businessId);
        BusinessTrackVo businessTrackVo = tbBusinessTrackRecordService.selectBusinessTrackRecord(businessId);
        return AjaxResult.success(businessTrackVo);
    }

    /**
     * 处理“获取商机跟进记录详细信息”请求
     * @param businessId
     * @return
     */
    @ApiOperation("获取商机跟进记录详细信息接口")
    @GetMapping("/{id}")
    public AjaxResult selectBusinessTrackRecordDetail(@PathVariable Long businessId) {
        log.info("执行获取商机跟进记录详细信息接口：{}", businessId);
        BusinessTrackVo businessTrackVo = tbBusinessTrackRecordService.selectBusinessTrackRecordDetail(businessId);
        return AjaxResult.success(businessTrackVo);
    }

}
