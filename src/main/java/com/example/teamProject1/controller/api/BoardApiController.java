package com.example.teamProject1.controller.api;

import com.example.teamProject1.Dto.BoardRequestDto;
import com.example.teamProject1.Dto.ResponseDto;
import com.example.teamProject1.config.auth.PrincipalDetail;
import com.example.teamProject1.model.Board;
import com.example.teamProject1.model.Reply;
import com.example.teamProject1.model.SubwayStation;
import com.example.teamProject1.service.BoardService;
import com.example.teamProject1.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/board/{boardId}/reply") // 댓글 작성
    public ResponseDto<Integer> replySave(@PathVariable int boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal) {

        boardService.writeReply(principal.getUser(), boardId,reply);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}") // 댓글 삭제
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
        boardService.deleteReply(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
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


}
