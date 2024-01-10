package com.huike.clues.mapper;

import com.huike.clues.domain.TbAssignRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 93238
* @description 针对表【tb_assign_record(分配记录表)】的数据库操作Mapper
* @createDate 2023-10-12 06:35:46
* @Entity com.huike.clues.domain.TbAssignRecord
*/
@Mapper
public interface TbAssignRecordMapper extends BaseMapper<TbAssignRecord> {

    void insertTbAssignRecord(TbAssignRecord tbAssignRecord);

    /**
     * 根据id查询分类记录
     * @param assignId
     * @return
     */
    TbAssignRecord selectAssignRecordByAssignId(Long assignId);

    Integer countClueNumByUserId(Long userId);

    Integer countBusinessNumByUserId(Long userId);

    List<TbAssignRecord> selectByCondition(TbAssignRecord tbAssignRecord);

    void updateTbAssignRecord(TbAssignRecord assignRecord);
}




