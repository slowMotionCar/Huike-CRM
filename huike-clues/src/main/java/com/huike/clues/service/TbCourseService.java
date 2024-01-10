package com.huike.clues.service;

import com.huike.clues.domain.TbCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.dto.TbCoursePageDTOTemp;
import com.huike.common.core.page.TableDataInfo;

import java.util.List;

/**
* @author 93238
* @description 针对表【tb_course(课程管理)】的数据库操作Service
* @createDate 2023-10-12 06:35:46
*/
public interface TbCourseService extends IService<TbCourse> {
    /**
     * 根据id获取课程管理详细信息
     * @param id
     * @return
     */
    TbCourse selectCourseById(Integer id);

    /**
     * 分页查询课程管理列表
     * @param tbCoursePageDTO
     * @return
     */
    TableDataInfo selectCourseListPage(TbCoursePageDTOTemp tbCoursePageDTO);

    /**
     * 课程下拉列表接口
     * @param subject
     * @return
     */
    List<TbCourse> selectListSelect(Integer subject);

    /**
     * 批量删除课程管理接口
     * @param ids
     */
    void deleteCourseByIdsids(List<Integer> ids);

    /**
     * 新增课程管理
     * @param tbCourse
     */
    void insertCourse(TbCourse tbCourse);

    /**
     * 修改课程管理
     * @param tbCourse
     */
    void updateCourse(TbCourse tbCourse);
}
