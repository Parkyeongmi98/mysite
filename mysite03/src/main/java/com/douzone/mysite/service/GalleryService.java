package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	@Autowired
	private GalleryRepository galleryRepository;
	
	public List<GalleryVo> getImages() {
		return galleryRepository.findAll();
	}
	
	public void removeImage(Long no) {
		galleryRepository.delete(no);
	}
	
	public void addImage(GalleryVo vo) {
		galleryRepository.insert(vo);
	}
}
