package com.huike.contract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.domain.SysUser;
import com.huike.contract.domain.vo.TransferVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description TransferMapper
 * @Author xhr
 * @Date 2023-10-17
 */
@Mapper
public interface TransferMapper extends BaseMapper<TransferVo> {

}
