package com.huike.clues.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.domain.dto.TbClueAssignmentDTO;
import com.huike.clues.domain.dto.TbClueDTO;
import com.huike.clues.domain.dto.TbFalseClue;
import com.huike.clues.mapper.TbActivityMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueTrackRecordMapper;
import com.huike.clues.service.TbAssignRecordService;
import com.huike.clues.service.TbClueService;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 93238
 * @description 针对表【tb_clue(线索)】的数据库操作Service实现
 * @createDate 2023-10-12 06:35:46
 */
@Service
public class TbClueServiceImpl extends ServiceImpl<TbClueMapper, TbClue>
        implements TbClueService {

    @Autowired
    private TbClueMapper tbClueMapper;

    /**
     * 添加新的线索
     *
     * @param dto
     */
    @Override
    public void insert(TbClueDTO dto) {
        TbClue tbClue = new TbClue();
        BeanUtils.copyProperties(dto, tbClue);
        tbClue.setCreateBy(SecurityUtils.getUsername());
        tbClue.setCreateTime(DateUtils.getNowDate());
        tbClue.setUpdateTime(DateUtils.getNowDate());
        tbClueMapper.insert(tbClue);
    }

    @Autowired
    private TbActivityMapper tbActivityMapper;
    @Autowired
    private TbClueTrackRecordMapper tbClueTrackRecordMapper;

    /**
     * 通过id查询线索信息
     *
     * @param id
     * @return
     */
    @Override
    public TbClueDTO findById(Long id) {
        TbClue tbClue = tbClueMapper.selectById(id);
        TbClueDTO tbClueDTO = new TbClueDTO();
        BeanUtils.copyProperties(tbClue, tbClueDTO);
        Long activityId = tbClueDTO.getActivityId();
        TbActivity tbActivity = tbActivityMapper.selectById(activityId);
        tbClueDTO.setActivityName(tbActivity.getName());
        tbClueDTO.setActivityInfo(tbActivity.getInfo());
        return tbClueDTO;
    }


    /**
     * 修改线索
     * todo 修改信息不明，不知道如何写
     *
     * @param dto
     */
    @Override
    public void update(TbClueDTO dto) {
        TbClue tbClue = new TbClue();
        BeanUtils.copyProperties(dto, tbClue);
        tbClue.setUpdateTime(DateUtils.getNowDate());


        tbClueMapper.updateById(tbClue);
    }

    /**
     * 分页查询
     *
     * @param dto @return
     */
    @Override
    public List<TbClueDTO> find(TbClueDTO dto) {
        return tbClueMapper.findPage(dto);

    }

    @Autowired
    private TbAssignRecordService tbAssignRecordService;

    /**
     * ## 批量分配
     *
     * @param dto
     */
    @Override
    public void assignment(TbClueAssignmentDTO dto) {
        tbAssignRecordService.insert(dto);

        List<Long> ids = dto.getIds();
        for (Long id : ids) {
            TbClue tbClue = tbClueMapper.selectById(id);
            tbClue.setUpdateTime(DateUtils.getNowDate());
            tbClue.setStatus("1");

            tbClueMapper.updateById(tbClue);
        }
    }

    @Autowired
    private TbAssignRecordMapper tbAssignRecordMapper;

    /**
     * 伪线索
     *
     * @param id
     * @param tbFalseClue
     */
    @Override
    public void falseClue(Long id, TbFalseClue tbFalseClue) {
        TbClue tbClue = tbClueMapper.selectById(id);
        tbClue.setStatus("4");
        tbClue.setUpdateTime(DateUtils.getNowDate());
        if (tbClue.getFalseCount() == 3) {
            tbClue.setEndTime(DateUtils.getNowDate());
        }
        tbClue.setFalseCount(tbClue.getFalseCount() + 1);
        tbClueMapper.updateById(tbClue);


        QueryWrapper<TbAssignRecord> qw = new QueryWrapper<>();
        qw.lambda().eq(TbAssignRecord::getLatest, "1")
                .eq(TbAssignRecord::getAssignId, id)
                .eq(TbAssignRecord::getType, "0");
        TbAssignRecord tbAssignRecord = tbAssignRecordMapper.selectOne(qw);


        TbClueTrackRecord tbClueTrackRecord = new TbClueTrackRecord();
        tbClueTrackRecord.setClueId(id);
        tbClueTrackRecord.setCreateBy(tbAssignRecord.getUserName());
        tbClueTrackRecord.setSubject(tbClue.getSubject());
        tbClueTrackRecord.setRecord(tbFalseClue.getRemark());
        tbClueTrackRecord.setLevel(tbClue.getLevel());
        tbClueTrackRecord.setCreateTime(DateUtils.getNowDate());
        tbClueTrackRecord.setType("1");
        tbClueTrackRecord.setFalseReason(tbFalseClue.getReason());

        tbClueTrackRecordMapper.insert(tbClueTrackRecord);
    }


    /**
     * 线索转商机
     *
     * @param id
     * @return
     */
    @Override
    public TbClueDTO delete(Long id) {
        TbClue tbClue = tbClueMapper.selectById(id);
        TbClueDTO tbClueDTO = new TbClueDTO();
        BeanUtils.copyProperties(tbClue, tbClueDTO);
        return tbClueDTO;
    }

    /**
     * 捞取
     * @param dto
     */
    @Override
    public void gain(TbClueAssignmentDTO dto) {
        tbAssignRecordService.insert(dto);

        List<Long> ids = dto.getIds();
        for (Long id : ids) {
            TbClue tbClue = tbClueMapper.selectById(id);
            tbClue.setUpdateTime(DateUtils.getNowDate());
            tbClue.setStatus("1");

            tbClueMapper.updateById(tbClue);
        }
    }

    /**
     * 查询线索池
     * @param dto
     * @return
     */
    @Override
    public List<TbClueDTO> findPool(TbClueDTO dto) {
        return tbClueMapper.findPagePool(dto);
    }
}

















































































































