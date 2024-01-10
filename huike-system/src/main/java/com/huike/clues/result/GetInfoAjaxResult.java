package com.huike.clues.result;

import com.huike.common.constant.HttpStatus;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysRoleDTO;
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
@ApiModel("获取用户详细信息")
public class GetInfoAjaxResult extends AjaxResult {

    @ApiModelProperty("角色列表")
    private List<SysRoleDTO> roles;

    @ApiModelProperty("忽略属性，设置为null即可")
    private String posts;


    @ApiModelProperty("忽略属性，设置为null即可")
    private String postIds;

    @ApiModelProperty("角色Id列表,根据用户id获取")
    private List<Integer> roleIds;


    public GetInfoAjaxResult(int code, String msg){
        super(code,msg);
    }

    public GetInfoAjaxResult(int code, String msg, T data){
        super(code,msg,data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static GetInfoAjaxResult success()
    {
        return GetInfoAjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static GetInfoAjaxResult success(T data)
    {
        return GetInfoAjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static GetInfoAjaxResult success(String msg)
    {
        return GetInfoAjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static GetInfoAjaxResult success(String msg, T data)
    {
        return new GetInfoAjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static GetInfoAjaxResult error()
    {
        return GetInfoAjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static GetInfoAjaxResult error(String msg)
    {
        return GetInfoAjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static GetInfoAjaxResult error(String msg, T data)
    {
        return new GetInfoAjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static GetInfoAjaxResult error(int code, String msg)
    {
        return new GetInfoAjaxResult(code, msg, null);
    }
}
