package com.huike.clues.service;

import com.huike.clues.domain.TbRulePool;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.dto.TbRulePoolDTO;

/**
* @author 93238
* @description 针对表【tb_rule_pool(线索池规则)】的数据库操作Service
* @createDate 2023-10-12 06:35:46
*/
public interface TbRulePoolService extends IService<TbRulePool> {

    /**
     * 根据type查询规则
     * @param type
     * @return
     */
    TbRulePool selectRule(Integer type);

    /**
     * 修改线索池规则
     * @param tbRulePoolDTO
     */
    void updateClueRule(TbRulePoolDTO tbRulePoolDTO);
}
