package com.huike.common.result;

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
@ApiModel("文件下载结果对象")
public class FileDownloadAjaxResult extends AjaxResult {

    @ApiModelProperty("文件名")
    private String fileName;

    @ApiModelProperty("下载文件地址")
    private String url;

    public FileDownloadAjaxResult(int code, String msg){
        super(code,msg);
    }

    public FileDownloadAjaxResult(int code, String msg, T data){
        super(code,msg,data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static FileDownloadAjaxResult success()
    {
        return FileDownloadAjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static FileDownloadAjaxResult success(T data)
    {
        return FileDownloadAjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static FileDownloadAjaxResult success(String msg)
    {
        return FileDownloadAjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static FileDownloadAjaxResult success(String msg, T data)
    {
        return new FileDownloadAjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static FileDownloadAjaxResult error()
    {
        return FileDownloadAjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static FileDownloadAjaxResult error(String msg)
    {
        return FileDownloadAjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static FileDownloadAjaxResult error(String msg, T data)
    {
        return new FileDownloadAjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static FileDownloadAjaxResult error(int code, String msg)
    {
        return new FileDownloadAjaxResult(code, msg, null);
    }
}
