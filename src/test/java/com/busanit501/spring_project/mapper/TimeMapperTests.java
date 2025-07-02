package com.busanit501.spring_project.mapper;

import com.busanit501.spring_project.mapper.TimeMapper;
import com.busanit501.spring_project.mapper.TimeMapper2;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class TimeMapperTests {

    @Autowired(required = false)
    private TimeMapper timeMapper;

    @Autowired(required = false)
    private TimeMapper2 timeMapper2;

    @Test
    public void testGetTime() {
        log.info(("TimeMapperTests, 현재 시간 조회 테스트 : " + timeMapper.getTime()));
    }

    @Test
    public void getNow() {
        log.info("sql 파일 분리해서 조회 : 시간 조회 테스트 = "+ timeMapper2.getNow());
    }
}
