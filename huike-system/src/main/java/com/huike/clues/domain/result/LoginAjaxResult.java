package com.huike.clues.domain.result;

import com.huike.common.constant.HttpStatus;
import com.huike.common.core.domain.AjaxResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description TODO
 * @Author itheima
 * @Date 2023-07-07 20:25
 */
@ApiModel("登录结果对象")
@Data
public class LoginAjaxResult extends AjaxResult {

    @ApiModelProperty("token")
    private String token;

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public LoginAjaxResult(int code, String msg)
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
    public LoginAjaxResult(int code, String msg, Object data)
    {
        super(code,msg,data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static LoginAjaxResult success()
    {
        return LoginAjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static LoginAjaxResult success(Object data)
    {
        return LoginAjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static LoginAjaxResult success(String msg)
    {
        return LoginAjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static LoginAjaxResult success(String msg, Object data)
    {
        return new LoginAjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static LoginAjaxResult error()
    {
        return LoginAjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static LoginAjaxResult error(String msg)
    {
        return LoginAjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static LoginAjaxResult error(String msg, Object data)
    {
        return new LoginAjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static LoginAjaxResult error(int code, String msg)
    {
        return new LoginAjaxResult(code, msg, null);
    }
}
