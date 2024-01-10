package com.huike.clues.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.domain.dto.TbClueTrackRecordDTO;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.service.TbClueTrackRecordService;
import com.huike.clues.mapper.TbClueTrackRecordMapper;
import com.huike.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
* @author 93238
* @description 针对表【tb_clue_track_record(线索跟进记录)】的数据库操作Service实现
* @createDate 2023-10-12 06:35:46
*/
@Service
public class TbClueTrackRecordServiceImpl extends ServiceImpl<TbClueTrackRecordMapper, TbClueTrackRecord>
    implements TbClueTrackRecordService{
    @Autowired
    private TbClueTrackRecordMapper tbClueTrackRecordMapper;
    @Autowired
    private TbAssignRecordMapper tbAssignRecordMapper;
    /**
     * @param id
     * @return
     */
    @Override
    public TbClueTrackRecord selectClueTrackRecordDetail(Integer id) {
        TbClueTrackRecord tbClueTrackRecord=tbClueTrackRecordMapper.selectClueTrackRecordDetail(id);
        return tbClueTrackRecord;
    }

    /**
     * 查询线索跟进记录列表
     *
     * @param clueId
     * @return
     */
    @Override
    public List<TbClueTrackRecord> selectClueTrackRecordList(Integer clueId) {
        List<TbClueTrackRecord> tbClueTrackRecordList=tbClueTrackRecordMapper.selectClueTrackRecordList(clueId);
        return tbClueTrackRecordList;
    }

    /**
     * 新增线索跟进记录接口
     *
     * @param tbClueTrackRecordDTO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertClueTrackRecord(TbClueTrackRecordDTO tbClueTrackRecordDTO) {
        //线索跟进记录表
        TbClueTrackRecord tbClueTrackRecord = new TbClueTrackRecord();
        BeanUtils.copyProperties(tbClueTrackRecordDTO,tbClueTrackRecord);
        //补充数据
        tbClueTrackRecord.setCreateTime(new Date());
        tbClueTrackRecord.setCreateBy(SecurityUtils.getUsername());
        //写入数据库
        tbClueTrackRecordMapper.insertClueTrackRecord(tbClueTrackRecord);

        //业务逻辑错误
        /*//写入跟进记录表
        TbAssignRecord tbAssignRecord = new TbAssignRecord();
        BeanUtils.copyProperties(tbClueTrackRecordDTO,tbAssignRecord);
        tbAssignRecord.setLatest("1");
        tbAssignRecord.setCreateTime(new Date());
        tbAssignRecord.setAssignId(tbClueTrackRecordDTO.getClueId());
        tbAssignRecord.setUserId(SecurityUtils.getUserId());
        tbAssignRecord.setUserName(SecurityUtils.getUsername());
        tbAssignRecord.setDeptId(SecurityUtils.getDeptId());
        tbAssignRecordMapper.insertTbAssignRecord(tbAssignRecord);*/
    }
}




