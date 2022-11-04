package com.free.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.free.dto.BoardDto;
import com.free.dto.UserDto.Response;
import com.free.service.BoardService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * REST API Controller
 */
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class BoardApiController {
	private final Logger logger = LoggerFactory.getLogger("BoardController의 로그");
	private final BoardService boardService;
	
	
	
	// CREATE
	@ApiOperation(value="글 생성",notes="성공시 게시글 생성합니다.")
	@PostMapping("/board")
	public ResponseEntity  save(@RequestBody BoardDto.Request dto, HttpSession httpSession) throws Exception {
		Response user = (Response) httpSession.getAttribute("user");
		Long boardId = boardService.save(dto, user.getNickname());
		
	
		return  ResponseEntity.ok(boardId);
	}
	
		

	// READ
	@ApiOperation(value="상세보기",notes="성공시 게시글 상세보기.")
	@GetMapping("/board/{id}")
	public ResponseEntity read(@PathVariable Long id) {

		return ResponseEntity.ok(boardService.findById(id));
	}

	// UPDATE
	@ApiOperation(value="수정하기",notes="성공시 게시글을 수정합니다.")
	@PutMapping("/board/{id}")
	public ResponseEntity update(@PathVariable Long id, @RequestBody BoardDto.Request dto) {
		boardService.update(id, dto);
		return ResponseEntity.ok(id);
	}

	// DELEATE
	@ApiOperation(value="삭제하기",notes="성공시 게시글을 삭제합니다.")
	@DeleteMapping("/board/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		boardService.delete(id);
		return ResponseEntity.ok(id);
	}

}
