package com.huike.web.controller.system;

import com.huike.clues.domain.SysMenu;
import com.huike.clues.domain.SysMenuBase;
import com.huike.clues.domain.dto.SysMenuConditionVO;
import com.huike.clues.result.RoleMenuTreeAjaxResult;
import com.huike.clues.service.ISysMenuService;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.TreeSelect;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class SysMenuController {
    @Autowired
    private ISysMenuService iSysMenuService;

    /**
     * 根据菜单编号获取详细信息
     * @param menuId
     * @return
     */
    @GetMapping("/{menuId}")
    @ApiOperation("根据菜单编号获取详细信息")
    public AjaxResult selectMenuByMenuId(@PathVariable Integer menuId){
        SysMenu sysMenu=iSysMenuService.selectMenuByMenuId(menuId);
        return AjaxResult.success(sysMenu);
    }

    /**
     * 获取菜单列表
     * @param sysMenuConditionVO
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("获取菜单列表")
    public AjaxResult selectByCondition(SysMenuConditionVO sysMenuConditionVO){
       List<SysMenu> sysMenuList= iSysMenuService.selectByCondition(sysMenuConditionVO);

        return AjaxResult.success(sysMenuList);
    }

    /**
     * 获取菜单下拉树列表接口!
     * @param
     * @return
     */
    @GetMapping("/treeselect")
    @ApiOperation("获取菜单下拉树列表")
    public AjaxResult selectMenuTree(){

        List<TreeSelect> roleMenuTreeAjaxResult=iSysMenuService.selectMenuTree();
        return AjaxResult.success(roleMenuTreeAjaxResult);
    }

    /**
     * 加载对应角色菜单列表树
     * @param roleId
     * @return
     */
    @GetMapping("/roleMenuTreeselect/{roleId}")
    @ApiOperation("加载对应角色菜单列表树")
    public RoleMenuTreeAjaxResult roleMenuTreeselect(@PathVariable Integer roleId){
        RoleMenuTreeAjaxResult roleMenuTreeAjaxResult = iSysMenuService.roleMenuTreeselect(roleId);

        return roleMenuTreeAjaxResult;
    }

    /**
     * 修改菜单
     * @param sysMenu
     * @return
     */
    @PutMapping
    @ApiOperation("修改菜单")
    public AjaxResult updateMenu(@RequestBody SysMenu sysMenu){
        iSysMenuService.updateMenu(sysMenu);
        return AjaxResult.success();
    }

    /**
     * 新增菜单
     * @param sysMenu
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜单")
    public AjaxResult insertMenu(@RequestBody SysMenu sysMenu){
        iSysMenuService.insertMenu(sysMenu);
        return AjaxResult.success();
    }

    /**
     * 删除菜单.存在子菜单或已分配,不允许删除
     * @param menuId
     * @return
     */
    //还有Bug,待排查
    @DeleteMapping("/{menuId}")
    @ApiOperation("删除菜单.存在子菜单或已分配,不允许删除")
    public AjaxResult deleteMenu(@PathVariable Integer menuId){
        iSysMenuService.deleteMenu(menuId);
        return AjaxResult.success();
    }
}
