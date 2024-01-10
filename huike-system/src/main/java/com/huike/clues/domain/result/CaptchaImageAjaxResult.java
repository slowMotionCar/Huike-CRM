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
@ApiModel("验证码图片结果对象")
@Data
public class CaptchaImageAjaxResult extends AjaxResult {

    @ApiModelProperty("验证码标识值")
    private String uuid;

    @ApiModelProperty("验证码图片")
    private String img;

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public CaptchaImageAjaxResult(int code, String msg)
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
    public CaptchaImageAjaxResult(int code, String msg, Object data)
    {
        super(code,msg,data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static CaptchaImageAjaxResult success()
    {
        return CaptchaImageAjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static CaptchaImageAjaxResult success(Object data)
    {
        return CaptchaImageAjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static CaptchaImageAjaxResult success(String msg)
    {
        return CaptchaImageAjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static CaptchaImageAjaxResult success(String msg, Object data)
    {
        return new CaptchaImageAjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static CaptchaImageAjaxResult error()
    {
        return CaptchaImageAjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static CaptchaImageAjaxResult error(String msg)
    {
        return CaptchaImageAjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static CaptchaImageAjaxResult error(String msg, Object data)
    {
        return new CaptchaImageAjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static CaptchaImageAjaxResult error(int code, String msg)
    {
        return new CaptchaImageAjaxResult(code, msg, null);
    }

}
