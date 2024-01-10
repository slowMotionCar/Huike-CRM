package com.huike.web.controller.monitor;

import com.huike.clues.domain.dto.SysOperLogDTO;
import com.huike.clues.domain.dto.SysQueryforDictDataDTO;
import com.huike.clues.domain.dto.SysQueryforOperLogDTO;
import com.huike.clues.service.SysOperLogService;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description 系统操作日志
 * @Date 2023-10-10
 * @Creater: 卓志霖
 */

@Api(tags = "系统操作日志接口")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/monitor/operlog")
public class SysOperlogController {

    private final SysOperLogService sysOperLogService;


    // TODO 关键字?
    /**
     * 分页查询系统操作日志
     * @param sysQueryforOperLogDTO
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("分页查询系统操作日志接口")
    public TableDataInfo getLog(SysQueryforOperLogDTO sysQueryforOperLogDTO){

        TableDataInfo tableDataInfo = sysOperLogService.getLog(sysQueryforOperLogDTO);

        return tableDataInfo;
    }


    /**
     * 操作日志删除
     * @param operIds
     * @return
     */
    @ApiOperation("操作日志删除接口")
    @DeleteMapping("/{operIds}")
    public AjaxResult deleteLog(@PathVariable List<Integer> operIds){

        log.info("操作日志删除接口{}",operIds);
        sysOperLogService.deleteLog(operIds);

        return AjaxResult.success();
    }

    /**
     * 操作日志清空
     * @return
     */
    @ApiOperation("操作日志删除接口")
    @DeleteMapping("/clean")
    public AjaxResult cleanLog(){

        log.info("清空操作日志接口");
        sysOperLogService.cleanLog();

        return AjaxResult.success();
    }


    //TODo 下载问题
    /**
     * 导出数据
     *
     * @param response
     * @return
     */
    @ApiOperation("导出数据接口")
    @GetMapping("/export")
    public AjaxResult getExport(HttpServletResponse response) {

        log.info("导出数据");
        sysOperLogService.getExport(response);
        return AjaxResult.success("数据已导出");
    }


}
