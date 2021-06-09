package com.neighbor.ServiceAPI.mobile.Counselor.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.neighbor.ServiceAPI.mobile.Counselor.Command.CarDispStatusCommand;
import com.neighbor.ServiceAPI.mobile.Counselor.DataSet.CarDispStatusDataSet;
import com.neighbor.ServiceAPI.mobile.Counselor.DataSet.MberListDataSet;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CarDispDetailDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CaralcSetupInfoDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Service.CarDispStatusService;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.EmrgncyDomain;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Service.CommonService;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Service.LocInfoService;
import com.neighbor.ServiceAPI.mobile.domain.CaralcHist;
import com.neighbor.ServiceAPI.mobile.util.ComUtil;

@Controller
public class CarDispStatusController {

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CarDispStatusService carDispStatusService;
	
	@Autowired
	private LocInfoService locInfoService;
	
	private static final Logger logger = LoggerFactory.getLogger(CarDispStatusController.class);
	
	@RequestMapping("/view/counselor/carDispStatus.do")
	public final ModelAndView carDispStatus(@RequestHeader("Accept") final String accepHeader,
			@ModelAttribute("command") final CarDispStatusCommand command,
			HttpSession session,
			final HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		logger.info("session : {}", session.getAttribute("cnter_cd"));
		logger.info("command : {}", command.toString());
		CarDispStatusDataSet ds = carDispStatusService.selectRceptList(command);
		EmrgncyDomain emrgncy = locInfoService.selectEmrgncy();
		
		mav.setViewName("tscp/counselor/carDispStatus");
		mav.addObject("dataSet", ds);
		mav.addObject("emrgncy", emrgncy);
		return mav;
	}
	
	@RequestMapping(value="/view/counselor/carDispUpdate.do", method=RequestMethod.GET)
	public final ModelAndView carDispUpdat(@RequestHeader("Accept") final String acceptHeader, 
			@RequestParam("mber_id") String mber_id,
			@RequestParam("cnter_cd") String cnter_cd,
			@RequestParam("caralcSttusCd") String caralcSttusCd,
			@RequestParam("resve_date") String resve_date,
			@RequestParam("resve_time") String resve_time,
			@RequestParam("resve_min") String resve_min,
			@RequestParam("strtpnt_adres") String strtpnt_adres,
			@RequestParam("start_lc_crdnt_x") String start_lc_crdnt_x,
			@RequestParam("start_lc_crdnt_y") String start_lc_crdnt_y,
			@RequestParam("aloc_adres") String aloc_adres,
			@RequestParam("arvl_lc_crdnt_x") String arvl_lc_crdnt_x,
			@RequestParam("arvl_lc_crdnt_y") String arvl_lc_crdnt_y,
			@RequestParam("roundtrip_at") String roundtrip_at,
			@RequestParam("mvmn_purps_cd") String mvmn_purps_cd,
			@RequestParam("wheelchair_se_cd") String wheelchair_se_cd,
			@RequestParam("brdng_nmpr") String brdng_nmpr,
			@RequestParam("rm") String rm,
			@RequestParam("rcept_se_cd") String rcept_se_cd,
			@RequestParam("resve_dt") String resve_dt,
			HttpSession session
			, final HttpServletRequest request){
		
		CarDispDetailDomain detailDomain = new CarDispDetailDomain();
		detailDomain.setResve_dt(resve_dt);
		detailDomain.setMber_id(mber_id);
		detailDomain.setCnter_cd(cnter_cd);
		detailDomain.setRcept_se_cd(rcept_se_cd);
		detailDomain.setMvmn_purps_cd(mvmn_purps_cd);
		detailDomain.setRoundtrip_at(roundtrip_at);
		detailDomain.setStrtpnt_adres(strtpnt_adres);
		detailDomain.setStart_lc_crdnt_x(start_lc_crdnt_x);
		detailDomain.setStart_lc_crdnt_y(start_lc_crdnt_y);
		detailDomain.setAloc_adres(aloc_adres);
		detailDomain.setArvl_lc_crdnt_x(arvl_lc_crdnt_x);
		detailDomain.setArvl_lc_crdnt_y(arvl_lc_crdnt_y);
		detailDomain.setWheelchair_se_cd(wheelchair_se_cd);
		detailDomain.setBrdng_nmpr(brdng_nmpr);
		detailDomain.setRm(rm);
		int update = carDispStatusService.updateCarDispStatus(detailDomain);
		
		if(caralcSttusCd == "10"){		//예약 요청으로 수정하는 경우 
			
		}
		
		
		String msg = "";
		ModelAndView mav = new ModelAndView("jsonView");
		
		if(update == 1){
			msg = "예약이 정상적으로 수정되었습니다.";
		} else {
			msg = "오류가 발생하였습니다.";
		}
		mav.addObject("update", update);
		mav.addObject("msg", msg);
		mav.setViewName("jsonView");
		
		return mav;
	}
	
