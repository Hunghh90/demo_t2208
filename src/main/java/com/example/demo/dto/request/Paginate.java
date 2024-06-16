package com.example.demo.dto.request;

public class Paginate {
    private int pageSize;
    private int limit;

    public Paginate(){};
    public Paginate(int pageSize, int limit) {
        this.pageSize = pageSize;
        this.limit = limit;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
