package com.neighbor.ServiceAPI.mobile.LocInfoSystem.Controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Command.LocInfoCommand;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.DataSet.LocInfoDataSet;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.EmrgncyDomain;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Service.CommonService;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Service.LocInfoService;
import com.neighbor.ServiceAPI.mobile.util.ComUtil;

@Controller
public class LocInfoController {
	
	@Autowired  
	private MessageSource messageSource;
	
	@Autowired
	private LocInfoService locInfoService;
	
	@Autowired
	private CommonService commonService;
	
	private static final Logger logger = LoggerFactory.getLogger(LocInfoController.class);
	
	@RequestMapping("/view/locInfo/locInfo.do")
	public final ModelAndView getLocInfo(@RequestHeader("Accept") final String accepHeader,
			@ModelAttribute("command") final LocInfoCommand command,
			HttpSession session,
			final HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
//		session.getAttribute("cnter_cd");
		LocInfoDataSet ds = locInfoService.selectDrverList(command);
		
		EmrgncyDomain emrgncy = locInfoService.selectEmrgncy();
		
		mav.setViewName("tscp/LocInfoSystem/LocInfo");
		mav.addObject("dataSet", ds);
		mav.addObject("emrgncy", emrgncy);
		return mav;
	}
	
	@RequestMapping(value="/{cnter_cd}/getPoi.do", method=RequestMethod.GET)
	public ModelAndView getPoi(@RequestHeader("Accept") final String accepHeader, @PathVariable("cnter_cd") final String cnter_cd,
			@ModelAttribute("command") final LocInfoCommand command,
			final HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		logger.info("test!!");
		logger.info("param : {}", command.toString());
		return mav;
	}
	
	@ModelAttribute("drverSttusCd")
	public LinkedHashMap<String, String> getDrverSttusCd(){
		List<HashMap<String, Object>> list = commonService.getDrverSttusCd();
		return ComUtil.convertToCodeMap(list);
	}
}
