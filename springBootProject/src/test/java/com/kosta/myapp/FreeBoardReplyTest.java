package com.kosta.myapp;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.FetchType;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;

import com.kosta.myapp.repository.FreeBoardRepliesRepository;
import com.kosta.myapp.repository.FreeBoardRepository;
import com.kosta.myapp.vo.relation.FreeBoard;
import com.kosta.myapp.vo.relation.FreeBoardReply;

import lombok.extern.java.Log;

@SpringBootTest
@Log
@Commit//fetch = FetchType.LAZY인 경우 필수
public class FreeBoardReplyTest {
	
	@Autowired
	FreeBoardRepository boardRepo;
	
	@Autowired
	FreeBoardRepliesRepository replyRepo;
	
	//@Test
	public void getcountReply() {
		List<Object[]> result1 = boardRepo.getCountReply();
		List<Object[]> result2 = boardRepo.getCountReply2();
		
		result1.forEach(aa->{
			log.info(Arrays.toString(aa));
		});
		log.info("-----------------------------");
		result2.forEach(aa->{
			log.info(Arrays.toString(aa));
		});
	}
	
	//@Transactional
	//@Test
	public void findByBnoGreaterThan() {
		Long bno = 257L;
		PageRequest page = PageRequest.of(0, 5);
		Page<FreeBoard> result = boardRepo.findByBnoGreaterThan(bno, page);
		log.info("getNumber : "+result.getNumber());
		log.info("getNumberOfElements : "+result.getNumberOfElements());
		log.info("getTotalElements : "+result.getTotalElements());
		log.info("getTotalPages : "+result.getTotalPages());
		List<FreeBoard> blist = result.getContent();
		blist.forEach(b->{log.info(b.toString());});
	}
	
	//@Transactional
	//@Test
	public void getReplyCount() {
		long bno = 270L;
		boardRepo.findById(bno).ifPresentOrElse(board->{
			log.info("댓글건수 : "+board.getReplies().size());
		}, ()->{});
	}
	
	//@Test
	public void insertReply2() {
		//board찾아서 댓글 넣기
		long bno = 270L;
		boardRepo.findById(bno).ifPresentOrElse(board->{
			FreeBoardReply reply1 = FreeBoardReply.builder()
					.reply("%%%")
					.replyer("sol")
					.board(board)
					.build();
			replyRepo.save(reply1);
		}, ()->{System.out.println(bno + " not found");});
	}
	
	//@Test
	public void retrieveAll() {
		boardRepo.findAll().forEach(board->{
			log.info(board.toString());
		});
	}
	
	//@Transactional //fetch = FetchType.LAZY인 경우 필수
	//@Test
	public void insertReply() {
		//board찾아서 댓글 넣기
		long bno = 270L;
		boardRepo.findById(bno).ifPresentOrElse(board->{
			List<FreeBoardReply> replies = board.getReplies();
			if(replies==null) replies = new ArrayList<>();
			FreeBoardReply reply1 = FreeBoardReply.builder()
					.reply("JPA배우기")
					.replyer("sol")
					.board(board)
					.build();
			FreeBoardReply reply2 = FreeBoardReply.builder()
					.reply("JPA 다대일, 일대다")
					.replyer("sol")
					.board(board)
					.build();
			replies.add(reply1);
			replies.add(reply2);
			//board.setReplies(replies); getReplies해서 주소 알고있음
			boardRepo.save(board);
		}, ()->{System.out.println(bno + " not found");});
	}
	
	//@Test
	public void insertBoard() {
		//board 10건, reply 없음
		IntStream.rangeClosed(1, 10).forEach(i->{
			FreeBoard board = FreeBoard.builder()
					.title("토요일"+i)
					.content("오늘은 출근 안해서 좋아요")
					.writer("진")
					.build();
			boardRepo.save(board);
		});
	}
	//@Test
	public void insert() {
		//board 10건, reply5건 입력
		IntStream.rangeClosed(1, 10).forEach(i->{
			FreeBoard board = FreeBoard.builder()
					.title("금요일"+i)
					.content("비가 그침!")
					.writer("정우")
					.build();
			List<FreeBoardReply> replies = new ArrayList<>();
			IntStream.rangeClosed(1, 5).forEach(j->{
				FreeBoardReply reply = FreeBoardReply.builder()
						.reply("또 비가 온다네요" + i + "==>" + j)
						.replyer("아무개")
						.board(board) //어떤 개시글의 댓글인지 setting해야함
						.build();
				replies.add(reply);
			});
			board.setReplies(replies);
			boardRepo.save(board);
		});
	}
}
