package com.free.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.free.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	@Transactional
	@Modifying
	@Query("update Board p set p.view = p.view + 1 where p.id = :id")
	int updateView(@Param("id")Long id);
	
	Page<Board> findByTitleContaining(String keyword, Pageable pageable);
	
	@Query("SELECT coalesce(max(b.id) + 1,1) FROM Board b") 
	Long getMaxId();

	@Transactional
	@Modifying
	@Query("UPDATE Board b SET b.groupOrd = b.groupOrd + 1 WHERE b.originNo = :originNo AND b.groupOrd >= :groupOrd")
	void updateGroupOrd(@Param("originNo")Long originNo, @Param("groupOrd")Long groupOrd);
	
}
