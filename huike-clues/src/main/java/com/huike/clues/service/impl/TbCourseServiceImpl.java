package com.huike.clues.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huike.clues.domain.TbCourse;
import com.huike.clues.domain.dto.TbCoursePageDTO;
import com.huike.clues.domain.dto.TbCoursePageDTOTemp;
import com.huike.clues.service.TbCourseService;
import com.huike.clues.mapper.TbCourseMapper;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
* @author 93238
* @description 针对表【tb_course(课程管理)】的数据库操作Service实现
* @createDate 2023-10-12 06:35:46
*/
@Service
public class TbCourseServiceImpl extends ServiceImpl<TbCourseMapper, TbCourse>
    implements TbCourseService{
    @Autowired
    private TbCourseMapper tbCourseMapper;

    /**
     * 根据id获取课程管理详细信息
     *
     * @param id
     * @return
     */
    @Override
    public TbCourse selectCourseById(Integer id) {
        TbCourse tbCourse=tbCourseMapper.selectCourseById(id);
        return tbCourse;
    }

    /**
     * 分页查询课程管理列表
     *
     * @param tbCoursePageDTOTemp
     * @return
     * */

    @Override
    public TableDataInfo selectCourseListPage(TbCoursePageDTOTemp tbCoursePageDTOTemp) {

        LocalDate beginCreateTimeDate=null;
        LocalDate endCreateTimeDate=null;
        TbCoursePageDTO tbCoursePageDTO = new TbCoursePageDTO();
        String beginCreateTime= tbCoursePageDTOTemp.getParams().get("beginCreateTime");
        String endCreateTime = tbCoursePageDTOTemp.getParams().get("endCreateTime");

        //String转化成LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(StringUtils.isNotEmpty(beginCreateTime)){
            beginCreateTimeDate = LocalDate.parse(beginCreateTime, formatter);
        }
        if(StringUtils.isNotEmpty(endCreateTime)){
            endCreateTimeDate = LocalDate.parse(endCreateTime, formatter);
        }

        BeanUtils.copyProperties(tbCoursePageDTOTemp,tbCoursePageDTO);
        tbCoursePageDTO.setBeginCreateTime(beginCreateTimeDate);
        tbCoursePageDTO.setEndCreateTime(endCreateTimeDate);
        PageHelper.startPage(tbCoursePageDTOTemp.getPageNum(),tbCoursePageDTOTemp.getPageSize());
        Page<TbCourse> coursePage=tbCourseMapper.selectCourseListPage(tbCoursePageDTO);
        List<TbCourse> result = coursePage.getResult();
        TableDataInfo<List<TbCourse>> objectTableDataInfo = new TableDataInfo<List<TbCourse>>(result,(int)coursePage.getTotal());
        objectTableDataInfo.setCode(200);
        objectTableDataInfo.setMsg("查询成功");
        objectTableDataInfo.setParams(null);
        return objectTableDataInfo;
    }

    /**
     * 课程下拉列表接口
     *
     * @param subject
     * @return
     */
    @Override
    public List<TbCourse> selectListSelect(Integer subject) {
        List<TbCourse> tbCourseList=tbCourseMapper.selectListSelect(subject);
        return tbCourseList;
    }

    /**
     * 批量删除课程管理接口
     *
     * @param ids
     */
    @Override
    public void deleteCourseByIdsids(List<Integer> ids) {
        //删除课程数据
        //删除课程表对应的商机数据(1对多)
        for (Integer id : ids) {
            //如果该课程存在对应的商机，则不能删除
            Integer count=tbCourseMapper.selectBusinessByCourseId(id);
            if(count==0||count==0){
                return;
            }
            //逻辑删除课程数据
            tbCourseMapper.updateDeleteFlagById(id);

        }
    }

    /**
     * 新增课程管理
     *
     * @param tbCourse
     */
    @Override
    public void insertCourse(TbCourse tbCourse) {
        tbCourseMapper.insert(tbCourse);
    }

    /**
     * 修改课程管理
     *
     * @param tbCourse
     */
    @Override
    public void updateCourse(TbCourse tbCourse) {
        tbCourseMapper.updateByCondithon(tbCourse);
    }
}




