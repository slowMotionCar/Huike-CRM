package com.huike.clues.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.SysDictData;
import com.huike.clues.domain.dto.SysQueryforDictTypeDTO;
import com.huike.clues.domain.pojo.SysDictType;
import com.huike.clues.domain.vo.SysDictTypeExportVO;
import com.huike.clues.domain.vo.SysDictTypeVO;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.mapper.SysDictTypeMapper;
import com.huike.clues.service.SysDictTypeService;
import com.huike.common.constant.HttpStatus;
import com.huike.common.core.domain.entity.SysDictTypeDTO;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.exception.BaseException;
import com.huike.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Zhilin
 * @description 针对表【sys_dict_type(字典类型表)】的数据库操作Service实现
 * @createDate 2023-10-15 15:26:18
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType>
        implements SysDictTypeService {

    private final SysDictTypeMapper sysDictTypeMapper;
    private final SysDictDataMapper sysDictDataMapper;
    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 添加字典类型接口
     *
     * @param sysDictTypeDTO
     */
    @Override
    public void addDictType(SysDictTypeDTO sysDictTypeDTO) {
        SysDictType sysDictType = new SysDictType();
        BeanUtils.copyProperties(sysDictTypeDTO, sysDictType);
        // 补全字段
        // 创建人
        String userName = SecurityUtils.getLoginUser().getUser().getUserName();
        Date date = new Date();
        sysDictType.setCreateBy(userName);
        sysDictType.setCreateTime(date);
        // 添加
        sysDictTypeMapper.insert(sysDictType);
        //清除缓存
        clearCache();
    }

    /**
     * 修改字典类型接口
     *
     * @param sysDictTypeDTO
     */
    @Override
    public void updateDictType(SysDictTypeDTO sysDictTypeDTO) {
        SysDictType sysDictType = new SysDictType();
        // 查看是否修改原始数据
        SysDictType sysDictTypeTemp = sysDictTypeMapper.selectById(sysDictTypeDTO.getDictId());
        System.out.println("DTO" + sysDictTypeDTO);
        System.out.println("Temp" + sysDictTypeTemp);

        if (!(sysDictTypeDTO.getDictName().equals(sysDictTypeTemp.getDictName()))
                || !(sysDictTypeDTO.getDictType().equals(sysDictTypeTemp.getDictType()))) {
            throw new BaseException("不可以修改原始数据");
        }
        BeanUtils.copyProperties(sysDictTypeDTO, sysDictType);
        // 补全字段
        // 创建人
        String userName = SecurityUtils.getLoginUser().getUser().getUserName();
        Date date = new Date(System.currentTimeMillis());
        sysDictType.setUpdateBy(userName);
        sysDictType.setUpdateTime(date);

        // 添加
        sysDictTypeMapper.updateById(sysDictType);
        //清除缓存
        clearCache();
    }


    /**
     * 清空缓存
     */

    @Override
    public void clearCache() {
        Set keys = redisTemplate.keys("dict*");
        Long delete = redisTemplate.delete(keys);
    }

    /**
     * 分页查询数据字典类型数据
     *
     * @param sysQueryforDictTypeDTO
     * @return
     */
    @Override
    public TableDataInfo listPage(SysQueryforDictTypeDTO sysQueryforDictTypeDTO) {

        // 寻找定义
        String dictName = sysQueryforDictTypeDTO.getDictName();
        String dictType = sysQueryforDictTypeDTO.getDictType();
        Integer pageNum = sysQueryforDictTypeDTO.getPageNum();
        Integer pageSize = sysQueryforDictTypeDTO.getPageSize();
        String status = sysQueryforDictTypeDTO.getStatus();
        Map<String, String> timeMap = sysQueryforDictTypeDTO.getParams();

        // 初始化时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        String beginTime;
        String endTime;

        // 空?还是不空
        if ((timeMap!= null) && (beginTime = timeMap.get("beginTime")) != null &&(!Objects.equals(timeMap.get("beginTime"), ""))) {
            try {
                beginDate = sdf.parse(beginTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }
        if ((timeMap!= null ) && (endTime = timeMap.get("endTime")) != null && (!Objects.equals(timeMap.get("beginTime"), ""))) {
            try {
                endDate = sdf.parse(endTime);
            } catch (ParseException e) {
                throw new BaseException("日期格式转换失败, 检查日期格式");
            }
        }

        // 开始查找
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        Page pageTemp = new Page<>();
        Page page = new Page<>();
        List<SysDictType> records = new ArrayList<>();

        // 奇奇怪怪的值
        if ((pageNum == null || pageNum == 0) || (pageSize == null || pageSize == 0)) {
            wrapper.like(dictName != null, SysDictType::getDictName, dictName)
                    .like(dictType != null, SysDictType::getDictType, dictType)
                    .eq(status != null, SysDictType::getStatus, status)
                    .between(beginDate != null && endDate != null,
                            SysDictType::getCreateTime, beginDate, endDate);
            page = sysDictTypeMapper.selectPage(pageTemp, wrapper);
            List<SysDictType> recordsTemp = sysDictTypeMapper.selectList(wrapper);

            for (SysDictType item : recordsTemp) {

                String statusTemp = item.getStatus();
                LambdaQueryWrapper<SysDictData> wrapperTemp = new LambdaQueryWrapper<>();
                wrapperTemp.eq(SysDictData::getDictValue, statusTemp).eq(SysDictData::getDictType, "sys_normal_disable");

                List<SysDictData> sysDictData = sysDictDataMapper.selectList(wrapperTemp);
                item.setStatus(sysDictData.get(0).getDictLabel());

                records.add(item);
            }

        }
        // 正常的值
        else {
            pageTemp = new Page<>(pageNum, pageSize);
            wrapper.like(dictName != null, SysDictType::getDictName, dictName)
                    .like(dictType != null, SysDictType::getDictType, dictType)
                    .eq(status != null, SysDictType::getStatus, status)
                    .between(beginDate != null && endDate != null,
                            SysDictType::getCreateTime, beginDate, endDate);
            page = sysDictTypeMapper.selectPage(pageTemp, wrapper);
            records = page.getRecords();

        }
        // 封装开始

        TableDataInfo tableDataInfo = new TableDataInfo<>();
        tableDataInfo.setTotal(page.getTotal());
        tableDataInfo.setRows(records);
        tableDataInfo.setMsg("查询成功");
        tableDataInfo.setCode(HttpStatus.SUCCESS);

        return tableDataInfo;
    }

    /**
     * findDataByTypeInDict
     *
     * @param records
     * @param dictType
     */
    private void findDataByTypeInDict(List<SysDictType> records, String dictType) {
        for (SysDictType item : records) {
            // 查询条件
            String statusTemp = item.getStatus();
            LambdaQueryWrapper<SysDictData> wrapperTemp = new LambdaQueryWrapper<>();
            wrapperTemp.eq(SysDictData::getDictValue, statusTemp).eq(SysDictData::getDictType, dictType);
            // 开始封装
            List<SysDictData> sysDictData = sysDictDataMapper.selectList(wrapperTemp);
            item.setStatus(sysDictData.get(0).getDictLabel());
        }
    }

    /**
     * 获取字典选择框列表
     *
     * @return
     */
    @Override
    public List optionSelect() {
        List<SysDictType> sysDictTypes = sysDictTypeMapper.selectList(null);
        return sysDictTypes;
    }

    /**
     * 查询字典类型详细接口
     *
     * @param dictId
     * @return
     */
    @Override
    public SysDictTypeVO getDictTypeById(Integer dictId) {

        SysDictType sysDictType = sysDictTypeMapper.selectById(dictId);

        // 封装开始
        SysDictTypeVO sysDictTypeVO = new SysDictTypeVO();
        BeanUtils.copyProperties(sysDictType, sysDictTypeVO);

        return sysDictTypeVO;
    }


    /**
     * 导出数据
     *
     * @param list
     * @param response
     */
    @Override
    public void getExport(List list, HttpServletResponse response) {
        // 初始化名字路径
        String templateFileName = this.getClass().getClassLoader().getResource("templates/dictType.xlsx").getPath();
        String fileName = UUID.randomUUID() + "字典类型.xlsx";

        // 本地根目录
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build();) {

            // ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();

            List<SysDictTypeExportVO> lists = new ArrayList<>();
            list.forEach((item) -> {
                SysDictTypeExportVO sysDictTypeExportVO = new SysDictTypeExportVO();
                BeanUtils.copyProperties(item, sysDictTypeExportVO);
                lists.add(sysDictTypeExportVO);
            });

            excelWriter.fill(lists, writeSheet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除字典类型
     *
     * @param dictIds
     * @return
     */
    @Override
    // 开启事务
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictTypeById(List<Integer> dictIds) {

        // TODO 三次调用数据库 冗余
        dictIds.forEach((dictId) -> {

            SysDictType sysDictTypeTemp = sysDictTypeMapper.selectById(dictId);
            // 删除dictData
            String dictType = sysDictTypeTemp.getDictType();
            LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysDictData::getDictType, dictType);
            sysDictDataMapper.delete(wrapper);
            // 删除dictType
            sysDictTypeMapper.deleteById(dictId);
            //清除缓存
            clearCache();
        });
    }
}




