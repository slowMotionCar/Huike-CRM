package com.huike.clues.utils;

import java.util.Calendar;
import java.util.Date;


import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.domain.dto.TbRulePoolDTO;
import com.huike.clues.mapper.TbRulePoolMapper;
import com.huike.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 获取结束时间的工具类
 * @author 86150
 *
 */
@Slf4j
public class HuiKeCrmDateUtils {

    /**
     * 自定义工具类, 接收TbAssignRecord返回结束时间
     * @param tbAssignRecord
     * @return
     */
    public static Date getEndDateByRule(TbAssignRecord tbAssignRecord) {
        log.info("\n\n\n {}\n",tbAssignRecord);
        TbRulePoolMapper rulePoolMapper = SpringUtils.getBean(TbRulePoolMapper.class);
        TbRulePool rulePool = rulePoolMapper.selectTbRulePoolByType(tbAssignRecord.getType());
        if (rulePool == null) {
            return null;
        }

        Date date = getDate(rulePool.getLimitTime().intValue(),
                rulePool.getLimitTimeType(), tbAssignRecord.getCreateTime());
        return date;
    }

	public static Date getDate(int time, String type, Date now){
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        if(TbRulePoolDTO.LimitTimeType.HOUR.getValue().equals(type)){
            cal.add(Calendar.HOUR, time);
        }else if(TbRulePoolDTO.LimitTimeType.DAY.getValue().equals(type)){
            cal.add(Calendar.DATE, time);
        }else if(TbRulePoolDTO.LimitTimeType.WEEK.getValue().equals(type)){
            cal.add(Calendar.DAY_OF_WEEK, time);
        }
        return cal.getTime();
    }
}
