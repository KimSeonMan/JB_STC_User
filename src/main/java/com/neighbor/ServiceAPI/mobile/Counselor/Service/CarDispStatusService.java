package com.neighbor.ServiceAPI.mobile.Counselor.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neighbor.ServiceAPI.mobile.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.neighbor.ServiceAPI.common.util.DateUtil;
import com.neighbor.ServiceAPI.controller.mapper.RecptSttusMapper;
import com.neighbor.ServiceAPI.mobile.Component.GuideComponent;
import com.neighbor.ServiceAPI.mobile.Counselor.Command.CarDispStatusCommand;
import com.neighbor.ServiceAPI.mobile.Counselor.Dao.CarDispStatusDao;
import com.neighbor.ServiceAPI.mobile.Counselor.DataSet.CarDispStatusDataSet;
import com.neighbor.ServiceAPI.mobile.Counselor.DataSet.MberListDataSet;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CarDispDetailDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CarDispStatusDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CarInfoDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CarListDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CaralcSetupInfoDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.DetailMberDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.MberListDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.VhcleInfoCnrsDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.VhcleInfoDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Page.CarDispStatusPage;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.LocInfoLcDomain;
import com.neighbor.ServiceAPI.mobile.dao.MobileApiDao;
import com.neighbor.ServiceAPI.mobile.model.ResCode;
import com.neighbor.ServiceAPI.mobile.service.MobileApiService;
import com.neighbor.ServiceAPI.mobile.util.ComUtil;

@Service("carDispStatusService")
public class CarDispStatusService {

	
	@Autowired
	RecptSttusMapper recptSttusMapper;
	
	@Autowired
	private CarDispStatusDao carDispStatusDao;
	
	@Autowired
	private MobileApiDao mobileDao;
	
	@Autowired
	private MobileApiService mobileService;
	
	@Autowired
	private GuideComponent guideComponent;
	
	private static final Logger logger = LoggerFactory.getLogger(CarDispStatusService.class);
	
	public CarDispStatusDataSet selectRceptList(CarDispStatusCommand command) {
		// TODO Auto-generated method stub
		CarDispStatusDataSet carDispDataSet = new CarDispStatusDataSet();
		
		
		
		int total = carDispStatusDao.getTotal(command);
		CarDispStatusPage page = command.getPage();
		page.setTotal(total);
		page.setPageNo(1);
		
		if(total > 0){
			List<CarDispStatusDomain> carDispStatusDomainList = carDispStatusDao.selectRceptList(command);
			
			carDispDataSet.setTotal(total);
			carDispDataSet.setPageNo(page.getPageNo());
			carDispDataSet.setPageCount(page.getPageCount());
			carDispDataSet.setCarDisStatusDomainList(carDispStatusDomainList);
			carDispDataSet.setLat(carDispStatusDomainList.get(0).getLat());
			carDispDataSet.setLon(carDispStatusDomainList.get(0).getLon());
//			carDispDataSet.setMberName(carDispStatusDomainList.get(0).getMber_nm());
//			carDispDataSet.setResveDt(carDispStatusDomainList.get(0).getResve_date());
		}
		return carDispDataSet;
	}
	
	public MberListDataSet selectMberList(String cnter_cd) {
		// TODO Auto-generated method stub
		MberListDataSet mberListDataSet = new MberListDataSet();
		CarDispStatusCommand command = new CarDispStatusCommand();
		command.setCnter_cd(cnter_cd);
		
		int total = carDispStatusDao.getMberTotal(command);
		CarDispStatusPage page = command.getPage();
		page.setTotal(total);
		
		List<MberListDomain> mberList = carDispStatusDao.getMberList(command);
		mberListDataSet.setMberlist(mberList);
		mberListDataSet.setTotal(total);
		mberListDataSet.setPageNo(page.getPageNo());
		mberListDataSet.setPageCount(page.getPageCount());
		
		return mberListDataSet;
	}

	public CarDispDetailDomain selectDetail(CarDispStatusCommand command) {
		// TODO Auto-generated method stub
		CarDispDetailDomain detail = carDispStatusDao.selectDetail(command);
		return detail;
	}

	public CaralcSetupInfoDomain selectCaralcSetupInfo(String cnter_cd) {
		// TODO Auto-generated method stub
		CaralcSetupInfoDomain domain = carDispStatusDao.selectCaralcSetupInfo(cnter_cd);
		return domain;
	}
	
