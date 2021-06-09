package com.neighbor.ServiceAPI.controller.view;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.neighbor.ServiceAPI.controller.service.LoginService;
import com.neighbor.ServiceAPI.mobile.util.ComUtil;
import com.neighbor.ServiceAPI.mobile.util.EncryptUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.neighbor.ServiceAPI.center.api.interceptor.annotation.Interceptor;
import com.neighbor.ServiceAPI.controller.service.MainService;
import com.neighbor.ServiceAPI.mobile.service.MobileApiService;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 1. Function : Descript section.<p>
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
public class HomeController {
	private final Log logger = LogFactory.getLog(HomeController.class);
	
	@Resource(name="mainService")
	private MainService mainService;

	@Resource(name="loginService")
	private LoginService loginService;
	
	@Autowired
	private MobileApiService mobileApiService;
	
	//메인페이지
	@Interceptor("PageCallReg")
	@RequestMapping(value="/view/index")
	public ModelAndView viewMain(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/main");
	}
	
	//로그인
	@RequestMapping(value="/view/login")
	public ModelAndView viewLogin(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("tscp/login/login");
//		mv.addObject("cnterInfo", mobileApiService.getCnterInfo("NAT-1-001"));
		return mv;
	}
	
	//회원가입(이용약관동의)
	@RequestMapping(value="/view/memberShipAgree")
	public ModelAndView viewMemberShipAgree(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/login/memberShipAgree");
	}
	
	//회원가입(기존센터가입여부확인)
	@RequestMapping(value="/view/memberShipCheck")
	public ModelAndView viewMemberShipCheck(HttpServletRequest request, HttpServletResponse response, @RequestParam("cnterCd") String cnterCd) {
		ModelAndView view = new ModelAndView("tscp/login/memberShipCheck");
		Map param = new HashMap();
		param.put("cnter_cd", cnterCd);

		Map result = null;
		try{
			result = mainService.selectMemberShipInfo(param);
		}catch (Exception e){
			logger.error(e);
		}
		String cn = "저장된 이용약관이 없습니다.";
		if (result != null && result.containsKey("CN")){
			cn = result.get("CN").toString();
		}
		view.addObject("CN",cn);
		return view;
	}
	
	//회원가입
	@RequestMapping(value="/view/memberShip")
	public ModelAndView viewMemberShip(HttpServletRequest request, HttpServletResponse response, @RequestParam("cnterCd") String cnterCd, Model model) {
		model.addAttribute("CNTER_CD", cnterCd);
		return new ModelAndView("tscp/login/memberShip");
	}
	
	//마이페이지
	@RequestMapping(value="/view/myPage")
	public ModelAndView viewMyPage(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/login/myPage");
	}
	
