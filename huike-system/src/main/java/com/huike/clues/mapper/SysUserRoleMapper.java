package com.huike.clues.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.domain.SysUserRole;
import com.huike.clues.domain.dto.SysUserRoleDTO;

/**
 * 用户与角色关联表 数据层
 * 
 * 
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole>
{
    /**
     * 通过用户ID删除用户和角色关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserRoleByUserId(Long userId);

    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    public int batchUserRole(List<SysUserRoleDTO> userRoleList);

    /**
     * 批量删除用户和角色关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserRole(Long[] ids);

}
