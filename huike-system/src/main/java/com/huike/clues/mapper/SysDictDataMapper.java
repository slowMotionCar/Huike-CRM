package com.huike.clues.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.domain.SysDictData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * @Description SysDishDataMapper
 * @Author FangYiHeng
 * @Date 2023-10-16
 */
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {
    /**
     * 查询字典数据
     * @param map
     * @return
     */
    List<SysDictData> selectDictDataByMap(Map<String, Object> map);
}
