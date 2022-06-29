package com.kosta.myapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.kosta.myapp.vo.BoardVO;

public interface BoardRepository extends CrudRepository<BoardVO, Long>{
	
}
