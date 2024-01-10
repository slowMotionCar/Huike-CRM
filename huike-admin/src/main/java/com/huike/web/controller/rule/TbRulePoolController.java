package com.huike.web.controller.rule;

import com.huike.clues.domain.TbRulePool;
import com.huike.clues.domain.dto.TbRulePoolDTO;
import com.huike.clues.service.TbRulePoolService;
import com.huike.common.core.domain.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 线索或商机池规则相关接口
 * @Date 2023-10-10
 */
@RestController
@RequestMapping("/rule/pool")
public class TbRulePoolController {
    @Autowired
    private TbRulePoolService tbRulePoolService;

    /**
     * 根据type查询规则
     * @param type
     * @return
     */
    @GetMapping("/{type}")
    @ApiOperation("获取规则基础信息type=0 线索 type=1 商机")
    public AjaxResult selectRule(@PathVariable Integer type){
        TbRulePool tbRulePool=tbRulePoolService.selectRule(type);
        return AjaxResult.success(tbRulePool);
    }

    /**
     * 修改线索池规则
     * @param tbRulePoolDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改线索池规则")
    private AjaxResult updateClueRule(@RequestBody TbRulePoolDTO tbRulePoolDTO){
        tbRulePoolService.updateClueRule(tbRulePoolDTO);

        return AjaxResult.success();
    }
}
