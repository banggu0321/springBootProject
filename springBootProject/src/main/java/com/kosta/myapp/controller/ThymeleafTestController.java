package com.kosta.myapp.controller;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kosta.myapp.repository.FreeBoardRepository;
import com.kosta.myapp.vo.CarVO;

@Controller
public class ThymeleafTestController {
	
	@GetMapping("/sample1")
	public String test1(Model model) {
		model.addAttribute("score", 100);
		model.addAttribute("myname", "jin");
		CarVO car = CarVO.builder()
				.carNO(1234L)
				.model("ABC")
				.build();
		model.addAttribute("mycar", car);
		return "thymeleaf1"; //templates/thymeleaf1.html
	}
	
	@Autowired
	FreeBoardRepository boardRepo;
	
	@GetMapping("/sample2")
	public String test2(Model model, HttpSession session) {
		model.addAttribute("boardlist", boardRepo.findAll());
		model.addAttribute("LoginUser", "정우");
		model.addAttribute("LoginUser2", "진");
		model.addAttribute("salary", 1234456764636462L);
		
		session.setAttribute("loginUser3","admin");
		
		return "thymeleaf2";
	}
	
	@GetMapping("/sample3")
	public String test3(Model model) {
		model.addAttribute("now", new Date());
		model.addAttribute("salary", 12344567.77F);
		model.addAttribute("message", "타임리프연습 hi product");
		model.addAttribute("fruits", Arrays.asList("사과","바나나","참외"));
		return "thymeleaf3";
	}
	@GetMapping("/sample4")
	public String test4(Model model) {
		return "thymeleaf4_inherit";
	}
}
