package com.busanit501.spring_project.mapper;

public interface TimeMapper2 {

    // resources -> mappers -> TimeMapper2.xml 에 연결됨.
    // 결론, getNow() 메서드를 사용시, -> 디비에 연결해서,
    // ->sql 전달하고 데이터를 받아옴, 이 메서드가.
    String getNow();
}
