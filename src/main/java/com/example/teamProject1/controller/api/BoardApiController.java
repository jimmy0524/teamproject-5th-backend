package com.example.teamProject1.controller.api;

import com.example.teamProject1.Dto.BoardRequestDto;
import com.example.teamProject1.Dto.ResponseDto;
import com.example.teamProject1.config.auth.PrincipalDetail;
import com.example.teamProject1.model.ReReply;
import com.example.teamProject1.model.Reply;
import com.example.teamProject1.model.Report;
import com.example.teamProject1.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/api/board/{id}/report") // 글 신고
    public ResponseDto<Integer> report(@RequestBody Report report, @PathVariable int id, @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.reportBoard(report, id, principal.getUser());
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
//    @PostMapping("/api/board/{id}/report") // 글 신고 테스트
//    public ResponseDto<Integer> report(@RequestBody Report report, @PathVariable int id) {
//        boardService.reportBoard(report, id);
//        return new ResponseDto<>(HttpStatus.OK.value(), 1);
//    }

    @GetMapping("/api/board/{id}") // 글 상세보기 화면
    public Map<String,Object> findById(@PathVariable int id, @AuthenticationPrincipal PrincipalDetail principal) {
        Map<String,Object> map = new HashMap<>();
        map.put("board", boardService.detailBoard(id));
        if (principal != null) { // 로그인한 사용자만 추가 정보 제공
            map.put("principal", principal.getUser());
        }
        return map;
    }

    @GetMapping("/board/saveForm") // 글 작성 화면 (로그인 한 사용자만)
    public Map<String,Object> saveForm(@AuthenticationPrincipal PrincipalDetail principal) {
        Map<String,Object> map = new HashMap<>();
        map.put("principal",principal.getUser());
        return map;
    }

    @PostMapping("/api/board/{boardId}/reply") // 댓글 작성
    public ResponseDto<Integer> replySave(@RequestBody Reply reply,@PathVariable int boardId, @AuthenticationPrincipal PrincipalDetail principal) {
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

    @PostMapping(value = "/api/board/{id}")  //글 추천
    public ResponseDto<Integer> like(@PathVariable int id, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.likeBoard(id, principal.getUser());
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

    @GetMapping(value = "/") //메인 화면
    public Map<String, Object> main(@AuthenticationPrincipal PrincipalDetail principal){
        Map<String,Object> map = new HashMap<>();
        if (principal != null){
            map.put("principal",principal.getUser());
        }
        return map;
    }

    @GetMapping(value = "/api/profile") //프로필 확인 화면 (로그인 한 사용자만)
    public Map<String, Object> profile(@AuthenticationPrincipal PrincipalDetail principal){
        Map<String,Object> map = new HashMap<>();
        map.put("principal", principal.getUser());
        return map;
    }

    @GetMapping(value = "/api/scrap") //스크랩한 글 화면 (로그인 한 사용자만)
    public Map<String, Object> myScrap(@AuthenticationPrincipal PrincipalDetail principal){
        Map<String, Object> map = new HashMap<>();
        map.put("scraps", boardService.scrapList(principal.getUser()));
        map.put("principal",principal.getUser());
        return map;
    }

    @GetMapping(value = "/api/board") //글 목록
    public Map<String, Object> boardList(@AuthenticationPrincipal PrincipalDetail principal){
        Map<String, Object> map = new HashMap<>();
        map.put("boards",boardService.boardAll());
        if (principal != null) { // 로그인한 사용자만 추가 정보 제공
            map.put("principal", principal.getUser());
        }
        return map;
    }

    @GetMapping("/api/board/popular") //실시간인기역
    public Map<String, Object> getPopularBoardsWithinPastTwelveHours(@AuthenticationPrincipal PrincipalDetail principal) {
        Map<String, Object> map = new HashMap<>();
        map.put("boards",boardService.getPopularBoards());
        if (principal != null) { // 로그인한 사용자만 추가 정보 제공
            map.put("principal", principal.getUser());
        }
        return map;
    }
}
