package com.kosta.myapp.controller;

import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.kosta.myapp.repository.WebBoardRepository;
import com.kosta.myapp.repository.WebReplyRepository;
import com.kosta.myapp.security.MemberService;
import com.kosta.myapp.vo.PageMaker;
import com.kosta.myapp.vo.PageVO;
import com.kosta.myapp.vo.relation.WebBoard;
import com.querydsl.core.types.Predicate;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/board/*")
public class WebBoardController {
	@Autowired
	WebBoardRepository boardRepo;
	
	@Autowired
	WebReplyRepository replyRepo;
	
	@Autowired
	MemberService mservice;
	
	@GetMapping("/boardlist.go")
	public String boardlist(@ModelAttribute PageVO pageVO, Model model, Principal principal, Authentication authentication, HttpSession session, HttpServletRequest request) {
		
		//--Security Test
		UserDetails member = (UserDetails) session.getAttribute("user");
		System.out.println("방법:"+member);
		
		System.out.println("방법1:" + principal);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		System.out.println("방법2:" + userDetails);
		Object principal2 = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		UserDetails userDetails2 = (UserDetails)principal2; 
		System.out.println("방법3:" + userDetails2);

		String mid = principal.getName();
		UserDetails userDetails3 = mservice.loadUserByUsername(mid);
		System.out.println("방법4:" +userDetails3);
		
		//----
		Map<String, Object> map = (Map<String, Object>) RequestContextUtils.getInputFlashMap(request);
		
		if(map!=null) {
			String message = (String) map.get("message");
			model.addAttribute("msg",message);
			pageVO = (PageVO) map.get("pageVO");
		}
		if(pageVO==null) pageVO = new PageVO();
		
		System.out.println("pageVO:" + pageVO);
		Pageable page = pageVO.makePaging(0, "bno");
		Predicate predicate = boardRepo.makePredicate(pageVO.getType(), pageVO.getKeyword());
		
		Page<WebBoard> blist = boardRepo.findAll(predicate, page);
		PageMaker<WebBoard> pgmaker = new PageMaker<>(blist);
		
		System.out.println(blist.getNumber());
		System.out.println(blist.getSize());
		System.out.println(blist.getTotalPages());
		System.out.println(blist.getContent());
		
		
		model.addAttribute("blist", pgmaker);
		//model.addAttribute("pageVO", pageVO);
		return "board/boardlist";
	}

	@GetMapping("/replyByBoard.go")
	public String replyByBoard(WebBoard board, Model model) {
		model.addAttribute("rlist", replyRepo.findByBoard(board));
		return "board/replyByBoard";
	}
	@RequestMapping("/view.go")
	public String view(PageVO pageVO, Long bno, Model model) {
		log.info(pageVO.toString());
		log.info("bno: " + bno);
		
		model.addAttribute("vo", boardRepo.findById(bno).get());
		return "board/boardview";
	}
	
	@RequestMapping(value="/register.go", method=RequestMethod.GET)
	public String register() {
		//board/register.html이 default가 된다.
		return "board/register";
	}
	
	@RequestMapping(value="/register.go", method=RequestMethod.POST)
	public String registerPost(WebBoard board, RedirectAttributes attr) {
		log.info(board.toString());
		WebBoard insertbBoard = boardRepo.save(board);
		attr.addFlashAttribute("message", insertbBoard != null?"insert success":"insert fail");
		return "redirect:/board/boardlist.go";
	}
	
	@GetMapping("/modify.go")
	public String modify(Model model, Long bno, @ModelAttribute PageVO pageVO) {
		model.addAttribute("vo",boardRepo.findById(bno).get());
		return "board/modify";
	}
	@PostMapping("/modify.go")
	public String modifyPost(PageVO pageVO, WebBoard board, RedirectAttributes reAttr) {
		boardRepo.findById(board.getBno()).ifPresentOrElse(original->{
			original.setContent(board.getContent());
			original.setTitle(board.getTitle());
			original.setWriter(board.getWriter());
			WebBoard updateBoard = boardRepo.save(original);
			reAttr.addFlashAttribute("message", updateBoard != null?"modify success":"modify fail");
			reAttr.addFlashAttribute("pageVO",pageVO);
		}, ()->{System.out.println("수정할 데이터 없음");});
		
		return "redirect:/board/boardlist.go";
	}
	@PostMapping("/delete.go")
	public String delete(PageVO pageVO, WebBoard board, RedirectAttributes reAttr) {
		boardRepo.deleteById(board.getBno());
		reAttr.addFlashAttribute("message","delete OK");
		reAttr.addFlashAttribute("pageVO",pageVO);
		
		return "redirect:/board/boardlist.go";
	}
}
