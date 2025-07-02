package com.busanit501.spring_project.controller.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@ControllerAdvice
@Log4j2
public class CommonExceptionAdvice {

    // 예외 처리 경우1, 숫자 포맷 예외처리.
    // 리턴값을 뷰가(화면제공이 아니라), 데이터만 전달.
    // HTTP Response body  로 직접 전송.
    @ResponseBody
    // 특정 예외 타입이 발생시, 실행될 메서드에 붙여서,
    // 예외를 파라미터로 받아서, 사용자가 커스텀 처리가 가능.
    @ExceptionHandler(NumberFormatException.class)
    // 서버에서, 데이터만 전달시, 한글 깨지는 문제 해결이 여러가지 있는데, 지금은
    // produce 기능으로 해결, -> ResponseEntity<String> 클래스를 이용할 예정.
    // 방법1, 스프링 4.x 버전 이상인경우라서,
//    @RequestMapping(produces = "text/plain; charset=UTF-8")
    // 방법2, ResponseEntity<String>
//    public String exceptNumber(NumberFormatException numberFormatException) {
    public ResponseEntity<String> exceptNumber(NumberFormatException numberFormatException) {
        log.error("===CommonExceptionAdvice에서, exceptNumber 담당 , 예외1=================");
        log.error(numberFormatException.getMessage());
//        return "number format exception";
//        return "숫자 타입 예외 발생입니다.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.valueOf("text/plain; charset=utf-8"))
                .body("숫자 타입 예외 발생입니다");
    }

    // 예외2, 범용적으로 예외처리하는 메서드 추가. 
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptCommon(Exception exception){
        // 서버 콘솔에서 예외 메세지 확인
        log.error("==exceptCommon, 일반적인 공통 예외 처리하기. ==================");
        log.error(exception.getMessage());

        // 화면에서 예외 메세지 확인, html 태그를 문자열 형식으로 전달하면, 
        // 웹 브라우저 html 읽어서, 표시함.
        StringBuffer buffer = new StringBuffer("<ul>");
        buffer.append("<li>" + "exception.getMessage(): 예외 내용 확인1" + "</li>");
        buffer.append("<li>" + exception.getMessage() + "</li>");

        // 예외 발생 추적 내용을 같이 포함해서 전달.
        // 병렬처리, 중간 집계, 최종 처리
        // stream(exception.getStackTrace()) : 예외의 발생 가계도 전체를 갖고 있고,
        // forEach( : 전체 요소 -> 하나씩 요소를 꺼내는 작업,
        // 꺼내서, 버퍼라는 문자열 추가하고, 여기까지 중간 집계
        // 최종 처리.
        buffer.append("<li>" + "stackTraceElement.toString(): 예외 추적 확인2" + "</li>");
        Arrays.stream(exception.getStackTrace()).forEach(
                stackTraceElement -> {
                    buffer.append("<li>" + stackTraceElement.toString() + "</li>");
                }
        );
        buffer.append("</ul>");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.valueOf("text/html; charset=utf-8"))
                .body(buffer.toString());
//        return buffer.toString();

        
    }

    //예외3, 404 , not found  처리 ,
    // 리턴 타입, String , 문자열이면, 그 내용의 jsp 화면으로 감.
    // /WEB-INF/views/custom404.jsp 로 연결됨.
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public  String notFound() {
        return "custom404";
    }
}