	public List<CarListDomain> getTime(List<CarListDomain> emptylist, List<CarListDomain> drivelist, String start_lc_crdnt_x, String start_lc_crdnt_y){
		//공차는 현재 위치 좌표에서 출발지 좌표로 이동시간 계산
		//실차는 현재 위차 좌표에서 운행중인 목적지 좌표 이동시간 + 목적지 좌표에서 출발지 목표 좌표 이동시간
		List<CarListDomain> resultList = new ArrayList<CarListDomain>();
		
		//공차 시간 계산
		//공차는 현재 위치 좌표에서 출발지 좌표로 이동시간 계산
		if(emptylist.size() > 0){
			for(CarListDomain emptyCar : emptylist){
				
				String expect_reqre_time = "";
				if(!emptyCar.getLc_crdnt_x().equals(start_lc_crdnt_x)){
					if(!emptyCar.getLc_crdnt_y().equals(start_lc_crdnt_y)){
						ModelMap guide = guideComponent.getGuide(emptyCar.getLc_crdnt_x(), emptyCar.getLc_crdnt_y(), start_lc_crdnt_x, start_lc_crdnt_y);
						expect_reqre_time = String.valueOf(((Long)guide.get("time")/60));		//api 상에서 time은 초단위 값으로 넘어옴. 분단위로 변경.
					}
				} else {
					expect_reqre_time = "0";
				}
				emptyCar.setTotalTime(expect_reqre_time);
			}
			resultList.addAll(emptylist);
		}
		
		//실차 시간 계산
		//실차는 현재 위차 좌표에서 운행중인 목적지 좌표 이동시간 + 목적지 좌표에서 출발지 목표 좌표 이동시간
		if(drivelist.size() > 0){
			for(CarListDomain driveCar : drivelist){
				//현재 위치에서 운행중인 목적지까지 이동시간
				ModelMap guide = guideComponent.getGuide(driveCar.getLc_crdnt_x(), driveCar.getLc_crdnt_y(), driveCar.getArvl_lc_crdnt_x(), driveCar.getArvl_lc_crdnt_y());
				long driveTime1 = ((Long)guide.get("time")/60);
				
				//목적지에서 예약 출발지 좌표까지 이동시간 계산
				ModelMap guide2 = guideComponent.getGuide(driveCar.getArvl_lc_crdnt_x(), driveCar.getArvl_lc_crdnt_y(), start_lc_crdnt_x, start_lc_crdnt_y);
				long driveTime2 = ((Long)guide2.get("time")/60);
				
				long totalTime = driveTime1+driveTime2;
				driveCar.setTotalTime(String.valueOf(totalTime));
			}
			resultList.addAll(drivelist);
		}
		
		//시간 순으로 정렬
		AscendingObj ascending = new AscendingObj();
		Collections.sort(resultList, ascending);
			
		return resultList;
	}
	class AscendingObj implements Comparator<CarListDomain>{

		@Override
		public int compare(CarListDomain o1, CarListDomain o2) {
			// TODO Auto-generated method stub
			return o1.getTotalTime().compareTo(o2.getTotalTime());
		}
		
	}
	public int insertDetail(CarDispDetailDomain detailDomain) {
		// TODO Auto-generated method stub
		return carDispStatusDao.insertCarDisp(detailDomain);
	}

	public int updateCarDispStatus(CarDispDetailDomain detailDomain) {
		// TODO Auto-generated method stub
		return carDispStatusDao.updateCarDispStatus(detailDomain);
	}

	public CaralcHist selectCaralcHist(CaralcHist caralcHist) {
		// TODO Auto-generated method stub
		return carDispStatusDao.selectCaralcHist(caralcHist);
	}

	public int insertCaralcHist(CaralcHist findCaralcHist) {
		// TODO Auto-generated method stub
//		return carDispStatusDao.carInsert(findCaralcHist);
		return 0;
	}

	public DetailMberDomain getDetailMber(DetailMberDomain mber) {
		// TODO Auto-generated method stub
//		DetailMberDomain mberInfo = carDispStatusDao.getMberDetail(mber);
//		
//		String brthday = mberInfo.getBrthdy();
//		brthday = brthday.substring(0, 3) + "-" + brthday.substring(4, 5) + "-" + brthday.substring(6, 7);
//		mberInfo.setBrthdy(brthday);
//		String[] mobile = mberInfo.getMbtlnum().split("-");
//		mberInfo.setMbtlnum_se_cd(mobile[0]);
//		mberInfo.setMbtlMiddle(mobile[1]);
//		mberInfo.setMbtlLast(mobile[2]);
//		
//		String[] tel = mberInfo.getTelno().split("-");
//		mberInfo.setArea_no_se_cd(tel[0]);
//		mberInfo.setTelMiddle(tel[1]);
//		mberInfo.setTelLast(tel[2]);
//		
//		String[] email = mberInfo.getEmail().split("@");
//		mberInfo.setEmailId(email[0]);
//		mberInfo.setEmailDomain(email[1]);
//		
//		return mberInfo;
		return null;
	}

	public int mberUpdate(DetailMberDomain mber) {
		// TODO Auto-generated method stub
//		return carDispStatusDao.mberUpdate(mber);
		return 0;
	}

	public int handicapUpdate(DetailMberDomain mber) {
		// TODO Auto-generated method stub
//		return carDispStatusDao.handicapUpdate(mber);
		return 0;
	}

