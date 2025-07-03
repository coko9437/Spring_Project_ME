package com.busanit501.spring_project.dto;

// 서버에서 -> 화면으로 페이징 정보 전달
    // 1) 페이징처리가 된 Todo의 목록, DB에서 조회한 내용 필요
    // 2) 전체 개수
    // 3) 기본 페이지 정보
    // 4) 사이즈 정보
    // 5) 시작페이지, 끝 페이지
    // 6) 이전 페이지 여부, 다음 페이지 여부
//        제너릭으로 표현한 이유? 1회용이 아닌 나중에 다른 도메인으로도 사용이 가능하게...
//        예) 댓글, 상품, 회원 등 목록화 할 수 있는 주제는 다 페이징한다.

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {
    private int page;
    private int size;
    private int total;

    private int start;
    private int end;

    private boolean prev;
    private boolean next;

    private List<E> dtoList;

    // 빌더 패턴을 이용한 생성자 구성
    @Builder(builderMethodName = "withAll") // 생성자를 "withAll"로 쓰겠다
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {
        // DB로 부터 조회한 데이터들
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;


        // 5) 시작페이지, 끝 페이지
        // 6) 이전 페이지 여부, 다음 페이지 여부

        this.end = (int)(Math.ceil(this.page/10.0)) * 10;
        this.start = this.end - 9;
        int last = (int)(Math.ceil((total/(double)size)));
        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;


    }
}
