package com.huike.clues.utils;

import java.util.Calendar;
import java.util.Date;

import com.huike.clues.domain.dto.TbRulePoolDTO;

public class JobUtils {

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
