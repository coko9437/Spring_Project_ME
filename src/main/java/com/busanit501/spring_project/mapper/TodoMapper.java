package com.busanit501.spring_project.mapper;

import com.busanit501.spring_project.domain.TodoVO;
import com.busanit501.spring_project.dto.PageRequestDTO;

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

    // 삭제하기
    void delete (Long tno);

    // 수정하기
    void update(TodoVO todoVO);

    // 페이징 처리가 된 전체 리스트
    List<TodoVO> selectList(PageRequestDTO pageRequestDTO);
        // 화면에서 어느 페이지를 선택했는지 알아야 정보를 줄수있기때문

    // 페이징 준비물2 전체개수 파악
    int getCount(PageRequestDTO pageRequestDTO);

}
