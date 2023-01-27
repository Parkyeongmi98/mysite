package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		String param = request.getParameter("a");

		BoardVo vo = new BoardDao().findView(no);
		request.setAttribute("vo", vo);


		int cookieValue = 0;
		if("view".equals(param)) {
			
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
	            for (Cookie cookie : cookies) {
	                if (cookie.getName().equals(request.getParameter("no"))) {
	                    cookieValue = Integer.parseInt(cookie.getValue());
	                }
	            }
	        }
			
			Cookie cookie = new Cookie(request.getParameter("no"), String.valueOf(cookieValue));
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(24 * 60 * 24);
	        response.addCookie(cookie);
	        new BoardDao().visitCount(no);
			
			MvcUtil.forward("board/view", request, response);
		} else {
			MvcUtil.forward("board/modify", request, response);
		}
	}

}
