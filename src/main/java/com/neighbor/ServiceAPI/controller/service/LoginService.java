package com.neighbor.ServiceAPI.controller.service;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : LoginService.java
 * @Description : LoginService Class
 * @
 * @  ModifyDate      Modifier              Modify Content
 * @ ---------   		---------   -------------------------------
 * @ 2017.02.08          khc 					
 *
 * @author khc
 * @since 2017. 02.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSsoft All right reserved.
 */
public interface LoginService {

	public Map selectUserInfo(Map mapParameter) throws Exception;

	public Map selectUserInfoByMBTLNUM(String phoneNum) throws Exception;

	public Map userInfoDetail(Map mapParameter) throws Exception;
	
	public void updateLimit(Map mapParameter);
	
	public void memberShipInsert(Map mapParameter) throws Exception;
	
	public void memberShipUpdate(Map mapParameter) throws Exception;
	
	public void memberShipDelete(String[] list) throws Exception;
	
	public Map memberIDDoubleAddChk(Map mapParameter) throws Exception;

	public List<Map> findLoginId(Map mapParameter);

	public Map findLoginPw(Map mapParameter);

	public void updateHaniUpdateAtchNo(Map mapParameter);

	public Map selectUserInfo_wID(Map mapParameter);
	
	public Map userBlackListChk(Map mapParameter) ;
	
	public Map userValidDTDhk(Map mapParameter) ;
}