package com.example.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class htmlControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void mainPageLoading(){
        String body = this.testRestTemplate.getForObject("/test", String.class);

        System.out.println(body);

        assertThat(body).contains("서버에 업로드");
    }
}