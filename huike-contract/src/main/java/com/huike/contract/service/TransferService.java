package com.huike.contract.service;

import com.huike.common.core.domain.entity.SysUserDTO;
import com.huike.contract.domain.vo.TransferVo;
import com.huike.contract.result.TransferAssignmentData;

import java.util.List;

/**
 * @Description TransferService
 * @Author xhr
 * @Date 2023-10-17
 */
public interface TransferService {
    /**
     * 转派分页查询
     *
     * @param sysUser
     */
    List<TransferVo> selectTransferList(SysUserDTO sysUser);

    /**
     * 转派处理
     *
     * @param transferUserId
     * @param type
     * @param userId
     * @return
     */
    TransferAssignmentData assignment(Long transferUserId, String type, Long userId);
}
