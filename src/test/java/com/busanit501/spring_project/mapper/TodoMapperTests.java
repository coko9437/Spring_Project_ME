package com.busanit501.spring_project.mapper;


import com.busanit501.spring_project.domain.TodoVO;
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
public class TodoMapperTests {
    @Autowired(required = false)
    private TodoMapper todoMapper;

    @Test
    public void testGetTime() {
        log.info("디비로부터 시간 정보 가져오기 확인 : " + todoMapper.getTime());
    }

    @Test
    public void testInsert() {
        // 더미 데이터, 화면에서 전달 받은 데이터
        TodoVO todoVO = TodoVO.builder()
                .title("오늘 점심 뭐 먹지? 스프링 버전")
                .dueDate(LocalDate.now())
                .writer("이상용 스프링 버전")
                .build();
        todoMapper.insert(todoVO);
    }

    @Test
    public void testSelectAll() {
        List<TodoVO> voList = todoMapper.selectAll();
        voList.forEach(vo -> log.info(vo));
    }

    @Test
    public void testSelectByTno() {
        TodoVO todoVO = todoMapper.selectByTno(18L);
        log.info(todoVO);
    }
}

