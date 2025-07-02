package com.busanit501.spring_project.sample;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

// 구현체2
@Repository
// 해결책1, 사용할것을 체크 해주기.
//@Primary
// 해결책2, 라벨 달기.
@Qualifier("event")
public class EventSampleDAOImpl implements SampleDAO{
}
