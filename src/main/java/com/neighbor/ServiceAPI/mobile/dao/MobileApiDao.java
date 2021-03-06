	package com.neighbor.ServiceAPI.mobile.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neighbor.ServiceAPI.mobile.domain.CaralcHist;
import com.neighbor.ServiceAPI.mobile.domain.CaralcSetupInfo;
import com.neighbor.ServiceAPI.mobile.domain.CmmnCdValueInfo;
import com.neighbor.ServiceAPI.mobile.domain.CnterInfo;
import com.neighbor.ServiceAPI.mobile.domain.DrverInfo;
import com.neighbor.ServiceAPI.mobile.domain.DrverLcHist;
import com.neighbor.ServiceAPI.mobile.domain.EmrgncyCallHist;
import com.neighbor.ServiceAPI.mobile.domain.HandicapLcHist;
import com.neighbor.ServiceAPI.mobile.domain.MberInfo;
import com.neighbor.ServiceAPI.mobile.domain.RceptHist;
import com.neighbor.ServiceAPI.mobile.domain.VhcleInfo;
import com.neighbor.ServiceAPI.mobile.domain.cnterResveUseTime;
import com.neighbor.ServiceAPI.mobile.service.MobileApiService;

@Repository("mobileApiDao")
public class MobileApiDao {
	
	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(MobileApiService.class);

	@Autowired
	private SqlSession sqlSessionTemp;
	
	public List<CmmnCdValueInfo> getCommonCdValue(final String coId){
		logger.info("getCommonCdValue() start! coId : {}", coId);
		return sqlSessionTemp.selectList("com.neighbor.ServiceAPI.dao.mapper.cmmnCdValueInfo.getCommon", coId);
	}

	public List<Object> getRceptHistList(String mber_id, String cnter_cd, String start_idx, String end_idx) {
		// TODO Auto-generated method stub
		logger.info("getRceptHistList() start!");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("mber_id", mber_id);
		resultMap.put("cnter_cd", cnter_cd);
		resultMap.put("start_idx", start_idx);
		resultMap.put("end_idx", end_idx);
		
		return sqlSessionTemp.selectList("com.neighbor.ServiceAPI.dao.mapper.rceptHist.getRecptHistList", resultMap);
	}

	public int carCancle(CaralcHist caralcHist) {
		// TODO Auto-generated method stub
		logger.info("carCancle() start!");
		return sqlSessionTemp.update("com.neighbor.ServiceAPI.dao.mapper.caralcHist.carCancle", caralcHist);
	}
	
	public List<RceptHist> getRceptHist(String mber_id, String cnter_cd, String year_month, String start_idx, String end_idx){
		logger.info("getRceptHist() start!");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("mber_id", mber_id);
		resultMap.put("cnter_cd", cnter_cd);
		resultMap.put("year_month", year_month);
		resultMap.put("start_idx", start_idx);
		resultMap.put("end_idx", end_idx);
		
		return sqlSessionTemp.selectList("com.neighbor.ServiceAPI.dao.mapper.rceptHist.getRecptHist", resultMap);
	}
	
	public DrverInfo getDriverInfo(String mber_id, String cnter_cd){
		logger.info("getDriverInfo() start!");
		
		DrverInfo drverInfo = new DrverInfo();
		drverInfo.setMber_id(mber_id);
		drverInfo.setCnter_cd(cnter_cd);
		
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.drverInfo.getDriver", drverInfo);
	}
	