	public int deleteMber(DetailMberDomain mber) {
		// TODO Auto-generated method stub
//		return carDispStatusDao.deleteMber(mber);
		return 0;
	}

	public ModelMap reservation(RceptHist rh) {
		// TODO Auto-generated method stub
		ModelMap result;
		
		if(rh.getRcept_se_cd() == "10" || rh.getRcept_se_cd().equals("10")){
			//당일 즉시
			result = mobileService.carInstant(rh);
		} else {
			result = mobileService.carAdvance(rh);
		}
		
		return result;
	}

	public ModelMap carReference(String cnter_cd, String mber_id, String start_lc_crdnt_x, String start_lc_crdnt_y,
			String arvl_lc_crdnt_x, String arvl_lc_crdnt_y, String session_cnter_cd) {
		// TODO Auto-generated method stub
		ModelMap result = new ModelMap();
		ModelMap resultData = new ModelMap();
		ArrayList<ModelMap> listData = new ArrayList<ModelMap>();
		
		String id = "API_005";
		result.put("id", id);
		result.put("trid", ComUtil.getTrid(id));
		
		MberInfo searchMber = new MberInfo();
		searchMber.setMber_id(mber_id);
		MberInfo mber = mobileDao.getMberInfo(searchMber);
		
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
		
		CnterInfo nCnter=mobileDao.getNatCnterCd();//국가이동지원센터
		
		String srtAddr1depth = startAddress.get("region_1depth_name").toString(); // 출발지 시/도
		String srtAddr2depth = startAddress.get("region_2depth_name").toString().split("\\s")[0]; // 출발지 시/구/군중 가장 대표		
		String endAddr1depth = endAddress.get("region_1depth_name").toString(); // 목적지 시/도
		String endAddr2depth = endAddress.get("region_2depth_name").toString().split("\\s")[0]; // 목적지 시/구/군중 가장 대표
		
		List<CnterInfo> startCnter = mobileDao.getCnterInfo(srtAddr1depth,srtAddr2depth); //출발지의 cnter_cd
		List<CnterInfo> endCnter = mobileDao.getCnterInfo(endAddr1depth,endAddr2depth); //목적지의 cnter_cd

		
		if(startCnter.size() < 1) {
			startCnter.add(nCnter);
		}

		if(endCnter.size() < 1) {
			endCnter.add(nCnter);
		}
		
		//센터 그룹정보
		Map startCnterGrp = mobileDao.getCnterGrpInfo(startCnter.get(0).getCnter_cd());
		Map endCnterGrp = mobileDao.getCnterGrpInfo(endCnter.get(0).getCnter_cd());

		CaralcSetupInfo caralcSetupInfo = mobileDao.getCaralcSetupInfo(startCnter.get(0).getCnter_cd());
		
		if(caralcSetupInfo == null){
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg","센터 오류");
			return result;
		}	
		CnterInfo start_upper_cnter = mobileDao.getCnterInfoByCnterCd(startCnter.get(0).getUpper_cnter_cd());
		CnterInfo end_upper_cnter = mobileDao.getCnterInfoByCnterCd(endCnter.get(0).getUpper_cnter_cd());
		
		/**
		  	10	광역간
			20	지역간
			30	지역내
		 */
		String chrg_cnter_cd ="";
		String grp_id = "";
		/*
		if (srtAddr1depth.equals("전북")){
			mvmn_ty_cd = "30";
			chrg_cnter_cd = session_cnter_cd;
			resultData.put("chrg_cnter_cd", session_cnter_cd);
		}else{
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다.");
			return result;
		}*/

//      20190311 완주센터 전북지역 예약 받는것에 대한 이슈 처리 (출목적지 기준 담당센터코드 지정 -> 세션 센터코드)
//		if(startCnter.get(0).getCnter_cd().equals(nCnter.getCnter_cd())||endCnter.get(0).getCnter_cd().equals(nCnter.getCnter_cd())) {
//			result.put("err_cd", ResCode.FAIL.getCode());
//			result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다.\n출발/목적지를 다시 선택해주세요.");
//			return result;
//		}
//
//		if(startCnter.get(0).getCnter_cd().equals(endCnter.get(0).getCnter_cd())) {			//출발지 센터 코드와 목적지 센터 코드가 같다면 지역내 이동 (30)
//			mvmn_ty_cd = "30";
//			chrg_cnter_cd = startCnter.get(0).getCnter_cd();
//			resultData.put("chrg_cnter_cd", startCnter.get(0).getCnter_cd());
//		} else {
//			if(start_upper_cnter.getCnter_cd().equals(end_upper_cnter.getCnter_cd())){
//			  	if(startCnter.get(0).getMrdn_at().equals("Y")){
//			  		mvmn_ty_cd="20";
//			  		chrg_cnter_cd = start_upper_cnter.getCnter_cd();
//			  		resultData.put("chrg_cnter_cd", start_upper_cnter.getCnter_cd());
//			  	}else {
//			  		result.put("err_cd", ResCode.FAIL2.getCode());
//					result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다."+'\n'+"사전예약 대중교통환승으로 변경해서 다시 예약하세요.");
//					result.put("result", listData);
//					return result;
//				}
//			} else{
//				result.put("err_cd", ResCode.FAIL2.getCode());
//				result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다."+'\n'+"사전예약 대중교통환승으로 변경해서 다시 예약하세요.");
//				result.put("result", listData);
//				return result;
//			}
//		}
/**
		if(startCnter.get(0).getCnter_cd().equals(endCnter.get(0).getCnter_cd())) {
			if(startCnter.get(0).getCnter_cd().equals(nCnter.getCnter_cd())) { //출:국가센터 = 목:국가센터 (세션)
				mvmn_ty_cd = "30";
	            chrg_cnter_cd = session_cnter_cd;
	            resultData.put("chrg_cnter_cd", session_cnter_cd);
			}else {																//출:센터 = 목:센터 (출센터)
				mvmn_ty_cd = "30";
	            chrg_cnter_cd = startCnter.get(0).getCnter_cd();
	            resultData.put("chrg_cnter_cd", startCnter.get(0).getCnter_cd());
			}
		}else {
			if(start_upper_cnter.getCnter_cd().equals(end_upper_cnter.getCnter_cd())) {	//출:상위센터 = 목:상위센터 (출상위센터)
				mvmn_ty_cd = "20";
	            chrg_cnter_cd = start_upper_cnter.getCnter_cd();
	            resultData.put("chrg_cnter_cd", start_upper_cnter.getCnter_cd());
			}else {	//출:상위센터 != 목:상위센터
				if(startCnter.get(0).getCnter_cd().equals(nCnter.getCnter_cd())) {			//출:국가센터 (목센터)
					mvmn_ty_cd = "20";
		            chrg_cnter_cd = endCnter.get(0).getCnter_cd();
		            resultData.put("chrg_cnter_cd", endCnter.get(0).getCnter_cd());
				}else if(endCnter.get(0).getCnter_cd().equals(nCnter.getCnter_cd())) {		//목:국가센터 (출센터)
					mvmn_ty_cd = "20";
		            chrg_cnter_cd = startCnter.get(0).getCnter_cd();
		            resultData.put("chrg_cnter_cd", startCnter.get(0).getCnter_cd());
				}else if(startCnter.get(0).getCnter_cd().equals(mber.getCnter_cd())){
					mvmn_ty_cd = "20";
		            chrg_cnter_cd = startCnter.get(0).getCnter_cd();
		            resultData.put("chrg_cnter_cd", startCnter.get(0).getCnter_cd());
				}else if(endCnter.get(0).getCnter_cd().equals(mber.getCnter_cd())){
					mvmn_ty_cd = "20";
		            chrg_cnter_cd = mber.getCnter_cd();
		            resultData.put("chrg_cnter_cd", mber.getCnter_cd());
				}else {
					result.put("err_cd", ResCode.FAIL2.getCode());
	                result.put("err_msg", "센터기준에 따라 이용할 수 없는 지역입니다.");
	                return result;
				}
			}
		}
		*/
		
		//20191008 전북 (담당센터:광역센터)
		if (startCnter.get(0).getCnter_cd().equals(endCnter.get(0).getCnter_cd())) {
			//출발지 센터 코드 = 목적지 센터 코드
			if(startCnter.get(0).getCnter_cd().equals(nCnter.getCnter_cd())){
				//출발지 센터 코드 = nCnter
				result.put("err_cd", ResCode.FAIL.getCode());
                result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다.");
                return result;
			}else {
		        mvmn_ty_cd = "30";
		        grp_id = startCnterGrp.get("GRP_ID").toString();
			}
	    } else {
	    	//출발지 센터 코드 != 목적지 센터 코드
	        if (start_upper_cnter.getCnter_cd().equals(end_upper_cnter.getCnter_cd())) {
	        	//출발지 상위 센터 코드 = 목적지 상위 센터 코드
	            if (startCnterGrp.get("GRP_ID").equals(endCnterGrp.get("GRP_ID"))) {
	            	//출발지 센터 그룹 아이디 = 목적지 센터 그룹 아이디 
	                mvmn_ty_cd = "30";
	                grp_id = startCnterGrp.get("GRP_ID").toString();
	            } else {
	            	//출발지 센터 그룹 아이디 != 목적지 센터 그룹 아이디 
	            	mvmn_ty_cd = "20";
	            	grp_id = startCnterGrp.get("GRP_ID").toString();
	            }
	        } else {
	        	//출발지 상위 센터 코드 != 목적지 상위 센터 코드
	        	if(!startCnter.get(0).getCnter_cd().equals(nCnter.getCnter_cd())) {
	        		mvmn_ty_cd = "10";
	        		grp_id = startCnterGrp.get("GRP_ID").toString();
				}else if(!endCnter.get(0).getCnter_cd().equals(nCnter.getCnter_cd())) {
					mvmn_ty_cd = "10";
	        		grp_id = endCnterGrp.get("GRP_ID").toString();
	        	}else {
	        		result.put("err_cd", ResCode.FAIL.getCode());
	                result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다.");
	                return result;
	        	}
	        		
	        }
	    }
		//무주, 진안, 장수7 -> 대전광역시 mvmn_ty_cd =30
		if(grp_id.equals("7")) {
			if(srtAddr1depth.indexOf("대전")>=0 || endAddr1depth.indexOf("대전")>=0) {
				mvmn_ty_cd = "30";
			}
		}
		//정읍, 고창4, 순창, 남원, 임실5 -> 광주광역시 mvmn_ty_cd =30
		if(grp_id.equals("4") || grp_id.equals("5")) {
			if(srtAddr1depth.indexOf("광주")>=0 || endAddr1depth.indexOf("광주")>=0) {
				mvmn_ty_cd = "30";
			}
		}
		
		if(mvmn_ty_cd == "10") {
			result.put("err_cd", ResCode.FAIL.getCode());
            result.put("err_msg","전라북도 외 지역이동은 상담원을 통해서만 가능합니다.");
            return result;
		}
				
/*				//무주, 진안, 장수 -> 대전광역시 mvmn_ty_cd =30
				if((startCnter.get(0).getCnter_cd().equals("ARE-3-012"))||(startCnter.get(0).getCnter_cd().equals("ARE-3-013"))||(startCnter.get(0).getCnter_cd().equals("ARE-3-014"))){
					if(endAddr1depth.indexOf("대전")>=0)
						mvmn_ty_cd = "30";
				}
				
				if((endCnter.get(0).getCnter_cd().equals("ARE-3-012"))||(endCnter.get(0).getCnter_cd().equals("ARE-3-013"))||(endCnter.get(0).getCnter_cd().equals("ARE-3-014"))){
					if(srtAddr1depth.indexOf("대전")>=0)
						mvmn_ty_cd = "30";
				}
				//고창, 순창, 남원 -> 광주광역시 mvmn_ty_cd =30
				if((startCnter.get(0).getCnter_cd().equals("ARE-3-006"))||(startCnter.get(0).getCnter_cd().equals("ARE-3-007"))||(startCnter.get(0).getCnter_cd().equals("ARE-3-008"))){
					if(endAddr1depth.indexOf("광주")>=0)
						mvmn_ty_cd = "30";
				}
				if((endCnter.get(0).getCnter_cd().equals("ARE-3-006"))||(endCnter.get(0).getCnter_cd().equals("ARE-3-007"))||(endCnter.get(0).getCnter_cd().equals("ARE-3-008"))){
					if(srtAddr1depth.indexOf("광주")>=0)
						mvmn_ty_cd = "30";
				}		*/
				
				
				if(startCnter.get(0).getCnter_cd().equals(nCnter.getCnter_cd())) {
					chrg_cnter_cd = end_upper_cnter.getCnter_cd();
				}else {
					chrg_cnter_cd = start_upper_cnter.getCnter_cd();
				}
		        resultData.put("chrg_cnter_cd", chrg_cnter_cd);
		        resultData.put("grp_id", grp_id);
		        
		        
		VhcleInfo solati = mobileDao.getSolati(resultData.get("chrg_cnter_cd").toString());

		if (solati == null){
			resultData.put("solati","N");
		}else{
			resultData.put("solati","Y");
		}

		caralcSetupInfo = mobileDao.getCaralcSetupInfo(chrg_cnter_cd);
		List<cnterResveUseTime> cnterResveUseTime = mobileDao.getCnterInfoUseTime(chrg_cnter_cd);// 담당센터의 예약가능시간

		if(mvmn_ty_cd == "10"){
			//출발지센터 목적지센터 비교해서 다르면 장거리로!
			ModelMap guide = guideComponent.getGuide(start_lc_crdnt_x, start_lc_crdnt_y, arvl_lc_crdnt_x, arvl_lc_crdnt_y);
			if(!ResCode.SUCCESS.getCode().toString().equals(guide.get("err_cd").toString())){		
				result.put("err_cd", ResCode.FAIL.getCode());
				result.put("err_msg","API 오류로 인해 검색이 불가능합니다.");
				return result;
			}
			Long expect_mvmn_dstnc = ((Long)guide.get("distance")/1000);	// km 단위
			
			int posbl_dstnc = (int)Double.parseDouble(caralcSetupInfo.getPosbl_dstnc());

			if(posbl_dstnc < expect_mvmn_dstnc ||!start_upper_cnter.getCnter_cd().equals(end_upper_cnter.getCnter_cd())){
				result.put("err_cd", ResCode.FAIL2.getCode());
				result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다."+'\n'+"사전예약 대중교통환승으로 변경해서 다시 예약하세요.");
				result.put("result", listData);
				return result;
			} 
			if(!mber.getCnter_cd().equals(chrg_cnter_cd)){		//회원 센터 != 담당센터
				if(caralcSetupInfo.getWdr_sctn_othinst_mber_use_posbl_at().equals("N")){		//광역구간 타기관회원 이용가능여부 체크 N일 경우 이용 불가.
					result.put("err_cd", ResCode.FAIL.getCode());
					result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다.\n출발/목적지를 다시 선택해주세요.");
					return result;
				}
			}
		} else if(mvmn_ty_cd == "20"){
			if(!mber.getCnter_cd().equals(chrg_cnter_cd)){		//회원 센터 != 담당센터
				if(caralcSetupInfo.getWhthrc_othinst_mber_use_posbl_at().equals("N")){		//관내 타기관 회원 이용 가능 여부 체크 N일 경우 이용 불가
					result.put("err_cd", ResCode.FAIL.getCode());
					result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다.\n출발/목적지를 다시 선택해주세요.");
					return result;
				}
			}
		} else if(mvmn_ty_cd == "30"){			//출발지 = 목적지
			if(!mber.getCnter_cd().equals(chrg_cnter_cd)){		//회원 센터 != 담당센터
				if(caralcSetupInfo.getWhthrc_othinst_mber_use_posbl_at().equals("N")){		//관내 타기관 회원 이용 가능 여부 체크 N일 경우 이용 불가
					result.put("err_cd", ResCode.FAIL.getCode());
					result.put("err_msg","센터기준에 따라 이용할 수 없는 지역입니다.\n출발/목적지를 다시 선택해주세요.");
					return result;
				}
			}
		}
		
		//왕복허용여부 -> caralc_setup_info 에서 조회  -> 출발지 센터 조회해서 조회 해오기
		//목적지 소요시간 (분)arvl_lc_crdnt_x -> T api 이용
		//차량이동가능여부 -> caralc_setup_info 에서 조회 	->출발지 센터 조회해서 조회.
		
		
		String roundtrip_perm_at = caralcSetupInfo.getRoundtrip_perm_at();
		String vhcle_mvmn_posbl_at = caralcSetupInfo.getVhcle_mvmn_posbl_at();
		String aloc_reqre_time = caralcSetupInfo.getAloc_reqre_time();
		String db_time_intrvl =caralcSetupInfo.getBeffat_resve_time_intrvl();
		
		ModelMap guide = guideComponent.getGuide(start_lc_crdnt_x, start_lc_crdnt_y, arvl_lc_crdnt_x, arvl_lc_crdnt_y);
		if(!ResCode.SUCCESS.getCode().toString().equals(guide.get("err_cd").toString())){		//광역구간 타기관회원 이용가능여부 체크 N일 경우 이용 불가.
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg","API 오류로 인해 검색이 불가능합니다.");
			return result;
		}
		String expect_reqre_time = String.valueOf(((Long)guide.get("time")/60)+Double.parseDouble(db_time_intrvl));			//api 상에서 time은 초단위 값으로 넘어옴. 분단위로 변경.
		String expect_mvmn_dstnc = String.valueOf(((Long)guide.get("distance")/1000));	// km 단위
		String expect_cychg = String.valueOf((Long)guide.get("fare"));						//택시 요금
		
		//담당센터 코드 추가 - 담당센터는 출발지 센터?
/*		if(mvmn_ty_cd == "20"){	//차량 이동 유형이 지역간일 경우 상위 센터가 담당센터가 됨.
			resultData.put("chrg_cnter_cd", startCnter.get(0).getUpper_cnter_cd());
		} else {
			resultData.put("chrg_cnter_cd", startCnter.get(0).getCnter_cd());
		}*/
		
		resultData.put("cnter_nm",cnterResveUseTime.get(0).getCnter_nm());
		
		resultData.put("sctn_begin_time_inst",cnterResveUseTime.get(0).getSctn_begin_time());
		resultData.put("sctn_end_time_inst",cnterResveUseTime.get(0).getSctn_end_time());
		
		resultData.put("sctn_begin_time_advc",cnterResveUseTime.get(1).getSctn_begin_time());
		resultData.put("sctn_end_time_advc",cnterResveUseTime.get(1).getSctn_end_time());
		
		resultData.put("beffat_resve_posble_de",cnterResveUseTime.get(1).getBeffat_resve_posble_de());	
		
		resultData.put("start_cnter_cd", startCnter.get(0).getCnter_cd());
		resultData.put("mvmn_ty_cd", mvmn_ty_cd);
		resultData.put("roundtrip_perm_at", roundtrip_perm_at);
		resultData.put("vhcle_mvmn_posbl_at", vhcle_mvmn_posbl_at);
		resultData.put("aloc_reqre_time", aloc_reqre_time);
		resultData.put("expect_reqre_time", expect_reqre_time);
		resultData.put("expect_mvmn_dstnc", expect_mvmn_dstnc);
		resultData.put("expect_cychg", expect_cychg);
				
		
		listData.add(resultData);
		result.put("err_cd", ResCode.SUCCESS.getCode());
		result.put("err_msg", ResCode.SUCCESS.toString());
		result.put("result", listData);
		return result;
	}

