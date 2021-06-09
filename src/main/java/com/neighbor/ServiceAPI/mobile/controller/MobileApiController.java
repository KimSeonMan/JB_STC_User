package com.neighbor.ServiceAPI.mobile.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neighbor.ServiceAPI.mobile.domain.EmrgncyCallHist;
import com.neighbor.ServiceAPI.mobile.domain.RceptHist;
import com.neighbor.ServiceAPI.mobile.service.MobileApiService;

@Controller
@RequestMapping(value="/mobile")
public class MobileApiController {

	@Resource(name="mobileApiService")
	private MobileApiService mobileApiService;

	private static final Logger logger = LoggerFactory.getLogger(MobileApiController.class);
		
	
	/**
	 * 공통코드 조회
	 * @param acceptHeader
	 * @param version
	 * @param co_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/common/code",
			method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getCommonCode(@PathVariable("version") final String version,
			@RequestParam(value="co_id", required=false) final String co_id,
			final HttpServletRequest request){
		ModelMap result ;
		result = mobileApiService.getCommonCode(co_id);		
		return result;
	}
	
	/**
	 * 로그인
	 * @param acceptHeader
	 * @param version
	 * @param command
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/member/login", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap getLogin(@PathVariable("version") final String version,
			@ModelAttribute("mber_id") final String mber_id,
			@ModelAttribute("password") final String password,
			final HttpServletRequest request){
		
		ModelMap result = new ModelMap();
		try {
			result = mobileApiService.signIn(mber_id, URLDecoder.decode(password, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("UnsupportedEncodingException ",e);
			System.err.println(e);
		}
		return result;
	}
	
	
	/**
	 * 차량 예약 현황 조회
	 * @param acceptHeader
	 * @param version
	 * @param start_idx
	 * @param end_idx
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/booking/status", method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getBookList(@PathVariable("version") final String version,
			@RequestParam final String cnter_cd, @RequestParam final String mber_id,
			@RequestParam final String start_idx, @RequestParam final String end_idx,
			final HttpServletRequest request){
		
		ModelMap result;
		result = mobileApiService.getBookList(mber_id, cnter_cd, start_idx, end_idx);
		return result;
	}
	
	/**
	 * 차량 예약 요청 취소
	 * @param acceptHeader
	 * @param version
	 * @param command
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/booking/car/cancle", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap carCancle(@PathVariable("version") final String version,
			@ModelAttribute("mber_id") final String mber_id,
			@ModelAttribute("cnter_cd") final String cnter_cd,
			@ModelAttribute("resve_dt") final String resve_dt,
			final HttpServletRequest request){
		
		ModelMap result;
		
		result = mobileApiService.carCancle(cnter_cd, mber_id, resve_dt);
		//request_result 요청결과 OK/Failure
		return result;
	}
	
	
	/**
	 * 출발/목적지로 센터 기준정보 조회
	 * @param acceptHeader
	 * @param version
	 * @param start_lc_crdnt_x

	 * @param start_lc_crdnt_y
	 * @param arvl_lc_crdnt_x
	 * @param arvl_lc_crdnt_y
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/booking/car/reference", method = RequestMethod.GET)
	@ResponseBody
	public ModelMap carReference(@PathVariable("version") final String version,
			@RequestParam final String cnter_cd, @RequestParam final String mber_id,
			@RequestParam final String start_lc_crdnt_x, @RequestParam final String start_lc_crdnt_y,
			@RequestParam final String arvl_lc_crdnt_x, @RequestParam final String arvl_lc_crdnt_y,
			final HttpServletRequest request){
		
		ModelMap result;
		result = mobileApiService.carReference(cnter_cd, mber_id, start_lc_crdnt_x, start_lc_crdnt_y, arvl_lc_crdnt_x, arvl_lc_crdnt_y);
		
		return result;
	}
		
	/**
	 * 차량예약요청(당일 즉시)
	 * @param acceptHeader
	 * @param version
	 * @param command
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/booking/car/instant", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap carInstant(@PathVariable("version") final String version,
			@ModelAttribute("mber_id") final String mber_id,
			@ModelAttribute("cnter_cd") final String cnter_cd,
			@ModelAttribute("online_at") final String online_at,
			@ModelAttribute("pbtrnsp_trnsit_at") final String  pbtrnsp_trnsit_at,
			@ModelAttribute("roundtrip_at") final String roundtrip_at,
			@ModelAttribute("mvmn_purps_cd") final String mvmn_purps_cd,
			@ModelAttribute("wheelchair_se_cd") final String wheelchair_se_cd,
			@ModelAttribute("mvmn_ty_cd") final String mvmn_ty_cd,
			@ModelAttribute("brdng_nmpr") final String brdng_nmpr,
			@ModelAttribute("start_lc_crdnt_x") final String start_lc_crdnt_x,
			@ModelAttribute("start_lc_crdnt_y") final String start_lc_crdnt_y,
			@ModelAttribute("arvl_lc_crdnt_x") final String arvl_lc_crdnt_x,
			@ModelAttribute("arvl_lc_crdnt_y") final String arvl_lc_crdnt_y,
			@ModelAttribute("strtpnt_adres") final String strtpnt_adres,
			@ModelAttribute("aloc_adres") final String aloc_adres,
			@ModelAttribute("rm") final String rm,
			@ModelAttribute("chrg_cnter_cd") final String chrg_cnter_cd,
			final HttpServletRequest request){

		ModelMap result;
		RceptHist rh = new RceptHist();
		rh.setMber_id(mber_id);
		rh.setCnter_cd(cnter_cd);
		rh.setOnline_at(online_at);
		rh.setPbtrnsp_trnsit_at(pbtrnsp_trnsit_at);
		rh.setRoundtrip_at(roundtrip_at);
		rh.setMvmn_purps_cd(mvmn_purps_cd);
		rh.setWheelchair_se_cd(wheelchair_se_cd);
		rh.setMvmn_ty_cd(mvmn_ty_cd);
		rh.setBrdng_nmpr(brdng_nmpr);
		rh.setStart_lc_crdnt_x(start_lc_crdnt_x);
		rh.setStart_lc_crdnt_y(start_lc_crdnt_y);
		rh.setArvl_lc_crdnt_x(arvl_lc_crdnt_x);
		rh.setArvl_lc_crdnt_y(arvl_lc_crdnt_y);
		try {
			rh.setStrtpnt_adres(URLDecoder.decode(strtpnt_adres, "UTF-8"));
			rh.setAloc_adres(URLDecoder.decode(aloc_adres, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("UnsupportedEncodingException ",e);
			System.err.println(e);
		}
		
		rh.setChrg_cnter_cd(chrg_cnter_cd);
		if(!rm.isEmpty()){
			rh.setRm(rm);
		}
		rh.setPbtrnsp_trnsit_at("N");
		
		result = mobileApiService.carInstant(rh);
		
		return result;
	}
	
	/**
	 * 차량예약요청(사전예약)
	 * @param acceptHeader
	 * @param version
	 * @param command
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/booking/car/advance", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap carAdvance(@PathVariable("version") final String version,
			@ModelAttribute("mber_id") final String mber_id,
			@ModelAttribute("cnter_cd") final String cnter_cd,
			@ModelAttribute("resve_dt") final String resve_dt,
			@ModelAttribute("online_at") final String online_at,
			@ModelAttribute("pbtrnsp_trnsit_at") final String  pbtrnsp_trnsit_at,
			@ModelAttribute("roundtrip_at") final String roundtrip_at,
			@ModelAttribute("mvmn_purps_cd") final String mvmn_purps_cd,
			@ModelAttribute("wheelchair_se_cd") final String wheelchair_se_cd,
			@ModelAttribute("mvmn_ty_cd") final String mvmn_ty_cd,
			@ModelAttribute("brdng_nmpr") final String brdng_nmpr,
			@ModelAttribute("start_lc_crdnt_x") final String start_lc_crdnt_x,
			@ModelAttribute("start_lc_crdnt_y") final String start_lc_crdnt_y,
			@ModelAttribute("arvl_lc_crdnt_x") final String arvl_lc_crdnt_x,
			@ModelAttribute("arvl_lc_crdnt_y") final String arvl_lc_crdnt_y,
			@ModelAttribute("strtpnt_adres") final String strtpnt_adres,
			@ModelAttribute("aloc_adres") final String aloc_adres,
			@ModelAttribute("rm") final String rm,
			@ModelAttribute("chrg_cnter_cd") final String chrg_cnter_cd,
			final HttpServletRequest request){
		
		ModelMap result;
		RceptHist rh = new RceptHist();
		rh.setMber_id(mber_id);
		rh.setCnter_cd(cnter_cd);
		rh.setResve_dt(resve_dt);
		rh.setOnline_at(online_at);
		rh.setPbtrnsp_trnsit_at(pbtrnsp_trnsit_at);
		rh.setRoundtrip_at(roundtrip_at);
		rh.setMvmn_purps_cd(mvmn_purps_cd);
		rh.setWheelchair_se_cd(wheelchair_se_cd);
		rh.setMvmn_ty_cd(mvmn_ty_cd);
		rh.setBrdng_nmpr(brdng_nmpr);
		rh.setStart_lc_crdnt_x(start_lc_crdnt_x);
		rh.setStart_lc_crdnt_y(start_lc_crdnt_y);
		rh.setArvl_lc_crdnt_x(arvl_lc_crdnt_x);
		rh.setArvl_lc_crdnt_y(arvl_lc_crdnt_y);
		try {
			rh.setStrtpnt_adres(URLDecoder.decode(strtpnt_adres, "UTF-8"));
			rh.setAloc_adres(URLDecoder.decode(aloc_adres, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("UnsupportedEncodingException ",e);
			System.err.println(e);
		}
		rh.setChrg_cnter_cd(chrg_cnter_cd);
		if(!rm.isEmpty()){
			rh.setRm(rm);
		}
		rh.setPbtrnsp_trnsit_at("N");
		
		result = mobileApiService.carAdvance(rh);
		//request_result 요청결과 OK/Failure
		return result;
	}
	
	
	/**
	 * 지난 이용 내역
	 * @param acceptHeader
	 * @param version
	 * @param start_lc_crdnt_x
	 * @param start_lc_crdnt_y
	 * @param arvl_lc_crdnt_x
	 * @param arvl_lc_crdnt_y
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/booking/history", method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getBookHistory(@PathVariable("version") final String version,
			@RequestParam final String cnter_cd, @RequestParam final String mber_id,
			@RequestParam final String year_month, @RequestParam final String start_idx,
			@RequestParam final String end_idx,
			final HttpServletRequest request){
		
		ModelMap result ;
		result = mobileApiService.getBookHistory(cnter_cd, mber_id, year_month, start_idx, end_idx);
		
		return result;
	}
	
	
	/**
	 * 운전자 차량 번호 등록
	 * @param acceptHeader
	 * @param version
	 * @param command
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/driver/vhcleNo", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap driverVhcleNo(@PathVariable("version") final String version,
			@ModelAttribute("vhcle_no") final String vhcle_no,
			@ModelAttribute("cnter_cd") final String cnter_cd,
			@ModelAttribute("mber_id") final String mber_id,
			final HttpServletRequest request){
		
		ModelMap result = new ModelMap();
		
		try {
			result = mobileApiService.updateDriverVhcleNo(cnter_cd, mber_id, URLDecoder.decode(vhcle_no, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("UnsupportedEncodingException ",e);
			System.err.println(e);
		}
		
		//request_result 요청결과 OK/Failure
		return result;
	}
	
	
	/**
	 * 금일 배차 현황
	 * @param acceptHeader
	 * @param version
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/driver/caralc/status", method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getDriverCaraleStatus(@PathVariable("version") final String version,
			@RequestParam final String cnter_cd, @RequestParam final String mber_id,
			@RequestParam final String vhcle_no,
			final HttpServletRequest request){
		
		ModelMap result;
		result = mobileApiService.getDriverCaraleStatus(mber_id, cnter_cd, vhcle_no);
		return result;
	}
	
	
	/**
	 * 운전자 상태정보 변경
	 * @param acceptHeader
	 * @param version
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/driver/sttusInfo", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap updateDriverStatus(@PathVariable("version") final String version,
			@ModelAttribute("mber_id") final String mber_id,
			@ModelAttribute("cnter_cd") final String cnter_cd,
			@ModelAttribute("drver_sttus_cd") final String drver_sttus_cd,
			@ModelAttribute("mvmn_dstnc") final String mvmn_dstnc,
			@ModelAttribute("cychg") final String cychg,
			@ModelAttribute("resve_dt") final String resve_dt,
			final HttpServletRequest request){
		
		ModelMap result;
		result = mobileApiService.updateDriverStatus(cnter_cd, mber_id, drver_sttus_cd, mvmn_dstnc, cychg, resve_dt);
		
		return result;
	}
	
	
	/**
	 * 위치정보 수집
	 * @param acceptHeader
	 * @param version
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/location", method = RequestMethod.POST)
	@ResponseBody
	public void updateLocation(@PathVariable("version") final String version,
			@ModelAttribute("mber_id") final String mber_id,
			@ModelAttribute("cnter_cd") final String cnter_cd,
			@ModelAttribute("colct_dt") final String colct_dt,
			@ModelAttribute("lc_crdnt_x") final String lc_crdnt_x,
			@ModelAttribute("lc_crdnt_y") final String lc_crdnt_y,
			@ModelAttribute("gps_ennc") final String gps_ennc,
			@ModelAttribute("mvmn_drc") final String mvmn_drc,
			@ModelAttribute("ve") final String ve,
			@ModelAttribute("wheelchair_fixing_sttus_cd") final String wheelchair_fixing_sttus_cd,
			final HttpServletRequest request){
		
		mobileApiService.updateLocation(cnter_cd, mber_id, colct_dt, lc_crdnt_x, lc_crdnt_y, gps_ennc, mvmn_drc, ve, wheelchair_fixing_sttus_cd);
	}
	
	/**
	 * 긴급호출
	 * @param acceptHeader
	 * @param version
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/emrgncyCall", method = RequestMethod.POST)
	@ResponseBody
	public void emrgncyCall(@PathVariable("version") final String version,
			@ModelAttribute("mber_id") final String mber_id,
			@ModelAttribute("cnter_cd") final String cnter_cd,
			@ModelAttribute("colct_dt") final String colct_dt,
			@ModelAttribute("lc_crdnt_x") final String lc_crdnt_x,
			@ModelAttribute("lc_crdnt_y") final String lc_crdnt_y,
			final HttpServletRequest request){
		
		EmrgncyCallHist emrgncyCallHist = new EmrgncyCallHist();
		emrgncyCallHist.setCnter_cd(cnter_cd);
		emrgncyCallHist.setMber_id(mber_id);
		emrgncyCallHist.setColct_dt(colct_dt);
		emrgncyCallHist.setLc_crdnt_x(lc_crdnt_x);
		emrgncyCallHist.setLc_crdnt_y(lc_crdnt_y);
		emrgncyCallHist.setCnfirm_at("N");
		
		mobileApiService.emrgncyCall(emrgncyCallHist);
	}
	
	/**
	 * 기존 출발/목적지 목록 조회
	 * @param acceptHeader
	 * @param version
	 * @param cnter_cd
	 * @param mber_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/booking/point", method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getBookPoint(@PathVariable("version") final String version,
			@RequestParam final String cnter_cd, @RequestParam final String mber_id,
			final HttpServletRequest request){
			
		ModelMap result ;
		result = mobileApiService.getBookPoint(cnter_cd, mber_id);
		
		return result;
	}
			
}
