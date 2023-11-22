package com.example.teamProject1.service;

import com.example.teamProject1.Dto.BoardRequestDto;
import com.example.teamProject1.model.Board;
import com.example.teamProject1.model.Reply;
import com.example.teamProject1.model.SubwayStation;
import com.example.teamProject1.model.User;
import com.example.teamProject1.repository.BoardRepository;
import com.example.teamProject1.repository.ReplyRepository;
import com.example.teamProject1.repository.SubwayStationRepository;
import com.example.teamProject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubwayStationRepository subwayStationRepository;

    //  글 작성
    @Transactional
    public void writeBoard(BoardRequestDto requestDto, User user){
        Board board = requestDto.getBoard();
        SubwayStation subwayStation = subwayStationRepository.findByName(requestDto.getSubwayStationName())
                .orElseThrow(() -> new IllegalArgumentException("해당 역이 존재하지 않습니다."));
        board.setUser(user);
        board.setSubwayStation(subwayStation);
        boardRepository.save(board);
    }
//    @Transactional // 테스트용
//    public void writeBoard(BoardRequestDto requestDto){
//        Board board = requestDto.getBoard();
//        SubwayStation subwayStation = subwayStationRepository.findByName(requestDto.getSubwayStationName())
//                .orElseThrow(() -> new IllegalArgumentException("해당 역이 존재하지 않습니다."));
//        User user = userRepository.findById(1)
//                .orElseThrow(() -> {
//                    return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
//                });
//        board.setUser(user);
//        board.setSubwayStation(subwayStation);
//        boardRepository.save(board);
//    }

    // 글 수정
    @Transactional
    public void updateBoard(int id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
                }); // 영속화

        board.setTitle(requestDto.getBoard().getTitle());
        board.setContent(requestDto.getBoard().getContent());

        SubwayStation subwayStation = subwayStationRepository.findByName(requestDto.getSubwayStationName())
                .orElseThrow(() -> new IllegalArgumentException("해당 역이 존재하지 않습니다."));
        board.setSubwayStation(subwayStation);

        boardRepository.save(board);
    }
    // 글 삭제
    @Transactional
    public void deleteBoard(int id) {
        boardRepository.deleteById(id);
    }

    // 글 상세보기
    @Transactional(readOnly = true)
    public Board detailBoard(int id) {
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
                });
    }

    //댓글 작성
    @Transactional
    public void writeReply(User user, int boardId, Reply reply) {
        Board board = boardRepository.findById(boardId)
                        .orElseThrow(() -> {
                            return new IllegalArgumentException("댓글 쓰기 실패");
                        });
        reply.setUser(user);
        reply.setBoard(board);

        replyRepository.save(reply);
    }

    //댓글 삭제
    @Transactional
    public void deleteReply(int replyId) {
        replyRepository.deleteById(replyId);
    }


}
