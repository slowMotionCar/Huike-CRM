package com.huike.web.controller.business;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huike.business.domain.TbBusiness;
import com.huike.business.domain.dto.TbBusinessAssingDTO;
import com.huike.business.domain.dto.TbBusinessDTODTO;
import com.huike.business.domain.dto.TbBusinessGainDTO;
import com.huike.business.service.TbBusinessService;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.mapper.TbClueMapper;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysUserDTO;
import com.huike.common.core.page.PageDomain;
import com.huike.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 商机Controller
 * @date 2023-04-25
 */
@RestController
@RequestMapping("/business")
@Api(tags = "商机相关接口")
@Slf4j
public class TbBusinessController extends BaseController {
    @Resource
    private TbBusinessService tbBusinessService;
    @Resource
    private TbClueMapper tbClueMapper;
    @Resource
    private TbAssignRecordMapper tbAssignRecordMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 处理“新增商机”请求
     * @param tbBusinessDTODTO
     * @return
     */
    @ApiOperation("新增商机接口")
    @PostMapping
    public AjaxResult insertBusiness(@RequestBody TbBusinessDTODTO tbBusinessDTODTO) {
        log.info("执行新增商机接口：{}", tbBusinessDTODTO);
        tbBusinessService.insertBusiness(tbBusinessDTODTO);
        return AjaxResult.success();
    }

    /**
     * 处理“修改商机”请求
     * @param tbBusinessDTODTO
     * @return
     */
    @ApiOperation("修改商机接口")
    @PutMapping
    public AjaxResult updateBusiness(@RequestBody TbBusinessDTODTO tbBusinessDTODTO) {
        log.info("执行修改商机接口：{}", tbBusinessDTODTO);
        tbBusinessService.updateBusiness(tbBusinessDTODTO);
        return AjaxResult.success();
    }

    /**
     * 处理“商机分配”请求
     * @param tbBusinessAssingDTO
     * @return
     */
    @ApiOperation("商机分配接口")
    @PutMapping("/assignment")
    public AjaxResult assignBusiness(@RequestBody TbBusinessAssingDTO tbBusinessAssingDTO) {
        log.info("执行商机分配接口：{}", tbBusinessAssingDTO);
        tbBusinessService.assignBusiness(tbBusinessAssingDTO);
        return AjaxResult.success();
    }

    /**
     * 处理”分页查询商机列表“请求
     * @param tbBusinessDTODTO
     * @return
     */
    @ApiOperation("分页查询商机列表接口")
    @GetMapping("/list")
    public TableDataInfo pageSelectBusiness(TbBusinessDTODTO tbBusinessDTODTO) {
        log.info("执行分页查询商机列表接口：{}", tbBusinessDTODTO);

        Long id = tbBusinessDTODTO.getId();
        String phone = tbBusinessDTODTO.getPhone();
        String name = tbBusinessDTODTO.getName();
        String status = tbBusinessDTODTO.getStatus();

        Map<String, Object> params = tbBusinessDTODTO.getParams();
        String beginCreateTime = (String) params.get("beginCreateTime");
        String endCreateTime = (String) params.get("endCreateTime");

        LambdaQueryWrapper<TbBusiness> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(id != null, TbBusiness::getId, id);
        lambdaQueryWrapper.eq(phone != null, TbBusiness::getPhone, phone);
        lambdaQueryWrapper.like(name != null, TbBusiness::getName, name);
        lambdaQueryWrapper.eq(status != null, TbBusiness::getStatus, status);
        if (beginCreateTime != null && !beginCreateTime.equals("")
                && endCreateTime != null && !endCreateTime.equals("")) {
            lambdaQueryWrapper.ge(TbBusiness::getCreateTime, beginCreateTime);
            lambdaQueryWrapper.le(TbBusiness::getCreateTime, endCreateTime);
        }
        lambdaQueryWrapper.orderByDesc(TbBusiness::getCreateTime);

        List<TbBusiness> tbBusinessList = tbBusinessService.list(lambdaQueryWrapper);

        return getDataTablePage(tbBusinessList);
    }

    /**
     * 处理”删除商机接口“请求
     * @param ids
     * @return
     */
    @ApiOperation("删除商机接口")
    @DeleteMapping("/{ids}")
    public AjaxResult deleteBusinessByIds(@PathVariable String ids) {
        log.info("执行删除商机接口：{}", ids);
        tbBusinessService.deleteBusinessByIds(ids);
        return AjaxResult.success();
    }

    /**
     * 处理”踢回公海接口“请求
     * @param id
     * @param reason
     * @return
     */
    @ApiOperation("踢回公海接口")
    @PutMapping("/back/{id}/{reason}")
    public AjaxResult backPoll(@PathVariable Integer id, @PathVariable Long reason) {
        log.info("执行踢回公海接口：{}，{}", id, reason);
        tbBusinessService.backPoll(id, reason);
        return AjaxResult.success();
    }

    /**
     * 处理“获取商机详细信息”请求
     * @param id
     * @return
     */
    @ApiOperation("获取商机详细信息接口")
    @GetMapping("/{id}")
    public AjaxResult selectBusinessDetail(@PathVariable Integer id) {
        TbBusiness tbBusiness = tbBusinessService.selectBusinessDetail(id);
        return AjaxResult.success(tbBusiness);
    }

    /**
     * 处理”分页查询公海接口“请求
     * @param tbBusinessDTODTO
     * @return
     */
    @ApiOperation("分页查询公海接口")
    @GetMapping("/pool")
    public TableDataInfo pageSelectPool(
            TbBusinessDTODTO tbBusinessDTODTO) {
        log.info("执行分页查询公海接口：{}", tbBusinessDTODTO);
        List<TbBusiness> tbBusinessList = tbBusinessService.pageSelectPool(tbBusinessDTODTO);
        return getDataTablePage(tbBusinessList);
    }

    /**
     * 处理”批量捞取“请求
     * @return
     */
    @ApiOperation("批量捞取接口")
    @PutMapping("/gain")
    public AjaxResult gain(@RequestBody TbBusinessGainDTO tbBusinessGainDTO) {
        log.info("执行批量捞取接口");
        tbBusinessService.gain(tbBusinessGainDTO);
        return AjaxResult.success();
    }
}
