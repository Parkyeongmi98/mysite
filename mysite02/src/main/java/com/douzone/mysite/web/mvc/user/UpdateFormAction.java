package com.douzone.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class UpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access Control(접근 제어)
		HttpSession session = request.getSession();
		if(session == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		////////////////////////////////////////////////////////////////////
		
		//UserVo vo = new UserDao().findByNo(authUser.getNo());
		//request.setAttribute("vo", vo);
		MvcUtil.forward("user/updateform", request, response);
	}

}
