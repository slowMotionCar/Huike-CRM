package com.huike.clues.domain.dto;

import java.util.List;

/**
 * @Description TbClueAssignmentDTO
 * @Author Leezi
 * @Date 2023-10-16
 */
public class TbClueAssignmentDTO {
    private List<Long> ids;
    private Long userId;

    public TbClueAssignmentDTO(List<Long> ids, Long userId) {
        this.ids = ids;
        this.userId = userId;
    }

    public TbClueAssignmentDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
