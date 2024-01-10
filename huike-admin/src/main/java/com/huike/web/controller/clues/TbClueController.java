package com.huike.web.controller.clues;


import com.huike.business.domain.dto.TbBusinessDTODTO;
import com.huike.business.service.TbBusinessService;
import com.huike.clues.domain.dto.TbClueAssignmentDTO;
import com.huike.clues.domain.dto.TbClueDTO;
import com.huike.clues.domain.dto.TbFalseClue;
import com.huike.clues.service.TbClueService;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 线索管理Controller
 *
 * @date 2023-04-02
 */
@RestController
@Slf4j
@RequestMapping("/clues/clue")
public class TbClueController extends BaseController {
    @Autowired
    private TbClueService tbClueService;

    /**
     * 添加线索管理
     *
     * @return
     */
    @PostMapping
    public AjaxResult insert(@RequestBody TbClueDTO dto) {
        log.info("\n{}\n", dto);
        tbClueService.insert(dto);
        return AjaxResult.success();
    }

    /**
     * 通过id查询线索信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult<TbClueDTO> findById(@PathVariable(name = "id") Long id) {
        TbClueDTO tbClueDTO = tbClueService.findById(id);
        return AjaxResult.success(tbClueDTO);
    }

    /**
     * 修改线索信息
     *
     * @param dto
     * @return
     */
    @PutMapping()
    public AjaxResult update(@RequestBody TbClueDTO dto) {
        tbClueService.update(dto);
        return AjaxResult.success();
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("list")
    public TableDataInfo find(TbClueDTO dto) {
        List<TbClueDTO> list = tbClueService.find(dto);
        return getDataTablePage(list);

    }

    /**
     * ## 批量分配
     *
     * @param dto
     * @return
     */
    @PutMapping("/assignment")
    public AjaxResult assignment(@RequestBody TbClueAssignmentDTO dto) {
        tbClueService.assignment(dto);
        return AjaxResult.success();
    }

    /**
     * 伪线索
     *
     * @return
     */
    @PutMapping("/false/{id}")
    public AjaxResult falseClue(@PathVariable Long id, @RequestBody TbFalseClue tbFalseClue) {
        tbClueService.falseClue(id, tbFalseClue);
        return AjaxResult.success();
    }

    @Autowired
    private TbBusinessService tbBusinessService;
    /**
     * 线索转商机
     * @param id
     * @return
     */
    @PutMapping("/changeBusiness/{id}")
    public AjaxResult delete(@PathVariable Long id) {
        TbClueDTO tbClueDTO = tbClueService.delete(id);
        TbBusinessDTODTO tbBusinessDTODTO = new TbBusinessDTODTO();
        BeanUtils.copyProperties(tbClueDTO, tbBusinessDTODTO);
        tbBusinessService.insertBusiness(tbBusinessDTODTO);
        return AjaxResult.success();
    }

    /**
     * 批量捞取
     */
    @PutMapping("/gain")
    public AjaxResult gain(@RequestBody TbClueAssignmentDTO dto) {
        tbClueService.gain(dto);
        return AjaxResult.success();
    }


    /**
     * ## 查询线索池
     */
    @GetMapping("pool")
    public TableDataInfo findPool(TbClueDTO dto) {
        List<TbClueDTO> list = tbClueService.findPool(dto);
        return getDataTablePage(list);
    }
}
