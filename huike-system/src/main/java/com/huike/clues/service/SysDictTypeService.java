package com.huike.clues.service;

import com.huike.clues.domain.dto.SysQueryforDictTypeDTO;
import com.huike.clues.domain.pojo.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.vo.SysDictTypeVO;
import com.huike.common.core.domain.entity.SysDictTypeDTO;
import com.huike.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author Zhilin
* @description 针对表【sys_dict_type(字典类型表)】的数据库操作Service
* @createDate 2023-10-15 15:26:18
*/
public interface SysDictTypeService extends IService<SysDictType> {
    /**
     * 添加字典类型接口
     * @param sysDictTypeDTO
     */
    void addDictType(SysDictTypeDTO sysDictTypeDTO);

    /**
     * 修改字典类型接口
     * @param sysDictTypeDTO
     */
    void updateDictType(SysDictTypeDTO sysDictTypeDTO);

    /**
     * 清空缓存
     */
    void clearCache();

    /**
     * 分页查询数据字典类型数据
     * @param sysQueryforDictTypeDTO
     * @return
     */
    TableDataInfo listPage(SysQueryforDictTypeDTO sysQueryforDictTypeDTO);


    /**
     * 获取字典选择框列表
     * @return
     */
    List optionSelect();

    /**
     * 查询字典类型详细接口
     *
     * @param dictId
     * @return
     */
    SysDictTypeVO getDictTypeById(Integer dictId);

    /**
     * 导出数据
     * @param list
     * @param response
     */
    void getExport(List list,HttpServletResponse response);

    /**
     * 删除字典类型
     * @param dictIds
     * @return
     */
    void deleteDictTypeById(List<Integer> dictIds);
}
