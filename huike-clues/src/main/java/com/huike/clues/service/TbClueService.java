package com.huike.clues.service;

import com.huike.clues.domain.TbClue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.dto.TbClueAssignmentDTO;
import com.huike.clues.domain.dto.TbClueDTO;
import com.huike.clues.domain.dto.TbFalseClue;

import java.util.List;

/**
* @author 93238
* @description 针对表【tb_clue(线索)】的数据库操作Service
* @createDate 2023-10-12 06:35:46
*/
public interface TbClueService extends IService<TbClue> {

    /**
     * 添加线索
     * @param dto
     */
    void insert(TbClueDTO dto);


    /**
     * 通过id 查询信息
     * @param id
     * @return
     */
    TbClueDTO findById(Long id);

    /**
     * 修改线索管理
     * @param dto
     */
    void update(TbClueDTO dto);

    /**
     * 分页查询
     *
     * @param dto @return
     */
    List<TbClueDTO> find(TbClueDTO dto);

    /**
     * ## 批量分配
     * @param dto
     */
    void assignment(TbClueAssignmentDTO dto);

    /**
     * 伪线索
     *
     * @param id
     * @param tbFalseClue
     */
    void falseClue(Long id, TbFalseClue tbFalseClue);

    /**
     * 线索转商机
     *
     * @param id
     * @return
     */
     TbClueDTO delete(Long id);

    /**
     * 捞取
     * @param dto
     */
    void gain(TbClueAssignmentDTO dto);

    /**
     * 查询线索池
     * @param dto
     * @return
     */
    List<TbClueDTO> findPool(TbClueDTO dto);
}
