package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(UserVo vo) {
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session, UserVo vo, Model model) {
		UserVo authUser = userService.getUser(vo);
		
		if(authUser == null) {
			model.addAttribute("email", vo.getEmail());
			return "/user/login";
		}
		
		session.setAttribute("authUser", authUser);
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		// Access Controller -> 접근권한제어(회원 인증)를 위해
		UserVo authUser = (UserVo)session.getAttribute("authUser"); // session에 있는 authUser를 가져와서 authUser에 넣어주기
		if(authUser == null) {
			return "redirect:/";
		}
		///////////////////////////////////////////////////////////
	
		UserVo userVo = userService.getUser(authUser.getNo()); 
		
		model.addAttribute("userVo", userVo);
		return "user/update";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(HttpSession session, UserVo vo) {
		// Access Controller
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		///////////////////////////////////////////////////////////
		
		vo.setNo(authUser.getNo());
		userService.updateUser(vo);
		
		authUser.setName(vo.getName());
		return "redirect:/user/update";
	}
}
