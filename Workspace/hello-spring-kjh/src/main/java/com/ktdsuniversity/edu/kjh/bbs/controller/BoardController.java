package com.ktdsuniversity.edu.kjh.bbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktdsuniversity.edu.kjh.bbs.service.BoardService;
import com.ktdsuniversity.edu.kjh.bbs.vo.BoardListVO;
import com.ktdsuniversity.edu.kjh.bbs.vo.BoardVO;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/list")
	public ModelAndView viewBoardList() {
		BoardListVO boardListVO = boardService.getAllBoard();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board/boardlist");
		modelAndView.addObject("boardList", boardListVO);
		return modelAndView;
	}
	
	@GetMapping("/board/write")
	public String viewBoardWritePage() {
		return "board/boardwrite";
	}
	
	@PostMapping("/board/write")
	public ModelAndView doBoardWrite(BoardVO boardVO) {

	    System.out.println("제목: " + boardVO.getSubject());
	    System.out.println("이메일: " + boardVO.getEmail());
	    System.out.println("내용: " + boardVO.getContent());
	    System.out.println("등록일: " + boardVO.getCrtDt());
	    System.out.println("수정일: " + boardVO.getMdfyDt());
	    System.out.println("FileName: " + boardVO.getFileName());
	    System.out.println("OriginFileName: " + boardVO.getOriginFileName());

	    ModelAndView modelAndView = new ModelAndView();

	    // 게시글 등록
	    boolean isSuccess = boardService.createNewBoard(boardVO);

	    if (isSuccess) {
	        // 성공 시 목록 페이지로 리다이렉트
	        modelAndView.setViewName("redirect:/board/list");
	    } else {
	        // 실패 시 작성 페이지로 다시 이동하며 데이터 전달
	        modelAndView.setViewName("board/boardwrite");
	        modelAndView.addObject("boardVO", boardVO);
	    }

	    return modelAndView;
	}
	
    @GetMapping("/board/view") // http://localhost:8080/board/view?id=1
    public ModelAndView viewOneBoard(@RequestParam int id) {
        BoardVO boardVO = boardService.getOneBoard(id, true);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("board/boardview");
        modelAndView.addObject("boardVO", boardVO);
        return modelAndView;
    }
    
    @GetMapping("/board/modify/{id}") // http://localhost:8080/board/modify/2
    public ModelAndView viewBoardModifyPage(@PathVariable int id) {
        // 게시글 수정을 위해 게시글의 내용을 조회한다.
    	// 게시글 조회와 동일한 코드 호출
        BoardVO boardVO = boardService.getOneBoard(id, false);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("board/boardmodify");
        modelAndView.addObject("boardVO", boardVO);

        return modelAndView;
    }
    
    @PostMapping("/board/modify")
    public ModelAndView doBoardUpdate(BoardVO boardVO) {
        System.out.println("ID: " + boardVO.getId());
        System.out.println("제목: " + boardVO.getSubject());
        System.out.println("이메일: " + boardVO.getEmail());
        System.out.println("내용: " + boardVO.getContent());
        System.out.println("등록일: " + boardVO.getCrtDt());
        System.out.println("수정일: " + boardVO.getMdfyDt());
        System.out.println("FileName: " + boardVO.getFileName());
        System.out.println("OriginFileName: " + boardVO.getOriginFileName());

        ModelAndView modelAndView = new ModelAndView();

        // 게시글 수정
        boolean isSuccess = boardService.updateOneBoard(boardVO);

        if (isSuccess) {
            // 성공 시 상세보기 페이지로 리다이렉트
            modelAndView.setViewName("redirect:/board/view?id=" + boardVO.getId());
        } else {
            // 실패 시 수정 페이지로 돌아감
            modelAndView.setViewName("board/boardmodify");
            modelAndView.addObject("boardVO", boardVO);
        }

        return modelAndView;
    }
    
    @GetMapping("/board/delete/{id}")
    public String doDeleteBoard(@PathVariable int id) {
        boolean isSuccess = boardService.deleteOneBoard(id);
        
        if (isSuccess) {
            return "redirect:/board/list";
        } else {
            return "redirect:/board/view?id=" + id;
        }
    }
	
}