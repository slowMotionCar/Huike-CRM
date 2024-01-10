package com.huike.clues.service;

import com.huike.clues.domain.TbActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.dto.TbActivityDTO;
import com.huike.clues.result.TableDataInfoActivityList;
import com.huike.common.core.page.PageDomain;

import java.util.List;

/**
* @author 93238
* @description 针对表【tb_activity(活动管理)】的数据库操作Service
* @createDate 2023-10-12 06:35:46
*/
public interface TbActivityService extends IService<TbActivity> {

    /**
     * 添加活动
     * @param dto
     */
    void insert(TbActivityDTO dto);

    /**
     * 修改活动信息
     */
    void updateActivity(TbActivityDTO dto);

    /**
     * 查询活动管理列表
     *
     * @return
     */
    TableDataInfoActivityList<List<TbActivity>> find(PageDomain domain, String code, Integer channel);

    /**
     * 获取状态为为2的渠道活动列表
     *
     * @return
     */
    List<TbActivity> findStatus(Integer status);

    /**
     * 删除活动
     * @param ids
     */
    void delete(Long ids);

    /**
     * 获取活动管理详细信息
     * @param id
     * @return
     */
    TbActivity findById(Long id);
}
