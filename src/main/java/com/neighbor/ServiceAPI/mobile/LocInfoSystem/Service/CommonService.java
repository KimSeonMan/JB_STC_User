package com.neighbor.ServiceAPI.mobile.LocInfoSystem.Service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Dao.CommonDao;

@Service("mobileCommonService")
public class CommonService {

	@Autowired
	private CommonDao commonDao;
	private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

	public List<HashMap<String, Object>> getDrverSttusCd() {
		// TODO Auto-generated method stub
		logger.info("CommonService - getDrverSttusCd()");
		List<HashMap<String, Object>> list = commonDao.getDrverSttusCd();
		return list;
	}

	public List<HashMap<String, Object>> getVhcleTyCd() {
		// TODO Auto-generated method stub
		logger.info("CommonService - getVhcleTyCd()");
		List<HashMap<String, Object>> list = commonDao.getVhcleTyCd();
		return list;
	}
	public List<HashMap<String, Object>> getMberSttusCd() {
		// TODO Auto-generated method stub
		logger.info("CommonService - getVhcleTyCd()");
		List<HashMap<String, Object>> list = commonDao.getMberSttusCd();
		return list;
	}
	public List<HashMap<String, Object>> getMberSeCd() {
		// TODO Auto-generated method stub
		logger.info("CommonService - getVhcleTyCd()");
		List<HashMap<String, Object>> list = commonDao.getMberSeCd();
		return list;
	}

	public List<HashMap<String, Object>> getRceptSeCd() {
		// TODO Auto-generated method stub
		logger.info("CommonService - getRceptSeCd()");
		List<HashMap<String, Object>> list = commonDao.getRceptSeCd();
		return list;
	}

	public List<HashMap<String, Object>> getCommonCd(String cd_id) {
		// TODO Auto-generated method stub
		logger.info("CommonService - getCommonCd({})", cd_id);
		List<HashMap<String, Object>> list = commonDao.getCommonCd(cd_id);
		return list;
	}

	public List<HashMap<String, Object>> getCnterCd() {
		// TODO Auto-generated method stub
		logger.info("CommonService - getCnterCd()");
		List<HashMap<String, Object>> list = commonDao.getCnterCd();
		return list;
	}
	
}
