package com.neighbor.ServiceAPI.controller.service;

import java.util.List;
import java.util.Map;

public interface StcMngService {

	List getSctMngList(Map mapParameter);

	List getStcMngListTotalCount(Map mapParameter);

	Map getSctMngDetailInfo(Map mapParameter);

	List getUpperCnterList(Map stcMngDetail);

	Map getCnterCdNo();

	void stcMngAdd(Map mapParameter);

	void stcMngModify(Map mapParameter);

	void stcMngDelete(Map mapParameter);

}
