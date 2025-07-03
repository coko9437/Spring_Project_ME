package com.busanit501.spring_project.service;

import com.busanit501.spring_project.domain.TodoVO;
import com.busanit501.spring_project.dto.TodoDTO;
import com.busanit501.spring_project.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor // final 필드를 생성자 주입.
public class TodoServiceImpl implements TodoService {
    // 의존성 주입, 외주 주기. 다른 객체 불러오기(포함), 의지(의존)하기
    private final TodoMapper todoMapper;
    private final ModelMapper modelMapper;


    @Override
    public void register(TodoDTO todoDTO) {
        log.info("컨트롤러부터 넘어온 데이터 확인, todoDTO : " + todoDTO);
        // 타입 변경 DTO -> VO ,
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
        log.info("변환 된 데이터 확인 todoVO : " + todoVO);
        // 실제 작업, 외주주기, DAO 부탁, 데이터 입력 해줘.
        todoMapper.insert(todoVO);

    }

    @Override
    public List<TodoDTO> getAll() {
        List<TodoDTO> dtoList = todoMapper.selectAll().stream().
                map(vo -> modelMapper.map(vo, TodoDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public TodoDTO selectByTno(Long tno) {
        TodoVO todoVO = todoMapper.selectByTno(tno);
        TodoDTO todoDTO = modelMapper.map(todoVO, TodoDTO.class);
        return todoDTO;
    }

    @Override
    public void remove(Long tno) {
        todoMapper.delete(tno);
    }

    @Override
    public void modify(TodoDTO todoDTO) {
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
        todoMapper.update(todoVO);
    }
}
