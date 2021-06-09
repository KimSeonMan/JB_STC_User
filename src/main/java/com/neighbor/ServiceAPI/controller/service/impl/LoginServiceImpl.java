package com.neighbor.ServiceAPI.controller.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import com.neighbor.ServiceAPI.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neighbor.ServiceAPI.controller.mapper.LoginMapper;
import com.neighbor.ServiceAPI.controller.mapper.StcMngMapper;
import com.neighbor.ServiceAPI.controller.service.LoginService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : LoginServiceImpl.java
 * @Description : LoginServiceImpl Implement Class
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

@Service("loginService")
public class LoginServiceImpl extends EgovAbstractServiceImpl implements LoginService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

	/** LoginDAO */
	@Resource(name="loginMapper")
	private LoginMapper loginMapper;
	
	@Resource(name="StcMngMapper")
	private StcMngMapper stcMngMapper;

	/**
	 * Descript section
	 * 로그인 정보 조회
	 * @param mapParameter
	 * @exception Exception
	 */
	public Map selectUserInfo(Map mapParameter) throws Exception {
		return loginMapper.selectUserInfo(mapParameter);
	}
	
	public Map selectUserInfo_wID(Map mapParameter) {
		return loginMapper.selectUserInfo_wID(mapParameter);
	}
	
	public void updateLimit(Map mapParameter) {
		loginMapper.updateLimit(mapParameter);
	}
	
	public Map userInfoDetail(Map mapParameter) throws Exception {
		return loginMapper.userInfoDetail(mapParameter);
	}

	public Map selectUserInfoByMBTLNUM(String phoneNum) throws Exception {
		Map param = new HashMap();
		param.put("MBTLNUM", phoneNum);
		return loginMapper.selectUserInfoByMBTLNUM(param);
	}
	
	@Transactional
	public void memberShipInsert(Map mapParameter) throws Exception {
		String[] address = mapParameter.get("ADRES").toString().split(" ");
		Map searchData = new HashMap<>();
		searchData.put("cityNm", address[0]);
		searchData.put("gugunNm", address[1]);
//		List cnterInfoList = stcMngMapper.findCnterCd(searchData);
//		// 시, 구군 으로 검색했을 때 나오는 센터정보가 하나일 경우
//		// 해당 지역의 센터를 찾은 것이므로 바로 저장
//		if(cnterInfoList.size() == 1) {
//			mapParameter.put("CNTER_CD", ((Map) cnterInfoList.get(0)).get("CNTER_CD"));
//		} else if(cnterInfoList.size() == 0) {
//			// 해당지역의 센터가 없다고 판단하여 국가 센터로 지정
//			mapParameter.put("CNTER_CD", "NAT-1-001");
//		} else {
//			// 시, 구군 으로 검색했을때 여러가지 센터가 존재할경우 3번째의 동 이름 까지 합해서 검색한다.
//			searchData.put("dongNm", address[2]);
//			cnterInfoList = stcMngMapper.findCnterCd(searchData);
//			if(cnterInfoList.size() > 0) {
//				// 동 이름  까지 검색했을 때 나오는 센터가 하나 이상일 경우 저장
//				mapParameter.put("CNTER_CD", ((Map) cnterInfoList.get(0)).get("CNTER_CD"));
//			} else {
//				cnterInfoList = stcMngMapper.findManageAreaCnterCd(searchData);
//				if(cnterInfoList.size() > 0) {
//					mapParameter.put("CNTER_CD", ((Map) cnterInfoList.get(0)).get("CNTER_CD"));
//				} else {
//					mapParameter.put("CNTER_CD", "NAT-1-001");
//				}
//			} 
//		}

		// 핸드폰 번호 중복 조회
		Map param = new HashMap();
		String mbtlNum = mapParameter.get("MBTLNUM").toString();
		param.put("mbtl_num",mbtlNum);

		Map result = loginMapper.memberMBTLNUMDoubleAddChk(param);

		if (!result.get("CNT").toString().equals("0")){
			throw new ApiException("입력된 휴대폰 번호로 가입된 정보가 있습니다.\n아이디/비밀번호찾기를 통해 가입된 정보를 찾은 후 서비스를 이용해주세요.");
		}

		loginMapper.memberShipInsert(mapParameter); //USER TABLE : INSERT
		loginMapper.handicapInsert(mapParameter); 	//HANDICAP TABLE : INSERT
	}
	
	@Transactional
	public void memberShipUpdate(Map mapParameter) throws Exception {
		Map param = new HashMap();
		String mbtlNum = mapParameter.get("MBTLNUM").toString();
		String mber_id = mapParameter.get("MBER_ID").toString();
		param.put("mbtl_num",mbtlNum);
		param.put("mber_id",mber_id);

		Map result = loginMapper.memberMBTLNUMDoubleAddChk(param);

		if (!result.get("CNT").toString().equals("0")){
			throw new ApiException("입력된 휴대폰 번호로 가입된 정보가 있습니다.\n아이디/비밀번호찾기를 통해 가입된 정보를 찾은 후 서비스를 이용해주세요.");
		}

		loginMapper.memberShipUpdate(mapParameter); //USER TABLE : UPDATE
		loginMapper.handicapUpdate(mapParameter); //HANDICAP TABLE : UPDATE
	}
	
	public void memberShipDelete(String[] list) throws Exception {
		loginMapper.memberShipDelete(list); //USER TABLE : UPDATE
	}
	
	public Map memberIDDoubleAddChk(Map mapParameter) throws Exception {
		return loginMapper.memberIDDoubleAddChk(mapParameter);
	}

	@Override
	public List<Map> findLoginId(Map mapParameter) {
		return loginMapper.findLoginId(mapParameter);
	}

	@Override
	public Map findLoginPw(Map mapParameter) {
		Map result = loginMapper.findLoginPw(mapParameter);
		if(result != null) {
			Map updatePw = new HashMap<String, String>();
			StringBuffer temp = new StringBuffer();
			Random rnd = new Random();
			for (int i = 0; i < 8; i++) {
			    int rIndex = rnd.nextInt(2);
			    switch (rIndex) {
			    case 0:
			        // a-z
			        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
			        break;
			    case 1:
			        // 0-9
			        temp.append((rnd.nextInt(10)));
			        break;
			    }
			}
			result = new HashMap<String,String>();
			result.put("newPw", temp.toString());
			mapParameter.put("newPw", temp.toString());
			loginMapper.updateImsiPw(mapParameter);
		}
		
		return result;
	}

	@Override
	public void updateHaniUpdateAtchNo(Map mapParameter) {
		loginMapper.updateHaniUpdateAtchNo(mapParameter);
	}
	
	public Map userBlackListChk(Map mapParameter){
		return loginMapper.userBlackListChk(mapParameter);
	}
	
	public Map userValidDTDhk(Map mapParameter){
		return loginMapper.userValidDTDhk(mapParameter);
	}
}