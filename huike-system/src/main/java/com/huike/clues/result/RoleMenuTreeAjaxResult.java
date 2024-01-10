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
@ApiModel("角色菜单列表树结果对象")
public class RoleMenuTreeAjaxResult extends AjaxResult {

    @ApiModelProperty("根据角色ID菜单id列表")
    private List<Integer> checkedKeys;

    @ApiModelProperty("菜单下拉树结构")
    private List<TreeSelect> menus;
    public RoleMenuTreeAjaxResult(int code, String msg){
        super(code,msg);
    }

    public RoleMenuTreeAjaxResult(int code, String msg, T data){
        super(code,msg,data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static RoleMenuTreeAjaxResult success()
    {
        return RoleMenuTreeAjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static RoleMenuTreeAjaxResult success(T data)
    {
        return RoleMenuTreeAjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static RoleMenuTreeAjaxResult success(String msg)
    {
        return RoleMenuTreeAjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static RoleMenuTreeAjaxResult success(String msg, T data)
    {
        return new RoleMenuTreeAjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static RoleMenuTreeAjaxResult error()
    {
        return RoleMenuTreeAjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static RoleMenuTreeAjaxResult error(String msg)
    {
        return RoleMenuTreeAjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static RoleMenuTreeAjaxResult error(String msg, T data)
    {
        return new RoleMenuTreeAjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static RoleMenuTreeAjaxResult error(int code, String msg)
    {
        return new RoleMenuTreeAjaxResult(code, msg, null);
    }
}
