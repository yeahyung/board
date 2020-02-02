package com.example.board.service;

import com.example.board.domain.entity.BoardEntity;
import com.example.board.domain.entity.TimeEntity;
import com.example.board.domain.repository.BoardRepository;
import com.example.board.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;

    @Transactional
    public Long savePost(BoardDto boardDto){
        System.out.println(boardDto.getContent());
        // repository.save를 하면 해당 repository가 extend한 걸로 return해주는 듯, 여기선 BoardEntity를 extends했기 때문에 entity로 return
        BoardEntity temp = boardRepository.save(boardDto.toEntity());
        System.out.println(temp);
        System.out.println(temp.getContent());
        System.out.println(temp.getTitle());
        return (long)1;
        //return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    // BoardEntity로 return해도 문제가 없지만, client와 controller간에는 Dto로 주고 받음
    public List<BoardDto> getBoardList(){
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(BoardEntity boardEntity : boardEntities){
            BoardDto boardDto = BoardDto.builder()
                    .id(boardEntity.getId())
                    .title(boardEntity.getTitle())
                    .content(boardEntity.getContent())
                    .writer(boardEntity.getWriter())
                    .createdDate(boardEntity.getCreatedDate())
                    .build();
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    @Transactional
    public BoardDto getPost(Long id){
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        BoardDto boardDto = BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();

        return boardDto;
    }

    @Transactional
    public void deletePost(Long id){

        boardRepository.deleteById(id);
    }
    
}
