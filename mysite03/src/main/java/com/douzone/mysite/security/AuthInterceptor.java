package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1. Handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletHandler가 처리하는 경우(정적 자원, /assets/**)
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 3. Handler Method의 @Auth 가져오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. Handler Method에 @Auth가 없으면 Type(class)에 붙어있는지 확인하기
		// 과제
		
		// 5. Type이나 Method에 @Auth가 없는 경우
		if(auth == null) {
			return true;
		}
		
		// 6. @Auth가 붙어 있기 때문에 인증(Athenfication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 7. 권한(Authorization) 체크를 위해 @Auth의 role 가져오기("ADMIN", "USER")
		String role = auth.role();
		//String authUserRole = authUser.getRole();
		
		// 8. 인증 확인
		return true;
	}
	
}