	@RequestMapping(value="/view/counselor/reservation.do", method=RequestMethod.GET)
	public ModelAndView reservation(
			@RequestParam("mber_id") String mber_id,
			@RequestParam("cnter_cd") String cnter_cd,
			@RequestParam("resve_date") String resve_date,
			@RequestParam("resve_time") String resve_time,
			@RequestParam("resve_min") String resve_min,
			@RequestParam("strtpnt_adres") String strtpnt_adres,
			@RequestParam("start_lc_crdnt_x") String start_lc_crdnt_x,
			@RequestParam("start_lc_crdnt_y") String start_lc_crdnt_y,
			@RequestParam("aloc_adres") String aloc_adres,
			@RequestParam("arvl_lc_crdnt_x") String arvl_lc_crdnt_x,
			@RequestParam("arvl_lc_crdnt_y") String arvl_lc_crdnt_y,
			@RequestParam("roundtrip_at") String roundtrip_at,
			@RequestParam("mvmn_purps_cd") String mvmn_purps_cd,
			@RequestParam("wheelchair_se_cd") String wheelchair_se_cd,
			@RequestParam("brdng_nmpr") String brdng_nmpr,
			@RequestParam("rm") String rm,
			@RequestParam("rcept_se_cd") String rcept_se_cd,			
			HttpSession session
			, final HttpServletRequest request
			){
		
		return null;
	}
	
	@RequestMapping(value="/view/counselor/getMberList.do", method=RequestMethod.GET)
	public ModelAndView getMberList(@RequestParam("cnter_cd") String cnter_cd,
			final HttpServletResponse response){
		MberListDataSet ds = carDispStatusService.selectMberList(cnter_cd);
		ModelAndView mav = new ModelAndView("jsonView");
//		mav.addObject("dataSet", ds);
		mav.addObject("list", ds.getMberlist());
		mav.setViewName("jsonView");
		return mav;
	}
	
	@RequestMapping(value="/view/counselor/carMatching.do", method=RequestMethod.GET)
	public ModelAndView carMatching(@RequestParam("mber_id") String mber_id, 
			@RequestParam("cnter_cd") String cnter_cd, @RequestParam("resve_dt") String resve_dt,
			@RequestParam("vhcle_no") String vhcle_no
			, final HttpServletResponse response){
		
		CaralcHist caralcHist = new CaralcHist();
		caralcHist.setMber_id(mber_id);
		caralcHist.setCnter_cd(cnter_cd);
		caralcHist.setResve_dt(resve_dt);
		CaralcHist findCaralcHist = carDispStatusService.selectCaralcHist(caralcHist);
		
		findCaralcHist.setVhcle_no(vhcle_no);
		findCaralcHist.setCaralc_sttus_cd("70");
		
		int insert = carDispStatusService.insertCaralcHist(findCaralcHist);
		
		
		String msg = "";
		ModelAndView mav = new ModelAndView("jsonView");
		
		if(insert == 1){
			msg = "배차가 정상적으로 되었습니다	.";
		} else {
			msg = "오류가 발생하였습니다.";
		}
		mav.addObject("update", insert);
		mav.addObject("msg", msg);
		mav.setViewName("jsonView");
		
		return mav;
	}
	
