package com.huike.clues.mapper;

import com.huike.clues.domain.TbRulePool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.domain.dto.TbRulePoolDTO;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 93238
* @description 针对表【tb_rule_pool(线索池规则)】的数据库操作Mapper
* @createDate 2023-10-12 06:35:46
* @Entity com.huike.clues.domain.TbRulePool
*/
@Mapper
public interface TbRulePoolMapper extends BaseMapper<TbRulePool> {

    void updateClueRule(TbRulePoolDTO tbRulePoolDTO);

    TbRulePool selectTbRulePoolByType(String type);
}




