package com.free.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.free.domain.Board;
import com.free.domain.User;
import com.free.dto.BoardDto;
import com.free.persistence.BoardRepository;
import com.free.persistence.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class BoardService {
	private final Logger logger = LoggerFactory.getLogger("BoardService의 로그");
    @Autowired
	private final BoardRepository boardRepository;
    @Autowired
    private final UserRepository userRepository;
   
   
   
    
    // CREATE 
    @Transactional
    public Long save(BoardDto.Request dto, String nickname) throws Exception {
      
    	//게시글 등록
        User user = userRepository.findByNickname(nickname);
        dto.setUser(user);
        
        
        	

  			if(dto.getBoardCheck().equals("reply")) { 
  	  		dto.setOriginNo(dto.getOriginNo());
  	  		dto.setId(boardRepository.getMaxId());
  	  		boardRepository.updateGroupOrd(dto.getOriginNo(), dto.getGroupOrd()+1L);
  	  		dto.setGroupOrd(dto.getGroupOrd()+1);
  	  		dto.setGroupLayer(dto.getGroupLayer()+1);
  	  		
  			}else if(dto.getBoardCheck().equals("update")){ 
  	  		dto.setType(dto.getType());
  	  		}
  			
        	else {

  			dto.setId(boardRepository.getMaxId());
  			dto.setOriginNo(dto.getId());
  			dto.setGroupOrd(0L);
  			dto.setGroupLayer(0L);
  		}	
        
        Board board= dto.toEntity();
        boardRepository.save(board);
        logger.info("게시글 save");
      
       
      return board.getId();
    }
        
    // READ
    @Transactional(readOnly = true)
    public BoardDto.Response findById(Long id) {
       
    	Board board = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

        return new BoardDto.Response(board);
    }
    
    // UPDATE
    @Transactional
    public void update(Long id, BoardDto.Request dto) {
    	Board board = boardRepository.findById(id).orElseThrow(() ->
    	new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
    	
    	board.update(dto.getTitle(), dto.getContent());
    }
    
    // DELETE
    @Transactional
    public void delete(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        boardRepository.delete(board);
    }
    
    // 게시글 리스트 (페이징)
    @Transactional
    public Page<Board> boardList(Pageable pageable) {
        
    	int page = (pageable.getPageNumber()==0 ? 0 : (pageable.getPageNumber() - 1));
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("originNo"),Sort.Order.asc("groupOrd"))); // <- Sort 추가
       
        return boardRepository.findAll(pageable);
    }
   
    // 검색
    @Transactional(readOnly = true)
    public Page<Board> search(String keyword, Pageable pageable) {
        Page<Board> boardList = boardRepository.findByTitleContaining(keyword, pageable);
        return boardList;
    }
    
    // 조회수
    @Transactional
    public int updateView(Long id) {
        return boardRepository.updateView(id);
    }
    
    
    
   
    
}

