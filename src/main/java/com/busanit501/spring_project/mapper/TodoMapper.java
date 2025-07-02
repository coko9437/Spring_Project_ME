package com.busanit501.spring_project.mapper;

import com.busanit501.spring_project.domain.TodoVO;

import java.util.List;

public interface TodoMapper {
    // 테스트 메소드, 디비로 부터 시간 데이터 가져오기
    String getTime();

    // todo 등록
    void insert(TodoVO todoVO);

    // 전체조회기능
    List<TodoVO> selectAll();

    // 하나 조회, 상세보기.
    TodoVO selectByTno(Long tno);

}
