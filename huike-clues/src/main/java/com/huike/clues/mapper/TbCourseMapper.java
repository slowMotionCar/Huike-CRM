package com.huike.clues.mapper;

import com.github.pagehelper.Page;
import com.huike.clues.domain.TbCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.domain.dto.TbCoursePageDTO;
import com.huike.clues.domain.dto.TbCoursePageDTOTemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 93238
* @description 针对表【tb_course(课程管理)】的数据库操作Mapper
* @createDate 2023-10-12 06:35:46
* @Entity com.huike.clues.domain.TbCourse
*/
@Mapper
public interface TbCourseMapper extends BaseMapper<TbCourse> {

    @Select("select * from tb_course where id=#{id}")
    TbCourse selectCourseById(Integer id);

    Page<TbCourse> selectCourseListPage(TbCoursePageDTO tbCoursePageDTO);

    @Select("select * from tb_course where subject=#{subject}")
    List<TbCourse> selectListSelect(Integer subject);

    Integer selectBusinessByCourseId(Integer courseId);

    @Update("update tb_course set is_delete=1")
    void updateDeleteFlagById(Integer id);

    void updateByCondithon(TbCourse tbCourse);
}




