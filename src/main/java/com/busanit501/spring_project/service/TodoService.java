package com.busanit501.spring_project.service;

import com.busanit501.spring_project.dto.PageRequestDTO;
import com.busanit501.spring_project.dto.PageResponseDTO;
import com.busanit501.spring_project.dto.TodoDTO;

import java.util.List;

// 인터페이스 명 : TodoService
// 구현 클래스명 : TodoServiceImpl
public interface TodoService {
    void register(TodoDTO todoDTO);
    // 페이징 전 전제조회
    // List<TodoDTO> getAll();
//    페이징 처리 후 전체조회
    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO);
    TodoDTO selectByTno(Long tno);
    void remove(Long tno);
    void modify(TodoDTO todoDTO); //수정하기.
}

