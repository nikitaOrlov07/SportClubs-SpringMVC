package com.example.web.dto;

import lombok.Data;

import java.util.List;
@Data
public class ClubsPagination {
    private List<ClubDto> data;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;



}
