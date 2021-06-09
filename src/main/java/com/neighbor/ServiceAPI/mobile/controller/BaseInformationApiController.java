package com.neighbor.ServiceAPI.mobile.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neighbor.ServiceAPI.mobile.domain.VhcleInfo;
import com.neighbor.ServiceAPI.mobile.service.BaseInformationApiService;

@Controller
@RequestMapping(value="/baseInformation")
public class BaseInformationApiController {

	@Autowired
	private BaseInformationApiService baseInformationApiService;
	
	private static final Logger logger = LoggerFactory.getLogger(BaseInformationApiController.class);
	
	
	/**
	 * 차량목록 조회
	 * @param acceptHeader
	 * @param version
	 * @param resve_vhcle_at
	 * @param copertn_caralc_at
	 * @param vhcle_ty_cd
	 * @param drver_nm
	 * @param vhcle_no
	 * @param start_idx
	 * @param end_idx
	 * @param requeest
	 * @return
	 */
	@RequestMapping(value="/{version}/cars"
			, method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getCars(@RequestHeader("Accept") final String acceptHeader, @PathVariable("version") final String version,
			@RequestParam(value="resve_vhcle_at", required=false) final String resve_vhcle_at,
			@RequestParam(value="copertn_caralc_at", required=false) final String copertn_caralc_at,
			@RequestParam(value="vhcle_ty_cd", required=false) final String vhcle_ty_cd,
			@RequestParam(value="drver_nm", required=false) final String drver_nm,
			@RequestParam(value="vhcle_no", required=false) final String vhcle_no,
			@RequestParam(value="start_idx", required=true) final String start_idx,
			@RequestParam(value="end_idx", required=true) final String end_idx,
			final HttpServletRequest requeest){
		
		ModelMap result = baseInformationApiService.getCars(resve_vhcle_at, copertn_caralc_at, vhcle_ty_cd
				, drver_nm, vhcle_no, start_idx, end_idx);
		return result;
	}
	
	/**
	 * 차량 정보 상세 조회
	 * @param acceptHeader
	 * @param version
	 * @param vhcle_no
	 * @param requeest
	 * @return
	 */
	@RequestMapping(value = "/{version}/car/detail"
			, method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getCarDetail(@RequestHeader("Accept") final String acceptHeader, @PathVariable("version") final String version,
			@RequestParam(value="vhcle_no", required=true) final String vhcle_no,
			final HttpServletRequest requeest) {
		
		ModelMap result = baseInformationApiService.getCarDetail(vhcle_no);
		
		return result;
	}
	
	/**
	 * 차량 정보 수정 및 삭제
	 * @param acceptHeader
	 * @param version
	 * @param gubn
	 * @param vhcle_no
	 * @param copertn_caralc_at
	 * @param resve_vhcle_at
	 * @param modl_nm
	 * @param mxmm_brdng_nmpr
	 * @param yridnw
	 * @param vhcle_ty_cd
	 * @param makr
	 * @param vin
	 * @param rm
	 * @param requeest
	 * @return
	 */
	@RequestMapping(value = "/{version}/car/modify", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap carModify(@PathVariable("version") final String version,
			@ModelAttribute("gubn") final String gubn,
			@ModelAttribute("vhcle_no") final String vhcle_no,
			@ModelAttribute("copertn_caralc_at") final String copertn_caralc_at,
			@ModelAttribute("resve_vhcle_at") final String resve_vhcle_at,
			@ModelAttribute("modl_nm") final String modl_nm,
			@ModelAttribute("mxmm_brdng_nmpr") final String mxmm_brdng_nmpr,
			@ModelAttribute("yridnw") final String yridnw,
			@ModelAttribute("vhcle_ty_cd") final String vhcle_ty_cd,
			@ModelAttribute("makr") final String makr,
			@ModelAttribute("vin") final String vin,
			@ModelAttribute("rm") final String rm,
			final HttpServletRequest requeest) {
		
		VhcleInfo vhcleInfo = new VhcleInfo();
		vhcleInfo.setVhcle_no(vhcle_no);
		vhcleInfo.setCopertn_caralc_at(copertn_caralc_at);
		vhcleInfo.setResve_vhcle_at(resve_vhcle_at);
		vhcleInfo.setModl_nm(modl_nm);
		vhcleInfo.setMxmm_brdng_nmpr(mxmm_brdng_nmpr);
		vhcleInfo.setYridnw(yridnw);
		vhcleInfo.setVhcle_ty_cd(vhcle_ty_cd);
		vhcleInfo.setMakr(makr);
		vhcleInfo.setVin(vin);
		vhcleInfo.setRm(rm);
		ModelMap result = baseInformationApiService.modifyCar(gubn, vhcleInfo);
		
		return result;
	}
	
	
	/**
	 * 차량 정보 등록
	 */
	@RequestMapping(value="/{version}/car/add", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap addCar(@PathVariable("version") final String version,
			@ModelAttribute("cnter_cd") final String cnter_cd,
			@ModelAttribute("vhcle_no") final String vhcle_no,
			@ModelAttribute("copertn_caralc_at") final String copertn_caralc_at,
			@ModelAttribute("resve_vhcle_at") final String resve_vhcle_at,
			@ModelAttribute("modl_nm") final String modl_nm,
			@ModelAttribute("mxmm_brdng_nmpr") final String mxmm_brdng_nmpr,
			@ModelAttribute("yridnw") final String yridnw,
			@ModelAttribute("vhcle_ty_cd") final String vhcle_ty_cd,
			@ModelAttribute("makr") final String makr,
			@ModelAttribute("vin") final String vin,
			@ModelAttribute("rm") final String rm,
			final HttpServletRequest requeest) {
		
		VhcleInfo vhcleInfo = new VhcleInfo();
		vhcleInfo.setVhcle_no(vhcle_no);
		vhcleInfo.setCopertn_caralc_at(copertn_caralc_at);
		vhcleInfo.setResve_vhcle_at(resve_vhcle_at);
		vhcleInfo.setModl_nm(modl_nm);
		vhcleInfo.setMxmm_brdng_nmpr(mxmm_brdng_nmpr);
		vhcleInfo.setYridnw(yridnw);
		vhcleInfo.setVhcle_ty_cd(vhcle_ty_cd);
		vhcleInfo.setMakr(makr);
		vhcleInfo.setVin(vin);
		vhcleInfo.setRm(rm);
		
		ModelMap result = baseInformationApiService.addCar(cnter_cd, vhcleInfo);
		
		return result;
	}
}


