package com.huike.clues.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description TODO
 * @Author itheima
 * @Date 2023-07-10 01:07
 */
@Data
@ApiModel("获取分页查询统计对象")
public class TableDataInfoActivityList<T>{



    @ApiModelProperty("活动统计对象")
    TableDataInfoActivityParams params;

    /** 总记录数 */
    @ApiModelProperty("总记录数")
    private long total;

    /** 列表数据 */
    @ApiModelProperty("当前页数据列表")
    private T rows;

    /** 消息状态码 */
    @ApiModelProperty("消息状态码")
    private int code;

    /** 消息内容 */
    @ApiModelProperty("消息内容")
    private String msg;


    /**
     * 表格数据对象
     */
    public TableDataInfoActivityList()
    {
    }

    /**
     * 分页
     *
     * @param list 列表数据
     * @param total 总记录数
     */
    public TableDataInfoActivityList(T list, int total)
    {
        this.rows = list;
        this.total = total;
    }
    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public T  getRows()
    {
        return rows;
    }

    public void setRows(T rows)
    {
        this.rows = rows;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

}
