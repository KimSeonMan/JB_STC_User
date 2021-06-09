package com.neighbor.ServiceAPI.mobile.LocInfoSystem.Dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("commonDao")
public class CommonDao {

	@Autowired
	private SqlSession sqlSession;
	private static final Logger logger = LoggerFactory.getLogger(CommonDao.class);
	public List<HashMap<String, Object>> getDrverSttusCd() {
		// TODO Auto-generated method stub
		logger.info("getDrverSttusCd() Start!!"); 
		List<HashMap<String, Object>> list = sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.common.getDrverSttusCd");
		return list;
	}
	public List<HashMap<String, Object>> getVhcleTyCd() {
		// TODO Auto-generated method stub
		logger.info("getVhcleTyCd() Start!!"); 
		List<HashMap<String, Object>> list = sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.common.getVhcleTyCd");
		return list;
	}
	public List<HashMap<String, Object>> getMberSttusCd() {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> list = sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.common.getMberSttusCd");
		return list;
	}
	public List<HashMap<String, Object>> getMberSeCd() {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> list = sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.common.getMberSeCd");
		return list;
	}
	public List<HashMap<String, Object>> getRceptSeCd() {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> list = sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.common.getRceptSeCd");
		return list;
	}
	public List<HashMap<String, Object>> getCommonCd(String cd_id) {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> list = sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.common.getCommonCd", cd_id);
		return list;
	}
	public List<HashMap<String, Object>> getCnterCd() {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> list = sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.common.getCnterCd");
		return list;
	}
	
}
