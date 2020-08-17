package com.example.board.dto;

import com.sun.xml.internal.xsom.impl.Ref;
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
