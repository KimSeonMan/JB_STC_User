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

import com.neighbor.ServiceAPI.mobile.service.CustomerApiService;

@Controller
@RequestMapping(value="/customer")
public class CustomerApiController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerApiController.class);
	
	@Autowired
	private CustomerApiService customerApiService;
	
	/**
	 * 배차가능 차량목록 조회
	 * @param acceptHeader
	 * @param version
	 * @param mber_id
	 * @param resve_dt
	 * @param vhcle_ty_cd
	 * @param drver_sttus_cd
	 * @param standard_radius
	 * @param requeest
	 * @return
	 */
	@RequestMapping(value="/{version}/service/allocation/cars"
			, method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getAllocationCars(@RequestHeader("Accept") final String acceptHeader, @PathVariable("version") final String version,
		@RequestParam(value="mber_id", required=true) final String mber_id,
		@RequestParam(value="resve_dt", required=true) final String resve_dt,
		@RequestParam(value="vhcle_ty_cd", required=false) final String vhcle_ty_cd,
		@RequestParam(value="drver_sttus_cd", required=false) final String drver_sttus_cd,
		@RequestParam(value="standard_radius", required=true) final String standard_radius,
		final HttpServletRequest requeest){

		ModelMap result = customerApiService.getAllocationCars(mber_id, resve_dt, vhcle_ty_cd, drver_sttus_cd, standard_radius);
		
		return result;
	}
	
	/**
	 * 배차 요청
	 * @param acceptHeader
	 * @param version
	 * @param mber_id
	 * @param resve_dt
	 * @param vhcle_no
	 * @param requeest
	 * @return
	 */
	@RequestMapping(value="/{version}/service/allocation/manual"
			, method = RequestMethod.POST)
	@ResponseBody
	public ModelMap getAllocationManual(@PathVariable("version") final String version,
			@ModelAttribute final String mber_id,
			@ModelAttribute final String resve_dt,
			@ModelAttribute final String vhcle_no,
			final HttpServletRequest requeest) {
		
		ModelMap result = customerApiService.getAllocationManual(mber_id, resve_dt, vhcle_no);
		return result;
	}

	/**
	 * 센터별 기준정보 조회
	 * @param acceptHeader
	 * @param version
	 * @param cnter_cd
	 * @param requeest
	 * @return
	 */
	@RequestMapping(value="/{version}/service/center/reference"
			, method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getCenterReference(@RequestHeader("Accept") final String acceptHeader, @PathVariable("version") final String version,
		@RequestParam final String cnter_cd,
		final HttpServletRequest requeest) {
		
		ModelMap result = customerApiService.getCenterReference(cnter_cd);
		
		return result;
	}
	
	
	/**
	 * 위치관제 - 운전자 목록 조회
	 * @param acceptHeader
	 * @param version
	 * @param cnter_cd
	 * @param requeest
	 * @return
	 */
	@RequestMapping(value="/{version}/service/positionControl/drivers"
			, method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getPositionDrivers(@RequestHeader("Accept") final String acceptHeader, @PathVariable("version") final String version,
		@RequestParam(value="cnter_cd", required=true) final String cnter_cd,
		@RequestParam(value="drver_sttus_cd", required=true) final String drver_sttus_cd,
		@RequestParam(value="drver_nm", required=false) final String drver_nm,
		@RequestParam(value="start_idx", required=true) final String start_idx,
		@RequestParam(value="end_idx", required=true) final String end_idx,
		final HttpServletRequest request){
		
		logger.info("cnter_cd : {}", cnter_cd);
		
		ModelMap result = customerApiService.getPositionDrivers(cnter_cd, drver_sttus_cd, drver_nm, start_idx, end_idx);
		
		
		return result;
	}
	
	/**
	 * 지도 poi정보 조회
	 * @param acceptHeader
	 * @param version
	 * @param cnter_cd
	 * @param drver_sttus_cd
	 * @param drver_nm
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{version}/service/map/pois"
			, method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getMapPois(@RequestHeader("Accept") final String acceptHeader, @PathVariable("version") final String version,
		@RequestParam(value="cnter_cd", required=true) final String cnter_cd,
		@RequestParam(value="drver_sttus_cd", required=true) final String drver_sttus_cd,
		@RequestParam(value="drver_nm", required=false) final String drver_nm,
		final HttpServletRequest request) {
		
		ModelMap result = customerApiService.getMapPois(cnter_cd, drver_sttus_cd, drver_nm);
		
		return result;
	}
}
