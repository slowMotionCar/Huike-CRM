package com.huike.clues.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.domain.SysDept;
import com.huike.common.core.domain.TreeSelect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description SysDeptMapper
 * @Author FangYiHeng
 * @Date 2023-10-17
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**
     * 根据部门id获取详细信息
     * @param deptId
     * @return
     */
    SysDept selectByDeptById(Integer deptId);

    /**
     * 获取部门下拉树列表
     * @param parentId
     * @return
     */
    List<TreeSelect> selectTree(Integer parentId);
}
