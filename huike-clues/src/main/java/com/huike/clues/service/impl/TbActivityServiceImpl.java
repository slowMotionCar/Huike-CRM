package com.huike.clues.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.dto.TbActivityDTO;
import com.huike.clues.result.TableDataInfoActivityList;
import com.huike.clues.result.TableDataInfoActivityParams;
import com.huike.clues.service.TbActivityService;
import com.huike.clues.mapper.TbActivityMapper;
import com.huike.common.constant.HttpStatus;
import com.huike.common.core.page.PageDomain;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.uuid.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 93238
 * @description 针对表【tb_activity(活动管理)】的数据库操作Service实现
 * @createDate 2023-10-12 06:35:46
 */
@Service
@Slf4j
public class TbActivityServiceImpl extends ServiceImpl<TbActivityMapper, TbActivity> implements TbActivityService {
    @Autowired
    private TbActivityMapper tbActivityMapper;

    /**
     * 添加活动
     *
     * @param dto
     */
    @Override
    public void insert(TbActivityDTO dto) {
        TbActivity tbActivity = new TbActivity();
        String name = dto.getName();
        QueryWrapper<TbActivity> qw = new QueryWrapper<>();
        qw.lambda().eq(TbActivity::getName, name);
        Integer count = tbActivityMapper.selectCount(qw);
        if (count > 0) {
            throw new CustomException("名字被注册", HttpStatus.CONFLICT);
        }
        BeanUtils.copyProperties(dto, tbActivity);
        if (dto.getDiscount() != null) {
            tbActivity.setDiscount(Double.valueOf(dto.getDiscount().toString()));
        }
        tbActivity.setCreateTime(DateUtils.getNowDate());
        tbActivity.setCode(UUIDUtils.getUUID());
        tbActivityMapper.insert(tbActivity);
    }

    /**
     * 修改活动信息
     */
    @Override
    public void updateActivity(TbActivityDTO dto) {
        TbActivity tbActivity = new TbActivity();
        String name = dto.getName();
        QueryWrapper<TbActivity> qw = new QueryWrapper<>();
        qw.lambda().eq(TbActivity::getName, name);

        List<TbActivity> list = tbActivityMapper.selectList(qw);

        if (list != null && list.size() > 1) {
            throw new CustomException("名字被注册，无法修改", HttpStatus.CONFLICT);
        }
        if (list != null && list.size() == 1) {
            if (!list.get(0).getId().equals(dto.getId())) {
                throw new CustomException("名字被注册，无法修改", HttpStatus.CONFLICT);
            }
        }
        if (dto.getType().equals("1")) {
            dto.setVouchers(null);
        } else {
            dto.setDiscount(null);
        }
        BeanUtils.copyProperties(dto, tbActivity);
        log.info("\nservice{}\n", tbActivity);
        if (dto.getDiscount() != null) {
            tbActivity.setDiscount(Double.valueOf(dto.getDiscount().toString()));
        }
        tbActivityMapper.updateById(tbActivity);
    }

    /**
     * 分页条件查询
     *
     * @return
     */
    @Override
    public TableDataInfoActivityList<List<TbActivity>> find(PageDomain domain, String code, Integer channel) {
        QueryWrapper<TbActivity> qw = new QueryWrapper<>();
        qw.lambda().like(code != null, TbActivity::getCode, code);
        qw.lambda().eq(channel != null, TbActivity::getChannel, channel);

        Map<String, Object> params = domain.getParams();
        if (params != null && params.size() > 0) {
            String beginCreateTime = String.valueOf(params.get("beginCreateTime"));
            String endCreateTime = String.valueOf(params.get("endCreateTime"));
            String beginTime = String.valueOf(params.get("beginTime"));
            String endTime = String.valueOf(params.get("endTime"));
            if (beginCreateTime != null && endCreateTime != null && (!beginCreateTime.equals("")) && (!endCreateTime.equals(""))) {
                qw.lambda().between( TbActivity::getCreateTime, beginCreateTime, endCreateTime);
            }
            qw.lambda().gt(beginTime != null && !beginTime.equals(""), TbActivity::getBeginTime, beginTime)
                    .lt(endTime != null && !endTime.equals(""), TbActivity::getEndTime, endTime);
        }


        Page<TbActivity> page = new Page<>(domain.getPageNum(), domain.getPageSize());

        Page<TbActivity> p = tbActivityMapper.selectPage(page, qw);

        TableDataInfoActivityList<List<TbActivity>> list = new TableDataInfoActivityList<>();
        list.setCode(HttpStatus.SUCCESS);
        list.setMsg("success");
        list.setRows(p.getRecords());
        list.setTotal(p.getTotal());
        list.setParams(new TableDataInfoActivityParams());

        return list;
    }

    /**
     * 通过状态查询活动
     *
     * @return
     */
    @Override
    public List<TbActivity> findStatus(Integer status) {
        QueryWrapper<TbActivity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TbActivity::getStatus, status);
        return tbActivityMapper.selectList(queryWrapper);
    }

    /**
     * 通过ids 删除活动
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        TbActivity tbActivity = tbActivityMapper.selectById(id);
        Date beginTime = tbActivity.getBeginTime();
        Date endTime = tbActivity.getEndTime();
        Date nowDate = DateUtils.getNowDate();
        if (beginTime.before(nowDate) && endTime.after(nowDate)) {
            throw new CustomException("活动期间禁止下架活动");
        }
        tbActivityMapper.deleteById(id);
    }

    /**
     * 通过id查询活动信息
     *
     * @param id
     * @return
     */
    @Override
    public TbActivity findById(Long id) {
        return tbActivityMapper.selectById(id);
    }
}




