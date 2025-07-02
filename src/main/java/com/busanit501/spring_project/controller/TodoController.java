package com.busanit501.spring_project.controller;

import com.busanit501.spring_project.dto.TodoDTO;
import com.busanit501.spring_project.service.TodoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.SimpleAttributeSet;
import javax.validation.Valid;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
// 대표 url,/todo,
// 밑에 메서드 하위  url 주소를 지정함.
// 최종 url, /todo/지정한 주소
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;
    // 최종 url : /todo/list
    @RequestMapping("/list")
    // void 라고하면, 메서드 명 : list
    // /WEB-INF/views/todo/list.jsp , 가리킴.
    // 자동 연결, 뷰 리졸버라는 친구의 업무.
    // 메소드명이 아니라, url 주소로 , 화면 연결을함.
    public void list2(Model model) {
        log.info("TodoController에서 작업, list 호출 ");
        List<TodoDTO> dtoList = todoService.getAll();
        dtoList.forEach(vo -> log.info(vo));
        // 서비스로 부터 외주 맡겨서, 디비 정보를 받아와서, 화면에 전달, 탑재하기.
        model.addAttribute("dtoList", todoService.getAll());
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
                // @Valid TodoDTO todoDTO : 유효성 검사 적용
                //  BindingResult bindingResult : 통과 못한 이유 원인 남겨져있다.
    public String registerPost(@Valid TodoDTO todoDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        log.info("TodoController에서 작업, register , post , 로직처리");
        log.info("todoDTO:"+todoDTO);
        // 만약 유효성체크를 통과 못한다면,
        // bindingResult 여기에 무언가 담겨져 있다.
        if(bindingResult.hasErrors()){
            log.info("TodoController클래스, register post d 에서 유효성 오류가 발생함.");
            // 서버 --> 앞 화면에 오류가 발생한 오류를 전달
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
                // 일회성으로 오류메세지를 전달함
            return "redirect:/todo/register";

        }

        // 실제 DB 반영하는 코드
        todoService.register(todoDTO);

        return "redirect:/todo/list";

    }
    @GetMapping("/read")
    public void read(Long tno, Model model){
        TodoDTO todoDTO = todoService.selectByTno(tno);
        log.info(todoDTO);
        model.addAttribute("dto", todoDTO);

    }

}
