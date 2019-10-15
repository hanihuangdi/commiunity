package com.example.demo.mapper;

import com.example.demo.dto.PageDTO;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionCustomMapper {
  void  update(Long id);
  void updateComment(Long id);

  List<Question> findRelated(Question question);

    Integer countBySearch(PageDTO pageDTO);

    List<Question> selectBySearchWithRowbounds(PageDTO pageDTO);
}