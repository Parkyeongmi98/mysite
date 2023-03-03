package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookRepository guestbookRepository;
	
	public List<GuestbookVo> getMessageList() {
		return guestbookRepository.findAll();
	}
	
	public List<GuestbookVo> getMessageList(Long no) {
		return guestbookRepository.findAll(no);
	}
	
	public Boolean deleteMessage(Long no, String password) {
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		
		return 1 == guestbookRepository.deleteByNoAndPassword(no, password);
	};
	
	public void addMessage(GuestbookVo vo) {
		guestbookRepository.insert(vo);
	}
}
