package com.huike.clues.domain.result;

import com.huike.common.constant.HttpStatus;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysUserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @Description TODO
 * @Author itheima
 * @Date 2023-07-07 20:25
 */
@ApiModel("用户信息结果对象")
@Data
public class UserInfoAjaxResult extends AjaxResult {

    @ApiModelProperty("用户对象")
    private SysUserDTO user;

    @ApiModelProperty("角色role_key集合，示例：admin,xiaosuo,zhuguan")
    private Set<String> roles;

    @ApiModelProperty("权限标识集合，示例：system:role:list,system:user:edit")
    private Set<String> permissions;


    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public UserInfoAjaxResult(int code, String msg)
    {
        super(code,msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public UserInfoAjaxResult(int code, String msg, Object data)
    {
        super(code,msg,data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static UserInfoAjaxResult success()
    {
        return UserInfoAjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static UserInfoAjaxResult success(Object data)
    {
        return UserInfoAjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static UserInfoAjaxResult success(String msg)
    {
        return UserInfoAjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static UserInfoAjaxResult success(String msg, Object data)
    {
        return new UserInfoAjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static UserInfoAjaxResult error()
    {
        return UserInfoAjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static UserInfoAjaxResult error(String msg)
    {
        return UserInfoAjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static UserInfoAjaxResult error(String msg, Object data)
    {
        return new UserInfoAjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static UserInfoAjaxResult error(int code, String msg)
    {
        return new UserInfoAjaxResult(code, msg, null);
    }
}
