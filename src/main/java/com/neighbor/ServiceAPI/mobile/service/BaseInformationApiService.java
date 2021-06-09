package com.neighbor.ServiceAPI.mobile.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.neighbor.ServiceAPI.mobile.dao.BaseInformationApiDao;
import com.neighbor.ServiceAPI.mobile.dao.MobileApiDao;
import com.neighbor.ServiceAPI.mobile.domain.VhcleInfo;
import com.neighbor.ServiceAPI.mobile.model.ResCode;
import com.neighbor.ServiceAPI.mobile.util.ComUtil;

@Service("baseInformationApiService")
public class BaseInformationApiService {
	
	@Autowired
	private BaseInformationApiDao baseInformationApiDao;
	
	@Autowired
	private MobileApiDao mobileApiDao;
	
	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(BaseInformationApiService.class);

	/**
	 * 차량 목록 조회
	 * @param resve_vhcle_at
	 * @param copertn_caralc_at
	 * @param vhcle_ty_cd
	 * @param drver_nm
	 * @param vhcle_no
	 * @param start_idx
	 * @param end_idx
	 * @return
	 */
	public ModelMap getCars(String resve_vhcle_at, String copertn_caralc_at, String vhcle_ty_cd, String drver_nm,
			String vhcle_no, String start_idx, String end_idx) {
		// TODO Auto-generated method stub
		ModelMap result = new ModelMap();
		String id = "API_018";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		ModelMap paramValue = new ModelMap();
		paramValue.put("resveVhcleAt", resve_vhcle_at);
		paramValue.put("copertnCarcleAt", copertn_caralc_at);
		paramValue.put("vhcleTyCd", vhcle_ty_cd);
		paramValue.put("drverNm", drver_nm);
		paramValue.put("vhcleNo", vhcle_no);
		paramValue.put("start_idx", start_idx);
		paramValue.put("end_idx", end_idx);
		List<VhcleInfo> resultList = baseInformationApiDao.getCar(paramValue);
		
		if(resultList.equals(null)||resultList == null){
			ModelMap resultText = new ModelMap();
			ArrayList<ModelMap> resultArrayList = new ArrayList<ModelMap>();
			result.put("err_cd", ResCode.NO_DATA.getCode());
			result.put("err_msg", ResCode.NO_DATA.toString());
			resultText.put("request_result", "Fail");
			resultArrayList.add(resultText);
			result.put("result", resultArrayList);
			return result;
		}
		result.put("err_cd", ResCode.SUCCESS.getCode());
		result.put("err_msg", ResCode.SUCCESS.toString());
		result.put("result", resultList);
		
		return result;
	}
	
	/**
	 * 차량 정보 상세 조회
	 * @param vhcle_no
	 * @return
	 */
	public ModelMap getCarDetail(String vhcle_no) {
		// TODO Auto-generated method stub
		logger.info("getCarDetail() service Start!");
		
		ModelMap result = new ModelMap();
		String id = "API_019";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		VhcleInfo resultList = baseInformationApiDao.getCarDetail(vhcle_no);
		
		if(resultList.equals(null)||resultList == null){
			ModelMap resultText = new ModelMap();
			ArrayList<ModelMap> resultArrayList = new ArrayList<ModelMap>();
			result.put("err_cd", ResCode.NO_DATA.getCode());
			result.put("err_msg", ResCode.NO_DATA.toString());
			resultText.put("request_result", "Fail");
			resultArrayList.add(resultText);
			result.put("result", resultArrayList);
			return result;
		}
		result.put("err_cd", ResCode.SUCCESS.getCode());
		result.put("err_msg", ResCode.SUCCESS.toString());
		result.put("result", resultList);
		
		return result;
	}
	
	/**
	 * 차량 정보 수정 및 삭제
	 * @param gubn
	 * @param vhcleInfo
	 * @return
	 */
	public ModelMap modifyCar(String gubn, VhcleInfo vhcleInfo) {
		// TODO Auto-generated method stub
		logger.info("modifyCar() service Start!"); 
		ModelMap result = new ModelMap();
		ModelMap resultText = new ModelMap();
		ArrayList<ModelMap> resultList = new ArrayList<ModelMap>();
		
		String id = "API_020";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		int resultValue = -1;
		
		if(gubn.equals("M") || gubn=="M"){		//수정
			VhcleInfo vhcle = mobileApiDao.getVhcleInfo(vhcleInfo.getVhcle_no());
			vhcle.setVhcleInfo(vhcleInfo);
			
			resultValue = baseInformationApiDao.updateCarInfo(vhcle);
		} else if (gubn.equals("D") || gubn=="D") {	//삭제
			resultValue = baseInformationApiDao.deleteCarInfo(vhcleInfo.getVhcle_no());
		}
		
		if(resultValue == 1){
			result.put("err_cd", ResCode.SUCCESS.getCode());
			result.put("err_msg", ResCode.SUCCESS.toString());
			resultText.put("request_result", "OK");
			resultList.add(resultText);
			result.put("result", resultList);
		} else {
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg", ResCode.FAIL.toString());
			resultText.put("request_result", "Fail");
			resultList.add(resultText);
			result.put("result", resultList);
		}
		
		return result;
	}
	
	/**
	 * 차량 정보 등록
	 * @param cnter_cd
	 * @param vhcleInfo
	 * @return
	 */
	public ModelMap addCar(String cnter_cd, VhcleInfo vhcleInfo) {
		// TODO Auto-generated method stub
		logger.info("addCar() service Start!"); 
		ModelMap result = new ModelMap();
		ModelMap resultText = new ModelMap();
		ArrayList<ModelMap> resultList = new ArrayList<ModelMap>();
		
		int insertResult = 0;
		
		try{
			insertResult = baseInformationApiDao.addCar(vhcleInfo);
		} catch(Exception e){
			result.put("err_cd", e.hashCode());
			result.put("err_msg", e.getMessage());
			resultText.put("request_result", "Fail");
			resultList.add(resultText);
			result.put("result", resultList);
			return result;
		}
		
		String id = "API_021";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		if(insertResult == 1){
			result.put("err_cd", ResCode.SUCCESS.getCode());
			result.put("err_msg", ResCode.SUCCESS.toString());
			resultText.put("request_result", "OK");
			resultList.add(resultText);
			result.put("result", resultList);
		} else {
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg", ResCode.FAIL.toString());
			resultText.put("request_result", "Fail");
			resultList.add(resultText);
			result.put("result", resultList);
		}
		
		return result;
	}

}
