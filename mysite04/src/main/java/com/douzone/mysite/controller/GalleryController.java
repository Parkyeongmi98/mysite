package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public String upload(GalleryVo vo, MultipartFile file) {
		String url = fileuploadService.restore(file);
		vo.setUrl(url);
		galleryService.addImage(vo);
		
		return "redirect:/gallery";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		galleryService.removeImage(no);
		
		return "redirect:/gallery";
	}
}
