package com.huike.clues.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.domain.dto.TbRulePoolDTO;
import com.huike.clues.service.TbRulePoolService;
import com.huike.clues.mapper.TbRulePoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 93238
* @description 针对表【tb_rule_pool(线索池规则)】的数据库操作Service实现
* @createDate 2023-10-12 06:35:46
*/
@Service
public class TbRulePoolServiceImpl extends ServiceImpl<TbRulePoolMapper, TbRulePool>
    implements TbRulePoolService{
    @Autowired
    private TbRulePoolMapper tbRulePoolMapper;
    /**
     * 根据type查询规则
     *
     * @param type
     * @return
     */
    @Override
    public TbRulePool selectRule(Integer type) {

        QueryWrapper<TbRulePool> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type); // 设置查询条件
        TbRulePool tbRulePool = tbRulePoolMapper.selectOne(queryWrapper);

        return tbRulePool;
    }

    /**
     * 修改线索池规则
     *
     * @param tbRulePoolDTO
     */
    @Override
    public void updateClueRule(TbRulePoolDTO tbRulePoolDTO) {
        tbRulePoolMapper.updateClueRule(tbRulePoolDTO);
    }
}




