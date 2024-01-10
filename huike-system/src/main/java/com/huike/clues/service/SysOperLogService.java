package com.huike.clues.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.dto.SysQueryforOperLogDTO;
import com.huike.clues.domain.pojo.SysOperLog;
import com.huike.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author Zhilin
* @description 针对表【sys_oper_log(操作日志记录)】的数据库操作Service
* @createDate 2023-10-16 21:18:44
*/
public interface SysOperLogService extends IService<SysOperLog> {

    /**
     * 分页查询系统操作日志
     * @param sysQueryforOperLogDTO
     * @return
     */
    TableDataInfo getLog(SysQueryforOperLogDTO sysQueryforOperLogDTO);


    /**
     * 操作日志删除
     * @param operIds
     */
    void deleteLog(List<Integer> operIds);

    /**
     * 操作日志清空
     *
     * @return
     */
    void cleanLog();

    /**
     * 导出数据
     * @param response
     */
    void getExport(HttpServletResponse response);
}
