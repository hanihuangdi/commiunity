package com.example.demo.dto;

import lombok.Data;

@Data
public class PageDTO {
    private String search;
    private int size;
    private int currentPage;
    private  String tagName;
}
