package com.douzone.mysite.web.mvc.main;

import com.douzone.web2.mvc.Action;
import com.douzone.web2.mvc.ActionFactory;

public class MainActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		return new MainAction();
	}

}
