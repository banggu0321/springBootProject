package com.kosta.myapp;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.myapp.repository.BoardRepository;
import com.kosta.myapp.vo.BoardVO;

@SpringBootTest
public class BoardTest {
	@Autowired
	BoardRepository boardRepo;
	
	public void test3() {
		//List<BoardVO> boardList = (List<BoardVO>) boardRepo.findById(1L);
		
	}
	
	@Test
	public void test2() {
		List<BoardVO> boardList = (List<BoardVO>) boardRepo.findAll();
		boardList.forEach(board->{
			System.out.println(board);
		});
	}
	
	//@Test
	public void test1() {
		IntStream.rangeClosed(1, 10).forEach(i->{
			BoardVO board = BoardVO.builder()
					.title("제목"+ i)
					.content("내용"+i)
					.writer("Hong")
					.build();
			
			boardRepo.save(board); //insert intot_boards values ()
			
		});
	}
}
