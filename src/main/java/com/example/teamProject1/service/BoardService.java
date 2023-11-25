package com.example.teamProject1.service;

import com.example.teamProject1.Dto.BoardRequestDto;
import com.example.teamProject1.model.*;
import com.example.teamProject1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private ReReplyRepository reReplyRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ScrapRepository scrapRepository;

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
//    @Transactional // 글 작성 테스트
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
        Board board = boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
                });
        boardRepository.deleteById(id);
        likeRepository.deleteByBoard(board);
        scrapRepository.deleteByBoard(board);
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
    public void writeReply(Reply reply, int boardId, User user) {
        Board board = boardRepository.findById(boardId)
                        .orElseThrow(() -> {
                            return new IllegalArgumentException("댓글 쓰기 실패");
                        });

        Reply newReply = Reply.builder()
                        .user(user)
                        .board(board)
                        .content(reply.getContent())
                        .build();

        replyRepository.save(newReply);
    }
//    //댓글 작성 테스트
//    @Transactional
//    public void writeReply(Reply reply, int boardId) {
//        Board board = boardRepository.findById(boardId)
//                .orElseThrow(() -> {
//                    return new IllegalArgumentException("댓글 쓰기 실패");
//                });
//
//        User user = userRepository.findById(1)
//                .orElseThrow(() -> {
//                    return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
//                });
//
//        Reply newReply = Reply.builder()
//                .user(user)
//                .board(board)
//                .content(reply.getContent())
//                .build();
//
//        replyRepository.save(newReply);
//    }


    //댓글 삭제
    @Transactional
    public void deleteReply(int replyId) {
        replyRepository.deleteById(replyId);
    }

    //대댓글 작성
    @Transactional
    public void writeReReply(ReReply reReply, int replyId, User user) {
        Reply reply=replyRepository.findById(replyId)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
                });

        ReReply newReReply=ReReply.builder()
                .user(user)
                .reply(reply)
                .content(reReply.getContent())
                .build();

        reReplyRepository.save(newReReply);
    }

//    //대댓글 작성 테스트
//    @Transactional
//    public void writeReReply(ReReply reReply, int id) {
//        Reply reply=replyRepository.findById(id)
//                .orElseThrow(() -> {
//                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
//                });
//        User user = userRepository.findById(1)
//                .orElseThrow(() -> {
//                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
//                });
//        ReReply newReReply=ReReply.builder()
//                .user(user)
//                .reply(reply)
//                .content(reReply.getContent())
//                .build();
//        reReplyRepository.save(newReReply);
//    }

    //대댓글 삭제
    @Transactional
    public void deleteReReply(int id){
        ReReply reReply = reReplyRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
                });
        reReplyRepository.deleteById(id);
    }

    //글 좋아요
    @Transactional
    public void likeBoard(int id,User user) {

        Board findBoard = boardRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
                });

        if(!likeRepository.existsByUserAndBoard(user,findBoard)){
            findBoard.setLikeCount(findBoard.getLikeCount() + 1);
            LikeBoard likes= LikeBoard.builder()
                    .user(user)
                    .board(findBoard)
                    .build();
            likeRepository.save(likes);
        }
        else{
            findBoard.setLikeCount(findBoard.getLikeCount() - 1);
            likeRepository.deleteByUserAndBoard(user,findBoard);
        }
        boardRepository.save(findBoard);
    }

//    //글 좋아요 테스트
//    @Transactional
//    public void likeBoard(int id) {
//
//        Board findBoard = boardRepository.findById(id)
//                .orElseThrow(() -> {
//                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
//                });
//        User user = userRepository.findById(1)
//                .orElseThrow(() -> {
//                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
//                });
//        if(!likeRepository.existsByUserAndBoard(user,findBoard)){
//            findBoard.setLikeCount(findBoard.getLikeCount() + 1);
//            LikeBoard likes= LikeBoard.builder()
//                    .user(user)
//                    .board(findBoard)
//                    .build();
//            likeRepository.save(likes);
//        }
//        else{
//            findBoard.setLikeCount(findBoard.getLikeCount() - 1);
//            likeRepository.deleteByUserAndBoard(user,findBoard);
//        }
//        boardRepository.save(findBoard);
//    }

    //글 스크랩
    @Transactional
    public void scrapBoard(int id, User user) {

        Board findBoard = boardRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
                });

        if(!scrapRepository.existsByUserAndBoard(user,findBoard)){
            Scrap scrap= Scrap.builder()
                    .user(user)
                    .board(findBoard)
                    .build();
            scrapRepository.save(scrap);
        }
        else{
            scrapRepository.deleteByUserAndBoard(user,findBoard);
        }
        boardRepository.save(findBoard);
    }

//    //글 스크랩 테스트
//    @Transactional
//    public void scrapBoard(int id) {
//
//        Board findBoard = boardRepository.findById(id)
//                .orElseThrow(() -> {
//                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
//                });
//        User user = userRepository.findById(1)
//                .orElseThrow(() -> {
//                    return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
//                });
//
//        if(!scrapRepository.existsByUserAndBoard(user,findBoard)){
//            Scrap scrap= Scrap.builder()
//                    .user(user)
//                    .board(findBoard)
//                    .build();
//            scrapRepository.save(scrap);
//        }
//        else{
//            scrapRepository.deleteByUserAndBoard(user,findBoard);
//        }
//        boardRepository.save(findBoard);
//    }
    public List<Board> getPopularBoards() {
        Integer minLikeCount = 10;
        Timestamp maxCreateDate = Timestamp.valueOf(LocalDateTime.now().minusHours(12));
        return boardRepository.findByLikeCountGreaterThanEqualAndCreateDateGreaterThan(minLikeCount, maxCreateDate);
    }
}
