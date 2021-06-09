package com.neighbor.ServiceAPI.mobile.LocInfoSystem.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Command.LocInfoCommand;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Dao.LocInfoDao;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.DataSet.LocInfoDataSet;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.EmrgncyDomain;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.LocDrverInfoDomain;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.LocInfoDomain;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Page.LocInfoPage;

@Service("locInfoService")
public class LocInfoService {

	@Autowired
	private LocInfoDao locInfoDao;
	
	private static final Logger logger = LoggerFactory.getLogger(LocInfoService.class);
	
	public LocInfoDataSet selectDrverList(LocInfoCommand command) {
		// TODO Auto-generated method stub
		
		LocInfoDataSet locInfoDataSet = new LocInfoDataSet();
		
		int total = locInfoDao.getTotal(command);
		LocInfoPage page = command.getPage();
		page.setTotal(total);
		
		logger.info("command.toString : {}", command.toString());
		List<LocInfoDomain> locInfoDomainList = locInfoDao.selectDrverList(command);
		
		if(locInfoDomainList.size()>0){
			List<String> mber_id_list = new ArrayList<String>();
			for(LocInfoDomain locInfo : locInfoDomainList){
				mber_id_list.add(locInfo.getMber_id());
			}
			
			command.setMber_id_list(mber_id_list);
			
			List<LocDrverInfoDomain> locDrverList = locInfoDao.getDrverInfo(command);			//운전자 인포 정보 조회
			
			List<LocInfoDomain> setLocInfoDomainList = settingDrverInfo(locInfoDomainList, locDrverList);
			
			locInfoDataSet.setLocInfoDomainList(setLocInfoDomainList);
			locInfoDataSet.setSel_mber_id(locInfoDomainList.get(0).getMber_id());
			locInfoDataSet.setLcCrdntX(locInfoDomainList.get(0).getLat());
			locInfoDataSet.setLcCrdntY(locInfoDomainList.get(0).getLon());
		}
		locInfoDataSet.setTotal(total);
		locInfoDataSet.setPageNo(page.getPageNo());
		locInfoDataSet.setPageCount(page.getPageCount());
		
		logger.info("total : {}, pageNo : {}", total, page.getPageNo());
		return locInfoDataSet;
	}

	public EmrgncyDomain selectEmrgncy() {
		// TODO Auto-generated method stub
		EmrgncyDomain emrgncy = locInfoDao.getEmrgncy();
		return emrgncy;
	}
	
	/**
	 * 반경안에 있는 좌표 정보에 운전자 인포  정보 셋팅
	 * @param locInfoDomainList			운전자 좌표 정보
	 * @param locDrverList		운전자 인포 정보
	 * @return
	 */
	private List<LocInfoDomain> settingDrverInfo(List<LocInfoDomain> locInfoDomainList,
			List<LocDrverInfoDomain> locDrverList) {
		// TODO Auto-generated method stub
		
		for(LocInfoDomain locLc : locInfoDomainList){
			for(LocDrverInfoDomain locDrver : locDrverList){
				if(locLc.getMber_id().equals(locDrver.getMber_id())){
					locLc.setMarkerInfo(locDrver);
				}
			}
			logger.info("locLc.toString : {}", locLc.toString());
		}
		return locInfoDomainList;
	}



}
