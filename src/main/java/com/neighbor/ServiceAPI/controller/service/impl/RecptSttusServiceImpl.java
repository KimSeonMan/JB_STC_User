package com.neighbor.ServiceAPI.controller.service.impl;

import java.text.ParseException;
import java.util.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neighbor.ServiceAPI.common.util.DateUtil;
import com.neighbor.ServiceAPI.controller.mapper.AllocMapper;
import com.neighbor.ServiceAPI.controller.mapper.RecptSttusMapper;
import com.neighbor.ServiceAPI.controller.service.RecptSttusService;
import com.neighbor.ServiceAPI.controller.service.SmsSendService;
import com.neighbor.ServiceAPI.mobile.Counselor.Command.CarDispStatusCommand;
import com.neighbor.ServiceAPI.mobile.Counselor.Dao.CarDispStatusDao;
import com.neighbor.ServiceAPI.mobile.Counselor.Service.CarDispStatusService;
import com.neighbor.ServiceAPI.mobile.dao.MobileApiDao;
import com.neighbor.ServiceAPI.mobile.domain.CaralcSetupInfo;
import com.neighbor.ServiceAPI.mobile.model.ResCode;
import org.springframework.ui.ModelMap;

@Service("recptSttusService")
public class RecptSttusServiceImpl implements RecptSttusService {

	private static final Logger logger = LoggerFactory.getLogger(CarDispStatusService.class);
	@Autowired
	SmsSendService smsSendService;
	
	@Autowired
	RecptSttusMapper recptSttusMapper;
	
	@Resource(name = "recptSttusService")
	RecptSttusService recptSttusService;
	
	@Autowired
	AllocMapper allocMapper;
	
	@Autowired
	private MobileApiDao mobileApiDao;

	@Autowired
	private CarDispStatusDao carDispStatusDao;
	
	@Resource(name="carDispStatusService")
	private CarDispStatusService carDispStatusService;
	
	
	@Override
	public List selectRecptList(Map mapParameter) {
		return recptSttusMapper.selectRecptList(mapParameter);
	}

	@Override
	public List selectRecptListCount(Map mapParameter) {
		return recptSttusMapper.selectRecptListCount(mapParameter);
	}

	@Override
	public List wheelChairTpList() {
		return recptSttusMapper.wheelChairTpList();
	}

	@Override
	public List moveTpList() {
		return recptSttusMapper.moveTpList();
	}

	@Override
	public List getCnterInfo(String cnterCd) {
		return recptSttusMapper.getCnterInfo(cnterCd);
	}

