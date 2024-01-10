package com.huike.clues.service;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.SysRole;
import com.huike.common.core.domain.entity.SysRoleDTO;
import com.huike.common.core.page.PageDomain;
import com.huike.common.core.page.TableDataInfo;

/**
 * 角色业务层
 * 
 * 
 */
public interface ISysRoleService extends IService<SysRole>
{
    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    public List<SysRoleDTO> selectRoleAll();

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRoleDTO> selectRoleList(SysRoleDTO role);

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    public List<Integer> selectRoleListByUserId(Long userId);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectRolePermissionByUserId(Long userId);

    void insertRole(SysRoleDTO sysRoleDTO);

    TableDataInfo pageSelectRole(PageDomain pageDomain);

    SysRoleDTO selectRoleById(Long roleId);

    void updateRole(SysRoleDTO sysRoleDTO);

    void changeStatus(SysRoleDTO sysRoleDTO);

    void dataScope(SysRoleDTO sysRoleDTO);

    List<SysRoleDTO> optionselect();

    void deleteRole(List<Long> roleIds);
}
