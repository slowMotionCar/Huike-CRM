package com.huike.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.business.domain.TbBusiness;
import com.huike.business.mapper.TbBusinessMapper;
import com.huike.clues.domain.SysDept;
import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.TbCourse;
import com.huike.clues.mapper.SysDeptMapper;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbActivityMapper;
import com.huike.clues.mapper.TbCourseMapper;
import com.huike.common.constant.HttpStatus;
import com.huike.common.core.domain.entity.SysUserDTO;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import com.huike.contract.domain.TbContract;
import com.huike.contract.domain.TbContractCount;
import com.huike.contract.domain.TbContractReport;
import com.huike.contract.domain.UserNumber;
import com.huike.contract.domain.dto.PageTbContractDTO;
import com.huike.contract.domain.dto.TbContractDTO;
import com.huike.contract.service.TbContractService;
import com.huike.contract.mapper.TbContractMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 93238
 * @description 针对表【tb_contract(合同)】的数据库操作Service实现
 * @createDate 2023-10-12 06:42:55
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TbContractServiceImpl extends ServiceImpl<TbContractMapper, TbContract>
        implements TbContractService {

    private final TbContractMapper tbContractMapper;
    private final TbCourseMapper tbCourseMapper;
    private final TbActivityMapper tbActivityMapper;
    private final SysUserMapper sysUserMapper;
    private final TbBusinessMapper tbBusinessMapper;

    /**
     * 新增合同
     *
     * @param dto
     */
    @Override
    public void insertContract(TbContractDTO dto) {
        //将dto对象复制到TbContract对象
        TbContract tbContract = new TbContract();
        BeanUtils.copyProperties(dto, tbContract);

        //完善创建者名字 和当前时间
        tbContract.setCreateBy(SecurityUtils.getUsername());
        tbContract.setCreateTime(DateUtils.getNowDate());

        //根据courseId查找coursePrice和courseName, 封装到TbContract中
        TbCourse tbCourse = tbCourseMapper.selectById(dto.getCourseId());
        tbContract.setCourseName(tbCourse.getName());
        tbContract.setCoursePrice(Double.valueOf(tbCourse.getPrice()));

        //根据ActivityId查询ActivityName, 封装到TbContract中
        TbActivity tbActivity = tbActivityMapper.selectById(dto.getActivityId());
        tbContract.setActivityName(tbActivity.getName());

        //根据合同创建者Id查询出其所在部门, 封装到TbContract中
        SysUserDTO sysUserDTO = sysUserMapper.selectUserByUserName(SecurityUtils.getUsername());
        tbContract.setDeptId(sysUserDTO.getDeptId());

        //调用Mapper将TbContract插入
        tbContractMapper.insert(tbContract);
    }

    /**
     * 合同分页查询
     *
     * @param dto
     * @return
     */
    @Override
    public TableDataInfo<List<TbContract>> selectTbContractList(PageTbContractDTO dto) {
        //设置当前页与页面大小
        Page<TbContract> page = new Page<>(dto.getPageNum(), dto.getPageSize());

        //创建分页条件
        QueryWrapper<TbContract> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(dto.getContractNo() != null, "contract_no", dto.getContractNo())
                .like(dto.getName() != null, "name", dto.getName())
                .like(dto.getPhone() != null, "phone", dto.getPhone())
                .like(dto.getSubject() != null, "subject", dto.getSubject())
                .like(dto.getCourseId() != null, "course_id", dto.getCourseId())
                .gt(dto.getParams().get("beginCreateTime") != null && dto.getParams().get("beginCreateTime") != "", "create_time", dto.getParams().get("beginCreateTime"))
                .lt(dto.getParams().get("endCreateTime") != null && dto.getParams().get("endCreateTime") != "", "create_time", dto.getParams().get("endCreateTime"));

        //执行查询
        Page<TbContract> selectPage = tbContractMapper.selectPage(page, queryWrapper);

        List<TbContract> records = page.getRecords();
        for (TbContract record : records) {
            record.setFinisTime(new Date());
            record.setOrder(record.getCoursePrice());
        }
        // 封装对象返回
        TableDataInfo<List<TbContract>> dataInfo = new TableDataInfo<>();

        List<TbContract> list = selectPage.getRecords();
        for (TbContract tbContract : list) {
            TbCourse tbCourse = tbCourseMapper.selectById(tbContract.getCourseId());
            tbContract.setCourseName(tbCourse.getName());
            log.info(tbContract.getCourseName());
        }

        dataInfo.setTotal(selectPage.getTotal());
        dataInfo.setRows(selectPage.getRecords());
        dataInfo.setCode(HttpStatus.SUCCESS);
        dataInfo.setMsg("");
        dataInfo.setParams(null);

        return dataInfo;
    }

    /**
     * 根据id查询合同
     *
     * @param id
     * @return
     */
    @Override
    public TbContract selectDetailById(Integer id) {
        TbContract tbContract = tbContractMapper.selectById(id);
        return tbContract;
    }

    /**
     * 根据id查询合同2
     *
     * @param id
     * @return
     */
    @Override
    public TbContract selectTbContractById(String id) {
        TbContract tbContract = tbContractMapper.selectById(id);
        tbContract.setFinisTime(new Date());
        tbContract.setOrder(tbContract.getCoursePrice());
        return tbContract;
    }

    /**
     * 商机转合同
     *
     * @param id
     */
    @Override
    @Transactional
    public void changeContract(Integer id, TbContractDTO dto) {
        //通过商机id查询商机对象
        TbBusiness tbBusiness = tbBusinessMapper.selectById(id);
        if (tbBusiness == null) {
            throw new CustomException("商机不存在");
        }
        log.info("课程id{}", tbBusiness.getCourseId());
        if (tbBusiness.getCourseId() == null) {
            throw new CustomException("商机里面没有选择课程, 无法转换成客户合同");
        }
        //将商机转化为合同
        TbContract tbContract = new TbContract();
        BeanUtils.copyProperties(tbBusiness, tbContract);

        tbContract.setCreateTime(DateUtils.getNowDate());
        tbContract.setContractNo(dto.getContractNo());
        tbContract.setActivityName(tbActivityMapper.selectById(tbContract.getActivityId()).getName());
        tbContract.setCourseName(tbCourseMapper.selectById(tbContract.getCourseId()).getName());
        tbContract.setDeptId(sysUserMapper.selectUserByUserName(SecurityUtils.getUsername()).getDeptId());
        tbContract.setCoursePrice(Double.valueOf(tbCourseMapper.selectById(tbContract.getCourseId()).getPrice()));
        tbContract.setFileName(dto.getFileName());
        tbContract.setBusinessId(tbBusiness.getId());
        tbContract.setStatus("4");
        tbContract.setFinisTime(new Date());
        tbContract.setOrder(tbContract.getCoursePrice());
        log.info("合同对象{}", tbContract);
        tbContractMapper.insert(tbContract);

        tbBusinessMapper.deleteById(id);

    }

    /**
     * ## 渠道统计-活动统计
     *
     * @param beginCreateTime
     * @param endCreateTime
     * @param createBy
     * @param ids
     * @param channel
     * @return
     */
    @Override
    public List<TbContract> pageList(Date beginCreateTime, Date endCreateTime, String createBy, List<Long> ids, Long channel) {
        String begin = DateUtils.parseDateToStr("yyyy-MM-dd", beginCreateTime);
        String end = DateUtils.parseDateToStr("yyyy-MM-dd", endCreateTime);

        return tbContractMapper.selectContract(begin, end, ids, channel, createBy);
    }

    /**
     * 客户统计新增客户数报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @Override
    public TbContractReport<Integer> contractStatistics(Date beginCreateTime, Date endCreateTime) {
        List<String> xAxis = new ArrayList<>();
        List<UserNumber<Integer>> series = new ArrayList<>();

        List<Integer> newUserData = new ArrayList<>();
        List<Integer> userNumberData = new ArrayList<>();
        while (beginCreateTime.before(endCreateTime)) {
            String begin = DateUtils.parseDateToStr("yyyy-MM-dd", beginCreateTime);
            QueryWrapper<TbContract> qw = new QueryWrapper<>();
            qw.lambda().eq(TbContract::getCreateTime, begin);
            Integer count = tbContractMapper.selectCount(qw);
            if (count == null) {
                count = 0;
            }
            if (userNumberData.size() == 0) {
                userNumberData.add(count);
            } else {
                int size = userNumberData.size();
                userNumberData.add(userNumberData.get(size - 1) + count);
            }

            newUserData.add(count);

            xAxis.add(begin);
            beginCreateTime = DateUtils.addDays(beginCreateTime, 1);
        }
        UserNumber<Integer> userNumber = new UserNumber<>();
        userNumber.setName("客户总个数");
        userNumber.setData(userNumberData);

        UserNumber<Integer> newUser = new UserNumber<>();
        userNumber.setName("新增用户数量");
        userNumber.setData(newUserData);

        series.add(newUser);
        series.add(userNumber);

        TbContractReport<Integer> tbContractReport = new TbContractReport<>();
        tbContractReport.setXAxis(xAxis);
        tbContractReport.setSeries(series);
        return tbContractReport;
    }

    /**
     * ## 线索统计新增线索数量报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @Override
    public TbContractReport<Double> salesStatistics(Date beginCreateTime, Date endCreateTime) {
        List<String> xAxis = new ArrayList<>();
        List<UserNumber<Double>> series = new ArrayList<>();

        List<Double> sale = new ArrayList<>();
        while (beginCreateTime.before(endCreateTime)) {
            String begin = DateUtils.parseDateToStr("yyyy-MM-dd", beginCreateTime);

            QueryWrapper<TbContract> qw = new QueryWrapper<>();
            qw.lambda().eq(TbContract::getCreateTime, begin)
                    .eq(TbContract::getStatus, "4");

            List<TbContract> tbContracts = tbContractMapper.selectList(qw);

            Double sum = 0.0;
            for (TbContract tbContract : tbContracts) {
                if (tbContract.getCoursePrice() == null) {
                    tbContract.setCoursePrice(0.0);
                }
                sum += tbContract.getCoursePrice();
            }
            sale.add(sum);
            xAxis.add(begin);

            beginCreateTime = DateUtils.addDays(beginCreateTime, 1);
        }
        UserNumber<Double> user = new UserNumber<>();
         user.setName("销售金额");
         user.setData(sale);

        TbContractReport<Double> tbContractReport = new TbContractReport<>();
        series.add(user);
        tbContractReport.setSeries(series);
        tbContractReport.setXAxis(xAxis);

        return tbContractReport;
    }

    @Autowired
    private SysDeptMapper deptMapper;
    /**
     * ## 销售统计归属部门明细列表
     *
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @Override
    public List<TbContractCount> pageListByDept(Date beginCreateTime, Date endCreateTime) {
        List<TbContractCount> tbContractCounts = tbContractMapper.selectCountByDept(beginCreateTime, endCreateTime);
        for (TbContractCount tbContractCount : tbContractCounts) {
            Integer deptId = tbContractCount.getDeptId();
            SysDept sysDept = deptMapper.selectByDeptById(deptId);
            String deptName = sysDept.getDeptName();
            tbContractCount.setDeptName(deptName);
        }
        return tbContractCounts;
    }


    /**
     * ## ## 销售统计归属渠道明细列表
     *
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @Override
    public List<TbContractCount> channelStatisticsList(Date beginCreateTime, Date endCreateTime) {
        return tbContractMapper.selectCountByActivity(beginCreateTime, endCreateTime);
    }


    /**
     * ## 销售统计归属人报表
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @Override
    public List<TbContractCount> ownerShipStatisticsList(Date beginCreateTime, Date endCreateTime) {
        return tbContractMapper.ownerShipStatisticsList(beginCreateTime, endCreateTime);
    }
}




