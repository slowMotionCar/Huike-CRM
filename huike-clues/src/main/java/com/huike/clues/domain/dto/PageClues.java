package com.huike.clues.domain.dto;

import com.huike.common.core.page.PageDomain;
import lombok.Data;

import java.util.Date;

@Data
public class PageClues  extends PageDomain {
    private Long id;
    private String phone;
    private String owner;
    private Integer channel;

    public PageClues() {
    }

    public PageClues(Long id, String phone, String owner, Integer channel) {
        this.id = id;
        this.phone = phone;
        this.owner = owner;
        this.channel = channel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
