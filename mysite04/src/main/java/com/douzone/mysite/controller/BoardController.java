package com.douzone.mysite.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String list(
			@RequestParam(value="pageNo", required = true, defaultValue = "1") Integer pageNo, 
			@RequestParam(value="keyword", required = true, defaultValue="") String keyword, 
			Model model) {
		Map<String, Object> map = boardService.getContentsList(pageNo, keyword);
		
		model.addAttribute("map", map);
		model.addAttribute("keyword", keyword);
		return "board/list";
	}
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
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
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(
			@AuthUser UserVo authUser, 
			@ModelAttribute @Valid BoardVo vo, 
			@RequestParam(value="pageNo", required = true, defaultValue = "1") Integer pageNo, 
			@RequestParam(value="keyword", required = true, defaultValue="") String keyword) {
		vo.setUserNo(authUser.getNo());
		boardService.updateContents(vo);
		
		return "redirect:/board/view/" + vo.getNo() + "?pageNo" + pageNo + "&keyword=" + keyword;
	}
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(
			@AuthUser UserVo authUser, 
			@PathVariable("no") Long no,
			@RequestParam(value="pageNo", required = true, defaultValue = "1") Integer pageNo, 
			@RequestParam(value="keyword", required = true, defaultValue="") String keyword) {
		boardService.deleteContents(no, authUser.getNo());
		
		return "redirect:/board?pageNo" + pageNo + "&keyword=" + keyword;
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(
			@AuthUser UserVo authUser, 
			@ModelAttribute @Valid BoardVo vo, 
			@RequestParam(value="pageNo", required = true, defaultValue = "1") Integer pageNo, 
			@RequestParam(value="keyword", required = true, defaultValue="") String keyword) {
		vo.setUserNo(authUser.getNo());
		boardService.writeContents(vo);
		
		return "redirect:/board?pageNo" + pageNo + "&keyword=" + keyword;
	}
	
	@RequestMapping("/reply/{no}")
	public String reply(
			@AuthUser UserVo authUser, 
			@PathVariable("no") Long no,
			Model model) {
		BoardVo boardVo = boardService.getContents(no);
		boardVo.setOrderNo(boardVo.getOrderNo() + 1);
		boardVo.setDepth(boardVo.getDepth() + 1);
		model.addAttribute("boardvo", boardVo);
		
		return "board/reply";
	}
}
