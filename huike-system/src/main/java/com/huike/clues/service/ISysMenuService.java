package com.huike.clues.service;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.SysMenu;
import com.huike.clues.domain.dto.SysMenuConditionVO;
import com.huike.clues.result.RoleMenuTreeAjaxResult;
import com.huike.common.core.domain.TreeSelect;
import com.huike.common.core.domain.entity.SysMenuDTO;
import com.huike.clues.domain.vo.RouterVo;

/**
 * 菜单 业务层
 *
 *
 */
public interface ISysMenuService extends IService<SysMenu>
{




    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenuDTO> selectMenuTreeByUserId(Long userId);



    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<RouterVo> buildMenus(List<SysMenuDTO> menus);


    /**
     * 根据菜单编号获取详细信息
     * @param menuId
     * @return
     */
    SysMenu selectMenuByMenuId(Integer menuId);

    /**
     * 获取菜单列表
     * @param sysMenuConditionVO
     * @return
     */
    List<SysMenu> selectByCondition(SysMenuConditionVO sysMenuConditionVO);

    /**
     * 获取菜单下拉树列表接口
     * @param
     * @return
     */
    List<TreeSelect> selectMenuTree();

    /**
     * 加载对应角色菜单列表树
     * @param roleId
     */
    RoleMenuTreeAjaxResult roleMenuTreeselect(Integer roleId);

    /**
     * 修改菜单
     * @param sysMenu
     */
    void updateMenu(SysMenu sysMenu);

    /**
     * 新增菜单
     * @param sysMenu
     */
    void insertMenu(SysMenu sysMenu);

    /**
     * 删除菜单.存在子菜单或已分配,不允许删除
     *
     * @param menuId
     * @return
     */
    void deleteMenu(Integer menuId);
}
