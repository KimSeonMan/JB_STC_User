package com.neighbor.ServiceAPI.center.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("PrePageCall")
public class PrePageCall implements HandlerInterceptor {
	private final Log log = LogFactory.getLog(PrePageCall.class);
	
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object obj, Exception ext) throws Exception {
	}

	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object obj, ModelAndView mv) throws Exception {
	}

	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object obj) throws Exception {
		try {
			log.info("Job");
		} catch(Exception e) {
			log.info(e);
		}
		
		return true;
	}
}