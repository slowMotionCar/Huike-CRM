package com.huike.clues.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.domain.SysRole;
import com.huike.common.core.domain.entity.SysRoleDTO;
import com.huike.common.core.page.PageDomain;

/**
 * 角色表 数据层
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据条件分页查询角色数据
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRoleDTO> selectRoleList(SysRoleDTO role);

    /**
     * 根据用户ID获取角色选择框列表
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    public List<Integer> selectRoleListByUserId(Long userId);

    /**
     * 根据用户ID查询角色
     * @param userId 用户ID
     * @return 角色列表
     */
    public List<SysRoleDTO> selectRolePermissionByUserId(Long userId);

    /**
     * 根据用户ID查询角色
     * @param userName 用户名
     * @return 角色列表
     */
    public List<SysRoleDTO> selectRolesByUserName(String userName);

    /**
     * 根据角色名称查询角色
     * @param roleName
     * @return
     */
    SysRole selectRolesByRoleName(String roleName);

    /**
     * 查询角色列表
     * @param pageDomain
     * @return
     */
//    List<SysRole> selectRoleListByCondition(PageDomain pageDomain, String beginTime, String endTime);
}
