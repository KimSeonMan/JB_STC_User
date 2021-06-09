package com.neighbor.ServiceAPI.mobile.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.neighbor.ServiceAPI.mobile.domain.VhcleInfo;

@Repository("baseInformationApiDao")
public class BaseInformationApiDao {

	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(BaseInformationApiDao.class);
	
	@Autowired
	private SqlSession sqlSessionTemp;
	
	//차량정보 조회
	public VhcleInfo getCarDetail(String vhcleNo) {
		// TODO Auto-generated method stub
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.vhcleInfo.getCarDetail", vhcleNo);
	}
	
	//차량 정보 수정
	public int updateCarInfo(VhcleInfo vhcle) {
		// TODO Auto-generated method stub
		return sqlSessionTemp.update("com.neighbor.ServiceAPI.dao.mapper.vhcleInfo.updateCar", vhcle);
	}

	//차량 정보 삭제
	public int deleteCarInfo(String vhcleNo) {
		// TODO Auto-generated method stub
		return sqlSessionTemp.delete("com.neighbor.ServiceAPI.dao.mapper.vhcleInfo.deleteCar", vhcleNo);
	}
	
	//차량 정보 등록
	public int addCar(VhcleInfo vhcleInfo) {
		// TODO Auto-generated method stub
		logger.info("addCar() Dao start!"); 
		return sqlSessionTemp.insert("com.neighbor.ServiceAPI.dao.mapper.vhcleInfo.inserCar", vhcleInfo);
	}

	public List<VhcleInfo> getCar(ModelMap paramValue) {
		// TODO Auto-generated method stub
		return sqlSessionTemp.selectList("com.neighbor.ServiceAPI.dao.mapper.vhcleInfo.getCarList", paramValue);
	}





}
