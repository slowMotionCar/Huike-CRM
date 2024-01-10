package com.huike.clues.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.huike.clues.domain.SysDictData;
import com.huike.clues.domain.dto.SysQueryforOperLogDTO;


import com.huike.clues.domain.pojo.SysDictType;
import com.huike.clues.domain.pojo.SysOperLog;
import com.huike.clues.domain.vo.SysDictDataExportVO;
import com.huike.clues.domain.vo.SysOperLogVO;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.mapper.SysOperLogMapper;
import com.huike.clues.service.SysOperLogService;
import com.huike.common.constant.HttpStatus;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.exception.BaseException;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Zhilin
 * @description 针对表【sys_oper_log(操作日志记录)】的数据库操作Service实现
 * @createDate 2023-10-16 21:18:44
 */
@Service
@RequiredArgsConstructor
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog>
        implements SysOperLogService {

    private final SysOperLogMapper sysOperLogMapper;
    private final SysDictDataMapper sysDictDataMapper;

    /**
     * 分页查询系统操作日志
     *
     * @param sysQueryforOperLogDTO
     * @return
     */
    @Override
    public TableDataInfo getLog(SysQueryforOperLogDTO sysQueryforOperLogDTO) {

        // 获取数字
        Integer pageNum = sysQueryforOperLogDTO.getPageNum();
        Integer pageSize = sysQueryforOperLogDTO.getPageSize();
        String keyWord = sysQueryforOperLogDTO.getKeyWord();
        Map<String, String> timeMap = sysQueryforOperLogDTO.getParams();

        // 初始化时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        String beginTime;
        String endTime;

        // 空?还是不空
        if ((timeMap.size() != 0) && !Objects.equals(beginTime = timeMap.get("beginTime"), "") && timeMap.get("beginTime") != null) {
            try {
                beginDate = sdf.parse(beginTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }
        if ((timeMap.size() != 0) && !Objects.equals(endTime = timeMap.get("endTime"), "") && timeMap.get("endTime") != null) {
            try {
                endDate = sdf.parse(endTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }
        Page<SysOperLog> pageTemp = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysOperLog> wrapper = new LambdaQueryWrapper<>();

        //.eq(keyWord != null, SysOperLog::get, dictName)??
        wrapper
                .between(beginDate != null && endDate != null,
                        SysOperLog::getOperTime, beginDate, endDate)
                .like(keyWord != null, SysOperLog::getRequestMethod, keyWord)
                .orderByDesc(SysOperLog::getOperTime);

        Page<SysOperLog> page = sysOperLogMapper.selectPage(pageTemp, wrapper);

        // 封装开始
        TableDataInfo tableDataInfo = new TableDataInfo<>();
        tableDataInfo.setTotal(page.getTotal());
        tableDataInfo.setRows(page.getRecords());
        tableDataInfo.setMsg("查询成功");
        tableDataInfo.setCode(HttpStatus.SUCCESS);

        return tableDataInfo;

    }

    /**
     * 删除系统操作日志
     *
     * @param operIds
     */
    @Override
    public void deleteLog(List<Integer> operIds) {

        sysOperLogMapper.deleteBatchIds(operIds);

    }

    /**
     * 操作日志清空
     *
     * @return
     */
    @Override
    public void cleanLog() {

        LambdaQueryWrapper<SysOperLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysOperLog::getOperId);
        List<SysOperLog> sysOperLogs = sysOperLogMapper.selectList(wrapper);
        List<Long> ids = new ArrayList<>();
        sysOperLogs.forEach((item)->{
            Long operId = item.getOperId();
            ids.add(operId);
        });

        System.out.println("hahah "+ids);
        sysOperLogMapper.deleteBatchIds(ids);

    }

    /**
     * 导出数据
     *
     * @param response
     */
    @Override
    public void getExport(HttpServletResponse response) {

        // 初始化名字路径
        String templateFileName = this.getClass().getClassLoader().getResource("templates/sysLog.xlsx").getPath();
        String fileName = UUID.randomUUID() + "操作日志.xlsx";

        // 本地根目录
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build()) {

            // ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();

            List<SysOperLogVO> list = new ArrayList<>();
            List<SysOperLog> listTemp = sysOperLogMapper.selectList(null);

            // DictData转换
            for (SysOperLog item : listTemp) {

                SysOperLogVO sysOperLogVO = new SysOperLogVO();
                BeanUtils.copyProperties(item, sysOperLogVO);

                // 业务类型
                Integer businessType = item.getBusinessType();
                LambdaQueryWrapper<SysDictData> wrapperTemp = new LambdaQueryWrapper<>();
                wrapperTemp.eq(SysDictData::getDictValue, businessType).eq(SysDictData::getDictType, "sys_oper_type");
                List<SysDictData> sysDictData = sysDictDataMapper.selectList(wrapperTemp);
                sysOperLogVO.setBusinessType(sysDictData.get(0).getDictLabel());

                // 操作类别
                Integer operatorType = item.getOperatorType();

                switch (operatorType) {
                    case 0:
                        sysOperLogVO.setOperatorType("其它");
                        break;
                    case 1:
                        sysOperLogVO.setOperatorType("后台用户");
                        break;
                    case 2:
                        sysOperLogVO.setOperatorType("手机端用户");
                        break;
                }

                // 状态
                Integer status = item.getStatus();
                LambdaQueryWrapper<SysDictData> wrapperTemp3 = new LambdaQueryWrapper<>();
                wrapperTemp3.eq(SysDictData::getDictValue, status).eq(SysDictData::getDictType, "sys_notice_status");
                List<SysDictData> sysDictData3 = sysDictDataMapper.selectList(wrapperTemp3);
                sysOperLogVO.setStatus(sysDictData3.get(0).getDictLabel());

                list.add(sysOperLogVO);
            }


            List<SysOperLogVO> lists = new ArrayList<>();
            list.forEach((item) -> {
                SysOperLogVO sysOperLogVO = new SysOperLogVO();
                BeanUtils.copyProperties(item, sysOperLogVO);
                lists.add(sysOperLogVO);
            });

            excelWriter.fill(lists, writeSheet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}




