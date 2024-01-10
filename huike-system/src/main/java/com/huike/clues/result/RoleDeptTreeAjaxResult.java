package com.huike.clues.result;

import com.huike.common.constant.HttpStatus;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.TreeSelect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @Description TODO
 * @Author itheima
 * @Date 2023-07-08 22:06
 */
@Data
@ApiModel("角色部门列表树结果对象")
public class RoleDeptTreeAjaxResult extends AjaxResult {

    @ApiModelProperty("根据角色ID查询部门id列表")
    private List<Integer> checkedKeys;

    @ApiModelProperty("部门下拉树结构")
    private List<TreeSelect> depts;
    public RoleDeptTreeAjaxResult(int code, String msg){
        super(code,msg);
    }

    public RoleDeptTreeAjaxResult(int code, String msg, T data){
        super(code,msg,data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static RoleDeptTreeAjaxResult success()
    {
        return RoleDeptTreeAjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static RoleDeptTreeAjaxResult success(T data)
    {
        return RoleDeptTreeAjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static RoleDeptTreeAjaxResult success(String msg)
    {
        return RoleDeptTreeAjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static RoleDeptTreeAjaxResult success(String msg, T data)
    {
        return new RoleDeptTreeAjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static RoleDeptTreeAjaxResult error()
    {
        return RoleDeptTreeAjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static RoleDeptTreeAjaxResult error(String msg)
    {
        return RoleDeptTreeAjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static RoleDeptTreeAjaxResult error(String msg, T data)
    {
        return new RoleDeptTreeAjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static RoleDeptTreeAjaxResult error(int code, String msg)
    {
        return new RoleDeptTreeAjaxResult(code, msg, null);
    }
}
