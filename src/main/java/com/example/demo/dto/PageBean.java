package com.example.demo.dto;

import com.example.demo.model.Question;
import lombok.Data;

import java.util.List;

@Data
public class PageBean {
    int totalPage;
    int currentPage;
    int size;
    boolean prePage;
    boolean nextPage;
    boolean fistPage;
    boolean lastPage;
    List<Integer> list;
    List<Question> questions;
}
