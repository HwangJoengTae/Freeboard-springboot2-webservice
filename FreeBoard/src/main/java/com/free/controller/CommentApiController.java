package com.free.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.free.dto.CommentDto;
import com.free.dto.UserDto.Response;
import com.free.service.CommentService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * REST API Controller
 */
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    /* CREATE */
    @ApiOperation(value="댓글 작성",notes="성공시 댓글 작성합니다.")
    @PostMapping("/board/{id}/comments")
    public ResponseEntity save(@PathVariable Long id, @RequestBody CommentDto.Request dto,
                              HttpSession httpSession) {
    	Response user = (Response) httpSession.getAttribute("user");
    	return ResponseEntity.ok(commentService.save(id, user.getNickname(), dto));
    }

    /* READ */
    @ApiOperation(value="댓글 읽기",notes="성공시 댓글을 읽습니다.")
    @GetMapping("/board/{id}/comments")
    public List<CommentDto.Response> read(@PathVariable Long id) {
        
    	return commentService.findAll(id);
    }

    /* UPDATE */
    @ApiOperation(value="댓글 수정",notes="성공시 댓글 수정합니다.")
    @PutMapping({"/board/{id}/comments/{id}"})
    public ResponseEntity update(@PathVariable Long id, @RequestBody CommentDto.Request dto) {
        commentService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @ApiOperation(value="댓글 삭제",notes="성공시 댓글 삭제합니다.")
    @DeleteMapping("/board/{id}/comments/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.ok(id);
    }
}
