package com.huike.clues.service;

import com.huike.common.core.domain.TreeSelect;
import com.huike.common.core.domain.entity.SysDeptDTO;

import java.util.List;

/**
 * @Description SysDeptService
 * @Author FangYiHeng
 * @Date 2023-10-17
 */
public interface SysDeptService {
    List<SysDeptDTO> selectDeptList();

    void insertDept(SysDeptDTO sysDeptDTO);

    SysDeptDTO selectDeptById(Integer deptId);

    void updateDept(SysDeptDTO sysDeptDTO);

    List<SysDeptDTO> exclude(Integer deptId);

    /*List<SysDeptDTO> roleDeptTreeselect(Integer roleId);*/

    /**
     * 部门删除
     * @param deptId
     */
    void delete(Long deptId);

    /**
     * 获取部门下拉树列表
     *
     * @return
     */
    List<TreeSelect> treeselect(Integer parentId);

    /**
     * 获取当前id下的所有子部门
     * @param deptId
     * @return
     */
    List<Long> findBydeptId(Long deptId);
}
