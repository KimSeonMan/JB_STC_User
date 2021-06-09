package com.neighbor.ServiceAPI.mobile.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neighbor.ServiceAPI.mobile.domain.CaralcSetupInfo;

@Repository("customerApiDao")
public class CustomerApiDao {

	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(CustomerApiDao.class);

	@Autowired
	private SqlSession sqlSessionTemp;
	
	public CaralcSetupInfo getCnterReference(String cnter_cd) {
		// TODO Auto-generated method stub
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.caralcSetupInfo.getCaralcSetupInfo",cnter_cd);
	}

	public List<Object> getDrverInfo(String cnter_cd, String drver_sttus_cd, String drver_nm, String start_idx, String end_idx) {
		// TODO Auto-generated method stub
		
		logger.info("getRceptHistList() start!");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("cnterCd", cnter_cd);
		resultMap.put("drverSttusCd", drver_sttus_cd);
		resultMap.put("drverNm",  drver_nm);
		resultMap.put("start_idx", start_idx);
		resultMap.put("end_idx", end_idx);
		
		return sqlSessionTemp.selectList("com.neighbor.ServiceAPI.dao.mapper.drverInfo.getDrverInfoByCnter", resultMap);
	}

	public List<Object> getMapPois(String cnter_cd, String drver_sttus_cd, String drver_nm) {
		// TODO Auto-generated method stub
		logger.info("getRceptHistList() start!");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("cnterCd", cnter_cd);
		resultMap.put("drverSttusCd", drver_sttus_cd);
		resultMap.put("drverNm",  drver_nm);
		return sqlSessionTemp.selectList("com.neighbor.ServiceAPI.dao.mapper.drverInfo.getMapPois", resultMap);
	}

}
