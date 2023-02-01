package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String list(Model model) {
		Map<String, Object> map = boardService.getContentsList(1, "");
		
		model.addAllAttributes(map);
		return "board/list";
	}
	
	@RequestMapping("/view/{no}")
	public String view(HttpSession session, @PathVariable("no") Long no, Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser"); // session에 있는 authUser를 가져와서 authUser에 넣어주기
		if(authUser == null) {
			return "redirect:/";
		}
		
		BoardVo boardvo = boardService.getContents(no);
		model.addAttribute("boardvo", boardvo);
		model.addAttribute("authUser", authUser);
		return "board/view";
	}
	
	@RequestMapping(value="/update/{no}", method=RequestMethod.GET)
	public String update(@PathVariable("no") Long no, Model model) {
		BoardVo boardvo = boardService.getContents(no);
		
		model.addAttribute("boardvo", boardvo);
		return "board/modify";
	}
	
	@RequestMapping(value="/update/{no}", method=RequestMethod.POST)
	public String update(@PathVariable("no") Long no, BoardVo vo) {
		boardService.updateContents(vo);
		
		return "redirect:/board/view/{no}";
	}
}
