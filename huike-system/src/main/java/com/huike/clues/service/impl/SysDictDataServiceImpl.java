package com.huike.clues.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.SysDictData;
import com.huike.clues.domain.dto.SysQueryforDictDataDTO;
import com.huike.clues.domain.vo.SysDictDataExportVO;
import com.huike.clues.domain.vo.SysDictDataVO;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.mapper.SysDictTypeMapper;
import com.huike.clues.service.SysDictDataService;
import com.huike.common.constant.HttpStatus;
import com.huike.common.core.domain.entity.SysDictDataDTO;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.exception.BaseException;
import com.huike.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Zhilin
 * @description 针对表【sys_dict_data(字典数据表)】的数据库操作Service实现
 * @createDate 2023-10-15 15:26:09
 */
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData>
        implements SysDictDataService {

    private final SysDictTypeMapper sysDictTypeMapper;
    private final SysDictDataMapper sysDictDataMapper;


    /**
     * 添加字典信息接口
     *
     * @param sysDictDataDTO
     * @return
     */
    @Override
    public void addDataType(SysDictDataDTO sysDictDataDTO) {

        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(sysDictDataDTO, sysDictData);

        // 奇奇怪怪的值
        if ((sysDictDataDTO.getDictType() == null) || (sysDictDataDTO.getStatus() == null)) {
            throw new BaseException("添加字典关键字不嫩为空");
        }

        // 补全字段
        // 创建人
        String userName = SecurityUtils.getLoginUser().getUser().getUserName();
        Date date = new Date();
        sysDictData.setCreateBy(userName);
        sysDictData.setCreateTime(date);
        // 添加
        sysDictDataMapper.insert(sysDictData);

    }


    /**
     * 分页查询字典数据信息接口
     *
     * @return
     * @SysDictDataDTO sysDictDataDTO
     */
    @Override
    public TableDataInfo listPage(SysQueryforDictDataDTO sysQueryforDictDataDTO) {
        // 寻找定义

        Integer pageNum = sysQueryforDictDataDTO.getPageNum();
        Integer pageSize = sysQueryforDictDataDTO.getPageSize();
        Integer status = sysQueryforDictDataDTO.getStatus();
        String dictType = sysQueryforDictDataDTO.getDictType();
        String dictLable = sysQueryforDictDataDTO.getDictLabel();
        System.out.println("dictlable !!"+dictLable);

        // 开始查找
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        Page page = new Page<>();
        Page pageTemp = new Page<>();
        List<SysDictData> records = new ArrayList<>();

        // 奇奇怪怪的值
        if ((pageNum == null || pageNum == 0) || (pageSize == null || pageSize == 0)) {
            wrapper.eq(dictType != null, SysDictData::getDictType, dictType)
                    .eq(status != null, SysDictData::getStatus, status)
                    .like(dictLable != null, SysDictData::getDictLabel, dictLable)
                    .orderByAsc(SysDictData::getDictSort);
            page = sysDictDataMapper.selectPage(pageTemp, wrapper);
            List<SysDictData> recordTemp = sysDictDataMapper.selectList(wrapper);

            for (SysDictData item : recordTemp) {

                //改变状态
                String statusTemp = item.getStatus();
                String isDefault = item.getIsDefault();

                LambdaQueryWrapper<SysDictData> wrapperTemp = new LambdaQueryWrapper<>();
                wrapperTemp.eq(SysDictData::getDictValue, statusTemp).eq(SysDictData::getDictType, "sys_normal_disable");
                List<SysDictData> sysDictData = sysDictDataMapper.selectList(wrapperTemp);
                item.setStatus(sysDictData.get(0).getDictLabel());

                LambdaQueryWrapper<SysDictData> wrapperTemp1 = new LambdaQueryWrapper<>();
                wrapperTemp1.eq(SysDictData::getDictValue, isDefault).eq(SysDictData::getDictType, "sys_yes_no");
                List<SysDictData> sysDictData1 = sysDictDataMapper.selectList(wrapperTemp1);
                item.setIsDefault(sysDictData1.get(0).getDictLabel());


                records.add(item);
            }

        }
        else {
            //正常情况
            pageTemp = new Page<>(pageNum, pageSize);
            wrapper.eq(dictType != null, SysDictData::getDictType, dictType)
                    .eq(status != null, SysDictData::getStatus, status)
                    .like(dictLable != null, SysDictData::getDictLabel, dictLable)
                    .orderByAsc(SysDictData::getDictSort);
            page = sysDictDataMapper.selectPage(pageTemp, wrapper);
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
     * 根据字典类型查询字典数据
     *
     * @param dictType
     * @return
     */
    @Override
    public List<SysDictDataVO> getDictDataByDictType(String dictType) {

        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dictType != null, SysDictData::getDictType, dictType);
        List<SysDictData> sysDictData = sysDictDataMapper.selectList(wrapper);
        List<SysDictDataVO> list = new ArrayList<>();
        for (SysDictData item : sysDictData) {
            SysDictDataVO sysDictDataVO = new SysDictDataVO();
            BeanUtils.copyProperties(item, sysDictDataVO);
            list.add(sysDictDataVO);
        }

        return list;
    }

    /**
     * 删除字典数据
     *
     * @param dictCodes
     * @return
     */
    @Override
    public void deleteDictDataByDictCodes(List<Integer> dictCodes) {

        // 删除dictData
        dictCodes.forEach(sysDictDataMapper::deleteById);
    }

    /**
     * 修改字典数据接口
     *
     * @param sysDictDataDTO
     * @return
     */
    @Override
    public void updateDictData(SysDictDataDTO sysDictDataDTO) {

        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(sysDictDataDTO, sysDictData);
        // 补全字段
        // 创建人
        String userName = SecurityUtils.getLoginUser().getUser().getUserName();
        Date date = new Date(System.currentTimeMillis());
        sysDictData.setUpdateBy(userName);
        sysDictData.setUpdateTime(date);

        // 添加
        sysDictDataMapper.updateById(sysDictData);
    }

    /**
     * 查询字典数据详细
     *
     * @param dictCode
     * @return
     */
    @Override
    public SysDictDataVO getDictDataByDictCode(Integer dictCode) {

        SysDictData sysDictData = sysDictDataMapper.selectById(dictCode);
        SysDictDataVO sysDictDataVO = new SysDictDataVO();
        BeanUtils.copyProperties(sysDictData, sysDictDataVO);
        return sysDictDataVO;

    }

    /**
     * 导出数据
     *
     * @param response
     * @return
     */
    public void getExport(List<SysDictData> list, HttpServletResponse response) {
        // 初始化名字路径
        String templateFileName = this.getClass().getClassLoader().getResource("templates/dictData.xlsx").getPath();
        String fileName = UUID.randomUUID() + "字典数据.xlsx";

        // 本地根目录
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {

            // ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();

            List<SysDictDataExportVO> lists = new ArrayList<>();
            list.forEach((item) -> {
                SysDictDataExportVO sysDictDataExportVO = new SysDictDataExportVO();
                BeanUtils.copyProperties(item, sysDictDataExportVO);
                lists.add(sysDictDataExportVO);
            });

            excelWriter.fill(lists, writeSheet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




