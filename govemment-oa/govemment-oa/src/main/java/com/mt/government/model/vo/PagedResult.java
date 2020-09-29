package com.mt.government.model.vo;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/17 16:24
 */
@Data
public class PagedResult {
    private Integer page;            // 当前页数
    private Integer pageSize;        // 每页显示数量
    private Integer total;           // 总页数
    private Long records;        // 总记录数
    private List<?> rows;        // 每页显示的内容

    public PagedResult() {
    }

    public PagedResult(int page, int pageSize, List<?> rows) {
        this.page = page;
        this.pageSize = pageSize;
        this.rows = rows;
        this.total = null;
        this.records = null;
    }

    public PagedResult(int page, int pageSize, int total, long records, List<?> rows) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records;
        this.rows = rows;
    }

    /**
     * 通用返回结果
     *
     * @param list 分页后的列表
     * @param <T>  类型
     * @return 通用分页结果
     */
    public static <T> PagedResult commonResult(List<T> list) {
        PageInfo<T> info = new PageInfo<>(list);
        PagedResult result = new PagedResult(info.getPageNum(), info.getPageSize(), info.getPages(), info.getTotal(), list);
        return result;
    }
}
