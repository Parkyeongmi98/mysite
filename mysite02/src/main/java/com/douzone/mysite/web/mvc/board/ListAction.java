package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String page = request.getParameter("page");
		if(page == null) {
			page = "1";
		}
		
		List<BoardVo> list;
		if(keyword == null) {
			list = new BoardDao().findAll();
		} else {
			list = new BoardDao().findKeyword(keyword);
		}
		
		request.setAttribute("page", page);
		request.setAttribute("list", list);
		
		MvcUtil.forward("board/list", request, response);

	}

}
