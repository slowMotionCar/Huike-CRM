package com.huike.clues.service;

import com.huike.clues.domain.SysDictData;
import com.huike.clues.domain.dto.SysQueryforDictDataDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.vo.SysDictDataVO;
import com.huike.common.core.domain.entity.SysDictDataDTO;
import com.huike.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author Zhilin
* @description 针对表【sys_dict_data(字典数据表)】的数据库操作Service
* @createDate 2023-10-15 15:26:09
*/
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 添加字典信息接口
     *
     * @param sysDictDataDTO
     * @return
     */
    void addDataType(SysDictDataDTO sysDictDataDTO);


    /**
     * 分页查询字典数据信息接口
     *
     * @return
     * @SysDictDataDTO sysDictDataDTO
     */
    TableDataInfo listPage(SysQueryforDictDataDTO sysQueryforDictDataDTO);


    /**
     * 根据字典类型查询字典数据
     * @param dictType
     * @return
     */
    List<SysDictDataVO> getDictDataByDictType(String dictType);

    /**
     * 删除字典数据
     *
     * @param dictCodes
     * @return
     */
    void deleteDictDataByDictCodes(List<Integer> dictCodes);

    /**
     * 修改字典数据接口
     *
     * @param sysDictDataDTO
     * @return
     */
    void updateDictData(SysDictDataDTO sysDictDataDTO);

    /**
     * 查询字典数据详细
     * @param dictCode
     * @return
     */
    SysDictDataVO getDictDataByDictCode(Integer dictCode);

    /**
     * 导出数据
     * @param response
     * @return
     */
    void getExport(List<SysDictData> rows, HttpServletResponse response);
}
