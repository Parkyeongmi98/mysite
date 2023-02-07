package com.douzone.mysite.controller;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.FileuploadService;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private SiteService siteService;
	@Autowired
	private FileuploadService fileuploadService;
	
	@RequestMapping("")	
	public String main(Model model) {
		SiteVo vo = siteService.getSite();
		model.addAttribute("siteVo", vo);
		
		return "admin/main";
	}
	
	@RequestMapping("/main/update")
	public String update(@RequestParam("title") String title,
				@RequestParam("welcome") String welcome,
				@RequestParam("file") MultipartFile profile,
				@RequestParam("description") String description,
				@ModelAttribute @Valid SiteVo vo) {
		vo.setTitle(title);
		vo.setWelcome(welcome);
		vo.setDescription(description);
		String url = fileuploadService.restore(profile);
		vo.setProfile(url);
		siteService.updateSite(vo);
		
		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
