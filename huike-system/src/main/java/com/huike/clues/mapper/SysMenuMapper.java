package com.huike.clues.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.domain.SysMenu;
import com.huike.clues.domain.dto.SysMenuConditionVO;
import com.huike.common.core.domain.TreeSelect;
import com.huike.common.core.domain.TreeSelectChildren;
import com.huike.common.core.domain.entity.SysMenuDTO;
import io.swagger.models.auth.In;

/**
 * 菜单表 数据层
 *
 * 
 */
public interface SysMenuMapper extends BaseMapper<SysMenu>
{
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单
     *
     * @return 菜单列表
     */
    public List<SysMenuDTO> selectMenuTreeAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenuDTO> selectMenuTreeByUserId(Long userId);

    /**
     * 查出所有的parentId
     * @return
     */
    List<Integer> selectParentAllId();

    List<TreeSelect> getMenuIdAndMenuName(Integer parentId);
    TreeSelectChildren getMenuIdAndMenuName2(SysMenu menu);

    List<SysMenu> selectList(SysMenuConditionVO sysMenuConditionVO);

    List<Integer> roleMenuTreeselect(Integer roleId);

    List<SysMenu> selectMenuListByroleId(Integer roleId);

    List<TreeSelect> selectTreeSelectListByroleId(List<Integer> menuIds);

    void updateMenu(SysMenu sysMenu);

    void deleteMenu(Integer menuId);

    List<SysMenu> selectByParentId(Integer menuId);

    Long selectRolemenu_check_strictlyByMenuId(Integer menuId);
}
