package com.neighbor.ServiceAPI.controller.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.input.CountingInputStream;
import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.controller.mapper.StndSettingMngMapper;
import com.neighbor.ServiceAPI.controller.service.StndSettingMngService;

@Service("StndSettingMngService")
public class StndSettingMngServiceImpl implements StndSettingMngService{

	@Resource(name="StndSettingMngMapper")
	private StndSettingMngMapper stndSettingMngMapper;
	
	
	@Override
	public Map getAllocateCarInfo(Map mapParameter) {
		return stndSettingMngMapper.getAllocateCarInfo(mapParameter);
	}


	@Override
	public void saveAllocateCarInfo(Map mapParameter) {
		stndSettingMngMapper.saveAllocateCarInfo(mapParameter);		
	}


	@Override
	public List getRankOfBarriersInfo(Map mapParameter) {
		return stndSettingMngMapper.getRankOfBarriersInfo(mapParameter);
	}


	@Override
	public List getTroblKndList() {
		return stndSettingMngMapper.getTroblKndList();
	}


	@Override
	public void rankOfBarriersSave(Map mapParameter) {
		String[] newTorblKndCdList = null;
		String[] newTorblGradList = null;
		String[] newTorblWheelchirUseYnList = null;
		String[] newTorblPrtctorAcmpanyAtList = null;
		
		
		if(mapParameter.containsKey("newTROBL_KND_CD")){
			newTorblKndCdList = new String[mapParameter.get("newTROBL_KND_CD").toString().split(",").length];
			newTorblGradList = new String[mapParameter.get("newTROBL_GRAD").toString().split(",").length];
			newTorblWheelchirUseYnList = new String[mapParameter.get("newWHEELCHAIR_USE_YN").toString().split(",").length];
			newTorblPrtctorAcmpanyAtList = new String[mapParameter.get("newPRTCTOR_ACMPNY_AT").toString().split(",").length];

			newTorblKndCdList = mapParameter.get("newTROBL_KND_CD").toString().split(",");
			newTorblGradList = mapParameter.get("newTROBL_GRAD").toString().split(",");
			newTorblWheelchirUseYnList = mapParameter.get("newWHEELCHAIR_USE_YN").toString().split(",");
			newTorblPrtctorAcmpanyAtList = mapParameter.get("newPRTCTOR_ACMPNY_AT").toString().split(",");
		}
				
		
		stndSettingMngMapper.deleteTroblGradInfo(mapParameter);
		
		for(int i = 0; i < newTorblKndCdList.length; i++){
			Map data = new HashMap();
			data.put("CNTER_CD", mapParameter.get("CNTER_CD"));
			data.put("TROBL_KND_CD", newTorblKndCdList[i]);
			data.put("TROBL_GRAD", newTorblGradList[i]);
			data.put("WHEELCHAIR_USE_YN", newTorblWheelchirUseYnList[i]);
			data.put("PRTCTOR_ACMPNY_AT", newTorblPrtctorAcmpanyAtList[i]);
			
			stndSettingMngMapper.saveTroblGradInfo(data);
			
		}
	}


	@Override
	public List getChargeForUsingInfo(Map mapParameter) {
		return stndSettingMngMapper.getChargeForUsingInfo(mapParameter);
	}


	@Override
	public void chargeForUsingSave(Map mapParameter) {
		String[] snList = null;
		String[] chargeSeCdList = null;
		String[] sctnBeginDsTncList = null;
		String[] sctnEndDsTncList = null;
		String[] cychgList = null;
		
		snList = new String[mapParameter.get("SN").toString().split(",").length];
		snList = mapParameter.get("SN").toString().split(",");
		
		chargeSeCdList = new String[mapParameter.get("CHRGE_SE_CD").toString().split(",").length];
		chargeSeCdList = mapParameter.get("CHRGE_SE_CD").toString().split(",");
		
		sctnBeginDsTncList = new String[mapParameter.get("SCTN_BEGIN_DSTNC").toString().split(",").length];
		sctnBeginDsTncList = mapParameter.get("SCTN_BEGIN_DSTNC").toString().split(",");
		
		sctnEndDsTncList = new String[mapParameter.get("SCTN_END_DSTNC").toString().split(",").length];
		sctnEndDsTncList = mapParameter.get("SCTN_END_DSTNC").toString().split(",");
		
		cychgList = new String[mapParameter.get("CYCHG").toString().split(",").length];
		cychgList = mapParameter.get("CYCHG").toString().split(",");
		
		stndSettingMngMapper.deleteChargeForUsing(mapParameter);
		
		for(int i = 0; i < snList.length; i++){
			Map data = new HashMap();
			data.put("CNTER_CD", mapParameter.get("CNTER_CD"));
			data.put("SN", Integer.parseInt(snList[i]));
			data.put("CHRGE_SE_CD", chargeSeCdList[i]);
			data.put("SCTN_BEGIN_DSTNC", Integer.parseInt(sctnBeginDsTncList[i]));
			data.put("SCTN_END_DSTNC", Integer.parseInt(sctnEndDsTncList[i]));
			data.put("CYCHG", Integer.parseInt(cychgList[i]));
			
			stndSettingMngMapper.saveChargeForUsing(data);
		}
	}


	@Override
	public List getMemberStipulationInfo(Map mapParameter) {
		return stndSettingMngMapper.getMemberStipulationInfo(mapParameter);
	}


