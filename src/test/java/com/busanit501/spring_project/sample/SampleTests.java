package com.busanit501.spring_project.sample;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;

@Log4j2
//JUnit 5 버전에서 테스트 도구를 이용하기 위해서 필요함.
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class SampleTests {

    // 서버에 등록된 빈(객체,인스턴스) 연결하는 도구.
    // 설정파일에서, 타입에 맞는 클래스가 등록되었다면,
    // 확인하고, 가져와서 이용 가능.
    // 테스트, 설정 파일에서, 주석 했을 경우, 컴파일러 오류 확인.
    // 이렇게 선언해서 사용하는 방식 ,
    // 필드 주입(Field Injection) 방식

    @Autowired
    private SampleService sampleService;
    // root-context.xml 설정 파일에, dataSource 이름의 객체안에 포함, hikariConfig 객체를 포함하고 있음.
    // 서버 시작시, 자동으로 객체를 생성 해주니, 우리는 이용하기.
    @Autowired
    private DataSource dataSource;

    @Test
    public void testSampleService() {

        log.info("testSampleService======의존성 주입 테스트1");
        log.info("sampleService 의 인스턴스 조회 : " + sampleService);
        Assertions.assertNotNull(sampleService);
    }

    // 디비 연결 테스트
    @Test
    public void testConnection () throws Exception {
        Connection connection = dataSource.getConnection();
        log.info("connection 의 유무 : "+ connection);
        Assertions.assertNotNull(connection);
        connection.close();
    }

}
