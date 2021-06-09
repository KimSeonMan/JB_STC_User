package com.neighbor.ServiceAPI.mobile.LocInfoSystem.Dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Command.LocInfoCommand;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.EmrgncyDomain;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.LocDrverInfoDomain;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.LocInfoDomain;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.LocInfoLcDomain;

@Repository("locInfoDao")
public class LocInfoDao {

	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(LocInfoDao.class);
	
	@Autowired
	private SqlSession sqlSession;

	public int getTotal(LocInfoCommand command) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.locInfoDomain.getTotal", command);
	}

	public List<LocInfoDomain> selectDrverList(LocInfoCommand command) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.locInfoDomain.selectDrverList", command);
	}

	public List<LocInfoLcDomain> selectLcList(LocInfoCommand command) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.locInfoDomain.selectLcList", command);
	}

	public List<LocInfoLcDomain> findLcList(LocInfoCommand command) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.locInfoDomain.findLcList", command);
	}

	public List<LocDrverInfoDomain> getDrverInfo(LocInfoCommand command) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.locInfoDomain.getDrverInfo", command);
	}

	public EmrgncyDomain getEmrgncy() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.locInfoDomain.getEmrgncy");
	}
	
}
