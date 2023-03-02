package com.douzone.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.service.GuestbookService;

@RestController("guestbookApiController")
@RequestMapping("/gb/api")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	
}
