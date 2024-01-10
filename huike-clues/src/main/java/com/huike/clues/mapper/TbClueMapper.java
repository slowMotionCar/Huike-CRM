package com.huike.clues.mapper;

import com.huike.clues.domain.TbClue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.domain.dto.TbClueDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
* @author 93238
* @description 针对表【tb_clue(线索)】的数据库操作Mapper
* @createDate 2023-10-12 06:35:46
* @Entity com.huike.clues.domain.TbClue
*/
@Mapper
public interface TbClueMapper extends BaseMapper<TbClue> {


    /**
     * 条件查询
     *
     * @param dto
     * @return
     */
    List<TbClueDTO> findPage(TbClueDTO dto);

    /**
     * 查询线索池
     * @param dto
     * @return
     */
    List<TbClueDTO> findPagePool(TbClueDTO dto);

    void updateTransfer(Long id, String status);

    void updateClueEndTimeById(Long id, Date endDate);
}




