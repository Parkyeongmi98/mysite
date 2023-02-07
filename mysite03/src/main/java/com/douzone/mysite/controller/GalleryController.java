package com.douzone.mysite.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileuploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private GalleryService galleryService;
	@Autowired
	private FileuploadService fileuploadService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GalleryVo> list = galleryService.getImages();
		model.addAttribute("list", list);
		
		return "gallery/index";
	}
	
	@RequestMapping("/upload")
	public String upload(
			@RequestParam("comments") String comments, 
			@RequestParam("file") MultipartFile file, 
			@ModelAttribute @Valid GalleryVo vo,
			Model model) {
		String url = fileuploadService.restore(file);
		vo.setComments(comments);
		vo.setUrl(url);
		galleryService.addImage(vo);
		
		model.addAttribute("url", url);
		
		return "redirect:/gallery";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		galleryService.removeImage(no);
		
		return "redirect:/gallery";
	}
}
