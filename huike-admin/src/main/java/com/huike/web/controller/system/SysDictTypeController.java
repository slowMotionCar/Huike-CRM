package com.huike.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.huike.clues.domain.dto.SysQueryforDictTypeDTO;
import com.huike.clues.domain.pojo.SysDictType;
import com.huike.clues.domain.vo.SysDictTypeVO;
import com.huike.clues.service.SysDictTypeService;
import com.huike.common.annotation.Log;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysDictTypeDTO;
import com.huike.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description SysDictTypeController
 * @Author Zhilin
 * @Date 2023-10-15
 */
@RestController
@Slf4j
@Api(tags = "数据字典类型信息相关接口")
@RequiredArgsConstructor
@RequestMapping("/system/dict/type")
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;
    @Resource
    private RedisTemplate redisTemplate;

    // 字典数据类型

    /**
     * 添加字典类型接口
     *
     * @param sysDictTypeDTO
     * @return
     */

    @ApiOperation("添加字典类型接口")
    @PostMapping("")
    @Log
    public AjaxResult addDictType(@RequestBody SysDictTypeDTO sysDictTypeDTO) {

        log.info("添加字典类型{}", sysDictTypeDTO);
        sysDictTypeService.addDictType(sysDictTypeDTO);
        return AjaxResult.success();

    }

    /**
     * 修改字典类型接口
     *
     * @param sysDictTypeDTO
     * @return
     */
    @ApiOperation("修改字典类型接口")
    @PutMapping("")
    @Log
    public AjaxResult updateDictType(@RequestBody SysDictTypeDTO sysDictTypeDTO) {

        log.info("修改字典类型接口{}", sysDictTypeDTO);
        sysDictTypeService.updateDictType(sysDictTypeDTO);
        return AjaxResult.success();
    }


    /**
     * 分页查询数据字典类型接口
     *
     * @return
     * @SysQueryforDictTypeDTO sysQueryforDictTypeDTO
     */

    @ApiOperation("分页查询数据字典类型接口")
    @GetMapping("/list")
    public String listPage(SysQueryforDictTypeDTO sysQueryforDictTypeDTO) {

        log.info("分页显示数据{}", sysQueryforDictTypeDTO);
        String id = JSON.toJSONString(sysQueryforDictTypeDTO);
        //定义key
        String key = "dict"+id;
        //是否有key
        String string = (String) redisTemplate.opsForValue().get(key);
        //有key
        if(string!=null){
            log.info("有缓存");
            return string;
        }
        //无key
        log.info("生缓存");
        TableDataInfo tableDataInfo = sysDictTypeService.listPage(sysQueryforDictTypeDTO);
        String jsonString = JSON.toJSONString(tableDataInfo);
        redisTemplate.opsForValue().set(key,jsonString);
        return jsonString;
    }

    /**
     * 清空缓存
     *
     * @return
     */

    @ApiOperation("清空缓存接口")
    @DeleteMapping("/clearCache")
    @Log
    public AjaxResult clearCache() {

        log.info("清空缓存");
        sysDictTypeService.clearCache();
        return AjaxResult.success("清除成功");

    }


    /**
     * 获取字典选择框列表
     *
     * @return
     */
    @ApiOperation("字典选择框接口")
    @GetMapping("/optionselect")
    public AjaxResult optionSelect() {

        log.info("字典选择框接口");
        List list = sysDictTypeService.optionSelect();
        return AjaxResult.success(list);
    }

    /**
     * 查询字典类型详细接口
     *
     * @param dictId
     * @return
     */
    @ApiOperation("查询字典类型详细接口")
    @GetMapping("/{dictId}")
    public AjaxResult getDictTypeById(@PathVariable Integer dictId) {

        log.info("查询id为{}字典类型详细", dictId);
        SysDictTypeVO sysDictTypeVO = sysDictTypeService.getDictTypeById(dictId);

        return AjaxResult.success(sysDictTypeVO);
    }


    /**
     * 删除字典类型
     *
     * @param dictIds
     * @return
     */
    @ApiOperation("删除字典类型接口")
    @DeleteMapping("/{dictIds}")
    @Log
    public AjaxResult deleteDictTypeById(@PathVariable List<Integer> dictIds) {

        log.info("删除字典中{}类型", dictIds);
        sysDictTypeService.deleteDictTypeById(dictIds);
        return AjaxResult.success();

    }


    // Todo 缺陷文件 (目前只能实现本地导出)

    /**
     * 导出数据
     *
     * @param sysQueryforDictTypeDTO
     * @param response
     * @return
     */
    @ApiOperation("导出数据接口")
    @GetMapping("/export")
    public AjaxResult getExport(SysQueryforDictTypeDTO sysQueryforDictTypeDTO, HttpServletResponse response) {

        log.info("导出数据");
        sysQueryforDictTypeDTO.setPageNum(null);
        sysQueryforDictTypeDTO.setPageSize(null);
        TableDataInfo tableDataInfo = sysDictTypeService.listPage(sysQueryforDictTypeDTO);
        List<SysDictType> rows = (List) tableDataInfo.getRows();
        System.out.println(rows);
        sysDictTypeService.getExport(rows, response);
        return AjaxResult.success("数据已导出");
    }
}
