package com.huike.clues.mapper;

import com.huike.clues.domain.TbClueTrackRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 93238
* @description 针对表【tb_clue_track_record(线索跟进记录)】的数据库操作Mapper
* @createDate 2023-10-12 06:35:46
* @Entity com.huike.clues.domain.TbClueTrackRecord
*/
@Mapper
public interface TbClueTrackRecordMapper extends BaseMapper<TbClueTrackRecord> {

    @Select("select * from tb_clue_track_record where id=#{id}")
    TbClueTrackRecord selectClueTrackRecordDetail(Integer id);

    @Select("select * from tb_clue_track_record where clue_id=#{clueId} ")
    List<TbClueTrackRecord> selectClueTrackRecordList(Integer clueId);

    void insertClueTrackRecord(TbClueTrackRecord tbClueTrackRecord);
}




