package com.example.board;

import com.example.board.dto.TerminalRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AutoApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @After
    public void afterTest() throws Exception{
        System.out.println("테스트 종료 후 실행된다, DB 테스트한 것 다시 삭제한다던지 뭐");
    }

    // Terminal Test
    @Test
    public void terminal_test() throws Exception{
        String cmd = "ifconfig";
        TerminalRequestDto requestDto = TerminalRequestDto.builder()
                .terminalCommand(cmd)
                .build();

        String url = "http://localhost:" + port + "/terminal";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        System.out.println(responseEntity);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("inet");
    }

    // Python Test
    @Test
    public void python_test() throws Exception{
        String file = "temp.py";

        String url = "http://localhost:" + port + "/python/" + file;

        System.out.println(url);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        System.out.println(responseEntity);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("0\n1\n2\n3\n4\n");
    }
}
