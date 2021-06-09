package com.neighbor.ServiceAPI.controller.view;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.neighbor.ServiceAPI.center.api.interceptor.annotation.Interceptor;

/**
 * 1. Function : Common Modules Controll.<p>
 *  <pre>
 *  <b>History:</b> 
 *     First author : ksh, 1.0, 2016/03/09
 *  </pre>
 *  
 * @author last modifier : ksh
 * @version 1.0
 * @see
 * <p/>
 */

@Controller
@Interceptor("CallLogger")
@RequestMapping(value="/view/common")
public class CommonController {
	@SuppressWarnings("unused")
	private final Log logger = LogFactory.getLog(CommonController.class);
	
//	@Resource(name="commonService")
//	private CommonService commonService;
	
	/**
	 * 잘못된 페이지 접근
	 * @param request
	 * @param response
	 * @return common/errorCode
	 */
	@RequestMapping(value="/errorCode")
	public ModelAndView errorCode(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("common/errorCode");
	}
	
	/**
	 * 상단 Header
	 * @param request
	 * @param response
	 * @return common/includeHeader
	 */
	@RequestMapping(value="/includeHeader")
	public ModelAndView includeHeader(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("common/includeHeader");
	}
	
	/**
	 * 하단 Footer
	 * @param request
	 * @param response
	 * @return common/includeFooter
	 */
	@RequestMapping(value="/includeFooter")
	public ModelAndView includeFooter(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("common/includeFooter");
	}
	
	/**
	 * 회원가입
	 * @param request
	 * @param response
	 * @return common/includeFooter
	 */
	@RequestMapping(value="/memberShipPopup")
	public ModelAndView memberShipPopup(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/login/memberShipPopup");
	}
	
}