	@RequestMapping(value="/view/counselor/DetailData.do", method=RequestMethod.GET)
	public void detailData(@RequestParam("mber_id") String mber_id, @RequestParam("resve_dt") String resve_dt,
			@RequestParam("cnter_cd") String cnter_cd
			, HttpSession session
			, final HttpServletResponse response) throws IOException {
		CarDispStatusCommand command = new CarDispStatusCommand();
		command.setMber_id(mber_id);
		command.setResve_dt(resve_dt);
		command.setCnter_cd(cnter_cd);
		CarDispDetailDomain detailDomain = carDispStatusService.selectDetail(command);
		
		
		//접수상태 변경 업데이트 추가
		detailDomain.setCaralc_sttus_cd("30");
		int update = carDispStatusService.insertDetail(detailDomain);
		
		String DataJson = null;
		
		if(detailDomain != null){
			DataJson = "{\"caralc_sttus_cd\":\""+detailDomain.getCaralc_sttus_cd()
			+"\",\"cnter_cd\":\""+detailDomain.getCnter_cd()
			+"\",\"rcept_se_cd\":\""+detailDomain.getRcept_se_cd()
			+"\",\"resve_dt\":\""+detailDomain.getResve_dt()
			+"\",\"resve_date\":\""+detailDomain.getResve_date()
			+"\",\"resve_time\":\""+detailDomain.getResve_time().split(":")[0]
			+"\",\"resve_min\":\""+detailDomain.getResve_time().split(":")[1]
			+"\",\"strtpnt_adres\":\""+detailDomain.getStrtpnt_adres()
			+"\",\"start_lc_crdnt_x\":\""+detailDomain.getStart_lc_crdnt_x()
			+"\",\"start_lc_crdnt_y\":\""+detailDomain.getStart_lc_crdnt_y()
			+"\",\"aloc_adres\":\""+detailDomain.getAloc_adres()
			+"\",\"arvl_lc_crdnt_x\":\""+detailDomain.getArvl_lc_crdnt_x()
			+"\",\"arvl_lc_crdnt_y\":\""+detailDomain.getArvl_lc_crdnt_y()
			+"\",\"roundtrip_at\":\""+detailDomain.getRoundtrip_at()
			+"\",\"mvmn_purps_cd\":\""+detailDomain.getMvmn_purps_cd()
			+"\",\"wheelchair_se_cd\":\""+detailDomain.getWheelchair_se_cd()
			+"\",\"brdng_nmpr\":\""+detailDomain.getBrdng_nmpr()			
			+"\",\"rm\":\""+detailDomain.getRm()
			+"\",\"mber_id\":\""+mber_id
			+"\"}";
		}
		
		try{
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(DataJson);
		} catch(IOException e) {
			logger.error("IOException ",e);
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/view/counselor/caralcSetupInfo.do", method=RequestMethod.GET)
	public void caralcSetupInfo(@RequestParam("cnter_cd")String cnter_cd
			, final HttpServletResponse response){
		CaralcSetupInfoDomain domain = carDispStatusService.selectCaralcSetupInfo(cnter_cd);
		String dataJson = null;
		if(domain != null){
			dataJson = "{\"cnter_cd\":\""+domain.getCnter_cd()
			+"\",\"roundtrip_perm_at\":\""+domain.getRoundtrip_perm_at()
			+"\",\"aloc_reqre_time\":\""+domain.getAloc_reqre_time()
			+"\",\"vhcle_search_radius\":\""+domain.getVhcle_search_radius()
			+"\",\"beffat_resve_time_intrvl\":\""+domain.getBeffat_resve_time_intrvl()
			+"\",\"vhcle_mvmn_posbl_at\":\""+domain.getVhcle_mvmn_posbl_at()
			+"\",\"posbl_dstnc\":\""+domain.getPosbl_dstnc()
			+"\",\"wdr_sctn_othinst_mber_use_posbl_at\":\""+domain.getWdr_sctn_othinst_mber_use_posbl_at()
			+"\",\"whthrc_othinst_mber_use_posbl_at\":\""+domain.getWhthrc_othinst_mber_use_posbl_at()
			+"\"}";
		}
		try{
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(dataJson);
		} catch(IOException e) {
			logger.error("IOException ",e);
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/view/counselor/carDisp.do", method=RequestMethod.GET)
	public ModelAndView getCarList(@RequestParam("cnter_cd") String cnter_cd
			, @RequestParam("rcept_se_cd") String rcept_se_cd
			, @RequestParam("resve_dt")String resve_dt
			, @RequestParam("mber_id")String mber_id
			, final HttpServletResponse response) throws ParseException{
		
		//배차 가능한 차량 리스트 출력하는 메소드.
//		List<CarInfoDomain> result = carDispStatusService.selectCarList(cnter_cd, rcept_se_cd, resve_dt, mber_id);
		String msg = "";
		ModelAndView mav = new ModelAndView("jsonView");
		
		
//		if(result.size() < 1){
//			msg = "배차가능한 차량이 없습니다.";
//		}
//
//		mav.addObject("list", result);
//		mav.addObject("count", result.size());
		mav.addObject("msg", msg);
//		mav.addObject("cnter_cd", cnter_cd);
//		mav.addObject("resve_dt", resve_dt);
//		mav.addObject("mber_id", mber_id);
		mav.setViewName("jsonView");
		
		return mav;
	}
	
	@ModelAttribute("rceptSeCd")
	public LinkedHashMap<String, String> getRceptSeCd(){
		List<HashMap<String, Object>> list = commonService.getRceptSeCd();
		return ComUtil.convertToCodeMap(list);
	}
	
	@ModelAttribute("caralcSttusCd")
	public LinkedHashMap<String, String> getCaralcSttusCd(){
		List<HashMap<String, Object>> list = commonService.getCommonCd("CARALC_STTUS_CD");
		return ComUtil.convertToCodeMap(list);
	}
	
	@ModelAttribute("cnterCd")
	public LinkedHashMap<String, String> getCnterCd(){
		List<HashMap<String, Object>> list = commonService.getCnterCd();
		return ComUtil.convertToCodeMap(list);
	}
	
	@ModelAttribute("mvmn_purps_cd")
	public LinkedHashMap<String, String> getMvmnPurpsCd(){
		List<HashMap<String, Object>> list = commonService.getCommonCd("MVMN_PURPS_CD");
		return ComUtil.convertToCodeMap(list);
	}
	
	@ModelAttribute("wheelchair_se_cd")
	public LinkedHashMap<String, String> getWheelchairSeCd(){
		List<HashMap<String, Object>> list = commonService.getCommonCd("WHEELCHAIR_SE_CD");
		return ComUtil.convertToCodeMap(list);
	}
}
