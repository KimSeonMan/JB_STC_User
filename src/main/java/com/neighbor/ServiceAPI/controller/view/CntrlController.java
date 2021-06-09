package com.neighbor.ServiceAPI.controller.view;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 1. 기능 : 관제컨트롤러.<p>
 * 2. 처리개요 : <p>
 * 3. 주의사항 : <p>
 *  <pre>
 *  <b>History:</b> 
 *     작성자 : 최재영, 1.0, 2017/02/20 초기 작성
 *  </pre>
 *  
 * @author 최종 수정자 : 최재영
 * @version 1.0
 * @see
 * <p/>
 */
import com.neighbor.ServiceAPI.center.api.interceptor.annotation.Interceptor;
import com.neighbor.ServiceAPI.controller.service.CntrlService;

@Controller
@Interceptor("CallLogger")
@RequestMapping(value="/view/cntrl")
public class CntrlController {

	@SuppressWarnings("unused")
	private final Log logger = LogFactory.getLog(CntrlController.class);
	
	@Resource(name="cntrlService")
	private CntrlService cntrlService;
	

}
