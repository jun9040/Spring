package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ktdsuniversity.edu.kjh.bbs.service.BoardService;
import com.ktdsuniversity.edu.kjh.bbs.vo.BoardListVO;

@Controller
public class HelloBootController {
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/list")
	public ModelAndView viewVoardList() {
		BoardListVO boardListVO = boardService.getAllBoard();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board/boardlist");
		modelAndView.addObject("boardList", boardListVO);
		return modelAndView;
	}
}