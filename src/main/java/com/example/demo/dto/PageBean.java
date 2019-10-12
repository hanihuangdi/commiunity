package com.example.demo.dto;

import com.example.demo.model.Question;
import lombok.Data;

import java.util.List;

@Data
public class PageBean<T> {
    int totalPage;
    int currentPage;
    int size;
    boolean prePage;
    boolean nextPage;
    boolean fistPage;
    boolean lastPage;
    int count;
    List<Integer> list;
    List<T> data;

}