	public List<CaralcHist> getCaralcHistListByDriver(String vhcle_no, String cnter_cd){
		logger.info("getCaralcHistListByDriver() start!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("cnter_cd", cnter_cd);
		resultMap.put("vhcle_no", vhcle_no);
		
		return sqlSessionTemp.selectList("com.neighbor.ServiceAPI.dao.mapper.caralcHist.getCaralcHistListByDriver", resultMap);
	}

	//???????????? (?????? ??????)
	public int carInstant(RceptHist rh) {
		// TODO Auto-generated method stub
		logger.info("carInstant() start!");
		rh.setRcept_se_cd("10");		//?????? ??????
		return sqlSessionTemp.insert("com.neighbor.ServiceAPI.dao.mapper.rceptHist.insertCarInstant", rh);
	}

	//????????????(????????????)
	public int carAdvance(RceptHist rh) {
		// TODO Auto-generated method stub
		logger.info("carAdvance() start!");
		rh.setRcept_se_cd("20");		//????????????
		return sqlSessionTemp.insert("com.neighbor.ServiceAPI.dao.mapper.rceptHist.insertCarAdvance", rh);
	}
	
	//???????????? (????????????/????????????) CARALC_HIST ??????
	public int carInstant2(Map<String, Object> paramterMap) {
		// TODO Auto-generated method stub
		logger.info("carInstant2(CARALC_HIST Insert) start!");
		return sqlSessionTemp.insert("com.neighbor.ServiceAPI.dao.mapper.caralcHist.insertCarInstant2", paramterMap);
	}
	
	//????????? ?????? ?????? ??????
	public int updateDriverStatus(DrverInfo drverInfo) {
		// TODO Auto-generated method stub
		logger.info("updateDriverStatus() start!");
		return sqlSessionTemp.update("com.neighbor.ServiceAPI.dao.mapper.drverInfo.updateDriver", drverInfo);
	}
	
	//?????? ?????? ??????
	public MberInfo getMberInfo(MberInfo mber) {
		// TODO Auto-generated method stub
		logger.info("getMberInfo() start!");
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.mberInfo.getMberInfo", mber);
	}

	public MberInfo login(MberInfo mber) {
		// TODO Auto-generated method stub
		logger.info("login() start!");
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.mberInfo.login", mber);
	}
	
	//???????????? ?????? ?????? ??????
	public int insertHandicapLcHist(HandicapLcHist handicapLcHist) {
		// TODO Auto-generated method stub
		logger.info("insertHandicapLcHist() start!");
		return sqlSessionTemp.insert("com.neighbor.ServiceAPI.dao.mapper.handicapLcHist.insertHandicapLcHist", handicapLcHist);
	}

	//????????? ?????? ?????? ??????
	public int insertDrverLcHist(DrverLcHist drverLcHist) {
		// TODO Auto-generated method stub
		logger.info("insertDrverLcHist() start!");
		return sqlSessionTemp.insert("com.neighbor.ServiceAPI.dao.mapper.drverLcHist.insertDrverLcHist", drverLcHist);
	}

	public void emrgncyCall(EmrgncyCallHist emrgncyCallHist) {
		// TODO Auto-generated method stub
		logger.info("emrgncyCall() start!");
		sqlSessionTemp.insert("com.neighbor.ServiceAPI.dao.mapper.emrgncyCallHist.emrgncyCall", emrgncyCallHist);
	}

	//???????????? ??????
	public VhcleInfo getVhcleInfo(String vhcle_no) {
		// TODO Auto-generated method stub
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.vhcleInfo.selectVhcleInfo", vhcle_no);
	}
	
	//?????? ????????? ????????? ?????? ??????
	public String getDrverCount(String vhcle_no) {
		// TODO Auto-generated method stub
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.drverInfo.getDrverCount", vhcle_no);
	}
	
	//????????? ?????? ?????? ??????
	public int updateDriverVhcleNo(DrverInfo drverInfo) {
		// TODO Auto-generated method stub
		return sqlSessionTemp.update("com.neighbor.ServiceAPI.dao.mapper.drverInfo.setVhcleNo",drverInfo);
	}
	
	//???????????? ??????????????? ?????? ??????
	public int updateDriverVhcleNo2(HashMap<String, Object> parameterMap) {
		// TODO Auto-generated method stub
		return sqlSessionTemp.update("com.neighbor.ServiceAPI.dao.mapper.drverInfo.setVhcleNo2",parameterMap);
	}
	//?????? ?????? ????????????. - ???????????? ??????
	public List<CnterInfo> getCnterInfo(String depth1, String depth2) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("depth1", depth1);
		resultMap.put("depth2", depth2);
		logger.info("depth1 : {}", depth1);
		logger.info("depth2 : {}", depth2);
		return sqlSessionTemp.selectList("com.neighbor.ServiceAPI.dao.mapper.cnterInfo.getCnterInfo", resultMap);
	}
	
	public CnterInfo getCnterInfoByCnterCd(String cnter_cd){
		
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.cnterInfo.getCnterInfoByCnterCd", cnter_cd);
	}
	
	public List<cnterResveUseTime> getCnterInfoUseTime(String cnter_cd) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("cnter_cd", cnter_cd);
		return sqlSessionTemp.selectList("com.neighbor.ServiceAPI.dao.mapper.cnterInfo.getCnterInfoUseTime", resultMap);
	}
	
	public CnterInfo getNatCnterCd(){
		
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.cnterInfo.getNatCnterCd");
	}

	public CaralcSetupInfo getCaralcSetupInfo(String cnter_cd) {
		// TODO Auto-generated method stub
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.caralcSetupInfo.getCaralcSetupInfo", cnter_cd);
	}

	public CaralcHist getCaralcHistByVhcleNo(String vhcle_no) {
		// TODO Auto-generated method stub
		logger.info("getCaralcHistByVhcleNo() start!");
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.caralcHist.getCaralcHistByVhcleNo", vhcle_no);
	}

	public int updateCaralcHist(CaralcHist caralcHist) {
		// TODO Auto-generated method stub
		return sqlSessionTemp.update("com.neighbor.ServiceAPI.dao.mapper.caralcHist.updateCaralcHist", caralcHist);
	}
	
	//??????????????????????????? ??? CARALC_HIST ??????
	public int carInstant3(CaralcHist caralcHist) {
		// TODO Auto-generated method stub
		logger.info("carInstant3(CARALC_HIST Insert) start!");
		return sqlSessionTemp.insert("com.neighbor.ServiceAPI.dao.mapper.caralcHist.insertCarInstant3", caralcHist);
	}

	public List<RceptHist> getBookPoint(String cnter_cd, String mber_id) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("cnter_cd", cnter_cd);
		resultMap.put("mber_id", mber_id);
		return sqlSessionTemp.selectList("com.neighbor.ServiceAPI.dao.mapper.rceptHist.getBookPoint", resultMap);
	}
    //?????????????????????
    public List<Map<String,Object>> searchHandicap(Map mapParameter) {
    	return sqlSessionTemp.selectList("com.neighbor.ServiceAPI.dao.mapper.mberInfo.searchHandicap", mapParameter);
    }

	// ??????????????????
	public VhcleInfo getSolati(String chrg_cnter_cd) {
		// TODO Auto-generated method stub
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.vhcleInfo.getSolati", chrg_cnter_cd);
	}
	// ??????????????????
	public Map getCnterGrpInfo (String cnter_cd) {
		return sqlSessionTemp.selectOne("com.neighbor.ServiceAPI.dao.mapper.cnterInfo.getCnterGrpInfo", cnter_cd);
	}
}