	public ModelMap selectCarList_check(String chrg_cnter_cd, String rcept_se_cd, String resve_dt, String mber_id, String wheelchair_se_cd,
			String mvmn_ty_cd, String start_lc_crdnt_x, String start_lc_crdnt_y, String arvl_lc_crdnt_x, String arvl_lc_crdnt_y
			, String cnter_cd) throws ParseException {

		//1. 예약하는 정보들 셋팅
		ModelMap result = new ModelMap();
		List<CarInfoDomain> resultCarList = new ArrayList<CarInfoDomain>();
		int allCarCnt = 0;		//전체차량
		
		Date resveDt = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(resve_dt);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(resveDt);
		int dayNum = cal.get(Calendar.DAY_OF_WEEK);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		CarDispStatusCommand command = new CarDispStatusCommand();
		command.setCnter_cd(chrg_cnter_cd);
		command.setRceptSeCd(rcept_se_cd);
		command.setResve_dt(resve_dt);
		command.setMber_id(mber_id);
		command.setSelect_date(String.valueOf(dayNum));
		command.setSelect_time(String.valueOf(hour));
		command.setVhcle_ty_cd("ALL");
		command.setDrver_sttus_cd("ALL");
		command.setWheelchair_se_cd(wheelchair_se_cd);
		
		command.setSel_lc_crdnt_x(start_lc_crdnt_x);
		command.setSel_lc_crdnt_y(start_lc_crdnt_y);
		
		//센터별 사전 예약 시간 확인 및 반경 확인
		CaralcSetupInfoDomain domain = carDispStatusDao.selectCaralcSetupInfo(chrg_cnter_cd);
		/**
		 * 예약시간 조회 조건은 예약시간 ~ 목적지 도착시간의  앞/뒤로 기준 설정 시간에 설정 되어 있는 시간을 더 더해서 예약이 없는 차 찾아옴.
		 * ex) 실제 예약시간 12:20 일 경우
		 * 		목적지 이동에 걸리는 시간이 30분이라고 가정.
		 * 	출발시간은 12:20 도착 예상시간은 12:50 임
		 *  센터별 배차 간격 시간을 30분이라 생각할때
		 *  11:50 ~ 13:20 사이에 예약이 없는 차를 찾아오게 했음.
		 */
		
		//예약건의 도착예상시간 
		ModelMap guide = guideComponent.getGuide(start_lc_crdnt_x, start_lc_crdnt_y, arvl_lc_crdnt_x, arvl_lc_crdnt_y);
		int expect_reqre_time = Integer.parseInt(String.valueOf(((Long)guide.get("time")/60)));
		//도착예상시간 + 기준설정 정보 배차 간격 시간
		String time_intrvl = domain.getBeffat_resve_time_intrvl();
		int int_time_intrvl = Integer.parseInt(time_intrvl.substring(0, time_intrvl.indexOf(".")));
		expect_reqre_time+=int_time_intrvl;
		
		Calendar cal_arr = Calendar.getInstance();
		Calendar cal_def = Calendar.getInstance();
		cal_arr.setTime(resveDt);
		cal_def.setTime(resveDt);
		/**
		 * 예상 도착시간을 
		 * 예상 소요시간 + 하차 예상시간(10분) > 기준설정 정보 배차 간격 시간 인 경우 예상 소요시간+하차예상시간으로 설정하고,  
		 * 
		 * 예상 소요시간 + 하차 예상시간(10분) < 기준설정 정보 배차 간격 시간 인 경우 기준 정보 배차 간격 시간으로 설정 함
		 */
		
		if(expect_reqre_time > int_time_intrvl){
			cal_arr.add(cal_arr.MINUTE, expect_reqre_time);
			command.setResve_dt_arr(df.format(cal_arr.getTime()).toString());
		} else {
			cal_arr.add(cal_arr.MINUTE, int_time_intrvl);
			command.setResve_dt_arr(df.format(cal_arr.getTime()).toString());
		}
		
		//예약시간 - 기준설정 정보 배차 간격 시간 
		cal_def.add(cal_def.MINUTE, -int_time_intrvl);
		command.setResve_dt_def(df.format(cal_def.getTime()).toString());
		command.setMvmn_ty_cd(mvmn_ty_cd);
		//이동 유형 확인 - 지역간일 경우 공동 배차 차량만 조회
		if(command.getMvmn_ty_cd() == "20" || command.getMvmn_ty_cd().equals("20")){
			command.setCopertn_caralc_at("Y");
		}

		//사전 예약 차량 배차 현황 확인
		List<VhcleInfoDomain> carlist = carDispStatusDao.selectCarStatus(command);
	
		if(carlist.size()>0) {
			//CarInfoDomain carInfo = carDispStatusDao.findCarInfo(carlist.get(0).getVhcle_no());
			CarInfoDomain carInfo = carDispStatusDao.findCarInfo_Work(command.getResve_dt().toString(), carlist.get(0).getVhcle_no());
			resultCarList.add(carInfo);
		}

		//예약 요청 시 시간별 차량 요청가능 Limit
		command.setChrg_cnter_cd(chrg_cnter_cd);
		Map<String, Object> carlcPossibleChk = carDispStatusDao.carlcPossibleChk(command);
		if(Integer.parseInt(carlcPossibleChk.get("ALL_CAR").toString()) < 3) {
			allCarCnt = Integer.parseInt(carlcPossibleChk.get("ALL_CAR").toString());
		}else if(DateUtil.isSameDayChk(resve_dt)) { //예약일이 오늘이면 나머지 모든 차량에 대해서 예약 가능
			allCarCnt = (int) Math.round(Double.parseDouble(carlcPossibleChk.get("ALL_CAR").toString())*0.7);
		} else {
			allCarCnt = (int) Math.round(Double.parseDouble(carlcPossibleChk.get("ALL_CAR").toString())*0.7);
		}
		int usedCar =Integer.parseInt(carlcPossibleChk.get("USED_CAR").toString());
		if(allCarCnt <= usedCar) {
			result.put("err_cd", ResCode.USE_CAR_FAIL.getCode());
			result.put("err_msg","해당 일시에 배차 가능한 차량이 없습니다.");
//			result.put("err_msg",command.getChrg_cnter_cd()+"해당 일시에 배차 가능한 차량이 없습니다."+allCarCnt+" "+usedCar);
			return result;
		}
		
		result.put("caralc_dt", resve_dt);
		result.put("err_cd", ResCode.SUCCESS.getCode());
		if(carlist.size()>0) {
			result.put("vhcle_no", resultCarList.get(0).getVhcle_no());
		}
		else {
			result.put("vhcle_no", "0000"); //차량이 없을때
		}
		return result;
	}

