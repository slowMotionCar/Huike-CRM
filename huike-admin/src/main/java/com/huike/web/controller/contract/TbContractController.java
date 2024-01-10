package com.huike.web.controller.contract;

import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.PageDomain;
import com.huike.common.core.page.TableDataInfo;
import com.huike.contract.domain.TbContract;
import com.huike.contract.domain.dto.PageTbContractDTO;
import com.huike.contract.domain.dto.TbContractDTO;
import com.huike.contract.service.TbContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 合同Controller
 */
@Slf4j
@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class TbContractController {

    private final TbContractService tbContractService;

    /**
     * 新增合同
     * @param dto
     * @return
     */
    @PostMapping
    public AjaxResult insertContract(@RequestBody TbContractDTO dto) {

        tbContractService.insertContract(dto);

        return AjaxResult.success();
    }

    /**
     * 合同分页查询
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo<List<TbContract>> selectTbContractList(PageTbContractDTO dto) {
        return tbContractService.selectTbContractList(dto);
    }

    /**
     * 通过id获取合同信息1
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public AjaxResult<TbContract> selectDetailById(@PathVariable Integer id) {

        TbContract tbContract = tbContractService.selectDetailById(id);

        return AjaxResult.success(tbContract);
    }

    /**
     * 通过id获取合同信息2
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult<TbContract> selectTbContractById(@PathVariable String id) {

        TbContract tbContract = tbContractService.selectTbContractById(id);

        return AjaxResult.success(tbContract);
    }

    /**
     * 商机转合同
     *
     * @param id
     * @return
     */
    @PutMapping("/changeContract/{id}")
    public AjaxResult<Integer> changeContract(@RequestBody TbContractDTO dto, @PathVariable Integer id) {
        log.info("dto{},=== id{}",dto,id);
        tbContractService.changeContract(id, dto);
        return AjaxResult.success();
    }
}
