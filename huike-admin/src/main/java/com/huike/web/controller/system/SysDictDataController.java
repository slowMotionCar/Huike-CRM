package com.huike.web.controller.system;

import com.huike.clues.domain.SysDictData;
import com.huike.clues.domain.dto.SysQueryforDictDataDTO;
import com.huike.clues.domain.vo.SysDictDataVO;
import com.huike.clues.result.GetInfoAjaxResult;
import com.huike.clues.service.SysDictDataService;
import com.huike.clues.service.SysDictTypeService;
import com.huike.common.annotation.Log;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysDictDataDTO;
import com.huike.common.core.domain.entity.SysDictTypeDTO;
import com.huike.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description 数据字典数据信息接口
 * @Date 2023-10-10
 * @Creater: 卓志霖
 */
@RestController
@Slf4j
@Api(tags = "字典数据信息相关接口")
@RequiredArgsConstructor
@RequestMapping("/system/dict/data")
public class SysDictDataController {

    private final SysDictDataService sysDictDataService;

    // 字典数据信息

    /**
     * 添加字典信息接口
     *
     * @param sysDictDataDTO
     * @return
     */
    @ApiOperation("添加字典数据信息接口")
    @PostMapping("")
    public AjaxResult addDictType(@RequestBody SysDictDataDTO sysDictDataDTO) {

        log.info("添加字典信息接口{}", sysDictDataDTO);
        sysDictDataService.addDataType(sysDictDataDTO);
        return AjaxResult.success();

    }

    /**
     * 分页查询字典数据信息接口
     *
     * @return
     * @SysDictDataDTO sysDictDataDTO
     */
    @ApiOperation("分页查询字典数据信息接口")
    @GetMapping("/list")
    public TableDataInfo listPage(SysQueryforDictDataDTO sysQueryforDictDataDTO) {

        log.info("分页查询字典数据信息接口{}", sysQueryforDictDataDTO);
        TableDataInfo tableDataInfo = sysDictDataService.listPage(sysQueryforDictDataDTO);
        return tableDataInfo;

    }

    /**
     * 根据字典类型查询字典数据
     * @param dictType
     * @return
     */
    @ApiOperation("根据字典类型查询字典数据接口")
    @GetMapping("/type/{dictType}")
    public AjaxResult getDictDataByDictType(@PathVariable String dictType){

        log.info("根据字典类型{}查询字典数据",dictType);
        List<SysDictDataVO> sysDictDataVO = sysDictDataService.getDictDataByDictType(dictType);

        return AjaxResult.success(sysDictDataVO);
    }

    /**
     * 删除字典数据
     *
     * @param dictCodes
     * @return
     */
    @ApiOperation("删除字典数据接口")
    @DeleteMapping("/{dictCodes}")
    public AjaxResult deleteDictDataByDictCodes(@PathVariable List<Integer> dictCodes) {

        log.info("删除字典数据{}", dictCodes);
        sysDictDataService.deleteDictDataByDictCodes(dictCodes);
        return AjaxResult.success();

    }

    /**
     * 修改字典数据接口
     *
     * @param sysDictDataDTO
     * @return
     */
    @ApiOperation("修改字典数据接口")
    @PutMapping("")
    public AjaxResult updateDictType(@RequestBody SysDictDataDTO sysDictDataDTO) {

        log.info("修改字典数据接口{}", sysDictDataDTO);
        sysDictDataService.updateDictData(sysDictDataDTO);
        return AjaxResult.success();
    }

    /**
     * 查询字典数据详细
     * @param dictCode
     * @return
     */
    @ApiOperation("查询字典数据详细接口")
    @GetMapping("/{dictCode}")
    public AjaxResult getDictDetialByDictCode(@PathVariable Integer dictCode){

        log.info("查询字典数据详细{}",dictCode);
        SysDictDataVO sysDictDataVO = sysDictDataService.getDictDataByDictCode(dictCode);

        return AjaxResult.success(sysDictDataVO);
    }


    // Todo 缺陷文件 (目前只能实现本地导出)

    /**
     * 导出数据
     *
     * @param sysQueryforDictDataDTO
     * @param response
     * @return
     */
    @ApiOperation("导出数据接口")
    @GetMapping("/export")
    public AjaxResult getExport(SysQueryforDictDataDTO sysQueryforDictDataDTO, HttpServletResponse response) {

        log.info("导出数据");
        sysQueryforDictDataDTO.setPageNum(null);
        sysQueryforDictDataDTO.setPageSize(null);
        TableDataInfo tableDataInfo = sysDictDataService.listPage(sysQueryforDictDataDTO);
        List<SysDictData> rows = (List) tableDataInfo.getRows();
        sysDictDataService.getExport(rows, response);
        return AjaxResult.success("数据已导出");
    }

}
