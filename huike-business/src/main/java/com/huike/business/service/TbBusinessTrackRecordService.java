package com.huike.business.service;

import com.huike.business.domain.TbBusinessTrackRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.business.domain.dto.TbBusinessDTODTO;
import com.huike.business.domain.dto.TbBusinessTrackRecordDTO;
import com.huike.business.domain.vo.BusinessTrackVo;

/**
* @author 93238
* @description 针对表【tb_business_track_record(商机跟进记录)】的数据库操作Service
* @createDate 2023-10-12 06:08:37
*/
public interface TbBusinessTrackRecordService extends IService<TbBusinessTrackRecord> {

    void addBusinessTrackRecord(TbBusinessTrackRecordDTO tbBusinessTrackRecordDTO);

    BusinessTrackVo selectBusinessTrackRecord(Long businessId);

    BusinessTrackVo selectBusinessTrackRecordDetail(Long id);
}
