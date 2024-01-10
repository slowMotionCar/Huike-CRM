package com.huike.web.controller.report;

import com.huike.clues.domain.vo.VulnerabilityMapVOnew;
import com.huike.common.core.domain.AjaxResult;
import com.huike.report.domain.vo.ChangeStatisticsVO;
import com.huike.report.domain.vo.IndexBaseInfoVO;
import com.huike.report.domain.vo.IndexTodayInfoVO;
import com.huike.report.domain.vo.IndexTodoInfoVO;
import com.huike.report.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 首页报表相关接口
 * @Date 2023-10-10
 * @Creator:卓志霖
 */
@RestController
@Slf4j
@Api(tags = "首页")
@RequiredArgsConstructor
public class IndexController {

    public final IndexService indexService;


    /**
     * 首页今日简报数据
     * @return
     */
    @ApiOperation("首页今日简报数据")
    @GetMapping("/index/getTodayInfo")
    public AjaxResult getTodayInfo(){

        log.info("首页今日简报数据");
        IndexTodayInfoVO indexTodayInfoVO = indexService.getTodayInfo();
        return AjaxResult.success(indexTodayInfoVO);
    }

    /**
     * 基础数据统计
     * @return
     */
    @GetMapping("/index/getBaseInfo")
    @ApiOperation("基础数据统计")
    public AjaxResult getBaseInfo(String beginCreateTime,String endCreateTime){

        log.info("基础数据统计");
        IndexBaseInfoVO indexBaseInfoVO = indexService.getBaseInfo(beginCreateTime,endCreateTime);

        return AjaxResult.success(indexBaseInfoVO);
    }


    /**
     * 首页--获取待办数据
     * @param beginCreateTime
     * @param endCreateTime
     * @return
     */
    @GetMapping("/index/getTodoInfo")
    @ApiOperation("首页--获取待办数据")
    public AjaxResult getTodoInfo(String beginCreateTime,String endCreateTime){

        IndexTodoInfoVO indexTodoInfoVO = indexService.getTodoInfo(beginCreateTime,endCreateTime);

        return AjaxResult.success(indexTodoInfoVO);
    }


    @GetMapping("/index/businessChangeStatistics")
    @ApiOperation("首页--商机转换龙虎榜")
    public AjaxResult businessChangeStatistics(String beginCreateTime,String endCreateTime){


        List<ChangeStatisticsVO> changeStatisticsVO = indexService.businessChangeStatistics(beginCreateTime,endCreateTime);

        return AjaxResult.success(changeStatisticsVO);
    }


    @GetMapping("/index/salesStatistic")
    @ApiOperation("首页--线索转化龙虎榜")
    public AjaxResult salesStatistic(String beginCreateTime,String endCreateTime){


        List<ChangeStatisticsVO> changeStatisticsVO = indexService.salesStatistic(beginCreateTime,endCreateTime);

        return AjaxResult.success(changeStatisticsVO);
    }


    @GetMapping("/report/getVulnerabilityMap/{beginCreateTime}/{endCreateTime}")
    @ApiOperation("线索统计线索转换率报表\n")
    public AjaxResult getVulnerabilityMap(@PathVariable String beginCreateTime, @PathVariable String endCreateTime){

        System.out.println(beginCreateTime);
        System.out.println(endCreateTime);
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        VulnerabilityMapVOnew vulnerabilityMapVOS = indexService.getVulnerabilityMap(beginCreateTime,endCreateTime);

        return AjaxResult.success(vulnerabilityMapVOS);
    }

}
