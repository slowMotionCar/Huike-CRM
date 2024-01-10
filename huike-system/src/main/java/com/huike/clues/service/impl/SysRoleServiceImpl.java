package com.huike.clues.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.huike.clues.domain.SysRole;
import com.huike.clues.mapper.SysUserRoleMapper;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.PageDomain;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.exception.BaseException;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.bean.BeanUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.huike.common.core.domain.entity.SysRoleDTO;
import com.huike.common.utils.StringUtils;
import com.huike.common.utils.spring.SpringUtils;
import com.huike.clues.mapper.SysRoleMapper;
import com.huike.clues.service.ISysRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * 角色 业务层处理
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;


    /**
     * 查询所有角色
     * @return 角色列表
     */
    @Override
    public List<SysRoleDTO> selectRoleAll() {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRoleDTO());
    }

    /**
     * 根据条件分页查询角色数据
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<SysRoleDTO> selectRoleList(SysRoleDTO role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID获取角色选择框列表
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Integer> selectRoleListByUserId(Long userId) {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 根据用户ID查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRoleDTO> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRoleDTO perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 新增角色
     * @param sysRoleDTO
     */
    @Override
    public void insertRole(SysRoleDTO sysRoleDTO) {
        if (sysRoleDTO.getRoleName().length() < 1 || sysRoleDTO.getRoleName().length() > 12) {
            throw new BaseException("请重写输入长度1~12个字符的角色名称");
        }

        // 根据角色名称查询角色
        SysRole sysRoleDB = roleMapper.selectRolesByRoleName(sysRoleDTO.getRoleName());
        if (sysRoleDB != null) {
            throw new BaseException("角色名称已存在");
        }

        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleDTO, sysRole);
        sysRole.setRoleSort(Integer.valueOf(sysRoleDTO.getRoleSort()));
        sysRole.setCreateBy(SecurityUtils.getUsername());
        sysRole.setCreateTime(new Date());
        sysRole.setUpdateBy(SecurityUtils.getUsername());
        sysRole.setUpdateTime(new Date());

        roleMapper.insert(sysRole);
    }

    /**
     * 分页查询角色列表
     * @param pageDomain
     * @return
     */
    @Override
    public TableDataInfo pageSelectRole(PageDomain pageDomain) {
       /* PageHelper.startPage(pageDomain.getPageNum(), pageDomain.getPageSize());

        Map<String, Object> map = pageDomain.getParams();
        String beginTime = String.valueOf(map.get("beginTime"));
        String endTime = String.valueOf(map.get("endTIme"));

        // 查询角色列表
        List<SysRole> sysRoleList = roleMapper.selectRoleListByCondition(pageDomain, beginTime, endTime);
        Page<SysRole> page = (Page<SysRole>) sysRoleList;*/

        Page<SysRole> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());

        String roleName = pageDomain.getRoleName();
        String roleKey = pageDomain.getRoleKey();
        String status = pageDomain.getStatus();

        Map<String, Object> params = pageDomain.getParams();
        String beginTime = (String) params.get("beginTime");
        String endTime = (String) params.get("endTime");

        LambdaQueryWrapper<SysRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(roleName != null, SysRole::getRoleName, roleName);
        lambdaQueryWrapper.like(roleKey != null, SysRole::getRoleKey, roleKey);
        lambdaQueryWrapper.eq(status != null, SysRole::getStatus, status);
        if (beginTime != null && beginTime != ""
                && endTime != null && endTime != "") {
            lambdaQueryWrapper.ge(SysRole::getCreateTime, beginTime);
            lambdaQueryWrapper.le(SysRole::getCreateTime, endTime);
        }

        roleMapper.selectPage(page, lambdaQueryWrapper);

        TableDataInfo tableDataInfo = new TableDataInfo();
        tableDataInfo.setTotal(page.getTotal());
        tableDataInfo.setRows(page.getRecords());

        return tableDataInfo;
    }

    /**
     * 根据角色编号获取详细信息
     * @param roleId
     * @return
     */
    @Override
    public SysRoleDTO selectRoleById(Long roleId) {
        SysRole sysRole = roleMapper.selectById(roleId);
        SysRoleDTO sysRoleDTO = new SysRoleDTO();
        BeanUtils.copyProperties(sysRole, sysRoleDTO);
        sysRoleDTO.setRoleSort(String.valueOf(sysRole.getRoleSort()));
        return sysRoleDTO;
    }

    /**
     * 修改保存角色
     * @param sysRoleDTO
     */
    @Override
    public void updateRole(SysRoleDTO sysRoleDTO) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleDTO, sysRole);
        roleMapper.updateById(sysRole);
    }

    /**
     * 处理”状态修改“请求
     * @param sysRoleDTO
     */
    @Override
    public void changeStatus(SysRoleDTO sysRoleDTO) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleDTO, sysRole);
        roleMapper.updateById(sysRole);
    }

    /**
     * 修改保存数据权限
     * @param sysRoleDTO
     */
    @Override
    public void dataScope(SysRoleDTO sysRoleDTO) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleDTO, sysRole);
        sysRole.setUpdateBy(SecurityUtils.getUsername());
        sysRole.setUpdateTime(new Date());
        roleMapper.updateById(sysRole);
    }

    /**
     * TODO 待完善，未确定需要返回的数据
     * 获取用户选择框列表
     * @return
     */
    @Override
    public List<SysRoleDTO> optionselect() {
        return null;
    }

    /**
     * TODO 待完善
     * 删除角色
     * @param roleIds
     */
    @Override
    public void deleteRole(List<Long> roleIds) {
        /*for (Long roleId : roleIds) {
            SysRole sysRole = roleMapper.selectById(roleId);
            userRoleMapper.selectById()
        }*/
        roleMapper.deleteBatchIds(roleIds);
    }
}
