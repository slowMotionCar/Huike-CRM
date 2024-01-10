package com.huike.web.controller.system;

import com.huike.clues.service.SysDeptService;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.TreeSelect;
import com.huike.common.core.domain.entity.SysDeptDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 部门管理相关接口
 * @Date 2023-10-10
 */
@RestController
@RequestMapping("/system/dept")
@Api(tags = "部门管理相关接口")
@Slf4j
public class SysDeptController {
    @Resource
    private SysDeptService sysDeptService;

    /**
     * 执行”查询部门列表“请求
     * @return
     */
    @ApiOperation("查询部门列表接口")
    @GetMapping("/list")
    public AjaxResult selectDeptList() {
        log.info("查询部门列表接口");
        List<SysDeptDTO> sysDeptDTOList = sysDeptService.selectDeptList();
        return AjaxResult.success(sysDeptDTOList);
    }

    /**
     * 处理“新增部门”请求
     * @param sysDeptDTO
     * @return
     */
    @ApiOperation("新增部门接口")
    @PostMapping
    public AjaxResult insertDept(@RequestBody SysDeptDTO sysDeptDTO) {
        log.info("执行新增部门接口：{}", sysDeptDTO);
        sysDeptService.insertDept(sysDeptDTO);
        return AjaxResult.success();
    }

    /**
     * 处理”根据部门id获取详细信息“请求
     * @param deptId
     * @return
     */
    @ApiOperation("根据部门编号获取详细信息接口")
    @GetMapping("/{deptId}")
    public AjaxResult selectDeptById(@PathVariable Integer deptId) {
        log.info("执行根据部门编号获取详细信息接口：{}", deptId);
        SysDeptDTO sysDeptDTO = sysDeptService.selectDeptById(deptId);
        return AjaxResult.success(sysDeptDTO);
    }

    /**
     * 处理”修改部门“请求
     * @param sysDeptDTO
     * @return
     */
    @ApiOperation("修改部门接口")
    @PutMapping
    public AjaxResult updateDept(@RequestBody SysDeptDTO sysDeptDTO) {
        log.info("执行修改部门接口：{}", sysDeptDTO);
        sysDeptService.updateDept(sysDeptDTO);
        return AjaxResult.success();
    }

    /**
     * 处理”查询部门列表（排除指定部门节点）“请求
     * @param deptId
     * @return
     */
    @ApiOperation("查询部门列表（排除指定部门节点）接口")
    @GetMapping("/list/exclude/{deptId}")
    public AjaxResult exclude(@PathVariable Integer deptId) {
        log.info("执行查询部门列表（排除指定部门节点）接口：{}", deptId);
        List<SysDeptDTO> sysDeptDTOList = sysDeptService.exclude(deptId);
        return AjaxResult.success(sysDeptDTOList);
    }

    /**
     * 处理”获取对应角色部门列表树“请求
     * @param roleId
     * @return
     */
    /*@ApiOperation("获取对应角色部门列表树接口")
    @GetMapping("/roleDeptTreeselect/{roleId}")
    public AjaxResult roleDeptTreeselect(@PathVariable Integer roleId) {
        log.info("执行获取对应角色部门列表树接口：{}", roleId);
        List<SysDeptDTO> sysDeptDTOList = sysDeptService.roleDeptTreeselect(roleId);
        return AjaxResult.success(sysDeptDTOList);
    }*/

    /**
     * 删除部门
     * @param deptId
     * @return
     */
    @DeleteMapping("/{deptId}")
    public AjaxResult delete(@PathVariable Long deptId) {
        sysDeptService.delete(deptId);
        return AjaxResult.success();
    }

    /**
     * 获取部门下拉树列表
     * @return
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect() {
        Integer parentId = 0;
        List<TreeSelect> tree = sysDeptService.treeselect(parentId);
        return AjaxResult.success(tree);
    }
}
