package org.galaxy.util.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageResponse<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long total;
	
	private long page;
	
	private long size;

	private ArrayList<T> list;

	public PageResponse(long total, long page, long size, List<T> list) {
		this.list = list.isEmpty() ? new ArrayList<>(1) : (ArrayList<T>) list;
        this.page = page;
        this.size = size;
        this.total = total;
    }

    private PageResponse(){}

	public long getPage() {
		return page;
	}

	public long getTotal() {
		return total;
	}

	public long getSize() {
		return size;
	}

	public ArrayList<T> getList() {
		return list;
	}
}