	@Override
	public void memberStipulationSave(Map mapParameter) {
		String[] codeList = null;
		
		codeList = new String[mapParameter.get("STPLAT_SE_CD").toString().split(",").length];
		codeList = mapParameter.get("STPLAT_SE_CD").toString().split(",");
		
		stndSettingMngMapper.deleteMemberStipulation(mapParameter);
		
		for(int i = 0; i < codeList.length; i++){
			Map<String, String> data = new HashMap();
			data.put("CNTER_CD", (String) mapParameter.get("CNTER_CD"));
			data.put("STPLAT_SE_CD", codeList[i]);
			if(codeList[i].equals("10")){
				data.put("CN", mapParameter.get("MBER_CN").toString());
			}else if(codeList[i].equals("20")){
				data.put("CN", mapParameter.get("OTHER_CN").toString());
			}
			
			stndSettingMngMapper.saveMemberStipulation(data);
		}
	}


	@Override
	public List getCarInfoShareInfo(Map mapParameter) {
		List shareInfo = stndSettingMngMapper.getCarInfoShareInfo(mapParameter);
		
		mapParameter = stndSettingMngMapper.getCenterInfo(mapParameter);
		if(shareInfo.size() != 0){
			List initShareInfo = stndSettingMngMapper.getCarInfoShareInitInfo(mapParameter);
			for(int j=0; j < shareInfo.size(); j++) {
				Map shareInfoData = (Map) shareInfo.get(j);
				for(int i=0; i < initShareInfo.size(); i++) {
					Map initshareInfoData = (Map) initShareInfo.get(i);
					if(initshareInfoData.get("CNTER_CD").equals(shareInfoData.get("CNRS_CNTER_CD"))){
						initShareInfo.remove(i);
					}
				}
			}
			for(int i = 0; i < initShareInfo.size(); i++) {
				Map initData = (Map) initShareInfo.get(i);
				Map data = new HashMap<>();
				
				data.put("CNTER_CD", mapParameter.get("CNTER_CD"));
				data.put("CNRS_CNTER_CD", initData.get("CNTER_CD"));
				data.put("CNTER_NM", initData.get("CNTER_NM"));
				
				shareInfo.add(shareInfo.size(), data);
			}
			
		} else {
			List initInfo = stndSettingMngMapper.getCarInfoShareInitInfo(mapParameter);
			
			for(int i = 0; i < initInfo.size(); i++) {
				Map initData = (Map) initInfo.get(i);
				Map data = new HashMap<>();
				data.put("CNTER_CD", mapParameter.get("CNTER_CD"));
				data.put("CNRS_CNTER_CD", initData.get("CNTER_CD"));
				data.put("CNTER_NM", initData.get("CNTER_NM"));
				
				shareInfo.add(shareInfo.size(), data);
			}
		}
		
		
		
		return shareInfo;
	}


	@Override
	public List getCarShareOption() {
		return stndSettingMngMapper.getCarShareOption();
	}


	@Override
	public void carInfoShareSave(Map mapParameter) {
		String[] cnrsCnterCdList = null;
		String[] cnrsSeCdList = null;
		
		cnrsCnterCdList = new String[mapParameter.get("CNRS_CNTER_CD").toString().split(",").length];
		cnrsCnterCdList = mapParameter.get("CNRS_CNTER_CD").toString().split(",");
		
		cnrsSeCdList = new String[mapParameter.get("CNRS_SE_CD").toString().split(",").length];
		cnrsSeCdList = mapParameter.get("CNRS_SE_CD").toString().split(",");
		
		stndSettingMngMapper.carInfoShareDelete(mapParameter);
		for(int i = 0; i < cnrsCnterCdList.length; i++){
			Map data = new HashMap();
			data.put("CNTER_CD", (String) mapParameter.get("CNTER_CD"));
			data.put("CNRS_CNTER_CD", cnrsCnterCdList[i]);
			data.put("CNRS_SE_CD", cnrsSeCdList[i]);
			
			stndSettingMngMapper.carInfoShareSave(data);
		}
	}


	@Override
	public List getBookAbleInfo(Map mapParameter) {
		return stndSettingMngMapper.getBookAbleInfo(mapParameter);
	}


	@Override
	public void saveBookAble(Map mapParameter) {
		String[] rceptSeCdArray = null;
		String[] sctnBeginTimeArray = null;
		String[] sctnEndTimeArray = null;
		String[] beffatResvePosblDeArray = null;
		
		rceptSeCdArray = new String[mapParameter.get("RCEPT_SE_CD").toString().split(",").length];
		sctnBeginTimeArray = new String[mapParameter.get("SCTN_BEGIN_TIME").toString().split(",").length];
		sctnEndTimeArray = new String[mapParameter.get("SCTN_END_TIME").toString().split(",").length];
		beffatResvePosblDeArray = new String[mapParameter.get("BEFFAT_RESVE_POSBL_DE").toString().split(",").length];
		
		rceptSeCdArray = mapParameter.get("RCEPT_SE_CD").toString().split(",");
		sctnBeginTimeArray = mapParameter.get("SCTN_BEGIN_TIME").toString().split(",");
		sctnEndTimeArray = mapParameter.get("SCTN_END_TIME").toString().split(",");
		beffatResvePosblDeArray = mapParameter.get("BEFFAT_RESVE_POSBL_DE").toString().split(",");
		stndSettingMngMapper.bookAbleDelete(mapParameter);
		for(int i = 0; i<beffatResvePosblDeArray.length; i++) {
			Map data = new HashMap();
			data.put("CNTER_CD", (String) mapParameter.get("CNTER_CD"));
			data.put("RCEPT_SE_CD", rceptSeCdArray[i]);
			data.put("SCTN_BEGIN_TIME", sctnBeginTimeArray[i]);
			data.put("SCTN_END_TIME", sctnEndTimeArray[i]);
			data.put("BEFFAT_RESVE_POSBL_DE", beffatResvePosblDeArray[i]);
			
			stndSettingMngMapper.bookAbleSave(data);
		}
		
	}


	@Override
	public List getBookAbleOption() {
		return stndSettingMngMapper.getBookAbleOption();
	}

}
