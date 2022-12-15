package com.study.board.repository;

import com.study.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    // findBy(Column) : 해당 컬럼 검색
    // findBy(Column)Containing : 키워드 검색
    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);

}
