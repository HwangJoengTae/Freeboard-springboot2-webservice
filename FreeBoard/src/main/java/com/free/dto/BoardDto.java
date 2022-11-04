package com.free.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.free.domain.Board;
import com.free.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * request, response DTO 클래스를 하나로 묶어 InnerStaticClass로 한 번에 관리
 */
public class BoardDto {


  
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {

        private Long id;
        private String title;
        private String writer;
        private String content;
        private String createdDate, modifiedDate;
        private int view;
        private User user;
        private Long originNo;
    	private Long groupOrd;
    	private Long groupLayer;
    	private String boardCheck;
    	private Long type;
        
        
       
      
       
       
        /* Dto -> Entity */
        public Board toEntity() {
        	Board board = Board.builder()
                    .id(id)
                    .title(title)
                    .writer(writer)
                    .content(content)
                    .view(0)
                    .user(user)
                    .originNo(originNo)
                    .groupOrd(groupOrd)
                    .groupLayer(groupLayer)
                    .boardCheck(boardCheck)
                    .type(type)
                    .build();
        			
            return board;
        }
    }

    
    @Getter
    public static class Response {
        private final Long id;
        private final String title;
        private final String writer;
        private final String content;
        private final String createdDate, modifiedDate;
        private final int view;
        private final Long userId;
        private final List<CommentDto.Response> comments;
        private Long originNo;
    	private Long groupOrd;
    	private Long groupLayer;
    	private String boardCheck;
    	private Long type;
        
       
       
        
       
      

       

        /* Entity -> Dto*/
        public Response(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.writer = board.getWriter();
            this.content = board.getContent();
            this.createdDate = board.getCreatedDate();
            this.modifiedDate = board.getModifiedDate();
            this.view = board.getView();
            this.userId = board.getUser().getId();
            this.comments = board.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());
            this.originNo=board.getOriginNo();
            this.groupOrd=board.getGroupOrd();
            this.groupLayer=board.getGroupLayer();
            this.boardCheck=board.getBoardCheck();
            this.type=board.getType();
           
           
			
            
            
            
           
        }
    }
}
