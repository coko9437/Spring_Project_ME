package com.busanit501.spring_project.controller;

import com.busanit501.spring_project.dto.TodoDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@Log4j2
public class SampleController {
    // 1)화면 제공, 2) 로직처리
    @GetMapping("/hello")
//    반환형이 void인 경우, 요청 URL을 기준으로 
//    JSP 페이지 이름 자동 매칭
    // /WEB-INF/views/hello.jsp
    // @GetMapping("/hello") , hello 동일 이름으로 화면 호출
    //  public void hello() : 메소드 이름은 상관없다.
    public void hello() {
        log.info("hello.......");
    }

    // 단순 파라미터 자동수집
    @GetMapping("/ex1")
    // 기본 자료형으로 정의했고,
    // 화면에서 , 쿼리스트링 형식으로 , get 방식으로
    // 데이터 전달
    // http://localhost:8080/ex1?name=lsy&age=20&hobby=수영
    public void ex1(String name, int age, String hobby) {
        // 이런 형식으로 데이터 전달을 받았지만, post
        // String mid = req.getParameter("mid");
        // 지금 자동 맵핑
        log.info("SampleController 작업중. ex1");
        log.info("name:" + name + ",age:" + age);
        log.info("hobby:" + hobby);
    }

    @GetMapping("/ex2")
    public void ex2(@RequestParam(name = "name", defaultValue = "사용자") String name,
                    @RequestParam(name = "age", defaultValue = "20") int age,
                    @RequestParam(name = "hobby", defaultValue = "운동") String hobby) {
        log.info("SampleController 작업중. ex2, 값이 없는 경우, 기본값 이용");
        log.info("name:" + name + ",age:" + age);
        log.info("hobby:" + hobby);
    }

    // 날짜 포맷터 인경우.
    // http://localhost:8080/ex3?dueDate=2025-07-01
    // 화면에서 전달 된 파라미터 쿼리스트링은 무조건 문자열
    // 서버에서 받을 때 LocalDate 형 일치가 안된다.
    // 그래서, 서버에서 시작시, 형 변환을 자동으로 해주겠다.
    @GetMapping("/ex3")
    public void ex3(LocalDate dueDate) {
        log.info("SampleController 작업중. ex3, 날짜 포맷터 형식인 경우");
        log.info("dueDate:" + dueDate);
    }

    // 서버 -> 웹 화면, 데이터 전달.
    // 데이터를 전달 받은 화면이 필요, /WEB-INF/views/ex4.jsp
    // 화면에서 ,EL 표기법으로 넘어온 데이터를 받기, 가져오기. 작업.
    @GetMapping("/ex4")
    public void ex4(Model model) {
        log.info("SampleController 작업중. ex4,서버 -> 웹 화면 데이터 전달");
//        model.addAttribute("message","<script>alert('공격예시, 일단 자바스크립트 실행')</script>");
        // 공격 예시 - 사용자 브라우저가 naver.com으로 이동하는 자바스크립트 삽입
        model.addAttribute("message", "<script>location.href='https://www.naver.com'</script>");

    }

    @GetMapping("/ex4_1")
    // TodoDTO , 모델 클래스는 , 자동으로 화면까지 객체를 전달.
    // 자동 전달.
    //기본 전달
//    public void ex4_1(TodoDTO todoDTO, Model model) {
    // todoDTO 이름 말고, dto 라는 이름으로 전달하기.
    // 화면에서는 사용시, todoDTO 아니라, dto라는 이름으로 이용하기.
//    public void ex4_1( TodoDTO todoDTO, Model model) {
    // todoDTO 이름을 -> dto 라고 변경해서 사용하기.
    // 즉, 화면에서, dto 이름으로 EL 표기법으로 사용함. ${dto}
    public void ex4_1(@ModelAttribute("dto") TodoDTO todoDTO, Model model) {
        // 비교, jsp 프로젝트에서, 서버에서, 데이터 탑재 형식.
        // req.setAttribute("dtoList", dtoList)
        todoDTO.setTitle("임시제목만 입력");
        log.info("SampleController 작업중. ex4_1,서버 -> 웹 화면 데이터 전달");
        log.info("데이터 탑재를 하는 부분이 없는데, 화면에서, 해당 todoDTO를 자동으로 이용가능함");
    }

    @GetMapping("/ex5")
    public String ex5(RedirectAttributes redirectAttributes) {
        // 서버 -> 웹브라우저 데이터 전달
        // 방법1 ) redirectAttributes.addAttribute(키, 값) : 데이터 추가
        // 리다이렉트할 URL에 쿼리스트링으로 추가함.
        redirectAttributes.addAttribute("name","이상용");
        // 방법2) redirectAttributes.addFlashAttribute(키,값) : 데이터 추가
        // URL에 보이지는 않지만, JSP 화면에서는 1회용으로 사용가능. 즉 1번 사용후 휘발된다.
        // 에러를 따로 1회성으로 보여줄 때 많이 사용함.
        redirectAttributes.addFlashAttribute("result","1회용 데이터 전달");
        return "redirect:/ex6";
    }
    @GetMapping("/ex6")
    public void ex6(){

    }

    @GetMapping("/ex7")
    //http://localhost:8080/ex7?p1=abc&p2=def
    public void ex7(String p1, int p2){
        // 시나리오는, 화면에서, 2개다 문자열로 전달해서, 강제로 예외 발생시키기,

        log.info("SampleController 작업중. ex7, 예에 처리 예시");
        log.info("p1:"+p1);
        log.info("p2:"+p2);
    }

}
