package com.huike.clues.result;

import com.huike.common.constant.HttpStatus;
import com.huike.common.core.domain.AjaxResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @Description TODO
 * @Author itheima
 * @Date 2023-07-08 22:06
 */
@Data
@ApiModel("上传用户头像结果对象")
public class AvatarAjaxResult extends AjaxResult {

    @ApiModelProperty("上传成功后的路径")
    private String imgUrl;

    public AvatarAjaxResult(int code, String msg){
        super(code,msg);
    }

    public AvatarAjaxResult(int code, String msg, T data){
        super(code,msg,data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AvatarAjaxResult success()
    {
        return AvatarAjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AvatarAjaxResult success(T data)
    {
        return AvatarAjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AvatarAjaxResult success(String msg)
    {
        return AvatarAjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AvatarAjaxResult success(String msg, T data)
    {
        return new AvatarAjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static AvatarAjaxResult error()
    {
        return AvatarAjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AvatarAjaxResult error(String msg)
    {
        return AvatarAjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AvatarAjaxResult error(String msg, T data)
    {
        return new AvatarAjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AvatarAjaxResult error(int code, String msg)
    {
        return new AvatarAjaxResult(code, msg, null);
    }
}
