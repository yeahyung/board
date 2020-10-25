package com.example.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TerminalRequestDto {
    private String terminalCommand;

    @Builder
    public TerminalRequestDto(String terminalCommand){
        this.terminalCommand = terminalCommand;
    }
}
