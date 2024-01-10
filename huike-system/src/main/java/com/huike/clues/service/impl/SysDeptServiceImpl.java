package com.huike.clues.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huike.clues.domain.SysDept;
import com.huike.clues.domain.SysRoleDept;
import com.huike.clues.mapper.SysDeptMapper;
import com.huike.clues.mapper.SysRoleDeptMapper;
import com.huike.clues.service.SysDeptService;
import com.huike.common.core.domain.TreeSelect;
import com.huike.common.constant.HttpStatus;
import com.huike.common.core.domain.entity.SysDeptDTO;
import com.huike.common.exception.BaseException;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description SysDeptServiceImpl
 * @Author FangYiHeng
 * @Date 2023-10-17
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysRoleDeptMapper sysRoleDeptMapper;

    /**
     * 查询部门列表
     *
     * @return
     */
    @Override
    public List<SysDeptDTO> selectDeptList() {
        // 1. 设置查询条件
        LambdaQueryWrapper<SysDept> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(SysDept::getOrderNum);

        // 2. 调用selectList方法，查询部门列表
        List<SysDept> sysDeptList = sysDeptMapper.selectList(lqw);

        // 3. 遍历部门列表返回新集合
        List<SysDeptDTO> sysDeptDTOList = sysDeptList.stream().map(sysDept -> {
            SysDeptDTO sysDeptDTO = new SysDeptDTO();
            BeanUtils.copyProperties(sysDept, sysDeptDTO);
            sysDeptDTO.setOrderNum(String.valueOf(sysDept.getOrderNum()));
            return sysDeptDTO;
        }).collect(Collectors.toList());

        // 4. 返回数据
        return sysDeptDTOList;
    }

    /**
     * 新增部门
     *
     * @param sysDeptDTO
     */
    @Override
    public void insertDept(SysDeptDTO sysDeptDTO) {
        LambdaQueryWrapper<SysDept> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(SysDept::getDeptName, sysDeptDTO.getDeptName());
        // 根据"部门名称"查询部门
        SysDept sysDeptDB1 = sysDeptMapper.selectOne(lqw1);
        // 判断部门名称是否存在
        if (sysDeptDB1 != null) {
            throw new BaseException("部门名称已存在");
        }

        LambdaQueryWrapper<SysDept> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(SysDept::getDeptId, sysDeptDTO.getParentId());
        // 根据部门中“父部门id”的查询部门
        SysDept sysDeptDB2 = sysDeptMapper.selectOne(lqw2);

        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(sysDeptDTO, sysDept);
        sysDept.setAncestors(sysDeptDB2.getAncestors() + "," + sysDeptDB2.getDeptId());
        sysDept.setCreateBy(SecurityUtils.getUsername());
        sysDept.setCreateTime(DateUtils.getNowDate());
        sysDept.setUpdateBy(SecurityUtils.getUsername());
        sysDept.setUpdateTime(DateUtils.getNowDate());

        sysDeptMapper.insert(sysDept);
    }

    /**
     * 根据部门id获取详细信息
     *
     * @param deptId
     * @return
     */
    @Override
    public SysDeptDTO selectDeptById(Integer deptId) {
        SysDept sysDept = sysDeptMapper.selectByDeptById(deptId);
        SysDeptDTO sysDeptDTO = null;
        if (sysDept != null) {
            sysDeptDTO = new SysDeptDTO();
            BeanUtils.copyProperties(sysDept, sysDeptDTO);
        }
        return sysDeptDTO;
    }

    /**
     * 修改部门
     *
     * @param sysDeptDTO
     */
    @Override
    public void updateDept(SysDeptDTO sysDeptDTO) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(sysDeptDTO, sysDept);

        LambdaQueryWrapper<SysDept> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysDept::getDeptId, sysDeptDTO.getDeptId());

        sysDeptMapper.update(sysDept, lqw);
    }

    /**
     * 查询部门列表（排除指定部门节点）
     *
     * @param deptId
     * @return
     */
    @Override
    public List<SysDeptDTO> exclude(Integer deptId) {
        LambdaQueryWrapper<SysDept> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysDept::getDeptId, deptId);
        // 查询部门表
        SysDept sysDeptDB1 = sysDeptMapper.selectOne(lqw);

        List<SysDeptDTO> sysDeptDTOList = new ArrayList<>();
        if (sysDeptDB1 != null) {
            String ancestors = sysDeptDB1.getAncestors();
            String[] deptIdArr = ancestors.split(",");
            if (deptIdArr.length > 2) {
                for (int i = 1; i < deptIdArr.length; i++) {
                    LambdaQueryWrapper<SysDept> lqw1 = new LambdaQueryWrapper<>();
                    lqw1.eq(SysDept::getDeptId, deptIdArr[i]);
                    // 查询部门表
                    SysDept sysDeptDB2 = sysDeptMapper.selectOne(lqw1);
                    if (sysDeptDB2 != null) {
                        SysDeptDTO sysDeptDTO = new SysDeptDTO();
                        BeanUtils.copyProperties(sysDeptDB2, sysDeptDTO);
                        sysDeptDTOList.add(sysDeptDTO);
                    }
                }
            } else if (deptIdArr.length == 2) {
                LambdaQueryWrapper<SysDept> lqw2 = new LambdaQueryWrapper<>();
                lqw2.eq(SysDept::getDeptId, deptIdArr[1]);
                // 查询部门表
                SysDept sysDeptDB3 = sysDeptMapper.selectOne(lqw2);
                if (sysDeptDB3 != null) {
                    SysDeptDTO sysDeptDTO = new SysDeptDTO();
                    BeanUtils.copyProperties(sysDeptDB3, sysDeptDTO);
                    sysDeptDTOList.add(sysDeptDTO);
                }
            } else {
                LambdaQueryWrapper<SysDept> lqw3 = new LambdaQueryWrapper<>();
                lqw3.eq(SysDept::getDeptId, 100);
                // 查询部门表
                SysDept sysDeptDB4 = sysDeptMapper.selectOne(lqw3);
                if (sysDeptDB4 != null) {
                    SysDeptDTO sysDeptDTO = new SysDeptDTO();
                    BeanUtils.copyProperties(sysDeptDB4, sysDeptDTO);
                    sysDeptDTOList.add(sysDeptDTO);
                }

            }
        }

        return sysDeptDTOList;
    }

    /**
     * 部门删除
     *
     * @param deptId
     */
    @Override
    public void delete(Long deptId) {
        // 1.判断 如果有子部门，不能删除
        QueryWrapper<SysDept> qw = new QueryWrapper<>();
        qw.lambda().eq(SysDept::getParentId, deptId);
        Integer count = sysDeptMapper.selectCount(qw);
        if (count > 0) {
            throw new CustomException("当前部门有下属部门", HttpStatus.CONFLICT);
        }

        // 2.判断 如果部门有角色，不能删除
        QueryWrapper<SysRoleDept> qw2 = new QueryWrapper<>();
        qw2.lambda().eq(SysRoleDept::getDeptId, deptId);
        count = sysRoleDeptMapper.selectCount(qw2);
        if (count > 0) {
            throw new CustomException("当前部门有角色，不能删除", HttpStatus.CONFLICT);
        }

        // 3.启用状态不能删除
        QueryWrapper<SysDept> qw3 = new QueryWrapper<>();
        qw3.lambda().eq(SysDept::getStatus, "0");
        count = sysDeptMapper.selectCount(qw);
        if (count > 0) {
            throw new CustomException("启用部门不能删除", HttpStatus.CONFLICT);
        }

        // 4.删除
        sysDeptMapper.deleteById(deptId);
    }


    /**
     * 获取部门下拉树列表
     *
     * @param parentId
     * @return
     */
    @Override
    public List<TreeSelect> treeselect(Integer parentId) {
        List<TreeSelect> tree = sysDeptMapper.selectTree(parentId);
        if (!tree.isEmpty()) {
            for (TreeSelect treeSelect : tree) {
                parentId = Integer.valueOf(String.valueOf(treeSelect.getId()));
                treeselect(parentId);
            }
        }
        return tree;
    }

    @Override
    public List<Long> findBydeptId(Long deptId) {

        QueryWrapper<SysDept> qw = new QueryWrapper<>();
        qw.lambda().like(SysDept::getAncestors, String.valueOf(deptId));
        List<SysDept> sysDepts = sysDeptMapper.selectList(qw);
        Set<Long> set = new HashSet<>();
        set.add(deptId);
        for (SysDept sysDept : sysDepts) {
            String[] str = sysDept.getAncestors().split(",");
            for (String s : str) {
                if (Long.parseLong(s) > deptId) {
                    set.add(Long.parseLong(s));
                }
            }
        }
        return new ArrayList<>(set);
    }

    /**
     * 获取对应角色部门列表树
     * @param roleId
     * @return
     */
    /*@Override
    public List<SysDeptDTO> roleDeptTreeselect(Integer roleId) {
        LambdaQueryWrapper<SysRoleDept> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(SysRoleDept::getRoleId, roleId);
        // 查询角色部门中间中间表
        List<SysRoleDept> sysRoleDeptList = sysRoleDeptMapper.selectList(lqw1);

        List<SysDeptDTO> sysDeptDTOList = new ArrayList<>();
        if (sysRoleDeptList != null && sysRoleDeptList.size() > 0) {
            for (SysRoleDept sysRoleDept : sysRoleDeptList) {
                LambdaQueryWrapper<SysDept> lqw2 = new LambdaQueryWrapper<>();
                lqw2.eq(SysDept::getDeptId, sysRoleDept.getDeptId());
                // 查询部门表
                SysDept sysDeptDB = sysDeptMapper.selectOne(lqw2);

                SysDeptDTO sysDeptDTO = new SysDeptDTO();
                BeanUtils.copyProperties(sysDeptDB, sysDeptDTO);
                sysDeptDTOList.add(sysDeptDTO);
            }
        }

        List<TreeSelect> treeSelectList  =


        return sysDeptRoleTreeVOList;
    }*/
}
