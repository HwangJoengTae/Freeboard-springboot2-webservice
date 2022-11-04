package com.free.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.free.domain.Board;
import com.free.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /* 게시글 댓글 목록 가져오기 */
    List<Comment> getCommentByBoardOrderById(Board board);
}
