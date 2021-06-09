package com.neighbor.ServiceAPI.mobile.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.neighbor.ServiceAPI.mobile.dao.CustomerApiDao;
import com.neighbor.ServiceAPI.mobile.domain.CaralcSetupInfo;
import com.neighbor.ServiceAPI.mobile.model.ResCode;
import com.neighbor.ServiceAPI.mobile.util.ComUtil;

@Service("customerApiService")
public class CustomerApiService {

	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(CustomerApiService.class);
	
	@Autowired
	private CustomerApiDao customerApiDao;

	/**
	 * 배차 가능 차량목록 조회
	 * @param mber_id
	 * @param resve_dt
	 * @param vhcle_ty_cd
	 * @param drver_sttus_cd
	 * @param standard_radius
	 * @return
	 */
	public ModelMap getAllocationCars(String mber_id, String resve_dt, String vhcle_ty_cd, String drver_sttus_cd,
			String standard_radius) {
		// TODO Auto-generated method stub
		/**
	  	rank	순위
		cnter_nm	소속센터
		vhcle_no	차량번호
		drver_sttus_cd	운전자상태
		vhcle_ty_cd	차량유형코드
		arrive_time	도착가능시간(분)
		allocation_yn	배차가능여부
	 */
		ModelMap result = new ModelMap();
		String id = "API_013";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		result.put("err_cd", ResCode.FAIL.getCode());
		result.put("err_msg", ResCode.FAIL.toString());
		return result;
	}
	
	/**
	 * 배차 요청
	 * @param mber_id
	 * @param resve_dt
	 * @param vhcle_no
	 * @return
	 */
	public ModelMap getAllocationManual(String mber_id, String resve_dt, String vhcle_no) {
		// TODO Auto-generated method stub
		/**
		request_result	요청 결과
		center	소속센터
		car_no	차량번호
		booking_time	예약일시
		destination	목적지
		 */
		ModelMap result = new ModelMap();
		String id = "API_014";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		result.put("err_cd", ResCode.FAIL.getCode());
		result.put("err_msg", ResCode.FAIL.toString());
		return result;
	}
	
	/**
	 * 센터별 기준정보 조회
	 * @param cnter_cd
	 * @return
	 */
	public ModelMap getCenterReference(String cnter_cd) {
		// TODO Auto-generated method stub
		ModelMap result = new ModelMap();
		String id = "API_015";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		ArrayList<CaralcSetupInfo> resultList = new ArrayList<CaralcSetupInfo>();
		CaralcSetupInfo caralcSetupInfo = customerApiDao.getCnterReference(cnter_cd);
		
		if(caralcSetupInfo == null){
			ModelMap resultText = new ModelMap();
			ArrayList<ModelMap> resultArrayList = new ArrayList<ModelMap>();
			
			result.put("err_cd", ResCode.NO_DATA.getCode());
			result.put("err_msg", ResCode.NO_DATA.toString());
			resultText.put("request_result", "센터정보 없음.");
			resultArrayList.add(resultText);
			result.put("result", resultArrayList);
			return result;
		}
		result.put("err_cd", ResCode.SUCCESS.getCode());
		result.put("err_msg", ResCode.SUCCESS.toString());
		resultList.add(caralcSetupInfo);
		result.put("result", resultList);
		return result;
	}

	/**
	 * 위치 관제 운전자 목록 조회
	 * @param cnter_cd
	 * @param drver_sttus_cd
	 * @param drver_nm
	 * @param start_idx
	 * @param end_idx
	 * @return
	 */
	public ModelMap getPositionDrivers(String cnter_cd, String drver_sttus_cd, String drver_nm, String start_idx, String end_idx) {
		// TODO Auto-generated method stub
		logger.info("getPositionDrivers() Service Start!!");
		
		ModelMap result = new ModelMap();
		String id = "API_016";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		List<Object> drverInfo = customerApiDao.getDrverInfo(cnter_cd, drver_sttus_cd, drver_nm, start_idx, end_idx);
		if(drverInfo.equals(null)||drverInfo == null){
			ModelMap resultText = new ModelMap();
			ArrayList<ModelMap> resultList = new ArrayList<ModelMap>();
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg", ResCode.FAIL.toString());
			resultText.put("request_result", "Fail");
			resultList.add(resultText);
			result.put("result", resultList);
			return result;
		}
		result.put("err_cd", ResCode.SUCCESS.getCode());
		result.put("err_msg", ResCode.SUCCESS.toString());
		result.put("result", drverInfo);
		return result;
	}

	/**
	 * 지도 poi 정보 조회
	 * @param cnter_cd
	 * @param drver_sttus_cd
	 * @param drver_nm
	 * @return
	 */
	public ModelMap getMapPois(String cnter_cd, String drver_sttus_cd, String drver_nm) {
		// TODO Auto-generated method stub
		ModelMap result = new ModelMap();
		String id = "API_017";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		logger.info("getMapPois() Service Start!!");
		List<Object> poiInfo = customerApiDao.getMapPois(cnter_cd, drver_sttus_cd, drver_nm);
		//리스트 값 중 핸드폰 번호 암호화 -> 복호화 진행 후 리턴.
		
		if(poiInfo.equals(null)||poiInfo == null){
			ModelMap resultText = new ModelMap();
			ArrayList<ModelMap> resultList = new ArrayList<ModelMap>();
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg", ResCode.FAIL.toString());
			resultText.put("request_result", "Fail");
			resultList.add(resultText);
			result.put("result", resultList);
			return result;
		}
		ModelMap resultList = new ModelMap();
		resultList.put("total_cnt", poiInfo.size());
		resultList.put("pois", poiInfo);
		
		result.put("err_cd", ResCode.SUCCESS.getCode());
		result.put("err_msg", ResCode.SUCCESS.toString());
		result.put("result", resultList);
		return result;
	}

}
