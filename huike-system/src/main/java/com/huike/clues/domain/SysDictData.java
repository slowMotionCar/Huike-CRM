package com.huike.clues.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
* 字典数据表
* @TableName sys_dict_data
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictData implements Serializable {

    /**
    * 字典编码
    */
    @NotNull(message="[字典编码]不能为空")
    @ApiModelProperty("字典编码")
    private Long dictCode;
    /**
    * 字典排序
    */
    @ApiModelProperty("字典排序")
    private Integer dictSort;
    /**
    * 字典标签
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("字典标签")
    private String dictLabel;
    /**
    * 字典键值
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("字典键值")
    private String dictValue;
    /**
    * 字典类型
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("字典类型")
    private String dictType;
    /**
    * 样式属性（其他样式扩展）
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("样式属性（其他样式扩展）")
    private String cssClass;
    /**
    * 表格回显样式
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("表格回显样式")
    private String listClass;
    /**
    * 是否默认（Y是 N否）
    */
    @ApiModelProperty("是否默认（Y是 N否）")
    private String isDefault;
    /**
    * 状态（0正常 1停用）
    */
    @ApiModelProperty("状态（0正常 1停用）")
    private String status;
    /**
    * 创建者
    */
    @Size(max= 64,message="编码长度不能超过64")
    @ApiModelProperty("创建者")
    private String createBy;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    /**
    * 更新者
    */
    @Size(max= 64,message="编码长度不能超过64")
    @ApiModelProperty("更新者")
    private String updateBy;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;
    /**
    * 备注
    */
    @Size(max= 500,message="编码长度不能超过500")
    @ApiModelProperty("备注")
    private String remark;
}
