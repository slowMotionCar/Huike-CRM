package com.huike.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("Treeselect树结构实体类")
public class TreeSelectChildren implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    @ApiModelProperty("节点ID")
    protected Object id;

    /**
     * 节点名称
     */
    @ApiModelProperty("节点名称")
    protected String label;
}

