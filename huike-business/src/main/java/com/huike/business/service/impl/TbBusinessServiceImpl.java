package com.huike.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.business.domain.TbBusiness;
import com.huike.business.domain.dto.TbBusinessAssingDTO;
import com.huike.business.domain.dto.TbBusinessDTODTO;
import com.huike.business.domain.dto.TbBusinessGainDTO;
import com.huike.business.service.TbBusinessService;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.clues.domain.SysDictData;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.common.core.domain.entity.SysUserDTO;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.exception.BaseException;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.bean.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 93238
 * @description 针对表【tb_business(商机)】的数据库操作Service实现
 * @createDate 2023-10-12 06:08:37
 */
@Service
public class TbBusinessServiceImpl extends ServiceImpl<TbBusinessMapper, TbBusiness> implements TbBusinessService {

    @Resource
    private TbBusinessMapper tbBusinessMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private TbAssignRecordMapper tbAssignRecordMapper;
    @Resource
    private SysDictDataMapper sysDictDataMapper;
    @Resource
    private TbClueMapper tbClueMapper;

    /**
     * 新增商机
     * @param tbBusinessDTODTO
     */
    @Override
    public void insertBusiness(TbBusinessDTODTO tbBusinessDTODTO) {
        TbBusiness tbBusiness = new TbBusiness();
        BeanUtils.copyProperties(tbBusinessDTODTO, tbBusiness);
        tbBusiness.setCreateBy(String.valueOf(SecurityUtils.getUsername())); // 设置“创建人”
        tbBusiness.setCreateTime(new Date()); // 设置“创建时间”
        tbBusinessMapper.insert(tbBusiness);
    }

    /**
     * 分页查询商机列表
     * @param tbBusinessDTODTO
     * @return
     */
    @Override
    public List<TbBusiness> pageSelectBusiness(TbBusinessDTODTO tbBusinessDTODTO) {
        // 1. 获取分页查询数据
        Long id = tbBusinessDTODTO.getId();
        String phone = tbBusinessDTODTO.getPhone();
        String name = tbBusinessDTODTO.getName();
        String status = tbBusinessDTODTO.getStatus();

        Map<String, Object> params = tbBusinessDTODTO.getParams();
        String beginCreateTime = (String) params.get("beginCreateTime");
        String endCreateTime = (String) params.get("endCreateTime");

        // 创建分页查询条件
        LambdaQueryWrapper<TbBusiness> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(id != null, TbBusiness::getId, id)
                .eq(phone != null, TbBusiness::getPhone, phone)
                .like(name != null, TbBusiness::getName, name)
                .eq(status != null, TbBusiness::getStatus, status)
                .le(status == null, TbBusiness::getStatus, 2);
        if (beginCreateTime != null && beginCreateTime != ""
                && endCreateTime != null && endCreateTime != "") {
            lambdaQueryWrapper.ge(TbBusiness::getCreateTime, beginCreateTime);
            lambdaQueryWrapper.le(TbBusiness::getCreateTime, endCreateTime);
        }
        lambdaQueryWrapper.orderByDesc(TbBusiness::getCreateTime);

        // 3. 调用selectList方法获取商机列表
        List<TbBusiness> tbBusinessList = tbBusinessMapper.selectList(lambdaQueryWrapper);

        // 4. 填充字段falseCount
        for (TbBusiness tbBusiness : tbBusinessList) {
            LambdaQueryWrapper<TbClue> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(tbBusiness.getPhone() != null, TbClue::getPhone, tbBusiness.getPhone());
            TbClue tbClue = tbClueMapper.selectOne(queryWrapper);
            if (tbClue != null) {
                tbBusiness.setFalseCount(tbClue.getFalseCount());
            }
        }

        // 5. 填充字段owner
        for (TbBusiness tbBusiness : tbBusinessList) {
            LambdaQueryWrapper<TbAssignRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(tbBusiness.getId() != null, TbAssignRecord::getAssignId, tbBusiness.getId())
                    .orderByDesc(TbAssignRecord::getCreateTime)
                    .last("limit 1");
            TbAssignRecord tbAssignRecord = tbAssignRecordMapper.selectOne(queryWrapper);
            if (tbAssignRecord != null) {
                SysUserDTO sysUserDTO = sysUserMapper.selectUserById(tbAssignRecord.getUserId());
                if (sysUserDTO != null) {
                    tbBusiness.setOwner(sysUserDTO.getUserName());
                }
            }
        }

        return tbBusinessList;
    }

    /**
     * 修改商机
     * @param tbBusinessDTODTO
     */
    @Override
    public void updateBusiness(TbBusinessDTODTO tbBusinessDTODTO) {
        TbBusiness tbBusiness = new TbBusiness();
        BeanUtils.copyProperties(tbBusinessDTODTO, tbBusiness);
        /*LambdaQueryWrapper<TbBusiness> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(tbBusiness.getId() != null, TbBusiness::getId, tbBusiness.getId());
        tbBusinessMapper.update(tbBusiness, lambdaQueryWrapper);*/
        tbBusinessMapper.updateById(tbBusiness);
    }

