package com.huike.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.business.domain.TbBusiness;
import com.huike.business.domain.TbBusinessTrackRecord;
import com.huike.business.domain.dto.TbBusinessTrackRecordDTO;
import com.huike.business.domain.vo.BusinessTrackVo;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.business.service.TbBusinessTrackRecordService;
import com.huike.business.mapper.TbBusinessTrackRecordMapper;
import com.huike.clues.domain.SysDictData;
import com.huike.clues.domain.TbClue;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.common.exception.BaseException;
import com.huike.common.utils.bean.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 93238
 * @description 针对表【tb_business_track_record(商机跟进记录)】的数据库操作Service实现
 * @createDate 2023-10-12 06:08:37
 */
@Service
public class TbBusinessTrackRecordServiceImpl extends ServiceImpl<TbBusinessTrackRecordMapper, TbBusinessTrackRecord>
        implements TbBusinessTrackRecordService {

    @Resource
    private TbBusinessTrackRecordMapper tbBusinessTrackRecordMapper;
    @Resource
    private TbBusinessMapper tbBusinessMapper;
    @Resource
    private SysDictDataMapper dictDataMapper;
    @Resource
    private TbAssignRecordMapper tbAssignRecordMapper;
    @Resource
    private TbClueMapper tbClueMapper;

    /**
     * 新增商机跟进记录
     * @param tbBusinessTrackRecordDTO
     */
    @Override
    public void addBusinessTrackRecord(TbBusinessTrackRecordDTO tbBusinessTrackRecordDTO) {
        TbBusinessTrackRecord tbBusinessTrackRecord = new TbBusinessTrackRecord();
        BeanUtils.copyProperties(tbBusinessTrackRecordDTO, tbBusinessTrackRecord);
        tbBusinessTrackRecord.setBusinessId(String.valueOf(tbBusinessTrackRecordDTO.getBusinessId()));
        tbBusinessTrackRecord.setTrackStatus("1");
        // 新增商机跟进记录
        tbBusinessTrackRecordMapper.insert(tbBusinessTrackRecord);
        // TODO 修改商机
        /*TbBusiness tbBusiness = new TbBusiness();
        BeanUtils.copyProperties(tbBusinessTrackRecordDTO, tbBusiness);
        tbBusiness.setId(Long.valueOf(tbBusinessTrackRecord.getBusinessId()));
        tbBusinessMapper.updateById(tbBusiness);*/
    }

    /**
     * 查询商机跟进记录
     * @param businessId
     * @return
     */
    @Override
    public BusinessTrackVo selectBusinessTrackRecord(Long businessId) {
        BusinessTrackVo businessTrackVo = new BusinessTrackVo();

        // 查询商机信息
        TbBusiness tbBusiness = tbBusinessMapper.selectById(businessId);
        if (tbBusiness == null) {
            throw new BaseException("查询的商机跟进记录不存在");
        }
        BeanUtils.copyProperties(tbBusiness, businessTrackVo);

        // 查询商机跟进记录
        LambdaQueryWrapper<TbBusinessTrackRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 定义查询条件
        lambdaQueryWrapper.eq(TbBusinessTrackRecord::getBusinessId, businessId)
                .orderByDesc(TbBusinessTrackRecord::getId)
                .last("limit 1");
        TbBusinessTrackRecord tbBusinessTrackRecord = tbBusinessTrackRecordMapper.selectOne(lambdaQueryWrapper);
        if (tbBusinessTrackRecord != null) {
            // 补充所需字段信息
            businessTrackVo.setId(tbBusinessTrackRecord.getId());
            businessTrackVo.setKeyItems(tbBusinessTrackRecord.getKeyItems());
            businessTrackVo.setRecord(tbBusinessTrackRecord.getRecord());
        }

        // 查询数字字典
        String keyItemStr = tbBusinessTrackRecord.getKeyItems();
        String[] keys = null;
        Map<String, Object> map = new HashMap<>();
        if (keyItemStr.contains(",")) {
            String[] keyItems = keyItemStr.split(",");
            keys = new String[keyItems.length];
            for (int i = 0; i < keys.length; i++) {
                map.put("dictValue", keys[i]);
                map.put("dictType", "communication_point");
                List<SysDictData> sysDictDataList = dictDataMapper.selectDictDataByMap(map);
                SysDictData sysDictData = sysDictDataList.get(0);
                keys[i] = sysDictData.getDictLabel();
            }
        } else {
            map.put("dictValue", keyItemStr);
            map.put("dictType", "communication_point");
            List<SysDictData> sysDictDataList = dictDataMapper.selectDictDataByMap(map);
            SysDictData sysDictData = sysDictDataList.get(0);
            keys = new String[1];
            keys[0] = sysDictData.getDictLabel();
        }

        // 补充所需字段信息
        List<String> keyList = Arrays.asList(keys);
        System.out.println(keyList);
        businessTrackVo.setKeys(keyList);

        return businessTrackVo;
    }

    /**
     * 获取商机跟进记录详细信息
     * @param id
     * @return
     */
    @Override
    public BusinessTrackVo selectBusinessTrackRecordDetail(Long id) {
        // 1. 查询商机跟进记录表
        TbBusinessTrackRecord tbBusinessTrackRecord = tbBusinessTrackRecordMapper.selectById(id);
        BusinessTrackVo businessTrackVo = new BusinessTrackVo();
        // 填充数据
        BeanUtils.copyProperties(tbBusinessTrackRecord, businessTrackVo);

        // 2. 查询商机表
        TbBusiness tbBusiness = tbBusinessMapper.selectBusinessById(tbBusinessTrackRecord.getBusinessId());
        // 填充数据
        BeanUtils.copyProperties(tbBusiness, businessTrackVo);

        // 3. 查询线索表
        LambdaQueryWrapper<TbClue> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(TbClue::getPhone, tbBusiness.getPhone());
        TbClue tbClue = tbClueMapper.selectOne(lambdaQueryWrapper2);
        if (tbClue != null) {
            // 填充数据
            businessTrackVo.setFalseCount(tbClue.getFalseCount());
        }
        return businessTrackVo;
    }
}




