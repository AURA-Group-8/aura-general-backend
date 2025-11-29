package com.aura8.general_backend.clean_arch.core.domain.valueobject;

import java.util.List;

public class PageElement<T> {
    final List<?> content;
    final Integer pageNumber;
    final Integer pageSize;
    final Long totalElements;
    final Integer totalPages;

    public PageElement(List<?> content, Integer pageNumber, Integer pageSize, Long totalElements, Integer totalPages) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<?> getContent() {
        return content;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }
}