	public List resveAbleTimeList(Map mapParameter) throws ParseException {
		String[] resveDtArr = mapParameter.get("RESVE_DT").toString().split(",");
		String[] chrgCnterCdArr = mapParameter.get("CHRG_CNTER_CD").toString().split(",");

		List result = new ArrayList();

		for(int i = 0; i< resveDtArr.length; i++) {
			Map tmpResult = new HashMap();
			Map param = new HashMap();
			param.put("wheelchair_se_cd", mapParameter.get("WHEELCHAIR_SE_CD").toString());
			param.put("mvmn_ty_cd", mapParameter.get("MVMN_TY_CD").toString());


			Date resveDt = new SimpleDateFormat("yyyy-MM-dd").parse(resveDtArr[i]);
			Date nowDate = new Date();
			String dtString = new SimpleDateFormat("yyyy-MM-dd").format(resveDt);
			String nowString = new SimpleDateFormat("yyyy-MM-dd").format(nowDate);

			param.put("full_resve_date", resveDtArr[i]);

			boolean today = false;
			if (dtString.equals(nowString)){
				param.put("weight",0.7);
				today = true;
			}else{
				param.put("weight",0.7);
				today = false;
			}

			param.put("chrg_cnter_cd",chrgCnterCdArr[i]);

			List<cnterResveUseTime> useTimeList = mobileDao.getCnterInfoUseTime(chrgCnterCdArr[i]);

			for (cnterResveUseTime useTimeInfo : useTimeList){
				if (useTimeInfo.getRcept_se_cd().equals("20")){
					int startIndex = (int)Math.round(Double.parseDouble(useTimeInfo.getSctn_begin_time()));
					int endIndex =  (int)Math.round(Double.parseDouble(useTimeInfo.getSctn_end_time()));

					if (today) {
						String hour = new SimpleDateFormat("HH").format(nowDate);
						startIndex = Integer.parseInt(hour);
					}

					List<String> timeList = new ArrayList();

					for (int j= startIndex; j<endIndex; j++){
						String time = String.valueOf(j);

						if (j<10){
							time = "0"+time;
						}

						param.put("resve_date",dtString);
						param.put("time",time);

						List<String> tmpList = carDispStatusDao.resveAbleTimeForHour(param);

						for (String timeString : tmpList){
							timeList.add(timeString);
						}
					}

					tmpResult.put("cnter_cd", useTimeInfo.getCnter_cd());
					tmpResult.put("cnter_name", useTimeInfo.getCnter_nm());
					tmpResult.put("timeList", timeList);
				}
			}

			tmpResult.put("resve_date", dtString);
			result.add(tmpResult);
		}

		return result;
	}
}
