package com.busanit501.spring_project.service;

import com.busanit501.spring_project.dto.TodoDTO;
import com.busanit501.spring_project.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class TodoServiceTests {

    @Autowired(required = false)
    private TodoService todoService;

    @Test
    public void testInsert() {
        // 화면에서 전달 받은 더미 데이터 작성, TodoDTO
        TodoDTO todoDTO = TodoDTO.builder()
                .title("서비스 단위테스트 스프링 버전")
                .dueDate(LocalDate.now())
                .writer("이상용, 서비스 단위테스트")
                .build();
        todoService.register(todoDTO);
    }

    @Test
    public void testGetAll() {
        List<TodoDTO> dtoList = todoService.getAll();
        dtoList.forEach(dto -> log.info(dto));
    }

    @Test
    public void testGetByTno() {
        TodoDTO todoDTO = todoService.selectByTno(21L);
        log.info(todoDTO);
    }

    @Test
    public void testDelete() {
        todoService.remove(18L);
    }

    @Test
    public void testUpdate() {
        // 실제 사용할 DB 필요,
        // 변경할 더미데이터 필요함 (TodoDTO)
//            TodoVO는 DB와 직접 연결하는 모델클래스임.
//                => 그래서 Setter를 안씀 (불변성)
//                방금 만든것처럼 따로 메서드를 만들어서 사용함.
//                but) DTO는 setter상관없이 사용가능함.
//                자유롭게 프레젠테이션 로직에서 자유롭게 화면에 표현할 수 있음.
        TodoDTO todoDTO = todoService.selectByTno(17L);
        todoDTO.setTitle("수정테스트, TodoServiceTests에서 진행중.!");
        todoService.modify(todoDTO); // DB에 반영하기
    }
}