	@Override
	@Transactional
	public Map rceptHistInsert(Map mapParameter) {
		Map result = new HashMap<>();
		
		String[] resveDtArr = mapParameter.get("RESVE_DT").toString().split(",");
		String[] startLcXArr = mapParameter.get("START_LC_CRDNT_X").toString().split(",");
		String[] startLcYArr = mapParameter.get("START_LC_CRDNT_Y").toString().split(",");
		String[] startAddrArr = mapParameter.get("STRTPNT_ADRES").toString().split(",");
		String[] startFullAddrArr = mapParameter.get("STRTPNT_FULL_ADRES").toString().split(",");
		String[] arvlLcXArr = mapParameter.get("ARVL_LC_CRDNT_X").toString().split(",");
		String[] arvlLcYArr = mapParameter.get("ARVL_LC_CRDNT_Y").toString().split(",");
		String[] arvlAddrArr = mapParameter.get("ALOC_ADRES").toString().split(",");
		String[] arvlFullAddrArr = mapParameter.get("ALOC_FULL_ADRES").toString().split(",");
		String[] chrgCnterCdArr = mapParameter.get("CHRG_CNTER_CD").toString().split(",");
		String[] grpId = mapParameter.get("GRP_ID").toString().split(",");
		String[] mvmnTyCdArr = mapParameter.get("MVMN_TY_CD").toString().split(",");
		String[] expectReqreTimeArr = mapParameter.get("EXPECT_REQRE_TIME").toString().split(",");
		String[] expectMvmnDstncArr = mapParameter.get("EXPECT_MVMN_DSTNC").toString().split(",");
		String[] expectExpectCychgArr = mapParameter.get("EXPECT_CYCHG").toString().split(",");
		int shared_total = Integer.parseInt(mapParameter.get("BRDNG_NMPR").toString())-1;
		List checkList = new ArrayList<>();
		
		try {
			//이용가능 시간인지 체크
			/*for(int i = 0; i < resveDtArr.length; i++) {
				Map possParam = new HashMap();
				possParam.put("resve_dt", resveDtArr[i]);
				possParam.put("cnter_cd", chrgCnterCdArr[i]);
				possParam.put("rcept_se_cd", mapParameter.get("RCEPT_SE_CD"));
				Map possTimeInfo = recptSttusService.rceptPossibleTimeChk(possParam);
				if((int) possTimeInfo.get("err_cd") != 0) {
					return possTimeInfo;
				};
			}*/
			
			if("20".equals(mapParameter.get("RCEPT_SE_CD"))){ //사전예약
				for(int i = 0; i< resveDtArr.length; i++){
					ModelMap tmp = carDispStatusService.selectCarList_check(chrgCnterCdArr[i]
							, mapParameter.get("RCEPT_SE_CD").toString()
							, resveDtArr[i]
							, mapParameter.get("MBER_ID").toString()
							, mapParameter.get("WHEELCHAIR_SE_CD").toString()
							, mvmnTyCdArr[i]
							, startLcXArr[i]
							, startLcYArr[i]
							, arvlLcXArr[i]
							, arvlLcYArr[i]
							, mapParameter.get("CNTER_CD").toString());

					Map data = new HashMap<>();
					data.put("MBER_ID", mapParameter.get("MBER_ID"));
					data.put("CNTER_CD", mapParameter.get("CNTER_CD"));
					data.put("RESVE_DT", resveDtArr[i]);
					data.put("EXPECT_REQRE_TIME", expectReqreTimeArr[i]);
					if(!recptSttusMapper.chkSameResveDt(data).toString().contains("0")) { //같은일시에 예약이 있다면
						result.put("err_cd", ResCode.RCEPT_FAIL.getCode());
						result.put("err_msg", "예약일시가 중복됩니다.");
						return result;
					}
					
					if (tmp.get("err_cd").equals(ResCode.USE_CAR_FAIL.getCode())){
						result.put("err_cd", ResCode.USE_CAR_FAIL.getCode());
						result.put("err_msg", "해당 일시에 배차 가능한 차량이 없습니다.");
						return result;
					}else{
						checkList.add(i, tmp);
					}
					
					if(!("0".equals(((Map) checkList.get(i)).get("err_cd").toString()))) {
						result.put("err_cd", ResCode.FAIL.getCode());
						result.put("err_msg", ((Map) checkList.get(i)).get("err_msg").toString());
						return result;
					}
				}
			}
			for(int i = 0; i<  resveDtArr.length; i++){
				Map data = new HashMap<>();
				data.put("RESVE_DT", resveDtArr[i]);
				data.put("MBER_ID", mapParameter.get("MBER_ID"));
				data.put("CNTER_CD", mapParameter.get("CNTER_CD"));
				data.put("RCEPT_SE_CD", mapParameter.get("RCEPT_SE_CD"));
				data.put("PBTRNSP_TRNSIT_AT", mapParameter.get("PBTRNSP_TRNSIT_AT"));
				if(mapParameter.get("ROUNDTRIP_AT") != null) {
					data.put("ROUNDTRIP_AT", mapParameter.get("ROUNDTRIP_AT"));
				} else {
					data.put("ROUNDTRIP_AT", "N");
				}
				data.put("MVMN_PURPS_CD", mapParameter.get("MVMN_PURPS_CD"));
				data.put("WHEELCHAIR_SE_CD", mapParameter.get("WHEELCHAIR_SE_CD"));
				data.put("MVMN_TY_CD", mvmnTyCdArr[i]);
				data.put("BRDNG_NMPR", 'N');
				/* data.put("BRDNG_NMPR", mapParameter.get("BRDNG_NMPR")); */
				data.put("START_LC_CRDNT_X", startLcXArr[i]);
				data.put("START_LC_CRDNT_Y", startLcYArr[i]);
				data.put("ARVL_LC_CRDNT_X", arvlLcXArr[i]);
				data.put("ARVL_LC_CRDNT_Y", arvlLcYArr[i]);
				data.put("STRTPNT_ADRES", startAddrArr[i]);
				data.put("ALOC_ADRES", arvlAddrArr[i]);
				data.put("STRTPNT_FULL_ADRES", startFullAddrArr[i]);
				data.put("ALOC_FULL_ADRES", arvlFullAddrArr[i]);
				data.put("SUPPORT_NUM", mapParameter.get("SUPPORT_NUM"));
				data.put("EXPECT_REQRE_TIME", expectReqreTimeArr[i]);
				data.put("EXPECT_MVMN_DSTNC", expectMvmnDstncArr[i]);
				data.put("EXPECT_CYCHG", expectExpectCychgArr[i]);
				data.put("EXPECT_REQRE_TIME", expectReqreTimeArr[i]);
				data.put("EXPECT_MVMN_DSTNC", expectMvmnDstncArr[i]);
				/*
				if(shared_total > 0) {
					data.put("MBER_NM2", mapParameter.get("MBER_NM2"));
					data.put("MBTLNUM2", mapParameter.get("MBTLNUM2"));
					data.put("WHEELCHAIR_SE_CD2", mapParameter.get("WHEELCHAIR_SE_CD2"));
					data.put("SUPPORT_NUM2", mapParameter.get("SUPPORT_NUM2"));
					if(shared_total>1) {
						data.put("MBER_NM3", mapParameter.get("MBER_NM3"));
						data.put("MBTLNUM3", mapParameter.get("MBTLNUM3"));
						data.put("WHEELCHAIR_SE_CD3", mapParameter.get("WHEELCHAIR_SE_CD3"));
						data.put("SUPPORT_NUM3", mapParameter.get("SUPPORT_NUM3"));
						}
				}*/
				data.put("RM", mapParameter.get("RM"));

//				if(mapParameter.get("RCEPT_SE_CD").equals("10")){ //즉시예약
				//예약일이 오늘이면 나머지 모든 차량에 대해서 예약 가능
				if(DateUtil.isSameDayChk(resveDtArr[i])) {
					//같은일시에 예약이 있다면
					if(!recptSttusMapper.chkSameResveDt(data).toString().contains("0")) {
						result.put("err_cd", ResCode.RCEPT_FAIL.getCode());
						result.put("err_msg", "예약일시가 중복됩니다.");
						return result;
					}
				}

				//예약 요청 시 시간별 차량 요청가능 Limit
				CarDispStatusCommand command = new CarDispStatusCommand();
				command.setResve_dt(resveDtArr[i]);
				command.setMvmn_ty_cd(mvmnTyCdArr[i]);
				command.setWheelchair_se_cd(mapParameter.get("WHEELCHAIR_SE_CD").toString());
				command.setChrg_cnter_cd(chrgCnterCdArr[i]);
				Map<String, Object> carlcPossibleChk = carDispStatusDao.carlcPossibleChk(command);


				/*
				int allCarCnt = (int) Math.round(Double.parseDouble(carlcPossibleChk.get("ALL_CAR").toString()));
				int usedCar =Integer.parseInt(carlcPossibleChk.get("USED_CAR").toString());
				if(mapParameter.get("RCEPT_SE_CD").equals("20")) {
					// 사전예약 배차가능차량대수
					if(allCarCnt <= usedCar) {
						result.put("err_cd", ResCode.USE_CAR_FAIL.getCode());
						result.put("err_msg","해당 일시에 배차 가능한 차량이 없습니다.");
						return result;
					}
				}else {
					// 즉시예약 배차가능차량대수
					if(allCarCnt <= usedCar) {
						result.put("err_cd", ResCode.USE_CAR_FAIL.getCode());
						result.put("err_msg","해당 일시에 배차 가능한 차량이 없습니다.");
						return result;
					}
				}*/
				
				recptSttusMapper.rceptHistInsert(data);
				
				Map alocMap = new HashMap<>();
				alocMap.put("MBER_ID", mapParameter.get("MBER_ID"));
				alocMap.put("CNTER_CD", mapParameter.get("CNTER_CD"));
				alocMap.put("RESVE_DT", resveDtArr[i]);
				alocMap.put("CARALC_STTUS_CD", "10");
				alocMap.put("GRP_ID", grpId[i]);
				alocMap.put("CHRG_CNTER_CD", chrgCnterCdArr[i]);
				alocMap.put("RCEPT_SE_CD", mapParameter.get("RCEPT_SE_CD"));
				allocMapper.addCarAlocHist(alocMap);


				/*String type = mapParameter.get("JOIN_TYPE").toString();
				if (type.equals("KOBUS")){
					Map kobusInfo = new HashMap();
					kobusInfo.put("resve_no",mapParameter.get("RESVE_NO").toString());
					kobusInfo.put("mber_id",mapParameter.get("MBER_ID").toString());
					kobusInfo.put("cnter_cd",mapParameter.get("CNTER_CD").toString());
					kobusInfo.put("run_type",mapParameter.get("RUN_TYPE").toString());
					kobusInfo.put("boarding_time",mapParameter.get("BOARDING_TIME").toString());
					kobusInfo.put("require_time",mapParameter.get("REQUIRE_TIME").toString());
					kobusInfo.put("resve_dt",resveDtArr[i]);

					recptSttusMapper.insertKobusRceptHist(kobusInfo);
				}*/
			}
			smsSendService.sendSms(3, mapParameter.get("CNTER_CD").toString(), mapParameter.get("MBER_ID").toString(),resveDtArr[0]);//resve추가필요
		} catch (ParseException e) {
			logger.error("ParseException ",e);
			e.printStackTrace();
		}catch (DuplicateKeyException e) {
			logger.error("DuplicateKeyException ",e);
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg","중복된 예약번호입니다. 등록된 예약을 취소 후 이용해주세요.");
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception ",e);
			e.printStackTrace();
			result.put("err_cd", ResCode.FAIL.getCode());
			result.put("err_msg","수정");
			return result;
		}


		result.put("err_cd", ResCode.SUCCESS.getCode());
		return result;
	}

