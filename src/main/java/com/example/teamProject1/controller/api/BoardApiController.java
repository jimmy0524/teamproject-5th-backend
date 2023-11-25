package com.example.teamProject1.controller.api;

import com.example.teamProject1.Dto.BoardRequestDto;
import com.example.teamProject1.Dto.ResponseDto;
import com.example.teamProject1.config.auth.PrincipalDetail;
import com.example.teamProject1.model.Board;
import com.example.teamProject1.model.ReReply;
import com.example.teamProject1.model.Reply;
import com.example.teamProject1.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board") // 글 작성
    public ResponseDto<Integer> save(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.writeBoard(requestDto, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
//    @PostMapping("/api/board") // 글 작성 테스트용
//    public ResponseDto<Integer> save(@RequestBody BoardRequestDto requestDto) {
//        boardService.writeBoard(requestDto);
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }

    @DeleteMapping("/api/board/{id}") // 글 삭제
    public ResponseDto<Integer> deleteById(@PathVariable int id) {
        boardService.deleteBoard(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}") // 글 수정
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody BoardRequestDto requestDto) {
        boardService.updateBoard(id, requestDto);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @GetMapping("/board/{id}") // 글 상세보기
    public ResponseDto<Integer> findById(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.detailBoard(id));
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    //user 권한이 필요
    @GetMapping("/board/saveForm")
    public ResponseDto<Integer> saveForm() {
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/{boardId}/reply") // 댓글 작성
    public ResponseDto<Integer> replySave(@RequestBody Reply reply,@PathVariable int boardId,@AuthenticationPrincipal PrincipalDetail principal) {
        boardService.writeReply(reply, boardId, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
//    @PostMapping("/api/board/{boardId}/reply") // 댓글 작성 테스트
//    public ResponseDto<Integer> replySave(@RequestBody Reply reply,@PathVariable int boardId) {
//        boardService.writeReply(reply, boardId);
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}") // 댓글 삭제
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
        boardService.deleteReply(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping(value = "/api/board/{boardId}/reply/{replyId}/reReply") //대댓글 작성
    public ResponseDto<Integer> reReplySave(@RequestBody ReReply reReply, @PathVariable int replyId, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.writeReReply(reReply, replyId, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
//    @PostMapping(value = "/api/board/{boardId}/reply/{replyId}/reReply") //대댓글 작성 테스트
//    public ResponseDto<Integer> reReplySave(@RequestBody ReReply reReply, @PathVariable int replyId){
//        boardService.writeReReply(reReply, replyId);
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}/reReply/{reReplyId}")  //대댓글 삭제
    public ResponseDto<Integer> reReplyDelete(@PathVariable int reReplyId) {
        boardService.deleteReReply(reReplyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping(value = "/api/board/{boardId}")  //글 추천
    public ResponseDto<Integer> like(@PathVariable int boardId, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.likeBoard(boardId, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

//    @PostMapping(value = "/api/board/{boardId}")  //글 추천 테스트
//    public ResponseDto<Integer> like(@PathVariable int boardId){
//        boardService.likeBoard(boardId);
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }

    @PostMapping(value = "/api/board/{boardId}/scrap")  //글 스크랩
    public ResponseDto<Integer> scrap(@PathVariable int boardId, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.scrapBoard(boardId, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

//    @PostMapping(value = "/api/board/{boardId}/scrap")  //글 스크랩 테스트
//    public ResponseDto<Integer> scrap(@PathVariable int boardId){
//        boardService.scrapBoard(boardId);
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }

    @GetMapping("/api/board/popular")
    public List<Board> getPopularBoardsWithinPastTwelveHours() {
        return boardService.getPopularBoards();
    }
}
