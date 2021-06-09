package com.neighbor.ServiceAPI.controller.service;

import java.util.List;
import java.util.Map;

public interface StndSettingMngService {

	Map getAllocateCarInfo(Map mapParameter);

	void saveAllocateCarInfo(Map mapParameter);

	List getRankOfBarriersInfo(Map mapParameter);

	List getTroblKndList();

	void rankOfBarriersSave(Map mapParameter);

	List getChargeForUsingInfo(Map mapParameter);

	void chargeForUsingSave(Map mapParameter);

	List getMemberStipulationInfo(Map mapParameter);

	void memberStipulationSave(Map mapParameter);

	List getCarInfoShareInfo(Map mapParameter);

	List getCarShareOption();

	void carInfoShareSave(Map mapParameter);

	List getBookAbleInfo(Map mapParameter);

	void saveBookAble(Map mapParameter);

	List getBookAbleOption();

}
