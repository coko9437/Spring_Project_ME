package com.busanit501.spring_project.controller;

import com.busanit501.spring_project.dto.PageRequestDTO;
import com.busanit501.spring_project.dto.TodoDTO;
import com.busanit501.spring_project.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.swing.text.SimpleAttributeSet;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void list2(@Valid PageRequestDTO pageRequestDTO,
                      BindingResult bindingResult,
                      Model model) {
        log.info("TodoController에서 작업, list 호출 ");

        if (bindingResult.hasErrors()) {

            pageRequestDTO = PageRequestDTO.builder().build();
        }
        model.addAttribute("responseDTO", todoService.getList(pageRequestDTO));
    }

    // 최종 url : /todo/register
    // 메소드 : get
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void register() {
        log.info("TodoController에서 작업, register 호출 ");
    }

    // 로직처리.
    // 최종 url : /todo/register ,
    // 메소드 : post

    @PostMapping("/register")
    public String registerPost(@Valid TodoDTO todoDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        log.info("TodoController에서 작업, register , post , 로직처리");
        log.info("todoDTO:" + todoDTO);

        if (bindingResult.hasErrors()) {
            log.info("TodoController에서 작업, register , post d에서. 유효성 오류가 발생했다");
            // 서버 -> 앞 화면에 , 오류가 발생한 이유를 전달.
            redirectAttributes.addFlashAttribute("errors",
                        bindingResult.getAllErrors());
            return "redirect:/todo/register";
        }


        // 실제 디비 반영하는 코드,
        todoService.register(todoDTO);

        return "redirect:/todo/list";
    }

    @GetMapping({"/read", "/modify"})

    public void read(Long tno, PageRequestDTO pageRequestDTO, Model model) {
        // 서버에서, 디비로 부터 tno 번호로 하나의 todo 정보를 조회
        // 정방향, 찍고, 역방향으로 돌아온 상태.
        TodoDTO todoDTO = todoService.selectByTno(tno);
        log.info(todoDTO);
        log.info("페이징 정보 받기 pageRequestDTO getLink(): " + pageRequestDTO.getLink());

        model.addAttribute("dto", todoDTO);
    }

    @PostMapping("/remove")
    public String remove(Long tno,
                         PageRequestDTO pageRequestDTO
            , RedirectAttributes redirectAttributes) {
        log.info("삭제 작업 중.,");
        log.info("tno:" + tno);

        todoService.remove(tno);

        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size", pageRequestDTO.getSize());


        return "redirect:/todo/list?"+pageRequestDTO.getLink();
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid TodoDTO todoDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("수정 적용 부분에서, 유효성 통과 못할 경우");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("tno", todoDTO.getTno());
            // 최종 url : /todo/modify?tno=21
            return "redirect:/todo/modify";
        }
        log.info("수정 로직처리 post 작업중 넘어온 데이터 확인 todoDTO: " + todoDTO);
        todoService.modify(todoDTO);


        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size", pageRequestDTO.getSize());
        // 수정 후, -> 상세 조회로 이동, : 필요한 준비물, 현재 tno 번호가 필요함.
        redirectAttributes.addAttribute("tno", todoDTO.getTno());


        return "redirect:/todo/read";

    }

    // 날씨 관련 컨트롤러
    @GetMapping("/weather")
    @ResponseBody
    public Map<String, Object> getWeather(@RequestParam(required = false) Double lat,
                                          @RequestParam(required = false) Double lon) {
        if (lat == null || lon == null) {
            lat = 37.5665;  // 서울 위도
            lon = 126.9780; // 서울 경도
        }

        String urlString = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true",
                lat, lon
        );

        Map<String, Object> result = new HashMap<>();
        long startTime = System.currentTimeMillis();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // 연결 시간
            conn.setReadTimeout(5000);    // 읽기 시간

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "UTF-8")
                );

                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }

                reader.close();

                // JSON
                JSONObject json = new JSONObject(responseBuilder.toString());
                JSONObject current = json.getJSONObject("current_weather");

                result.put("temp", current.getDouble("temperature"));
                result.put("windspeed", current.getDouble("windspeed"));

            } else {
                result.put("error", "API 요청 실패 (응답 코드: " + responseCode + ")");
            }

        } catch (Exception e) {
            result.put("error", "예외 발생: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();  // 호출 종료 시간
        long duration = endTime - startTime;

        // 응답 -> 호출 시간
        result.put("apiCallDurationMs", duration);

        return result;
    }

}