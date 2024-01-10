package com.huike.web.controller.clues;

import com.huike.clues.domain.TbCourse;
import com.huike.clues.domain.dto.TbCoursePageDTOTemp;
import com.huike.clues.service.TbCourseService;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程管理Controller
 * @date 2023-04-01
 */
@RestController
@RequestMapping("/clues/course")
@Api(tags = "课程管理相关接口")
public class TbCourseController {
    @Autowired
    private TbCourseService tbCourseService;

    /**
     * 根据id获取课程管理详细信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("获取课程管理详细信息")
    public AjaxResult selectCourseById(@PathVariable Integer id){
        TbCourse tbCourse=tbCourseService.selectCourseById(id);
        return AjaxResult.success(tbCourse);
    }

    /**
     * 分页查询课程管理列表
     * @param tbCoursePageDTO
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询课程管理列表")
    public TableDataInfo selectCourseListPage(TbCoursePageDTOTemp tbCoursePageDTO){
        TableDataInfo tableDataInfo= tbCourseService.selectCourseListPage(tbCoursePageDTO);

        return tableDataInfo;
    }

    /**
     * 课程下拉列表接口
     * @param subject
     * @return
     */
    @GetMapping("/listselect")
    @ApiOperation("课程下拉列表")
    public AjaxResult selectListSelect(Integer subject){

        List<TbCourse> tbCourseList=tbCourseService.selectListSelect(subject);

        return AjaxResult.success(tbCourseList);
    }

    /**
     * (批量)删除课程管理接口
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("(批量)删除课程管理接口")
    public AjaxResult deleteCourseByIds(@PathVariable List<Integer> ids){
        tbCourseService.deleteCourseByIdsids(ids);
        return AjaxResult.success();
    }

    /**
     * 新增课程管理
     * @param tbCourse
     * @return
     */

    @PostMapping
    @ApiOperation("新增课程管理")
    public AjaxResult insertCourse(@RequestBody TbCourse tbCourse){
        tbCourseService.insertCourse(tbCourse);

        return AjaxResult.success();
    }

    /**
     * 修改课程管理
     * @param tbCourse
     * @return
     */
    @PutMapping
    @ApiOperation("修改课程管理")
    public AjaxResult updateCourse(@RequestBody TbCourse tbCourse){
        tbCourseService.updateCourse(tbCourse);
        return AjaxResult.success();
    }
}