	//이용자 조회 및 관리
	@RequestMapping(value="/view/bassInfo/handicapMng")
	public ModelAndView viewHandicapMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/bassInfo/handicapMng/index");
	}
	
	//이동보조원 조회 및 관리
	/*@RequestMapping(value="/view/bassInfo/supporterMng")
	public ModelAndView viewSupporterMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/bassInfo/supporterMng/index");
	}*/
	
	//차량 조회 및 관리
	@RequestMapping(value="/view/bassInfo/carMng")
	public ModelAndView viewCarMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/bassInfo/carMng/index");
	}
	
	//운행차량 위치 관제
	/*@RequestMapping(value="/view/cntrl/carLocCntrl")
	public ModelAndView viewCarLocCntrl(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/cntrl/carLocCntrl/index");
	}*/
	
	//이동보조원 위치 관제
	/*@RequestMapping(value="/view/cntrl/supporterLocCntrl")
	public ModelAndView viewSupporterLocCntrl(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/cntrl/supporterLocCntrl/index");
	}*/
	
	//이용자 위치 관제
	/*@RequestMapping(value="/view/cntrl/handicapLocCntrl")
	public ModelAndView viewHandicapLocCntrl(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/cntrl/handicapLocCntrl/index");
	}*/
	
	//차량 예약
	/*@RequestMapping(value="/view/alloc/carReceipt")
	public ModelAndView viewCarReceipt(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/alloc/carReceipt/index");
	}*/
	
	//차량 배차 목록
	/*@RequestMapping(value="/view/alloc/carAlloc")
	public ModelAndView viewCarAlloc(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/alloc/carAlloc/index");
	}*/
	
	//이동보조원 예약
	/*@RequestMapping(value="/view/alloc/supporterResve")
	public ModelAndView viewSupporterResve(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/alloc/supporterResve/index");
	}*/
	
	//지원시설물관리
	/*@RequestMapping(value="/view/facility/supportFtyMng")
	public ModelAndView viewSupportFtyMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/facility/supportFtyMng/index");
	}*/
	
	//셔틀버스운행관리
	/*@RequestMapping(value="/view/facility/shuttleBusOpratMng")
	public ModelAndView viewShuttleBusOpratMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/facility/shuttleBusOpratMng/index");
	}*/
	
	//이용자 관리 18-01-05 hongbw
	@RequestMapping(value="/view/bassInfo/userMng")
	public ModelAndView viewMngrUserMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/bassInfo/userMng/index");
	}
		
	//메뉴 관리
	@RequestMapping(value="/view/mngr/menuMng")
	public ModelAndView viewMenuMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/menuMng/index");
	}
	
	//정보컨텐츠 관리
	@RequestMapping(value="/view/mngr/contentsMng")
	public ModelAndView viewContentsMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/contentsMng/index");
	}
	
	//설문조사 관리
	@RequestMapping(value="/view/mngr/qestnarMng")
	public ModelAndView viewQestnarMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/qestnarMng/index");
	}
	
	//관련사이트 관리
	@RequestMapping(value="/view/mngr/siteMng")
	public ModelAndView viewSiteMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/siteMng/index");
	}

	// 기준설정 관리 ( 배차관련 설정 정보 )
	@RequestMapping(value="/view/mngr/stndSettingMng/allocateCarsSetting")
	public ModelAndView viewAllocateCarsSetting(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/stndSettingMng/allocateCarsSetting");
	}
		
	// 기준설정 관리 ( 센터이용 가능 장애등급 설정 정보 )
	@RequestMapping(value="/view/mngr/stndSettingMng/rankOfBarriersSetting")
	public ModelAndView viewRankOfBarriersSetting(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/stndSettingMng/rankOfBarriersSetting");
	}
	
	// 기준설정 관리 ( 이용요금 설정 정보 )
	@RequestMapping(value="/view/mngr/stndSettingMng/chargeForUsingSetting")
	public ModelAndView viewChargeForUsingSetting(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/stndSettingMng/chargeForUsingSetting");
	}
	
	// 기준설정 관리 ( 예약 가능시간 기준 )
	@RequestMapping(value="/view/mngr/stndSettingMng/bookAbleSetting")
	public ModelAndView viewBookAbleSetting(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/stndSettingMng/bookAbleSetting");
	}
	
	// 기준설정 관리 ( 회원약관정보 )
	@RequestMapping(value="/view/mngr/stndSettingMng/memberStipulationSetting")
	public ModelAndView viewMemberStipulationSetting(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/stndSettingMng/memberStipulationSetting");
	}
	
	// 기준설정 관리 ( 차량정보공유 )
	@RequestMapping(value="/view/mngr/stndSettingMng/carInfoShareSetting")
	public ModelAndView viewCarInfoShareSetting(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/stndSettingMng/carInfoShareSetting");
	}
	
	// 정산 관리 ( 차량정보공유 )
	@RequestMapping(value="/view/mngr/statHistoryMng")
	public ModelAndView viewStatHistoryMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/statHistoryMng/index");
	}

	// SMS발송이력관리 관리 ( 차량정보공유 )
	@RequestMapping(value="/view/mngr/smsHistoryMng")
	public ModelAndView viewSmsHistoryMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/smsHistoryMng/index");
	}
	
	// 긴급호출이력 관리 ( 차량정보공유 )
	@RequestMapping(value="/view/mngr/emgHistoryMng")
	public ModelAndView viewEmgHistoryMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/mngr/emgHistoryMng/index");
	}
	
	
	//운행(이용)현황
	@RequestMapping(value="/view/stats/carLocUseStats")
	public ModelAndView viewCarLocUseStats(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/stats/carLocUseStats/index");
	}
	
	//배차처리현황
	@RequestMapping(value="/view/stats/carAllocStats")
	public ModelAndView viewCarAllocStats(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/stats/carAllocStats/index");
	}
	
	//대기시간
	@RequestMapping(value="/view/stats/waitTimeStats")
	public ModelAndView viewWaitTimeStats(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/stats/waitTimeStats/index");
	}
	
	//사고처리
	@RequestMapping(value="/view/stats/acdntStats")
	public ModelAndView viewAcdntStats(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/stats/acdntStats/index");
	}
	
	//사용자 페이지
	@RequestMapping(value="/view/userSite")
	public ModelAndView viewQA(HttpServletRequest request, HttpServletResponse response, @RequestParam("gubn") String gubn, Model model) throws Exception {
		String tempUrl = "";
		String pageTitle = "";		
		
		HttpSession httpSession = request.getSession();
		
		Map<String, String> mapParameter = new HashMap<String,String>();
		mapParameter.put("gubn", gubn);
		
		String cnter_cd = (String) httpSession.getAttribute("cnter_cd");
		if(cnter_cd == null || cnter_cd.equals("") || cnter_cd.equals("null")) {
			cnter_cd = "NAT-1-001"; //이동약자(사용자) 메뉴구성
		}
		mapParameter.put("CNTER_CD", cnter_cd);
			
		Map contentsInfo = mainService.selectContentsInfo(mapParameter);
		
		if(contentsInfo != null) {
			//if(contentsInfo.get("MENU_ID").equals(gubn) ){
			if(contentsInfo.get("MENU_ID").equals("useInfo201") ){				
				tempUrl = "tscp/userSiteTemplate/introduction/carUse";
				pageTitle = "안내자료";
				
			}else if(contentsInfo.get("MENU_ID").equals("useInfo202") ){				
				tempUrl = "tscp/userSiteTemplate/introduction/carUse";
				pageTitle = "차량현황";
			}else{
				if(contentsInfo.get("CNTNTS_TYPE1_CD").equals("10")) { //소개
					tempUrl = "tscp/userSiteTemplate/introduction/form1";
				} else { //게시판
					if(contentsInfo.get("CNTNTS_TYPE2_CD").equals("40")) { //Q&A 
						tempUrl = "tscp/userSiteTemplate/board/form1";
					} else if(contentsInfo.get("CNTNTS_TYPE2_CD").equals("50")) { //FAQ
						tempUrl = "tscp/userSiteTemplate/board/form2";
					} else if(contentsInfo.get("CNTNTS_TYPE2_CD").equals("60")) { //공지사항
						if(contentsInfo.get("CNTNTS_CLS_CD").equals("50")) { //갤러리
							tempUrl = "tscp/userSiteTemplate/board/form4";
						} else {
							tempUrl = "tscp/userSiteTemplate/board/form3";
						}
					}
				}
			}
			model.addAttribute("pageTitle", pageTitle);
			model.addAttribute("contentsInfo", contentsInfo);
		} else {
			tempUrl = "tscp/userSiteTemplate/etc/form99";
			model.addAttribute("msg", "등록된 정보컨텐츠 정보가 없습니다.");
		}
		
		return new ModelAndView(tempUrl);
	}
	
	//접수 현황
	@RequestMapping(value="/view/carUseResve/rceptSttus")
	public ModelAndView viewRceptSttus(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/carUseResve/rceptSttus/index");
	}

	//접수 현황
	@RequestMapping(value="/view/reservation")
	public ModelAndView viewReservation(HttpServletRequest request,
										HttpServletResponse response,
										@RequestParam("tel_no") String tel_no,
										@RequestParam("resve_no") String resve_no,
										@RequestParam("run_type") String run_type,
										@RequestParam("boarding_time") String boarding_time,
										@RequestParam("require_time") String require_time) {
		ModelAndView view = new ModelAndView("tscp/carUseResve/rceptSttus/index");

		Map userInfo = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		Map param = request.getParameterMap();
		String ip = request.getRemoteAddr();
		String url = request.getRequestURL().toString();

		String decryptTel_no = EncryptUtil.decryptDataForKobus(ComUtil.KEY_KOBUS,tel_no);

		try{
			String paramString = mapper.writer().writeValueAsString(param);
			Map kobusInfo = new HashMap();
			kobusInfo.put("api_url",url);
			kobusInfo.put("req_param",paramString);
			kobusInfo.put("acc_ip",ip);
			// kobus api 로그 저장
			mainService.insertKobusApiLog(kobusInfo);

			userInfo = loginService.selectUserInfoByMBTLNUM(decryptTel_no);
		}catch (Exception e) {
			logger.error(e);
		}

		if (userInfo == null){
			view.addObject("STATUS","FAIL");
			return view;
		}

		request.getSession().setAttribute("sessionId", request.getSession().getId());
		request.getSession().setAttribute("user_id", userInfo.get("USER_ID"));
		request.getSession().setAttribute("name", userInfo.get("NAME"));
		request.getSession().setAttribute("mobile", userInfo.get("MOBILE"));

		// 회원상태코드 00:탈퇴, 10:예약, 20:대기, 30:탑승, 40:하차, 50:도보, 60:휠체어, 90:돌발
		request.getSession().setAttribute("user_stat_cd", userInfo.get("USER_STAT_CD"));

		// 회원구분코드 10:이동약자, 20:이동보조원, 30:운전자, 40:역무원, 50:상담원, 80:국가/광역이동지원센터운영자(운영자), 90:관리자(운영자), 91시스템관리자
		request.getSession().setAttribute("user_gbn_cd", userInfo.get("USER_GBN_CD"));
		request.getSession().setAttribute("cnter_cd", userInfo.get("CNTER_CD"));
		request.getSession().setAttribute("user_gbn_nm", userInfo.get("MBER_SE_NM"));
		request.getSession().setAttribute("cnter_nm", userInfo.get("CNTER_NM"));
		request.getSession().setAttribute("cnter_addr", userInfo.get("CNTER_ADDR"));
		request.getSession().setAttribute("cnter_zip", userInfo.get("CNTER_ZIP"));
		request.getSession().setAttribute("cnter_tel", userInfo.get("CNTER_TEL"));
		request.getSession().setAttribute("cnter_nm_en", userInfo.get("CNTER_NM_EN"));


		view.addObject("JOIN_TYPE", "KOBUS");
		view.addObject("STATUS","PASS");
		view.addObject("RESVE_NO", resve_no);
		view.addObject("RUN_TYPE", run_type);
		view.addObject("BOARDING_TIME", boarding_time);
		view.addObject("REQUIRE_TIME", require_time);

		return view;
	}

	
	//게시판관리
	@RequestMapping(value="/view/mngr/boardMng")
	public ModelAndView viewBoardMng(HttpServletRequest request, HttpServletResponse response, @RequestParam("CNTNTS_NO") String CNTNTS_NO, Model model) {
		model.addAttribute("CNTNTS_NO", CNTNTS_NO);
		return new ModelAndView("tscp/mngr/boardMng/index");
	}
	
	//공통코드 관리
	@RequestMapping(value="/view/system/commonCDMng")
	public ModelAndView viewCommonCDMng(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/system/commonCDMng/index");
	}
	
	//STC 관리
	@RequestMapping(value="/view/system/stcMng")
	public ModelAndView viewStcMn(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("tscp/system/stcMng/index");
	}
	
	//아이디 비밀번호 찾기
	@RequestMapping(value="/view/login/find")
	public ModelAndView viewFindUser(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("tscp/login/find");
		return mv;
	}
	
}