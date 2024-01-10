package com.huike.clues.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.dto.TbClueAssignmentDTO;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.service.TbAssignRecordService;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.common.core.domain.entity.SysUserDTO;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 93238
 * @description 针对表【tb_assign_record(分配记录表)】的数据库操作Service实现
 * @createDate 2023-10-12 06:35:46
 */
@Service
public class TbAssignRecordServiceImpl extends ServiceImpl<TbAssignRecordMapper, TbAssignRecord>
        implements TbAssignRecordService {

    @Autowired
    private TbAssignRecordMapper tbAssignRecordMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 添加分配记录
     *
     * @param dto
     */
    @Override
    public void insert(TbClueAssignmentDTO dto) {
        Long userId = dto.getUserId();
        SysUserDTO sysUserDTO = sysUserMapper.selectUserById(userId);
        for (Long id : dto.getIds()) {
            QueryWrapper<TbAssignRecord> qw = new QueryWrapper<>();
            qw.lambda().eq(TbAssignRecord::getAssignId, id)
                    .eq(TbAssignRecord::getLatest, "1");
            List<TbAssignRecord> list = tbAssignRecordMapper.selectList(qw);
            if (list != null && list.size() != 0) {
                for (TbAssignRecord tbAssignRecord : list) {
                    tbAssignRecord.setLatest("0");
                    tbAssignRecordMapper.updateById(tbAssignRecord);
                }
            }
            TbAssignRecord tbAssignRecord = TbAssignRecord
                    .builder()
                    .assignId(id)
                    .userId(userId)
                    .userName(sysUserDTO.getUserName())
                    .deptId(sysUserDTO.getDeptId())
                    .createTime(DateUtils.getNowDate())
                    .createBy(SecurityUtils.getUsername())
                    .build();
            tbAssignRecordMapper.insert(tbAssignRecord);
        }
    }
}








































