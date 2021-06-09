package com.neighbor.ServiceAPI.center.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neighbor.ServiceAPI.center.api.exception.HttpStatusException;

public abstract class AbsAuthorized implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse res, Object obj, Exception ex)
			throws Exception {}

	public void postHandle(HttpServletRequest req, HttpServletResponse res,
			Object obj, ModelAndView mv) throws Exception {}

	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object obj) throws Exception {
		
		try {
			authorized(req, res);
		} catch(HttpStatusException ex) {
			throw ex;
		}
		return true;
	}
	
	public abstract void authorized(HttpServletRequest req, HttpServletResponse res) throws HttpStatusException;
}
