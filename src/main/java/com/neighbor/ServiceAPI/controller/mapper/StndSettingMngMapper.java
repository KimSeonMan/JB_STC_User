package com.neighbor.ServiceAPI.controller.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("StndSettingMngMapper")
public class StndSettingMngMapper extends EgovAbstractMapper {

	public Map getAllocateCarInfo(Map mapParameter) {
		return selectOne("stndSettingMng.getAllocateCarInfo", mapParameter);
	}

	public void saveAllocateCarInfo(Map mapParameter) {
		Map selectInfo = getAllocateCarInfo(mapParameter);
		
		if(selectInfo == null){
			insert("stndSettingMng.insertAllocateCarInfo", mapParameter);
		} else {
			String option =  mapParameter.get("SAVE_OPTION").toString();
			
			if(option != null) {
				if(option.equals("basic")) {
					update("stndSettingMng.updateAllocateCarBasicInfo", mapParameter);
				} else if (option.equals("WDR_SCTN")){
					update("stndSettingMng.updatAllocateCareWdrSctnInfo", mapParameter);
				} else if (option.equals("WHTHRC")){
					update("stndSettingMng.updateAllocateCarWhthrcInfo", mapParameter);
				}
			}
		}
	}

	public List getRankOfBarriersInfo(Map mapParameter) {
		return selectList("stndSettingMng.getRankOfBarriersInfo", mapParameter);
	}

	public List getTroblKndList() {
		return selectList("stndSettingMng.getTroblKndList");
	}


	public void saveTroblGradInfo(Map data) {
		insert("stndSettingMng.insertTroblGradInfo", data);
	}

	public void deleteTroblGradInfo(Map mapParameter) {
		delete("stndSettingMng.deleteTroblGradInfo", mapParameter);
	}

	public List getChargeForUsingInfo(Map mapParameter) {
		return selectList("stndSettingMng.getChargeForUsingInfo", mapParameter);
	}

	public void deleteChargeForUsing(Map mapParameter) {
		delete("stndSettingMng.deleteChargeForUsing", mapParameter);		
	}

	public void saveChargeForUsing(Map data) {
		insert("stndSettingMng.saveChargeForUsing", data);
	}

	public List getMemberStipulationInfo(Map mapParameter) {
		return selectList("stndSettingMng.getMemberStipulationInfo", mapParameter);
	}

	public void deleteMemberStipulation(Map mapParameter) {
		delete("stndSettingMng.deleteMemberStipulation", mapParameter);		
	}

	public void saveMemberStipulation(Map data) {
		insert("stndSettingMng.saveMemberStipulation", data);
	}

	public List getCarInfoShareInfo(Map mapParameter) {
		return selectList("stndSettingMng.getCarInfoShareInfo", mapParameter);
	}

	public Map getCenterInfo(Map mapParameter) {
		return selectOne("stndSettingMng.getCenterInfo", mapParameter);
	}

	public List getCarInfoShareInitInfo(Map cnterData) {
		return selectList("stndSettingMng.getCarInfoShareInitInfo", cnterData);
	}

	public List getCarShareOption() {
		return selectList("stndSettingMng.getCarShareOption");
	}

	public void carInfoShareSave(Map data) {
		insert("stndSettingMng.carInfoShareSave", data);
	}

	public void carInfoShareDelete(Map mapParameter) {
		delete("stndSettingMng.carInfoShareDelete", mapParameter);	
	}

	public List getNatCenterInfo() {
		return selectList("stndSettingMng.getNatCenterInfo");
	}

	public List getBookAbleInfo(Map mapParameter) {
		return selectList("stndSettingMng.getBookAbleInfo", mapParameter);
	}

	public void bookAbleDelete(Map mapParameter) {
		delete("stndSettingMng.bookAbleDelete", mapParameter);	
	}

	public void bookAbleSave(Map data) {
		insert("stndSettingMng.bookAbleSave", data);		
	}

	public List getBookAbleOption() {
		return selectList("stndSettingMng.getBookAbleOption");
	}
	
	
}
