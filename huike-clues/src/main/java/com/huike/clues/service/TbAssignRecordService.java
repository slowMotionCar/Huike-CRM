package com.huike.clues.service;

import com.huike.clues.domain.TbAssignRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.dto.TbClueAssignmentDTO;

/**
* @author 93238
* @description 针对表【tb_assign_record(分配记录表)】的数据库操作Service
* @createDate 2023-10-12 06:35:46
*/
public interface TbAssignRecordService extends IService<TbAssignRecord> {

    /**
     * 添加分配记录
     * @param dto
     */
    void insert(TbClueAssignmentDTO dto);
}
