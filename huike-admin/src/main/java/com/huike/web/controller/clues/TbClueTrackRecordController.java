package com.huike.web.controller.clues;

import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.domain.dto.TbClueTrackRecordDTO;
import com.huike.clues.service.TbClueTrackRecordService;
import com.huike.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 线索跟进记录Controller
 * @date 2023-04-22
 */
@RestController
@RequestMapping("/clues/record")
@Api(tags = "线索跟进记录管理相关接口")
@Slf4j
public class TbClueTrackRecordController {
    @Autowired
    private TbClueTrackRecordService tbClueTrackRecordService;

    /**
     * 获取线索跟进记录详细信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("获取线索跟进记录详细信息")
    public AjaxResult selectClueTrackRecordDetail(@PathVariable Integer id){
        log.info("线索跟进记录详细信息,id={}",id);
        TbClueTrackRecord tbClueTrackRecord=tbClueTrackRecordService.selectClueTrackRecordDetail(id);

        return AjaxResult.success(tbClueTrackRecord);
    }

    /**
     * 线索跟进记录列表
     * @param clueId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询线索跟进记录列表")
    public AjaxResult selectClueTrackRecordList(Integer clueId){
        log.info("查询线索跟进记录列表，线索id={}",clueId);
       List<TbClueTrackRecord> tbClueTrackRecordList= tbClueTrackRecordService.selectClueTrackRecordList(clueId);
        return AjaxResult.success(tbClueTrackRecordList);
    }

    /**
     * 新增线索跟进记录接口
     * @param tbClueTrackRecordDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增线索跟进记录接口")
    public AjaxResult insertClueTrackRecord(@RequestBody TbClueTrackRecordDTO tbClueTrackRecordDTO){
        log.info("新增线索跟进记录接口，新增内容{}",tbClueTrackRecordDTO);
        tbClueTrackRecordService.insertClueTrackRecord(tbClueTrackRecordDTO);
        return AjaxResult.success();
    }

}
