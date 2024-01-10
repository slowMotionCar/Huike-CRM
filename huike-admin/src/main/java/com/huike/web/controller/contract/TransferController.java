package com.huike.web.controller.contract;

import com.huike.clues.domain.SysUser;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysUserDTO;
import com.huike.common.core.page.TableDataInfo;
import com.huike.contract.domain.vo.TransferVo;
import com.huike.contract.result.TransferAssignmentData;
import com.huike.contract.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 转派相关请求
 * @Date 2023-10-10
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController extends BaseController {

    private final TransferService transferService;

    /**
     * 获取转派列表
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo selectTransferList(SysUserDTO sysUser) {
        startPage();
        List<TransferVo> list = transferService.selectTransferList(sysUser);
        return getDataTablePage(list);
    }

    /**
     * 转派处理
     * @return
     */
    @PutMapping("/assignment/{type}/{userId}/{transferUserId}")
    public AjaxResult<TransferAssignmentData> assignment(@PathVariable Long transferUserId, @PathVariable String type, @PathVariable Long userId ) {

        TransferAssignmentData transferAssignmentData = transferService.assignment(transferUserId, type, userId);

        return AjaxResult.success(transferAssignmentData);
    }

}
