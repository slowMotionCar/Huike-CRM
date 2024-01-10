package com.huike.common.core.domain;

import com.huike.common.constant.HttpStatus;
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
@ApiModel("通用上传请求果对象")
public class UploadFileAjaxResult extends AjaxResult {

    @ApiModelProperty("文件名，UUID+原始文件名后缀")
    private String fileName;

    @ApiModelProperty("上传后的完整路径")
    private String url;



    public UploadFileAjaxResult(int code, String msg){
        super(code,msg);
    }

    public UploadFileAjaxResult(int code, String msg, T data){
        super(code,msg,data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static UploadFileAjaxResult success()
    {
        return UploadFileAjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static UploadFileAjaxResult success(T data)
    {
        return UploadFileAjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static UploadFileAjaxResult success(String msg)
    {
        return UploadFileAjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static UploadFileAjaxResult success(String msg, T data)
    {
        return new UploadFileAjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static UploadFileAjaxResult error()
    {
        return UploadFileAjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static UploadFileAjaxResult error(String msg)
    {
        return UploadFileAjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static UploadFileAjaxResult error(String msg, T data)
    {
        return new UploadFileAjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static UploadFileAjaxResult error(int code, String msg)
    {
        return new UploadFileAjaxResult(code, msg, null);
    }
}
