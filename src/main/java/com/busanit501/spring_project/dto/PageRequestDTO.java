package com.busanit501.spring_project.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Builder
@Data // 게터 세터 투스트링 등 5개 세트
@NoArgsConstructor
@AllArgsConstructor
                // 용도 : 화면에서 -> 서버쪽으로 전달하는 용도
public class PageRequestDTO {

    @Builder.Default
    @Min(value=1)
    @Positive
    // 페이징 하기위한 준비물 준비하기
    // 낱개로 따로따로 이동하기보다는 한번에 담아서 전달
    private int page = 1;


    //유효성검사
    @Builder.Default
    @Min(value=10)
    @Max(value=100)
    @Positive
    private int size = 10;

    // 건너 뛰기 (skip)할 데이터의 갯수
//        1페이지 10개, 2페이지 11개부터... skip 10
//        3페이지 21개부터..
    public int getSkip() {
        return (page - 1) * size;
    }
}
