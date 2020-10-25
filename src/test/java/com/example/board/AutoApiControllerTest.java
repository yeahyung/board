package com.example.board;

import com.example.board.dto.TerminalRequestDto;
import org.apache.tomcat.util.codec.binary.Base64;
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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

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

    @Test
    public void zz_test() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String space = " ";					// one space
        String newLine = "\n";					// new line
        String method = "POST";					// method
        String url = "/photos/puppy.jpg?query1=&query2";	// url (include query string)
        String accessKey = "{accessKey}";		// access key id (from portal or sub account)
        String secretKey = "{secretKey}";

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        String timestamp = String.valueOf(currentTimestamp.getTime());

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);
    }
}
