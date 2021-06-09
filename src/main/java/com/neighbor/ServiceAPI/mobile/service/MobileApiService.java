package com.neighbor.ServiceAPI.mobile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.neighbor.ServiceAPI.mobile.Component.GuideComponent;
import com.neighbor.ServiceAPI.mobile.dao.MobileApiDao;
import com.neighbor.ServiceAPI.mobile.domain.CaralcHist;
import com.neighbor.ServiceAPI.mobile.domain.CaralcSetupInfo;
import com.neighbor.ServiceAPI.mobile.domain.CmmnCdValueInfo;
import com.neighbor.ServiceAPI.mobile.domain.CnterInfo;
import com.neighbor.ServiceAPI.mobile.domain.DrverInfo;
import com.neighbor.ServiceAPI.mobile.domain.DrverLcHist;
import com.neighbor.ServiceAPI.mobile.domain.EmrgncyCallHist;
import com.neighbor.ServiceAPI.mobile.domain.HandicapLcHist;
import com.neighbor.ServiceAPI.mobile.domain.MberInfo;
import com.neighbor.ServiceAPI.mobile.domain.RceptHist;
import com.neighbor.ServiceAPI.mobile.domain.VhcleInfo;
import com.neighbor.ServiceAPI.mobile.domain.cnterResveUseTime;
import com.neighbor.ServiceAPI.mobile.model.ResCode;
import com.neighbor.ServiceAPI.mobile.util.ComUtil;
import com.neighbor.ServiceAPI.mobile.util.EncryptUtil;

@Service("mobileApiService")
public class MobileApiService {

	@Autowired
	private MobileApiDao mobileApiDao;
	
	@Autowired
	private GuideComponent guideComponent;
		
	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(MobileApiService.class);
	
	/**
	 * 공통코드 조회
	 * @param coId
	 * @return
	 */
	public ModelMap getCommonCode(final String coId){
		
		ModelMap result = new ModelMap();
		
		List<CmmnCdValueInfo> cmmnCdValueInfoList = mobileApiDao.getCommonCdValue(coId);
		String id = "API_001";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		if(cmmnCdValueInfoList == null){
			result.put("err_cd", ResCode.NO_DATA.getCode());
			result.put("err_msg", ResCode.NO_DATA.toString());
		} else {
			result.put("err_cd", ResCode.SUCCESS.getCode());
			result.put("err_msg", ResCode.SUCCESS.toString());
			result.put("result", cmmnCdValueInfoList);
		}
		
		return result;
	}
	
