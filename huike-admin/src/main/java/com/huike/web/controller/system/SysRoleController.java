package com.huike.web.controller.system;

import com.google.common.io.BaseEncoding;
import com.huike.clues.domain.SysRole;
import com.huike.clues.service.ISysRoleService;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysRoleDTO;
import com.huike.common.core.page.PageDomain;
import com.huike.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description SysRoleController
 * @Author FangYiHeng
 * @Date 2023-10-16
 */
@RestController
@RequestMapping("/system/role")
@Api(tags = "角色信息相关接口")
@Slf4j
public class SysRoleController {
    @Resource
    private ISysRoleService iSysRoleService;

    /**
     * 处理“新增角色”请求
     * @param sysRoleDTO
     * @return
     */
    @ApiOperation("新增角色接口")
    @PostMapping
    public AjaxResult insertRole(@RequestBody SysRoleDTO sysRoleDTO) {
        log.info("执行新增角色接口：{}", sysRoleDTO);
        iSysRoleService.insertRole(sysRoleDTO);
        return AjaxResult.success();
    }

    /**
     * 处理”分页查询角色列表“请求
     * @param pageDomain
     * @return
     */
    @ApiOperation("分页查询角色列表接口")
    @GetMapping("/list")
    public TableDataInfo<List<SysRole>> pageSelectRole(PageDomain pageDomain) {
        log.info("执行分页查询角色列表接口：{}", pageDomain);
        TableDataInfo tableDataInfo = iSysRoleService.pageSelectRole(pageDomain);
        return tableDataInfo;
    }

    /**
     * 处理”根据角色编号获取详细信息“请求
     * @param roleId
     * @return
     */
    @ApiOperation("根据角色编号获取详细信息接口")
    @GetMapping("/{roleId}")
    public AjaxResult selectRoleById(@PathVariable Long roleId) {
        log.info("根据角色编号获取详细信息：{}", roleId);
        SysRoleDTO sysRoleDTO = iSysRoleService.selectRoleById(roleId);
        return AjaxResult.success(sysRoleDTO);
    }

    /**
     * 执行”修改保存角色“请求
     * @param sysRoleDTO
     * @return
     */
    @ApiOperation("修改保存角色接口")
    @PutMapping
    public AjaxResult updateRole(@RequestBody SysRoleDTO sysRoleDTO) {
        log.info("执行修改保存角色接口：{}", sysRoleDTO);
        iSysRoleService.updateRole(sysRoleDTO);
        return AjaxResult.success();
    }

    /**
     * @param sysRoleDTO
     * @return
     */
    @ApiOperation("状态修改接口")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysRoleDTO sysRoleDTO) {
        log.info("执行状态修改接口：{}", sysRoleDTO);
        iSysRoleService.changeStatus(sysRoleDTO);
        return AjaxResult.success();
    }


    /**
     * 处理”修改保存数据权限“请求
     * @param sysRoleDTO
     * @return
     */
    @ApiOperation("修改保存数据权限接口")
    @PutMapping("/dataScope")
    public AjaxResult dataScope(@RequestBody SysRoleDTO sysRoleDTO) {
        log.info("执行修改保存数据权限接口：{}", sysRoleDTO);
        iSysRoleService.dataScope(sysRoleDTO);
        return AjaxResult.success();
    }


    /**
     * 处理”获取用户选择框列表“请求
     * @return
     */
    @ApiOperation("获取用户选择框列表接口")
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        log.info("执行获取用户选择框列表接口");
        List<SysRoleDTO> sysRoleDTOList = iSysRoleService.optionselect();
        return AjaxResult.success(sysRoleDTOList);
    }

    /**
     * 处理“删除角色接口”请求
     * @param roleIds
     * @return
     */
    @ApiOperation("删除角色接口")
    @DeleteMapping("/{roleIds}")
    public AjaxResult deleteRole(@PathVariable List<Long> roleIds) {
        log.info("执行删除角色接口：{}", roleIds);
        iSysRoleService.deleteRole(roleIds);
        return AjaxResult.success();
    }
}