    /**
     * 商机分配
     * @param tbBusinessAssingDTO
     */
    @Override
    public void assignBusiness(TbBusinessAssingDTO tbBusinessAssingDTO) {
        Long[] ids = tbBusinessAssingDTO.getIds();
        Long userId = tbBusinessAssingDTO.getUserId();
        for (Long id : ids) {
            TbBusiness tbBusiness = tbBusinessMapper.selectById(id);
            if (tbBusiness == null) {
                throw new BaseException("分配的商机不存在");
            }
            // 查询用户表
            SysUserDTO sysUserDTO = sysUserMapper.selectUserById(userId);
            TbAssignRecord tbAssignRecord = TbAssignRecord.builder()
                    .assignId(id)
                    .userId(userId)
                    .userName(sysUserDTO.getUserName())
                    .deptId(sysUserDTO.getDeptId())
                    .latest(String.valueOf(userId))
                    .createTime(DateUtils.getNowDate())
                    .createBy(SecurityUtils.getUsername())
                    .type("1")
                    .build();
            tbAssignRecordMapper.insert(tbAssignRecord);
        }
    }

    /**
     * 删除商机
     * @param ids
     */
    @Override
    public void deleteBusinessByIds(String ids) {
        tbBusinessMapper.deleteById(ids);
    }

    /**
     * 查询商机跟进记录
     * @return
     */
    @Override
    public TbBusiness selectBusinessDetail(Integer id) {
        TbBusiness tbBusiness = tbBusinessMapper.selectById(id);

        if (tbBusiness != null) {
            LambdaQueryWrapper<TbClue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(tbBusiness.getPhone() != null, TbClue::getPhone, tbBusiness.getPhone());
            TbClue tbClue = tbClueMapper.selectOne(lambdaQueryWrapper);
            if (tbClue != null) {
                tbBusiness.setFalseCount(tbClue.getFalseCount());
            }
        }
        return tbBusiness;
    }

    /**
     * 踢回公海
     * @param id
     * @param reason
     */
    @Override
    public void backPoll(Integer id, Long reason) {
        // 查询字典数据
        LambdaQueryWrapper<SysDictData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDictData::getDictType, "reasons_for_business_reporting")
                .eq(SysDictData::getDictValue, reason);
        SysDictData sysDictData = sysDictDataMapper.selectOne(lambdaQueryWrapper);
        TbBusiness tbBusiness = null;
        if (sysDictData != null) {
            tbBusiness = TbBusiness.builder()
                    .id(Long.valueOf(id))
                    .status("3")
                    .reasons(sysDictData.getDictLabel())
                    .build();
        }
        tbBusinessMapper.updateById(tbBusiness);
    }

    /**
     * 查询公海接口
     * @param tbBusinessDTODTO
     * @return
     */
    @Override
    public List<TbBusiness> pageSelectPool(TbBusinessDTODTO tbBusinessDTODTO) {
        // 1. 获取分页所需数据
        Long id = tbBusinessDTODTO.getId();
        String name = tbBusinessDTODTO.getName();
        String phone = tbBusinessDTODTO.getPhone();
        String subject = tbBusinessDTODTO.getSubject();

        Map<String, Object> params = tbBusinessDTODTO.getParams();
        String beginCreateTime = (String) params.get("beginCreateTime");
        String endCreateTime = (String) params.get("endCreateTime");

        // 2. 创建分页所需条件
        LambdaQueryWrapper<TbBusiness> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(id != null, TbBusiness::getId, id)
                .eq(phone != null, TbBusiness::getPhone, phone)
                .eq(subject != null, TbBusiness::getSubject, subject)
                .like(name != null, TbBusiness::getName, name)
                .ge(TbBusiness::getStatus, 3);
        if (beginCreateTime != null && beginCreateTime != ""
                && endCreateTime != null && endCreateTime != "") {
            lambdaQueryWrapper.ge(TbBusiness::getCreateTime, beginCreateTime);
            lambdaQueryWrapper.le(TbBusiness::getCreateTime, endCreateTime);
        }
        lambdaQueryWrapper.orderByDesc(TbBusiness::getCreateTime);

        // 3. 调用selectList查询商机列表
        List<TbBusiness> tbBusinessList = tbBusinessMapper.selectList(lambdaQueryWrapper);

        return tbBusinessList;
    }

    /**
     * 批量捞取
     * @param tbBusinessGainDTO
     */
    @Override
    public void gain(TbBusinessGainDTO tbBusinessGainDTO) {
        Integer[] ids = tbBusinessGainDTO.getIds();
        for (Integer id : ids) {
            TbBusiness tbBusinessDB = tbBusinessMapper.selectById(id);
            if (tbBusinessDB == null) {
                throw new BaseException("捞取的商机不存在");
            }
            TbBusiness tbBusiness = TbBusiness.builder()
                    .id(Long.valueOf(id))
                    .status("1")
                    .build();
            tbBusinessMapper.updateById(tbBusiness);
        }
    }
}




