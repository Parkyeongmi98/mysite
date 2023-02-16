package com.douzone.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class); // error코드 Log처리
			
	@ExceptionHandler(Exception.class)
	public ModelAndView handlerException(Exception e) {
		// 1. 404 Error 처리
		if(e instanceof NoHandlerFoundException) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("error/404");
			
			return mav;
		}
		
		// 2. 로깅(Logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		
		logger.error(errors.toString());
		
		// 3. 사과페이지(4. 정상종료)
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", errors.toString());
		mav.setViewName("error/exception");
		
		return mav;
	}
}