	/**
	 * 로그인
	 * @param command
	 * @return
	 */
	public ModelMap signIn(final String mber_id, final String password){
		ModelMap result = new ModelMap();
		ArrayList<ModelMap> list = new ArrayList<ModelMap>();
		ModelMap mber_result = new ModelMap();
		
		String id = "API_002";
		
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));		//추후 공통으로 변경
		
		MberInfo mber = new MberInfo();
		mber.setMber_id(mber_id);
		//암호화 되어서 들어온 비밀번호 복호화 후 
		// 쿼리로 단방향 암호화로 회원 찾아오기.
		mber.setPassword(EncryptUtil.decryptData(ComUtil.KEY, password));
		mber.setKey(ComUtil.KEY);

		MberInfo findMber = mobileApiDao.login(mber);
		if(findMber == null){
			MberInfo findId = mobileApiDao.getMberInfo(mber);
			
			if(findId == null){		//아이디가 없는 경우 
				mber_result.put("login_result", "Failure");
				mber_result.put("failure_code", "00");
			} else {
				mber_result.put("login_result", "Failure");
				mber_result.put("failure_code", "01");
			}
		} else {
			if(findMber.getMber_sttus_cd().equals("00")) { //회원탈퇴한 회원
				mber_result.put("login_result", "Failure");
				mber_result.put("failure_code", "02");
			} else if(findMber.getMber_sttus_cd().equals("01")) { //회원가입 승인전 회원
				mber_result.put("login_result", "Failure");
				mber_result.put("failure_code", "03");
			} else {
				mber_result.put("login_result", "OK");
				mber_result.put("cnter_cd", findMber.getCnter_cd());		//로그인한 회원의 소속 센터 코드
				CnterInfo cnterInfo = mobileApiDao.getCnterInfoByCnterCd(findMber.getCnter_cd());
				mber_result.put("telno", cnterInfo.getTelno());		//로그인 한 회원의 소속 센터 전화번호
				
				if(findMber.getMber_se_cd().equals("30")){	// 로그인 한 회원이 운전자 일 경우
					
					DrverInfo drver = mobileApiDao.getDriverInfo(findMber.getMber_id(), findMber.getCnter_cd());
					if(drver != null){	
						mber_result.put("vhcle_no", drver.getVhcle_no());
					
						VhcleInfo vhcle = mobileApiDao.getVhcleInfo(drver.getVhcle_no());
						mber_result.put("resve_vhcle_at", vhcle.getResve_vhcle_at());
						mber_result.put("copertn_caralc_at", vhcle.getCopertn_caralc_at());
					}
				}
			}
		}
		
		list.add(mber_result);
		result.put("result", list);
		return result;
	}
	
	/**
	 * 차량예약현황 조회
	 * @param mberId
	 * @param cnterCd
	 * @param start_idx
	 * @param end_idx
	 * @return
	 */
	public ModelMap getBookList(final String mber_id, final String cnter_cd, final String start_idx,
			final String end_idx){
		ModelMap result = new ModelMap();
		
		List<Object> rceptHist = mobileApiDao.getRceptHistList(mber_id, cnter_cd, start_idx, end_idx);

		String id = "API_003";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));

		if(rceptHist == null){
			ArrayList<String> resultText = new ArrayList<String>();
			result.put("err_cd", ResCode.NO_DATA.getCode());
			result.put("err_msg", ResCode.NO_DATA.toString());
			resultText.add(0, "NO_DATA");
			result.put("result", resultText);
		} else {
			result.put("err_cd", ResCode.SUCCESS.getCode());
			result.put("err_msg", ResCode.SUCCESS.toString());
			result.put("result", rceptHist);
		}
		return result;
	}
	
	/**
	 * 차량예약요청 취소
	 * @param cnterCd
	 * @param mberId
	 * @param resveDt
	 * @return
	 */
	public ModelMap carCancle(final String cnter_cd, final String mber_id, final String resveDt){
		
		ModelMap result = new ModelMap();
		ModelMap carCancleResult = new ModelMap();
		ArrayList<ModelMap> resultText = new ArrayList<ModelMap>();
		
		CaralcHist caralcHist = new CaralcHist();
		caralcHist.setCnter_cd(cnter_cd);
		caralcHist.setMber_id(mber_id);
		caralcHist.setResve_dt(resveDt);
		
		int	resultValue = mobileApiDao.carCancle(caralcHist);
		
		String id = "API_004";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		if(resultValue == 1){
			result.put("err_cd", ResCode.SUCCESS.getCode());
			result.put("err_msg", ResCode.SUCCESS.toString());
			carCancleResult.put("request_result", "OK");
		} else {
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg", ResCode.FAIL.toString());
			carCancleResult.put("request_result", "Failure");
		}
		resultText.add(carCancleResult);
		result.put("result", resultText);
		return result;
	}
	
	/**
	 * 출발/목적지로 센터 기준 정보 조회
	 * @param cnterCd
	 * @param mberId
	 * @param start_lc_crdnt_x
	 * @param start_lc_crdnt_y
	 * @param arvl_lc_crdnt_x
	 * @param arvl_lc_crdnt_y
	 * @return
	 */
	public ModelMap carReference(String cnter_cd, String mber_id, String start_lc_crdnt_x, String start_lc_crdnt_y, String arvl_lc_crdnt_x,
			String arvl_lc_crdnt_y) {
		// TODO Auto-generated method stub
		ModelMap result = new ModelMap();
		ModelMap resultData = new ModelMap();
		ArrayList<ModelMap> listData = new ArrayList<ModelMap>();
		
		String id = "API_005";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		MberInfo searchMber = new MberInfo();
		searchMber.setMber_id(mber_id);
		MberInfo mber = mobileApiDao.getMberInfo(searchMber);
		
		ModelMap startAddress = guideComponent.coord2address(start_lc_crdnt_x, start_lc_crdnt_y); //출발지의 시/도, 시/구/군 
		ModelMap endAddress = guideComponent.coord2address(arvl_lc_crdnt_x, arvl_lc_crdnt_y); //목적지의 시/도, 시/구/군 
		
		if(startAddress.isEmpty() || endAddress.isEmpty()){
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg","주소 오류");
			return result;
		}

		//이동유형코드  구분!
		// 출발지좌표로 주소 조회 -> 센터 조회 
		// 목적지 좌표로 주소 조회 -> 센터 조회
		// 1. 각각의 센터 코드가 동일 => 지역간 (30)
		// 2. 센터코드 불일치 -> 출발지 센터 코드의 상위 코드 센터 조회. 
		// 
		/*
			1.국가
			2.-1 광역
			2.-2 광역에 속하지 않은 지역
			3. 광역에 포함된 지역
			
			출발지 기준으로 센터 찾고
			상위코드가 광역이면 MOU가 있는것 
			상위코드에 광역이 MOU가 사용이면 광역간
			상위코드가 국가이면 지역간

		 	[센터코드]
			센터구분-레벨-레벨별 일련번호
			1. 센터구분
			국가 : NAT
			광역 : WDR
			지역 : ARE 
		 *
		 *
		 */
		
		String mvmn_ty_cd = "";			//이동구분 코드
		
		CnterInfo nCnter=mobileApiDao.getNatCnterCd();//국가이동지원센터
		
		String srtAddr1depth = startAddress.get("region_1depth_name").toString(); // 출발지 시/도
		String srtAddr2depth = startAddress.get("region_2depth_name").toString().split("\\s")[0]; // 출발지 시/구/군중 가장 대표		
		String endAddr1depth = endAddress.get("region_1depth_name").toString(); // 목적지 시/도
		String endAddr2depth = endAddress.get("region_2depth_name").toString().split("\\s")[0]; // 목적지 시/구/군중 가장 대표
		
		List<CnterInfo> startCnter = mobileApiDao.getCnterInfo(srtAddr1depth,srtAddr2depth); //출발지의 cnter_cd
		List<CnterInfo> endCnter = mobileApiDao.getCnterInfo(endAddr1depth,endAddr2depth); //목적지의 cnter_cd
		
		if(startCnter.size() < 1) {
			startCnter.add(nCnter);
		}

		if(endCnter.size() < 1) {
			endCnter.add(nCnter);
		}
		
		
		CaralcSetupInfo caralcSetupInfo = mobileApiDao.getCaralcSetupInfo(startCnter.get(0).getCnter_cd());

		if(caralcSetupInfo == null){
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg","센터 오류");
			return result;
		}	
		CnterInfo start_upper_cnter = mobileApiDao.getCnterInfoByCnterCd(startCnter.get(0).getUpper_cnter_cd());
		CnterInfo end_upper_cnter = mobileApiDao.getCnterInfoByCnterCd(endCnter.get(0).getUpper_cnter_cd());
		
		/**
	  	10	광역간
		20	지역간
		30	지역내
	 */
		String chrg_cnter_cd ="";
		
		if(startCnter.get(0).getCnter_cd().equals(endCnter.get(0).getCnter_cd())) {			//출발지 센터 코드와 목적지 센터 코드가 같다면 지역내 이동 (30)
			
			if(startCnter.get(0).getCnter_cd().equals("NAT-1-001")){			//국가 코드로 시작할 경우 광역
				mvmn_ty_cd = "10";
				chrg_cnter_cd = nCnter.getCnter_cd(); 
				resultData.put("chrg_cnter_cd", nCnter.getCnter_cd());
				
			} else {
				mvmn_ty_cd = "30";
				chrg_cnter_cd = startCnter.get(0).getCnter_cd();
				resultData.put("chrg_cnter_cd", startCnter.get(0).getCnter_cd());
			}
		} else {

			if(start_upper_cnter.getCnter_cd().equals(end_upper_cnter.getCnter_cd())){
			  	if(startCnter.get(0).getMrdn_at().equals("Y")){
			  		mvmn_ty_cd="20";
			  		chrg_cnter_cd = start_upper_cnter.getCnter_cd();
			  		resultData.put("chrg_cnter_cd", start_upper_cnter.getCnter_cd());
			  	}else {
			  		mvmn_ty_cd = "10";
			  		chrg_cnter_cd = nCnter.getCnter_cd();
			  		resultData.put("chrg_cnter_cd", nCnter.getCnter_cd());
			  	}
			} else{
			 	mvmn_ty_cd = "10";
			 	chrg_cnter_cd = nCnter.getCnter_cd();
			 	resultData.put("chrg_cnter_cd", nCnter.getCnter_cd());
			}
		}
		List<cnterResveUseTime> cnterResveUseTime = mobileApiDao.getCnterInfoUseTime(chrg_cnter_cd);// 해당 센터의 예약가능시간
		
		if(!mber.getCnter_cd().equals(startCnter.get(0).getCnter_cd())){	//회원 센터와 출발지 센터가 같지 않을때.
			if(mvmn_ty_cd == "10"){			//광역간 이동일 경우
				if(caralcSetupInfo.getWdr_sctn_othinst_mber_use_posbl_at().equals("N")){		//광역구간 타기관회원 이용가능여부 체크 N일 경우 이용 불가.
					result.put("err_cd", ResCode.FAIL.getCode());
					result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다.\n출발/목적지를 다시 선택해주세요.");
					return result;
				}
			} else if(mvmn_ty_cd == "20"){	//지역내 이동인 경우
				if(caralcSetupInfo.getWhthrc_othinst_mber_use_posbl_at().equals("N")){		//관내 타기관 회원 이용 가능 여부 체크 N일 경우 이용 불가
					result.put("err_cd", ResCode.FAIL.getCode());
					result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다.\n출발/목적지를 다시 선택해주세요.");
					return result;
				}
			}
		}
		
		if(mvmn_ty_cd == "10"){
			ModelMap guide = guideComponent.getGuide(start_lc_crdnt_x, start_lc_crdnt_y, arvl_lc_crdnt_x, arvl_lc_crdnt_y);
			Long expect_mvmn_dstnc = ((Long)guide.get("distance")/1000);	// km 단위
			
			int posbl_dstnc = (int)Double.parseDouble(caralcSetupInfo.getPosbl_dstnc());
			
			if(posbl_dstnc < expect_mvmn_dstnc){
				result.put("err_cd", ResCode.FAIL.getCode());
				result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다."+'\n'+"사전예약 대중교통환승으로 변경해서 다시 예약하세요.");
				return result;
			}
		
		}

			
		
		//왕복허용여부 -> caralc_setup_info 에서 조회  -> 출발지 센터 조회해서 조회 해오기
		//목적지 소요시간 (분)arvl_lc_crdnt_x -> T api 이용
		//차량이동가능여부 -> caralc_setup_info 에서 조회 	->출발지 센터 조회해서 조회.
		
		String roundtrip_perm_at = caralcSetupInfo.getRoundtrip_perm_at();
		String vhcle_mvmn_posbl_at = caralcSetupInfo.getVhcle_mvmn_posbl_at();
		String aloc_reqre_time = caralcSetupInfo.getAloc_reqre_time();
		
		
//		ModelMap guideTime = guideComponent.getGuide(start_lc_crdnt_x, start_lc_crdnt_y, arvl_lc_crdnt_x, arvl_lc_crdnt_y);
//		String aloc_reqre_time = String.valueOf(((Long)guideTime.get("time")/60));			//api 상에서 time은 초단위 값으로 넘어옴. 분단위로 변경.
		
		resultData.put("cnter_nm",cnterResveUseTime.get(0).getCnter_nm());
		resultData.put("mvmn_ty_cd", mvmn_ty_cd);
		resultData.put("roundtrip_perm_at", roundtrip_perm_at);
		resultData.put("vhcle_mvmn_posbl_at", vhcle_mvmn_posbl_at);
		resultData.put("aloc_reqre_time", aloc_reqre_time);
		
		resultData.put("sctn_begin_time_inst",cnterResveUseTime.get(0).getSctn_begin_time());
		resultData.put("sctn_end_time_inst",cnterResveUseTime.get(0).getSctn_end_time());
		
		resultData.put("sctn_begin_time_advc",cnterResveUseTime.get(1).getSctn_begin_time());
		resultData.put("sctn_end_time_advc",cnterResveUseTime.get(1).getSctn_end_time());
		
		resultData.put("beffat_resve_posble_de",cnterResveUseTime.get(1).getBeffat_resve_posble_de());		
		
		listData.add(resultData);
		result.put("err_cd", ResCode.SUCCESS.getCode());
		result.put("err_msg", ResCode.SUCCESS.toString());
		result.put("result", listData);
		return result;
	}
	
	/**
	 * 차량예약요청(당일 즉시)
	 * @param rh
	 * @return
	 */
	public ModelMap carInstant(final RceptHist rh){
		ModelMap result = new ModelMap();
		ModelMap carInstantResult = new ModelMap();
		ArrayList<ModelMap> resultText = new ArrayList<ModelMap>();
		
		ModelMap guide = guideComponent.getGuide(rh.getStart_lc_crdnt_x(), rh.getStart_lc_crdnt_y(), rh.getArvl_lc_crdnt_x(), rh.getArvl_lc_crdnt_y());
		String expect_reqre_time = String.valueOf(((Long)guide.get("time")/60));			//api 상에서 time은 초단위 값으로 넘어옴. 분단위로 변경.
		String expect_mvmn_dstnc = String.valueOf(((Long)guide.get("distance")/1000));	// km 단위
		String expect_cychg = String.valueOf((Long)guide.get("fare"));						//택시 요금
		
		rh.setExpect_reqre_time(expect_reqre_time);
		rh.setExpect_mvmn_dstnc(expect_mvmn_dstnc);
		rh.setExpect_cychg(expect_cychg);
		logger.info("rh.toString : {}", rh.toString());
		int resultValue = mobileApiDao.carInstant(rh);
		
		String id = "API_006";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		if(resultValue == 1){
			Map<String, Object> paramterMap = new HashMap<String, Object>();
			paramterMap.put("mber_id", rh.getMber_id());
			paramterMap.put("cnter_cd", rh.getCnter_cd());
			paramterMap.put("chrg_cnter_cd", rh.getChrg_cnter_cd());
			paramterMap.put("caralc_sttus_cd", "10"); //배차상태코드 10 : 신청
			paramterMap.put("rcept_se_cd", "10");
			
			int resultValue2 = mobileApiDao.carInstant2(paramterMap);
			
			if(resultValue2 == 1) {
				result.put("err_cd", ResCode.SUCCESS.getCode());
				result.put("err_msg", ResCode.SUCCESS.toString());
				carInstantResult.put("request_result", "OK");
			} else {
				result.put("err_cd", ResCode.FAIL.getCode());
				result.put("err_msg", ResCode.FAIL.toString());
				carInstantResult.put("request_result", "Failure");
			}
		} else {
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg", ResCode.FAIL.toString());
			carInstantResult.put("request_result", "Failure");
		}
		resultText.add(carInstantResult);
		result.put("result", resultText);
		return result;
	}
	
	/**
	 * 차량예약요청(사전예약)
	 * @param rh
	 * @return
	 */
	public ModelMap carAdvance(RceptHist rh) {
		// TODO Auto-generated method stub
		ModelMap result = new ModelMap();
		ModelMap carAdvanceResult = new ModelMap();
		ArrayList<ModelMap> resultText = new ArrayList<ModelMap>();
		
		if(rh.getResve_dt() == "" || rh.getResve_dt().equals("")){
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg", ResCode.FAIL.toString());
			carAdvanceResult.put("request_result", "Failure");
			resultText.add(carAdvanceResult);
			result.put("result", resultText);
			return result;
		}
		ModelMap guide = guideComponent.getGuide(rh.getStart_lc_crdnt_x(), rh.getStart_lc_crdnt_y(), rh.getArvl_lc_crdnt_x(), rh.getArvl_lc_crdnt_y());
		String expect_reqre_time = String.valueOf(((Long)guide.get("time")/60));		//api 상에서 time은 초단위 값으로 넘어옴. 분단위로 변경.
		String expect_mvmn_dstnc = String.valueOf(((Long)guide.get("distance")/1000));	// km 단위
		String expect_cychg = String.valueOf((Long)guide.get("fare"));						//택시 요금
		
		rh.setExpect_reqre_time(expect_reqre_time);
		rh.setExpect_mvmn_dstnc(expect_mvmn_dstnc);
		rh.setExpect_cychg(expect_cychg);
		int resultValue = mobileApiDao.carAdvance(rh);
		
		String id = "API_007";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		if(resultValue == 1){
			Map<String, Object> paramterMap = new HashMap<String, Object>();
			paramterMap.put("mber_id", rh.getMber_id());
			paramterMap.put("cnter_cd", rh.getCnter_cd());
			paramterMap.put("chrg_cnter_cd", rh.getChrg_cnter_cd());
			paramterMap.put("resve_dt", rh.getResve_dt());
			paramterMap.put("caralc_sttus_cd", "10"); //배차상태코드 10 : 신청
			paramterMap.put("rcept_se_cd", "20");
			
			int resultValue2 = mobileApiDao.carInstant2(paramterMap);
			
			if(resultValue2 == 1) {
				result.put("err_cd", ResCode.SUCCESS.getCode());
				result.put("err_msg", ResCode.SUCCESS.toString());
				carAdvanceResult.put("request_result", "OK");
			} else {
				result.put("err_cd", ResCode.FAIL.getCode());
				result.put("err_msg", ResCode.FAIL.toString());
				carAdvanceResult.put("request_result", "Failure");
			}
		} else {
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg", ResCode.FAIL.toString());
			carAdvanceResult.put("request_result", "Failure");
		}
		
		resultText.add(carAdvanceResult);
		result.put("result", resultText);
		return result;
	}
	
	/**
	 * 지난이용 내역
	 * @param cnterGuCd
	 * @param mberId
	 * @param year_month
	 * @param start_idx
	 * @param end_idx
	 * @return
	 */
	public ModelMap getBookHistory(String cnter_cd, String mber_id, String year_month, 
			String start_idx, String end_idx){
		ModelMap result = new ModelMap();
		List<RceptHist> rceptHist = mobileApiDao.getRceptHist(mber_id, cnter_cd, year_month, start_idx, end_idx);

		String id = "API_008";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		if(rceptHist.size() == 0){
			result.put("err_cd", ResCode.NO_DATA.getCode());
			result.put("err_msg", ResCode.NO_DATA.toString());
			result.put("search_text", year_month);
		} else {
			result.put("err_cd", ResCode.SUCCESS.getCode());
			result.put("err_msg", ResCode.SUCCESS.toString());
			for(RceptHist resultList : rceptHist){
				resultList.setYear_month(year_month);
			}
			
			result.put("result", rceptHist);
		}
		
		return result;
	}
	
	/**
	 * 운전자 차량 번호 등록
	 * @param cnter_cd
	 * @param mber_id
	 * @param vhcle_no
	 * @return
	 */
	public ModelMap updateDriverVhcleNo(String cnter_cd, String mber_id, String vhcle_no) {
			// TODO Auto-generated method stub
			
			ModelMap result = new ModelMap();
			ArrayList<ModelMap> resultText = new ArrayList<ModelMap>();
			ModelMap resultList = new ModelMap();
			
			String id = "API_009";
			result.put("id", id);
			result.put("trid", ComUtil.getTrid(id));
			
			// 들어온 차량으로 등록되어 있는 운전자가 있는지 체크 후
			// 없을 경우 업데이트
			// 있는 경우 해당 차량 운행중
			
			VhcleInfo vhcleInfo = mobileApiDao.getVhcleInfo(vhcle_no);

			if(vhcleInfo != null){ //차량이 있는 경우
				String drverId = mobileApiDao.getDrverCount(vhcle_no);	//차량번호로 조회했을 때 운전자가 있는지 체크
				
				
				DrverInfo drverInfo = new DrverInfo();
				drverInfo.setCnter_cd(cnter_cd);
				drverInfo.setMber_id(mber_id);
				drverInfo.setVhcle_no(vhcle_no);
				
				if(drverId != null && mber_id.equals(drverId)) { //차량의 운전자정보가 같다면
					try {
						mobileApiDao.updateDriverVhcleNo(drverInfo);
					} catch (Exception e) {
						// TODO: handle exception
						logger.error(e.getMessage());
						result.put("err_cd", ResCode.FAIL.getCode());
						result.put("err_msg", ResCode.FAIL.toString());
						resultList.put("request_result", "Failure");
					} finally {
						result.put("err_cd", ResCode.SUCCESS.getCode());
						result.put("err_msg", ResCode.SUCCESS.toString());
						resultList.put("request_result", "OK");
					}
				} else { //해당 차량 번호로 등록되어 있는 운전자가 있거나 운전자가 없을 경우
					HashMap<String, Object> parameterMap = new HashMap<String, Object>();
					
					if(drverId != null) {
						parameterMap.put("mber_id", drverId);
						
						try {
							mobileApiDao.updateDriverVhcleNo2(parameterMap);
						} catch (Exception e) {
							// TODO: handle exception
							logger.error(e.getMessage());
							result.put("err_cd", ResCode.FAIL.getCode());
							result.put("err_msg", ResCode.FAIL.toString());
							resultList.put("request_result", "Failure");
						} finally {
							result.put("err_cd", ResCode.SUCCESS.getCode());
							result.put("err_msg", ResCode.SUCCESS.toString());
							resultList.put("request_result", "OK");
						}
					}
					
					try {
						mobileApiDao.updateDriverVhcleNo(drverInfo);
					} catch (Exception e) {
						// TODO: handle exception
						logger.error(e.getMessage());
						result.put("err_cd", ResCode.FAIL.getCode());
						result.put("err_msg", ResCode.FAIL.toString());
						resultList.put("request_result", "Failure");
					} finally {
						result.put("err_cd", ResCode.SUCCESS.getCode());
						result.put("err_msg", ResCode.SUCCESS.toString());
						resultList.put("request_result", "OK");
					}
				}
			} else {	//차량이 없는 경우
				result.put("err_cd", ResCode.FAIL.getCode());
				result.put("err_msg", ResCode.FAIL.toString());
				resultList.put("request_result", "Failure");
				resultList.put("failure_code", "00");
			}
			
			resultText.add(resultList);
			result.put("result", resultText);	
			
			return result;
		}
	
	/**
	 * 금일 배차 현황
	 * @param mberId
	 * @param cnterCd
	 * @return
	 */
	public ModelMap getDriverCaraleStatus(final String mber_id, final String cnter_cd, final String vhcle_no){
		
		ModelMap result = new ModelMap();
				
		List<CaralcHist> caralcHistList = mobileApiDao.getCaralcHistListByDriver(vhcle_no, cnter_cd);

		String id = "API_010";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		if(caralcHistList.isEmpty()){
			result.put("err_cd", ResCode.NO_DATA.getCode());
			result.put("err_msg", ResCode.NO_DATA.toString());
		} else {
			result.put("err_cd", ResCode.SUCCESS.getCode());
			result.put("err_msg", ResCode.SUCCESS.toString());
			result.put("result", caralcHistList);
		}
		return result;
	}
	
	/**
	 * 은전자 상태정보 변경
	 * @param cnter_cd
	 * @param mber_id
	 * @param drver_sttus_cd
	 * @param mvmn_dstnc
	 * @param cychg
	 * @return
	 */
	public ModelMap updateDriverStatus(String cnter_cd, String mber_id, String drver_sttus_cd, String mvmn_dstnc,
			String cychg, String resve_dt) {
		// TODO Auto-generated method stub
		//DRVER_STTUS_CD -> 10번 코드가 두개임. 
		//운행 정지 일 경우 운임비 UPDATE? 운행 중인 것은 어떻게 찾아오는가?
		/*
		 * DRVER_STTUS_CD
		 * 00 운행 시작
		 * 10 운행 정지
		 * 20 대기중
		 * 30 운행중
		 * 40 휴가중
		 */

		ModelMap result = new ModelMap();
		ArrayList<ModelMap> resultText = new ArrayList<ModelMap>();
		ModelMap resultList = new ModelMap();
		
		DrverInfo drverInfo = mobileApiDao.getDriverInfo(mber_id, cnter_cd);
		
//		CaralcHist caralcHist = mobileApiDao.getCaralcHistByVhcleNo(drverInfo.getVhcle_no());
		
		CaralcHist caralcHist = new CaralcHist();
		caralcHist.setMber_id(mber_id);
		caralcHist.setVhcle_no(drverInfo.getVhcle_no());
		caralcHist.setResve_dt(resve_dt);
		
		if(drver_sttus_cd.equals("80")){ //승차예정(운전자가 배차를 받고 출발지로 이동 시)
			drverInfo.setDrver_sttus_cd("30");
			caralcHist.setCaralc_sttus_cd("80");		//배차 상태는 승차대기로 변경
		} else if (drver_sttus_cd.equals("100")){ //승차중(운전자가 이용자 확인을 마치고 이용자를 태우고 목적지로 출발 시)
			drverInfo.setDrver_sttus_cd("30");
			caralcHist.setCaralc_sttus_cd("100");
		} else if(drver_sttus_cd.equals("110")) { //하차완료(목적지에 도착해서 결제 후 이용자가 하차를 마칠 시 )
			drverInfo.setDrver_sttus_cd("20");
			caralcHist.setCaralc_sttus_cd("110");
			caralcHist.setMvmn_dstnc(mvmn_dstnc);
			caralcHist.setCychg(cychg);
		}
		
		int resultValue = mobileApiDao.updateDriverStatus(drverInfo); //운전자상태정보 변경
		int resultValue_caralcHist = mobileApiDao.carInstant3(caralcHist); //배차_이력 Insert
//		if(!drver_sttus_cd.equals("40")){
//			resultValue_caralcHist = mobileApiDao.updateCaralcHist(caralcHist);
//		}
		
		String id = "API_011";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		if(resultValue == 1 && resultValue_caralcHist == 1){
			result.put("err_cd", ResCode.SUCCESS.getCode());
			result.put("err_msg", ResCode.SUCCESS.toString());
			resultList.put("request_result", "OK");
			resultText.add(resultList);
			result.put("result", resultText);
		} else {
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg", ResCode.FAIL.toString());
			resultList.put("request_result", "FAIL");
			resultText.add(resultList);
			result.put("result", resultText);
		}
		
		return result;
	}

	/**
	 * 위치 정보 수집
	 * @param cnter_cd 센터 
	 * @param mber_id
	 * @param colct_dt
	 * @param lc_crdnt_x
	 * @param lc_crdnt_y
	 * @param gps_ennc
	 * @param mvmn_drc
	 * @param ve
	 * @param wheelchair_fixing_sttus_cd
	 * @return
	 */
	public void updateLocation(String cnter_cd, String mber_id, String colct_dt, String lc_crdnt_x,
			String lc_crdnt_y, String gps_ennc, String mvmn_drc, String ve, String wheelchair_fixing_sttus_cd) {
		// TODO Auto-generated method stub
		
		MberInfo mber = new MberInfo();
		mber.setMber_id(mber_id);
		mber.setCnter_cd(cnter_cd);
		
		MberInfo mberInfo = mobileApiDao.getMberInfo(mber);
		
		ModelMap result = new ModelMap();
		String id = "API_012";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		if(mberInfo.getMber_se_cd().equals("10")){	//이동약자 일 경우
			HandicapLcHist handicapLcHist = new HandicapLcHist();
			handicapLcHist.setColct_dt(colct_dt);
			handicapLcHist.setMber_id(mberInfo.getMber_id());
			handicapLcHist.setCnter_cd(mberInfo.getCnter_cd());
			handicapLcHist.setLc_crdnt_x(lc_crdnt_x);
			handicapLcHist.setLc_crdnt_y(lc_crdnt_y);
			if(gps_ennc != null && !gps_ennc.equals("")) {
				handicapLcHist.setGps_ennc(gps_ennc);
			}
			if(mvmn_drc != null && !mvmn_drc.equals("")) {
				handicapLcHist.setMvmn_drc(mvmn_drc);
			}
			
			try {
				mobileApiDao.insertHandicapLcHist(handicapLcHist);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage());
				System.err.println(e);
			}
		} else if (mberInfo.getMber_se_cd().equals("30")){		//운전자 일 경우
			DrverLcHist drverLcHist = new DrverLcHist();
			drverLcHist.setColct_dt(colct_dt);
			drverLcHist.setMber_id(mber_id);
			drverLcHist.setCnter_cd(cnter_cd);
			drverLcHist.setLc_crdnt_x(lc_crdnt_x);
			drverLcHist.setLc_crdnt_y(lc_crdnt_y);
			drverLcHist.setVe(ve);
			if(gps_ennc != null && !gps_ennc.equals("")) {
				drverLcHist.setGps_ennc(gps_ennc);
			}
			if(mvmn_drc != null && !mvmn_drc.equals("")) {
				drverLcHist.setMvmn_drc(mvmn_drc);
			}
			
			drverLcHist.setWheelchair_fixing_sttus_cd(wheelchair_fixing_sttus_cd);
			
			try {
				mobileApiDao.insertDrverLcHist(drverLcHist);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage());
				System.err.println(e);
			}
		}
	}

	/**
	 * 긴급 호출
	 * @param emrgncyCallHist
	 */
	public void emrgncyCall(EmrgncyCallHist emrgncyCallHist) {
		// TODO Auto-generated method stub
		
		//위치 좌표로 주소 찾아오기!!!
		ModelMap address = guideComponent.coord2address(emrgncyCallHist.getLc_crdnt_x(), emrgncyCallHist.getLc_crdnt_y());
		emrgncyCallHist.setAdres(address.get("address_name").toString());
//		emrgncyCallHist.setAdres("긴급호출");
		
		try {
			mobileApiDao.emrgncyCall(emrgncyCallHist);
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.err.println(e);
		}
		
	}

	/**
	 * 기존 출발/목적지 조회
	 * @param cnter_cd
	 * @param mber_id
	 * @return
	 */
	public ModelMap getBookPoint(String cnter_cd, String mber_id) {
		// TODO Auto-generated method stub
		
		ModelMap result = new ModelMap();
		String id = "API_022";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		List<RceptHist> rceptHist = mobileApiDao.getBookPoint(cnter_cd, mber_id);
		
		if(rceptHist.isEmpty()){
			result.put("err_cd", ResCode.NO_DATA.getCode());
			result.put("err_msg", ResCode.NO_DATA.toString());
			result.put("result", "FAIL");
		} else {
			result.put("err_cd", ResCode.SUCCESS.getCode());
			result.put("err_msg", ResCode.SUCCESS.toString());
			result.put("total_count", rceptHist.size());
			result.put("result", rceptHist);
		}
		
		return result;
	}

	public CnterInfo getCnterInfo(String cnterCd) {
		return mobileApiDao.getCnterInfoByCnterCd(cnterCd);
	}
	
}
