package com.kosta.myapp;

import java.util.List;
import java.util.Optional;
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

	//@Test
	public void findByBnoGreaterThan() {
		boardRepo.findByBnoGreaterThan(90L).forEach(b->{
			System.out.println(b);
		});
	}
	//@Test
	public void findByTitleContaining() {
		boardRepo.findByTitleContaining("제목").forEach(b->{
			System.out.println(b);
		});
	}
	//@Test
	public void findByTitleLike() {
		boardRepo.findByTitleLike("제목"+'%').forEach(b->{
			System.out.println(b);
		});
	}
	//@Test
	public void findByWriterAndTitle() {
		boardRepo.findByWriterAndTitle("KIM","title update").forEach(b->{
			System.out.println(b);
		});
	}
	//@Test
	public void findByWriter() {
		boardRepo.findByWriter("Hong").forEach(b->{
			System.out.println(b);
		});
	}
	//@Test
	public void selectByTitle() {
		boardRepo.findByTitle("title update").forEach(b->{
			System.out.println(b);
		});
	}
	
	//@Test
	public void countAndExists() {
		Long cnt = boardRepo.count();
		System.out.println("board table의 전체 건수 : " + cnt);
		
		boolean result = boardRepo.existsById(1L);
		System.out.println(result?"존재":"없음");
	}
	
	//삭제
	public void test7() {
		boardRepo.deleteById(1L);
	}
	//수정
	//@Test
	public void test6() {
		boardRepo.findById(200L).ifPresentOrElse(board->{
			board.setTitle("title update");
			board.setContent("content update");
			board.setWriter("KIM");
			boardRepo.save(board);
		}, ()->{
			BoardVO board = BoardVO.builder()
					.title("제목 new")
					.content("내용 new")
					.writer("KIM")
					.build();
			boardRepo.save(board);
		});
	}
	
	//1건조회
	//@Test
	public void test5() {
		Optional<BoardVO> board = boardRepo.findById(101L);
		if(board.isPresent()) {
			BoardVO b = board.get();
			System.out.println(b);
		}else {
			System.out.println("존재하지 않는다.");
		}
	}
	//@Test
	public void test4() {
		boardRepo.findById(100L).ifPresentOrElse(board->{
			System.out.println(board);
		}, ()->{System.out.println("존재하지 않는다.");});
	}
	//@Test
	public void test3() {
		boardRepo.findById(101L).ifPresent(board->{
			System.out.println(board);
		});
	}
	
	//전체조회
	//@Test
	public void test2() {
		List<BoardVO> boardList = (List<BoardVO>) boardRepo.findAll();
		boardList.forEach(board->{
			System.out.println(board);
		});
	}
	
	//삽입
	//@Test
	public void test1() {
		IntStream.rangeClosed(11, 100).forEach(i->{
			BoardVO board = BoardVO.builder()
					.title("제목"+ i)
					.content("내용"+i)
					.writer("Hong")
					.build();
			
			boardRepo.save(board); //insert intot_boards values ()
		});
	}
}
