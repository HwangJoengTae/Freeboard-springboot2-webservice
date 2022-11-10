package com.free.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.free.domain.Board;
import com.free.dto.BoardDto;
import com.free.dto.CommentDto;
import com.free.dto.UserDto.Response;
import com.free.service.BoardService;

import io.swagger.annotations.ApiOperation;


@Controller
public class BoardController {
	private final Logger logger = LoggerFactory.getLogger("BoardController의 로그");
	
	@Autowired
	private BoardService boardService;
	
	@ApiOperation(value="게시판 글 목록 출력")
	@GetMapping("/")
	public String boardList(Model model, HttpSession httpSession,@PageableDefault Pageable pageable) {
		logger.info("게시판 글 목록");
		
		// userName을 사용할 수 있게 model에 저장
		Response user = (Response) httpSession.getAttribute("user");
		if (user != null) {
			model.addAttribute("userName", user.getNickname());
			
		}
			
		Page<Board> board = boardService.boardList(pageable);
		
		int startPage =Math.max(1, board.getPageable().getPageNumber() -4);
		int endPage= Math.min(board.getPageable().getPageNumber() +4,board.getTotalPages());
		
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);
		model.addAttribute("list",board);
			
		
		
		return "board/main";

	}
	
	//글 작성
	@ApiOperation(value="게시판 글작성페이지 이동")
	@GetMapping("/board/write")
	public String write(Model model, HttpSession httpSession,Board board){
		Response user = (Response) httpSession.getAttribute("user");
		if (user != null) {
			model.addAttribute("userName", user.getNickname());
			model.addAttribute("board", board);
			
			
		}
		return "board/board-write";
	}
			
	//답변 작성
	@ApiOperation(value="답변 작성 페이지 이동")
	@GetMapping("/board/reply/{id}")
	public String rewrite(Model model, HttpSession httpSession,@PathVariable Long id){
		Response user = (Response) httpSession.getAttribute("user");
		BoardDto.Response dto = boardService.findById(id);
		if (user != null) {
			model.addAttribute("userName", user.getNickname());
		}
		model.addAttribute("board",dto);
		return "board/board-reply";
	}
	
	
	 //글 상세보기
	@ApiOperation(value="상세 페이지 이동")
    @GetMapping("/board/read/{id}")
    public String read(@PathVariable Long id, Model model,HttpSession httpSession) {
    	Response user = (Response) httpSession.getAttribute("user");
    	BoardDto.Response dto = boardService.findById(id);
    	boardService.updateView(id);
    	
    	List<CommentDto.Response> comments = dto.getComments();
    	
    
    	//댓글 확인
        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
           
        }
        
        //유저 확인
    	if (user != null) {
             model.addAttribute("userName", user.getNickname());
             
             /* 게시글 작성자 본인인지 확인 */
             if (dto.getUserId().equals(user.getId())) {
                 model.addAttribute("writer", true);
             }

             /* 댓글 작성자 본인인지 확인 */
             if (comments.stream().anyMatch(s -> s.getUserId().equals(user.getId()))) {
                 model.addAttribute("isWriter", true);
             }
    	
    	}    	  
        
    	model.addAttribute("board",dto);
       
        return "board/board-read";
    }
    
    
    //글 수정
	@ApiOperation(value="수정 페이지 이동")
    @GetMapping("/board/update/{id}")
    public String update(@PathVariable Long id, Model model, HttpSession httpSession) {
    	Response user = (Response) httpSession.getAttribute("user");
    	BoardDto.Response dto = boardService.findById(id);
        if (user != null) {
            model.addAttribute("user", user.getNickname());
        }
        model.addAttribute("board", dto);

        return "board/board-update";
    }
   
    
    //글 검색
	@ApiOperation(value="검색페이지 이동")
    @GetMapping("/board/search")
    public String search(String keyword, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
    Pageable pageable, HttpSession httpSession) {
    	Response user = (Response) httpSession.getAttribute("user");
    	Page<Board> searchList = boardService.search(keyword, pageable);

    	if (user != null) {
    		model.addAttribute("user", user.getNickname());
    	}
    	
    	
    	
    	
    	int startPage =Math.max(1, searchList.getPageable().getPageNumber() -4);
		int endPage= Math.min(searchList.getPageable().getPageNumber() +4,searchList.getTotalPages());
		
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);
		model.addAttribute("searchList", searchList);
		

    	return "board/board-search";
}	
    
}
