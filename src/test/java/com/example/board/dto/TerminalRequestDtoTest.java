package com.example.board.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TerminalRequestDtoTest {

    @Test
    public void terminalRequestDtoTest(){
        String command = "test";

        TerminalRequestDto requestDto = new TerminalRequestDto(command);

        assertThat(requestDto.getTerminalCommand()).isEqualTo(command);

        TerminalRequestDto requestDto1 = TerminalRequestDto.builder()
                .terminalCommand(command)
                .build();

        assertThat(requestDto1.getTerminalCommand()).isEqualTo(command);
    }
}
