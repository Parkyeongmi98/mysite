package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String list(@RequestParam(value="pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value="keyword", required = false) String keyword ,Model model) {
		Map<String, Object> map = boardService.getContentsList(pageNo, keyword);
		
		model.addAllAttributes(map);
		return "board/list";
	}
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		boardService.visitCount(no); // 조회수
		BoardVo boardvo = boardService.getContents(no);
		model.addAttribute("boardvo", boardvo);

		return "board/view";
	}
	
	@Auth
	@RequestMapping(value="/update/{no}", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model) {
		BoardVo boardvo = boardService.getContents(no, authUser.getNo());
		
		model.addAttribute("boardvo", boardvo);
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/update/{no}", method=RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, @PathVariable("no") Long no, BoardVo vo) {
		boardService.updateContents(vo);
		
		return "redirect:/board/view/{no}";
	}
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(@AuthUser UserVo authUser, @PathVariable("no") Long no) {
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board?page=1&keyword";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, @ModelAttribute @Valid BoardVo vo, BindingResult result, Model model) {
		vo.setUserNo(authUser.getNo());
		boardService.writeContents(vo);
		return "redirect:/board?page=1&keyword";
	}
	
	@RequestMapping(value ="/reply", method = RequestMethod.POST)
	public String reply(BoardVo vo, Model model) {
		model.addAttribute("boardvo", vo);
		return "board/write";
	}
}
