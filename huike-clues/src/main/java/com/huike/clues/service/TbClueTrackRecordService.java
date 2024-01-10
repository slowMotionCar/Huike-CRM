package com.huike.clues.service;

import com.huike.clues.domain.TbClueTrackRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.dto.TbClueTrackRecordDTO;

import java.util.List;

/**
* @author 93238
* @description 针对表【tb_clue_track_record(线索跟进记录)】的数据库操作Service
* @createDate 2023-10-12 06:35:46
*/
public interface TbClueTrackRecordService extends IService<TbClueTrackRecord> {
    /**
     *
     * @param id
     * @return
     */
    TbClueTrackRecord selectClueTrackRecordDetail(Integer id);

    /**
     * 线索跟进记录列表
     * @param clueId
     * @return
     */
    List<TbClueTrackRecord> selectClueTrackRecordList(Integer clueId);

    /**
     * 新增线索跟进记录接口
     * @param tbClueTrackRecordDTO
     */
    void insertClueTrackRecord(TbClueTrackRecordDTO tbClueTrackRecordDTO);
}
