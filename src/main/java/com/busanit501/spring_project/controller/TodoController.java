package com.busanit501.spring_project.controller;

import com.busanit501.spring_project.dto.TodoDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Log4j2
// 대표 url,/todo,
// 밑에 메서드 하위  url 주소를 지정함.
// 최종 url, /todo/지정한 주소
@RequestMapping("/todo")
public class TodoController {

    // 최종 url : /todo/list
    @RequestMapping("/list")
    // void 라고하면, 메서드 명 : list
    // /WEB-INF/views/todo/list.jsp , 가리킴.
    // 자동 연결, 뷰 리졸버라는 친구의 업무.
    // 메소드명이 아니라, url 주소로 , 화면 연결을함.
    public void list2(){
        log.info("TodoController에서 작업, list 호출 ");
    }

    // 최종 url : /todo/register
    // 메소드 : get 
    // void 라고하면, 메서드 명 : register
    // /WEB-INF/views/todo/register.jsp , 가리킴.
    // 자동 연결, 뷰 리졸버라는 친구의 업무.
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void register(){
        log.info("TodoController에서 작업, register 호출 ");
    }
    
    // 로직처리. 
    // 최종 url : /todo/register , 
    // 메소드 : post
    // 화면에서, TodoDTO 형식의 데이터를 전달을 받으면,
    // 각각 받는게 아니라, TodoDTO 모델 클래스로 한번에 받기 예시.
    @PostMapping("/register")
    public void registerPost(TodoDTO todoDTO) {
        log.info("TodoController에서 작업, register , post , 로직처리");
        log.info("todoDTO:"+todoDTO);
    }

}