	@Override
	public List startPointList(Map mapParameter) {
		return recptSttusMapper.startPointList(mapParameter);
	}

	@Override
	public List endPointList(Map mapParameter) {
		return recptSttusMapper.endPointList(mapParameter);
	}

	//현재 시간이 예약가능한 시간인지 여부 확인
	public Map rceptPossibleTimeChk(Map mapParameter) {
		Map result = new HashMap();
		int err_cd = ResCode.SUCCESS.getCode();
    	String err_msg = "";
    	
    	try {
    		int startTime = 0;		//예약 가능 시작시간
    		int endTime = 0;		//예약 가능 종료시간
    		int nowTime = Integer.parseInt(DateUtil.nowTimeMilli().substring(0,  2));	//현재시간
    		
    		Map possTimeInfo = recptSttusMapper.getRceptPossibleTime(mapParameter);
    		
    		//오늘날짜인지 체크
    		if(DateUtil.isSameDayChk(mapParameter.get("resve_dt").toString())) {
    			startTime = Integer.parseInt(possTimeInfo.get("DAY_RESVE_BEGIN_TIME").toString());
    			endTime =Integer.parseInt(possTimeInfo.get("DAY_RESVE_END_TIME").toString());
    		} else {		//오늘 이후일 경우
    			startTime = Integer.parseInt(possTimeInfo.get("TOM_RESVE_BEGIN_TIME").toString());
    			endTime = Integer.parseInt(possTimeInfo.get("TOM_RESVE_END_TIME").toString());
    		}
    		
    		if(nowTime >= startTime && nowTime < endTime) {
    		} else {
    			err_cd = ResCode.FAIL.getCode();
        		err_msg = "예약신청은 "+startTime+"시 ~ "+endTime+"시에 가능합니다.";
    		}
    	   
    	} catch (Exception e) {
    		err_cd = ResCode.FAIL.getCode();
    		err_msg = "지금은 예약을 할 수 없습니다.";
    		logger.info(e.toString());
    		logger.error("Exception ",e);
    	}
    	
		result.put("err_cd", err_cd);
		result.put("err_msg", err_msg);
		
		return result;
	}
    //추가 탑승자 확인
    public ModelMap searchHandicap(Map parameter) {
    	ModelMap result = new ModelMap();
    	String NoSearchNM = "";
    	List<Map<String,Object>> searchHandicap = mobileApiDao.searchHandicap(parameter);
    	int searchNum = Integer.parseInt(parameter.get("shared_passenger_total").toString());
    	for(int i=0; i<searchNum; i++) {
    		if(searchHandicap.get(i).get("CHK_NM")==null)
    			NoSearchNM =searchHandicap.get(i).get("MBER_NM").toString();
    	}
    	if(NoSearchNM != "") {
    		result.put("err_cd", ResCode.FAIL.getCode());
            result.put("err_msg", NoSearchNM+"님의 입력정보를 확인해주세요.");
    	} else {
    		result.put("err_cd", ResCode.SUCCESS.getCode());
            result.put("err_msg", ResCode.SUCCESS.toString());
    	}
    	return result;
    }
}