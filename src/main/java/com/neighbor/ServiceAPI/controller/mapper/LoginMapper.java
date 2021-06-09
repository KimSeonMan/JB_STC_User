package com.neighbor.ServiceAPI.controller.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * @Class Name : LoginServiceImpl.java
 * @Description : LoginServiceImpl DAO Class
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

@Repository("loginMapper")
public class LoginMapper extends EgovAbstractMapper {

	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	/*아이디 비밀번호 비교*/
	public Map selectUserInfo(Map mapParameter) throws Exception {
		return selectOne("login.selectUserInfo", mapParameter);
	}

	/*KOBUS 전화번호 로그인*/
	public Map selectUserInfoByMBTLNUM(Map mapParameter) throws Exception {
		return selectOne("login.selectUserInfoByMBTLNUM", mapParameter);
	}

	/*아이디 비교*/
	public Map selectUserInfo_wID(Map mapParameter){
		return selectOne("login.selectUserInfo_wID", mapParameter);
	}
	
	public void updateLimit(Map mapParameter){
		update("login.updateLimit", mapParameter);
	}
	
	public Map userInfoDetail(Map mapParameter) throws Exception {
		return selectOne("login.userInfoDetail", mapParameter);
	}
	
	public void memberShipInsert(Map mapParameter) throws Exception {
		insert("login.memberShipInsert", mapParameter);
	}
	
	public void handicapInsert(Map mapParameter) throws Exception {
		insert("login.handicapInsert", mapParameter);
	}
	
	public void supporterInsert(Map mapParameter) throws Exception {
		insert("login.supporterInsert", mapParameter);
	}
	
	public void driverInsert(Map mapParameter) throws Exception {
		insert("login.driverInsert", mapParameter);
	}
	
	public void stationEmpInsert(Map mapParameter) throws Exception {
		insert("login.stationEmpInsert", mapParameter);
	}
	
	public void memberShipUpdate(Map mapParameter) throws Exception {
		update("login.memberShipUpdate", mapParameter);
	}
	
	public void handicapUpdate(Map mapParameter) throws Exception {
		update("login.handicapUpdate", mapParameter);
	}
	
	public void supporterUpdate(Map mapParameter) throws Exception {
		update("login.supporterUpdate", mapParameter);
	}
	
	public void driverUpdate(Map mapParameter) throws Exception {
		update("login.driverUpdate", mapParameter);
	}
	
	public void stationEmpUpdate(Map mapParameter) throws Exception {
		update("login.stationEmpUpdate", mapParameter);
	}
	
	public void memberShipDelete(String[] list) throws Exception {
		update("login.memberShipDelete", list);
	}
	
	public Map memberIDDoubleAddChk(Map mapParameter) throws Exception {
		return selectOne("login.memberIDDoubleAddChk", mapParameter);
	}

	public Map memberMBTLNUMDoubleAddChk(Map mapParameter) throws Exception {
		return selectOne("login.memberMBTLNUMDoubleAddChk", mapParameter);
	}

	public List<Map> findLoginId(Map mapParameter) {
		return selectList("login.findLoginId", mapParameter);
	}

	public Map findLoginPw(Map mapParameter) {
		return selectOne("login.findLoginPw", mapParameter);
	}

	public void updateImsiPw(Map mapParameter) {
		update("login.updateImsiPw", mapParameter);	
	}

	public void updateHaniUpdateAtchNo(Map mapParameter) {
		update("login.updateHaniUpdateAtchNo", mapParameter);			
	}
	
	public Map userBlackListChk(Map mapParameter) {
		return selectOne("login.userBlackListChk", mapParameter);
	}
	
	public Map userValidDTDhk(Map mapParameter) {
		return selectOne("login.userValidDTDhk", mapParameter);
	}
}