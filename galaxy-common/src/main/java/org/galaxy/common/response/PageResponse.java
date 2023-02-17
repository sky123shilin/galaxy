package org.galaxy.common.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页响应对象
 * @param <T>
 */
@Data
public class PageResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private long pageSize;//分页大小

    private long pageNumber;//分页数量

    private long totalCount;//总数量

    private List<T> listData;//列表数据

    public PageResponse(){}

    public PageResponse(List<T> listData, long totalCount, long pageNumber, long pageSize) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalCount = totalCount;
        this.listData = listData.isEmpty() ? new ArrayList<>(1) : (ArrayList<T>) listData;
    }

    public PageResponse(Page<T> result) {
        this.pageSize = result.getSize();
        this.pageNumber = result.getCurrent();
        this.totalCount = result.getTotal();
        this.listData = result.getRecords().isEmpty() ? new ArrayList<>(1): result.getRecords();
    }

    public static <T> PageResponse<T> defaultValue(){
        List<T> listData = new ArrayList<>(1);
        return new PageResponse<T>(listData,0,1,10);
    }

    public static <T> PageResponse<T> create(List<T> listData, long totalCount, long pageNumber, long pageSize){
        return new PageResponse<>(listData,totalCount,pageNumber,pageSize);
    }

    public static <T> PageResponse<T> create(Page<T> result){
        return new PageResponse<>(result);
    }

}
