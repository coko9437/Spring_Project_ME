package com.busanit501.spring_project.sample;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@ToString
//@RequiredArgsConstructor
public class SampleService {

    // 필드 주입,
    // 루트 컨텍스트 의 파일 위치를 인식를 못해서, 컴파일러 오류상황

//    @Autowired
//    private SampleDAO sampleDAO;

    // 방법2,
    // 생성자 주입 방식. -> 롬복 이용하기. ->@RequiredArgsConstructor
    // 해결책2,
    // 우리가 지정한 라벨을 표기. 결론, 선택.

    // 중복 사용이 안되서 , 따로 분리해서 설정.
//    @Qualifier("normal")
    private final  SampleDAO sampleDAO;

//    해결책, 따로 재정의해서, 적용하기.
    public SampleService( @Qualifier("normal") SampleDAO sampleDAO) {
        this.sampleDAO = sampleDAO;
    }
    // 문제점, 제시.
    // 1, SampleDAO 를 구현한 , 구현체 클래스가 1개였다가
    // 구현체 클래스가 2개인 상황.
    // 시스템에서는 어느 @Repository 사용할지 의문?
}
