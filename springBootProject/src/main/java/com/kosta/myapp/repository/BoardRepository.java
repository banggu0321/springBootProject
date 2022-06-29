package com.kosta.myapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kosta.myapp.vo.BoardVO;

public interface BoardRepository extends CrudRepository<BoardVO, Long>{
	//1. 기본 CRUD는 제공된다.. findAll(), findById() save(), delete(), count(), exist()
	//2. 규칙에 맞는 메서드 정의 추가
	//https://docs.spring.io/spring-data/jpa/docs/2.5.1/reference/html/#jpa.query-methods
	List<BoardVO> findByTitle(String title); //where title = ?
	List<BoardVO> findByWriter(String writer2);
	List<BoardVO> findByWriterAndTitle(String writer, String title); 
	List<BoardVO> findByTitleLike(String title); //where title like '%'?'%'
	List<BoardVO> findByTitleContaining(String title); //where title like '%'?'%'
	List<BoardVO> findByBnoGreaterThan(Long bno); //where bno>?
	
	
}
