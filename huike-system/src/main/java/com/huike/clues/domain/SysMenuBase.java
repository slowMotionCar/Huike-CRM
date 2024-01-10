package com.huike.clues.domain;

import java.util.List;

public class SysMenuBase {
    private List<Object> children;
    private Integer parentName;

    public SysMenuBase() {
    }

    public List<Object> getChildren() {
        return children;
    }

    public void setChildren(List<Object> children) {
        this.children = children;
    }

    public Integer getParentName() {
        return parentName;
    }

    public void setParentName(Integer parentName) {
        this.parentName = parentName;
    }

    public SysMenuBase(List<Object> children, Integer parentName) {
        this.children = children;
        this.parentName = parentName;
    }
}
