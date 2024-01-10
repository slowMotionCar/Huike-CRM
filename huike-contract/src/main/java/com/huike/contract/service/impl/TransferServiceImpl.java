package com.huike.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.clues.domain.SysUser;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.clues.mapper.TbRulePoolMapper;
import com.huike.clues.utils.HuiKeCrmDateUtils;
import com.huike.common.core.domain.entity.SysUserDTO;
import com.huike.common.utils.DateUtils;
import com.huike.contract.domain.vo.TransferVo;
import com.huike.contract.mapper.TransferMapper;
import com.huike.contract.result.TransferAssignmentData;
import com.huike.contract.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TransferServiceImpl
 * @Author xhr
 * @Date 2023-10-17
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferMapper transferMapper;
    private final SysUserMapper sysUserMapper;
    private final TbAssignRecordMapper tbAssignRecordMapper;
    private final TbRulePoolMapper tbRulePoolMapper;
    private final TbClueMapper tbClueMapper;
    private final TbBusinessMapper tbBusinessMapper;

    /**
     * 转派分页查询
     *
     * @param sysUser
     * @return
     */
    @Override
    public List<TransferVo> selectTransferList(SysUserDTO sysUser) {

        //查询用户列表
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(sysUser.getUserName() != null && !sysUser.getUserName().equals(""), "user_name", sysUser.getUserName())
                .like(sysUser.getPhonenumber() != null && !sysUser.getPhonenumber().equals(""), "phonenumber", sysUser.getPhonenumber())
                .gt(sysUser.getParams().get("beginTime") != null && sysUser.getParams().get("beginTime") != "", "create_time", sysUser.getParams().get("beginTime"))
                .lt(sysUser.getParams().get("endTime") != null && sysUser.getParams().get("endTime") != "", "create_time", sysUser.getParams().get("endTime"));
        List<SysUser> users = sysUserMapper.selectList(queryWrapper);

        List<TransferVo> VOlist = users.stream().map(user -> {
            TransferVo vo = new TransferVo();
            BeanUtils.copyProperties(user, vo);

            vo.setClueNum(tbAssignRecordMapper.countClueNumByUserId(user.getUserId()));
            vo.setBusinessNum(tbAssignRecordMapper.countBusinessNumByUserId(user.getUserId()));

            return vo;
        }).collect(Collectors.toList());

        return VOlist;
    }

    /**
     * 转派处理
     *
     * @param transferUserId
     * @param type
     * @param userId
     * @return
     */
    @Override
    public TransferAssignmentData assignment(Long transferUserId, String type, Long userId) {
        //创建返回对象
        TransferAssignmentData assignment = new TransferAssignmentData();

        //如果是线索
        if ("0".equals(type)) {
            //根据用户id获取线索数量
            Integer clueNum = tbAssignRecordMapper.countClueNumByUserId(userId);
            if (clueNum > 0) {
                //获取线索池的线索对象
                TbRulePool tbRulePool = tbRulePoolMapper.selectTbRulePoolByType(type);
                //获取线索转派人保有的线索数量
                Integer transferUserClueNum = tbAssignRecordMapper.countClueNumByUserId(transferUserId);
                //当转派人数量达到最大保有量
                if (transferUserClueNum >= tbRulePool.getMaxNunmber()) {
                    assignment.setFlag(false);
                    assignment.setMsg("线索转换失败，转派人数量达到最大保有量");
                } else {
                    // 转派人未达到最大线索持有数量
                    // 创建跟进记录
                    TbAssignRecord tbAssignRecord = new TbAssignRecord();
                    tbAssignRecord.setUserId(userId);
                    tbAssignRecord.setType(type);
                    tbAssignRecord.setLatest("1");
                    //通过跟进记录查询用户的所有线索
                    List<TbAssignRecord> list = tbAssignRecordMapper.selectByCondition(tbAssignRecord);
                    int i = 0;
                    for (TbAssignRecord assignRecord : list) {

                        i++;
                        // HttpStatus
                        //查询转派用户
                        SysUserDTO sysUserDTO = sysUserMapper.selectUserById(transferUserId);
                        assignRecord.setUserId(transferUserId);
                        assignRecord.setDeptId(sysUserDTO.getDeptId());
                        assignRecord.setUserName(sysUserDTO.getUserName());
                        assignRecord.setCreateTime(DateUtils.getNowDate());
                        //更新分配记录表
                        tbAssignRecordMapper.updateTbAssignRecord(assignRecord);
                        //状态修改为待跟进
                        String status = "1";
                        //更新转派人线索
                        Long assignId = assignRecord.getAssignId();
                        tbClueMapper.updateTransfer(assignId, status);
                        //获取结束时间
                        Date endDate = HuiKeCrmDateUtils.getEndDateByRule(assignRecord);
                        //更新线索最后时间
                        tbClueMapper.updateClueEndTimeById(tbAssignRecord.getAssignId(), endDate);
                        //当转派人数量达到最大保有量
                        if (transferUserClueNum + i >= tbRulePool.getMaxNunmber()) {
                            assignment.setFlag(false);
                            assignment.setMsg("线索转换失败！已经分配" + i + " 线索");
                            break;
                        }
                    }
                }
            }
        }
        //如果是商机
        if ("1".equals(type)) {
                Integer businessNum = tbAssignRecordMapper.countBusinessNumByUserId(userId);
                if (businessNum >= 0) {
                    TbRulePool rulePool = tbRulePoolMapper.selectTbRulePoolByType(type);
                    Integer transferUserBusinessNum = tbAssignRecordMapper.countBusinessNumByUserId(transferUserId);

                    //被转派人保有量达到最大值
                    if (transferUserBusinessNum >= rulePool.getMaxNunmber()) {
                        assignment.setFlag(false);
                        assignment.setMsg("商机转换失败，转派人数量达到最大保有量");
                    } else {
                        TbAssignRecord tbAssignRecord = new TbAssignRecord();
                        tbAssignRecord.setUserId(userId);
                        //是否当前最新分配人
                        tbAssignRecord.setLatest("1");
                        tbAssignRecord.setType("1");
                        List<TbAssignRecord> list = tbAssignRecordMapper.selectByCondition(tbAssignRecord);
                        int i = 0;
                        for (TbAssignRecord assignRecord : list) {
                            i++;
                            //根据transferUserId查询用户对象
                            SysUserDTO sysUserDTO = sysUserMapper.selectUserById(transferUserId);
                            tbAssignRecord.setUserId(transferUserId);
                            tbAssignRecord.setUserName(sysUserDTO.getUserName());
                            tbAssignRecord.setDeptId(sysUserDTO.getDeptId());
                            tbAssignRecord.setCreateTime(DateUtils.getNowDate());
                            //更新分配记录表
                            tbAssignRecordMapper.updateTbAssignRecord(assignRecord);
                            Date endDate = HuiKeCrmDateUtils.getEndDateByRule(tbAssignRecord);
                            //更新商机最后时间
                            tbBusinessMapper.updateBusinessEndTimeById(tbAssignRecord.getAssignId(), endDate);
                            if (transferUserBusinessNum + i >= rulePool.getMaxNunmber()) {
                                assignment.setFlag(false);
                                assignment.setMsg("商机转换失败！已经分配" + i + " 商机");
                                break;
                            }
                        }
                    }
                }
            }
        assignment.setFlag(true);
        assignment.setMsg("");
        return assignment;
    }
}