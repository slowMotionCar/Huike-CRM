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
@ApiModel("个人信息结果对象")
public class ProfileAjaxResult extends AjaxResult {

    @ApiModelProperty("所属角色名称列表,逗号分隔")
    private String roleGroup;

    @ApiModelProperty("所属岗位列表,逗号分隔")
    private String postGroup;

    public ProfileAjaxResult(int code, String msg){
        super(code,msg);
    }

    public ProfileAjaxResult(int code, String msg, T data){
        super(code,msg,data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static ProfileAjaxResult success()
    {
        return ProfileAjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static ProfileAjaxResult success(T data)
    {
        return ProfileAjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static ProfileAjaxResult success(String msg)
    {
        return ProfileAjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static ProfileAjaxResult success(String msg, T data)
    {
        return new ProfileAjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static ProfileAjaxResult error()
    {
        return ProfileAjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static ProfileAjaxResult error(String msg)
    {
        return ProfileAjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static ProfileAjaxResult error(String msg, T data)
    {
        return new ProfileAjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static ProfileAjaxResult error(int code, String msg)
    {
        return new ProfileAjaxResult(code, msg, null);
    }
}
