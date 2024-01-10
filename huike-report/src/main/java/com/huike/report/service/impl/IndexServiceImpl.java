package com.huike.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huike.business.domain.TbBusiness;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.clues.domain.SysUser;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.vo.VulnerabilityMapVOnew;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.common.exception.BaseException;
import com.huike.contract.domain.TbContract;
import com.huike.contract.mapper.TbContractMapper;
import com.huike.report.domain.vo.ChangeStatisticsVO;
import com.huike.report.domain.vo.IndexBaseInfoVO;
import com.huike.report.domain.vo.IndexTodayInfoVO;
import com.huike.report.domain.vo.IndexTodoInfoVO;
import com.huike.report.service.IndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @Description IndexServiceImpl
 * @Author Zhilin
 * @Date 2023-10-17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private final TbClueMapper tbClueMapper;
    private final TbBusinessMapper tbBusinessMapper;
    private final TbContractMapper tbContractMapper;
    private final SysUserMapper sysUserMapper;
    private final TbAssignRecordMapper tbAssignRecordMapper;


    /**
     * 首页今日简报数据
     *
     * @return
     */
    @Override
    public IndexTodayInfoVO getTodayInfo() {

        LocalDate now = LocalDate.now();

        // start
        LocalDateTime begin = LocalDateTime.of(now, LocalTime.MIN);
        // end
        LocalDateTime end = LocalDateTime.of(now, LocalTime.MAX);

        // todayBusinessNum
        LambdaQueryWrapper<TbBusiness> wrapperBusiness = new LambdaQueryWrapper<>();

        wrapperBusiness.between(TbBusiness::getCreateTime, begin, end);
        List<TbBusiness> tbBusinesses = tbBusinessMapper.selectList(wrapperBusiness);
        Integer countBusiness = 0;
        for (TbBusiness item : tbBusinesses) {
            countBusiness++;
        }

        // todayCluesNum
        LambdaQueryWrapper<TbClue> wrapperClues = new LambdaQueryWrapper<>();
        wrapperClues.between(TbClue::getCreateTime, begin, end);
        List<TbClue> tbClues = tbClueMapper.selectList(wrapperClues);
        Integer countClues = 0;
        for (TbClue tbClue : tbClues) {
            countClues++;
        }

        // todayContractNum
        LambdaQueryWrapper<TbContract> wrapperContract = new LambdaQueryWrapper<>();
        wrapperContract.between(TbContract::getCreateTime, begin, end);
        List<TbContract> tbContracts = tbContractMapper.selectList(wrapperContract);
        Integer countContr = 0;
        Double price = 0.0;
        for (TbContract item : tbContracts) {
            Double coursePrice = item.getCoursePrice();
            countContr++;
            price += coursePrice;
        }

        // todaySalesAmount
        IndexTodayInfoVO indexTodayInfoVO = new IndexTodayInfoVO();
        indexTodayInfoVO.setTodaySalesAmount(price);
        indexTodayInfoVO.setTodayCluesNum(countClues);
        indexTodayInfoVO.setTodayBusinessNum(countBusiness);
        indexTodayInfoVO.setTodayContractNum(countContr);

        return indexTodayInfoVO;
    }

    @Override
    public IndexBaseInfoVO getBaseInfo(String beginCreateTime, String endCreateTime) {
        // LocalDate now = LocalDate.now();

        Map<String, String> timeMap = new HashMap<>();
        Date beginDate = null;
        Date endDate = null;
        String beginTime;
        String endTime;
        timeMap.put("beginTime", beginCreateTime);
        timeMap.put("endTime", endCreateTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 空?还是不空
        if ((timeMap != null) && (beginTime = timeMap.get("beginTime")) != null && (!Objects.equals(timeMap.get("beginTime"), ""))) {
            try {
                beginDate = sdf.parse(beginTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }
        if ((timeMap != null) && (endTime = timeMap.get("endTime")) != null && (!Objects.equals(timeMap.get("beginTime"), ""))) {
            try {
                endDate = sdf.parse(endTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }

        // todayBusinessNum
        LambdaQueryWrapper<TbBusiness> wrapperBusiness = new LambdaQueryWrapper<>();
        System.out.println(beginDate);
        System.out.println(endDate);
        wrapperBusiness.between(TbBusiness::getCreateTime, beginDate, endDate);
        List<TbBusiness> tbBusinesses = tbBusinessMapper.selectList(wrapperBusiness);
        Integer countBusiness = 0;
        for (TbBusiness item : tbBusinesses) {
            countBusiness++;
        }
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");

        // todayCluesNum
        LambdaQueryWrapper<TbClue> wrapperClues = new LambdaQueryWrapper<>();
        wrapperClues.between(TbClue::getCreateTime, beginDate, endDate);
        List<TbClue> tbClues = tbClueMapper.selectList(wrapperClues);
        Integer countClues = 0;
        for (TbClue tbClue : tbClues) {
            countClues++;
        }
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println(countClues);
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        // todayContractNum
        LambdaQueryWrapper<TbContract> wrapperContract = new LambdaQueryWrapper<>();
        wrapperContract.between(TbContract::getCreateTime, beginDate, endDate);
        List<TbContract> tbContracts = tbContractMapper.selectList(wrapperContract);
        Integer countContr = 0;
        Double price = 0.0;
        for (TbContract item : tbContracts) {
            Double coursePrice = item.getCoursePrice();
            countContr++;
            price += coursePrice;
        }

        // todaySalesAmount
        IndexBaseInfoVO indexBaseInfoVO = new IndexBaseInfoVO();
        indexBaseInfoVO.setSalesAmount(price);
        indexBaseInfoVO.setCluesNum(countClues);
        indexBaseInfoVO.setBusinessNum(countBusiness);
        indexBaseInfoVO.setContractNum(countContr);

        return indexBaseInfoVO;


    }

    /**
     * 首页--获取待办数据
     *
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @Override
    public IndexTodoInfoVO getTodoInfo(String beginCreateTime, String endCreateTime) {

        IndexTodoInfoVO indexTodoInfoVO = new IndexTodoInfoVO();
        Map<String, String> timeMap = new HashMap<>();
        Date beginDate = null;
        Date endDate = null;
        String beginTime;
        String endTime;
        timeMap.put("beginTime", beginCreateTime);
        timeMap.put("endTime", endCreateTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 空?还是不空
        if ((timeMap != null) && (beginTime = timeMap.get("beginTime")) != null && (!Objects.equals(timeMap.get("beginTime"), ""))) {
            try {
                beginDate = sdf.parse(beginTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }
        if ((timeMap != null) && (endTime = timeMap.get("endTime")) != null && (!Objects.equals(timeMap.get("beginTime"), ""))) {
            try {
                endDate = sdf.parse(endTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }

        // toallocatedBusinessNum
        LambdaQueryWrapper<TbBusiness> wrapperAllocatedBusiness = new LambdaQueryWrapper<>();
        wrapperAllocatedBusiness.eq(TbBusiness::getStatus, "2").between(TbBusiness::getCreateTime, beginDate, endDate);
        List<TbBusiness> tbAllocatedBusiness = tbBusinessMapper.selectList(wrapperAllocatedBusiness);
        Integer countAllocatedBusiness = 0;
        for (TbBusiness item : tbAllocatedBusiness) {
            countAllocatedBusiness++;
        }

        // toallocatedCluesNum 待分配
        LambdaQueryWrapper<TbClue> wrapperAllocatedClue = new LambdaQueryWrapper<>();
        wrapperAllocatedClue.eq(TbClue::getStatus, "2").between(TbClue::getCreateTime, beginDate, endDate);
        List<TbClue> tbAllocatedClue = tbClueMapper.selectList(wrapperAllocatedClue);
        Integer counAllocatedClue = 0;
        for (TbClue item : tbAllocatedClue) {
            counAllocatedClue++;
        }

        // tofollowedBusinessNum

        LambdaQueryWrapper<TbBusiness> wrapperfollowedBusiness = new LambdaQueryWrapper<>();
        wrapperfollowedBusiness.eq(TbBusiness::getStatus, "1").between(TbBusiness::getCreateTime, beginDate, endDate);
        List<TbBusiness> tbfollowedBusiness = tbBusinessMapper.selectList(wrapperfollowedBusiness);
        Integer countfollowedBusiness = 0;
        for (TbBusiness item : tbfollowedBusiness) {
            countfollowedBusiness++;
        }


        // tofollowedCluesNum 跟进
        LambdaQueryWrapper<TbClue> wrapperFollowClue = new LambdaQueryWrapper<>();
        wrapperFollowClue.eq(TbClue::getStatus, "1").between(TbClue::getCreateTime, beginDate, endDate);
        List<TbClue> tbFollowClue = tbClueMapper.selectList(wrapperFollowClue);
        Integer countFollowClue = 0;
        for (TbClue item : tbFollowClue) {
            countFollowClue++;
        }

        indexTodoInfoVO.setToallocatedBusinessNum(countAllocatedBusiness);
        indexTodoInfoVO.setToallocatedCluesNum(counAllocatedClue);
        indexTodoInfoVO.setTofollowedBusinessNum(countfollowedBusiness);
        indexTodoInfoVO.setTofollowedCluesNum(countFollowClue);

        return indexTodoInfoVO;
    }

    @Override
    public List<ChangeStatisticsVO> businessChangeStatistics(String beginCreateTime, String endCreateTime) {

        List<ChangeStatisticsVO> list = new ArrayList<>();

        IndexTodoInfoVO indexTodoInfoVO = new IndexTodoInfoVO();
        Map<String, String> timeMap = new HashMap<>();
        Date beginDate = null;
        Date endDate = null;
        String beginTime;
        String endTime;
        timeMap.put("beginTime", beginCreateTime);
        timeMap.put("endTime", endCreateTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 空?还是不空
        if ((timeMap != null) && (beginTime = timeMap.get("beginTime")) != null && (!Objects.equals(timeMap.get("beginTime"), ""))) {
            try {
                beginDate = sdf.parse(beginTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }
        if ((timeMap != null) && (endTime = timeMap.get("endTime")) != null && (!Objects.equals(timeMap.get("beginTime"), ""))) {
            try {
                endDate = sdf.parse(endTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }

        // getren
        List<SysUser> users = sysUserMapper.selectList(null);

        Date finalBeginDate = beginDate;
        Date finalEndDate = endDate;
        users.forEach((user) -> {

            // number
            LambdaQueryWrapper<TbContract> wrapperTbContract = new LambdaQueryWrapper<>();
            wrapperTbContract.eq(TbContract::getCreateBy, user.getUserName()).between(TbContract::getCreateTime, finalBeginDate, finalEndDate);
            List<TbContract> tbATbContract = tbContractMapper.selectList(wrapperTbContract);
            Integer counTbContract = 0;
            for (TbContract item : tbATbContract) {
                counTbContract++;
            }
            // total


            LambdaQueryWrapper<TbAssignRecord> wrapperRecord = new LambdaQueryWrapper<>();
            wrapperRecord.eq(TbAssignRecord::getUserName, user.getUserName()).between(TbAssignRecord::getCreateTime, finalBeginDate, finalEndDate);
            List<TbAssignRecord> tbRecord = tbAssignRecordMapper.selectList(wrapperRecord);
            Integer counRecord = 0;
            for (TbAssignRecord item : tbRecord) {
                counRecord++;
            }

            Double ratio = counTbContract * 100.0 / counRecord;

            // dept
            Long deptId = user.getDeptId();


            ChangeStatisticsVO changeStatisticsVO = new ChangeStatisticsVO();
            changeStatisticsVO.setCreate_by(user.getUserName());
            // changeStatisticsVO.setDeptName(user.getDeptId());
            changeStatisticsVO.setNum(counTbContract);
            changeStatisticsVO.setRadio(ratio);

            if (ratio > 0.0) {
                list.add(changeStatisticsVO);
            }
        });


        return list;
    }

    @Override
    public List<ChangeStatisticsVO> salesStatistic(String beginCreateTime, String endCreateTime) {

        List<ChangeStatisticsVO> list = new ArrayList<>();

        Map<String, String> timeMap = new HashMap<>();
        Date beginDate = null;
        Date endDate = null;
        String beginTime;
        String endTime;
        timeMap.put("beginTime", beginCreateTime);
        timeMap.put("endTime", endCreateTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 空?还是不空
        if ((timeMap != null) && (beginTime = timeMap.get("beginTime")) != null && (!Objects.equals(timeMap.get("beginTime"), ""))) {
            try {
                beginDate = sdf.parse(beginTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }
        if ((timeMap != null) && (endTime = timeMap.get("endTime")) != null && (!Objects.equals(timeMap.get("beginTime"), ""))) {
            try {
                endDate = sdf.parse(endTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }

        // getren
        List<SysUser> users = sysUserMapper.selectList(null);

        Date finalBeginDate = beginDate;
        Date finalEndDate = endDate;
        users.forEach((user) -> {

            // number
            LambdaQueryWrapper<TbBusiness> wrapperBusiness = new LambdaQueryWrapper<>();
            wrapperBusiness.eq(TbBusiness::getCreateBy, user.getUserName()).between(TbBusiness::getCreateTime, finalBeginDate, finalEndDate);
            List<TbBusiness> tbBusinesses = tbBusinessMapper.selectList(wrapperBusiness);
            Integer countBusiness = 0;
            for (TbBusiness item : tbBusinesses) {
                countBusiness++;
            }

            // total
            LambdaQueryWrapper<TbClue> wrapperClues = new LambdaQueryWrapper<>();
            wrapperClues.eq(TbClue::getCreateBy, user.getUserName()).between(TbClue::getCreateTime, finalBeginDate, finalEndDate);
            List<TbClue> tbRecord = tbClueMapper.selectList(wrapperClues);
            Integer countClues = 0;
            for (TbClue item : tbRecord) {
                countClues++;
            }


            Double ratio = countBusiness * 100.0 / countClues;

            // dept
            Long deptId = user.getDeptId();


            ChangeStatisticsVO changeStatisticsVO = new ChangeStatisticsVO();
            changeStatisticsVO.setCreate_by(user.getUserName());
            // changeStatisticsVO.setDeptName(user.getDeptId());
            changeStatisticsVO.setNum(countBusiness);
            changeStatisticsVO.setRadio(ratio);

            if (ratio > 0.0) {
                list.add(changeStatisticsVO);
            }
        });


        return list;
    }

    @Override
    public VulnerabilityMapVOnew getVulnerabilityMap(String beginCreateTime, String endCreateTime) {

        VulnerabilityMapVOnew list = new VulnerabilityMapVOnew();

        Map<String, String> timeMap = new HashMap<>();
        Date beginDate = null;
        Date endDate = null;
        String beginTime;
        String endTime;
        timeMap.put("beginTime", beginCreateTime);
        timeMap.put("endTime", endCreateTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 空?还是不空
        if ((timeMap != null) && (beginTime = timeMap.get("beginTime")) != null && (!Objects.equals(timeMap.get("beginTime"), ""))) {
            try {
                beginDate = sdf.parse(beginTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }
        if ((timeMap != null) && (endTime = timeMap.get("endTime")) != null && (!Objects.equals(timeMap.get("beginTime"), ""))) {
            try {
                endDate = sdf.parse(endTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }
        //businessNum
        // toallocatedBusinessNum
        LambdaQueryWrapper<TbBusiness> wrapperAllocatedBusiness = new LambdaQueryWrapper<>();
        wrapperAllocatedBusiness.between(TbBusiness::getCreateTime, beginDate, endDate);
        List<TbBusiness> tbAllocatedBusiness = tbBusinessMapper.selectList(wrapperAllocatedBusiness);
        Integer countBusiness = 0;
        for (TbBusiness item : tbAllocatedBusiness) {
            countBusiness++;
        }

        //clueNum

        LambdaQueryWrapper<TbClue> wrapperAllocatedClue = new LambdaQueryWrapper<>();
        wrapperAllocatedClue.between(TbClue::getCreateTime, beginDate, endDate);
        List<TbClue> tbAllocatedClue = tbClueMapper.selectList(wrapperAllocatedClue);
        Integer counClue = 0;
        for (TbClue item : tbAllocatedClue) {
            counClue++;
        }
        //contractNum

        LambdaQueryWrapper<TbContract> wrapperTbContract = new LambdaQueryWrapper<>();
        wrapperTbContract.between(TbContract::getCreateTime, beginDate, endDate);
        List<TbContract> tbATbContract = tbContractMapper.selectList(wrapperTbContract);
        Integer counTbContract = 0;
        for (TbContract item : tbATbContract) {
            counTbContract++;
        }
        // effectiveNum

        LambdaQueryWrapper<TbAssignRecord> wrapperRecord = new LambdaQueryWrapper<>();
        wrapperRecord.between(TbAssignRecord::getCreateTime, beginDate, endDate);
        List<TbAssignRecord> tbRecord = tbAssignRecordMapper.selectList(wrapperRecord);
        Integer counRecord = 0;
        for (TbAssignRecord item : tbRecord) {
            counRecord++;
        }
        list.setBusinessNums(countBusiness);
        list.setContractNums(counTbContract);
        list.setCluesNums(counClue);
        list.setEffectiveCluesNums(counRecord);

        return list;
    }

}

