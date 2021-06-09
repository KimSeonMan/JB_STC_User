package com.neighbor.ServiceAPI.controller.mapper;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.neighbor.ServiceAPI.mobile.Counselor.Command.CarDispStatusCommand;
import com.neighbor.ServiceAPI.mobile.Counselor.Service.CarDispStatusService;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("recptSttusMapper")
public class RecptSttusMapper extends EgovAbstractMapper{
	private static final Logger logger = LoggerFactory.getLogger(CarDispStatusService.class);
	public List selectRecptList(Map mapParameter) {
		return selectList("rcept.selectRecptList", mapParameter);
	}

	public List selectRecptListCount(Map mapParameter) {
		return selectList("rcept.selectRecptListCount", mapParameter);
	}

	public List wheelChairTpList() {
		return selectList("rcept.wheelChairTpList");
	}

	public List moveTpList() {
		return selectList("rcept.moveTpList");
	}

	public List getCnterInfo(String cnterCd) {
		return selectList("rcept.getCnterInfo", cnterCd);
	}

	public void rceptHistInsert(Map data) {
		insert("rcept.rceptHistInsert", data);
	}

	public List startPointList(Map mapParameter) {
		return selectList("rcept.startPointList", mapParameter);
	}

	public List endPointList(Map mapParameter) {
		return selectList("rcept.endPointList", mapParameter);
	}
	
	public Map chkSameResveDt(Map mapParameter) {
		return selectOne("rcept.chkSameResveDt",mapParameter);
	}

	//센터별 예약가능여부 시간확인
	public Map getRceptPossibleTime(Map mapParameter) {
		return selectOne("rcept.getRceptPossibleTime",mapParameter);
	}

	//KOBUS 예약이력 추가
	public void insertKobusRceptHist(Map mapParameter) {
		insert("rcept.insertKobusRceptHist",mapParameter);
	}
	
	//KOBUS 예약이력 확인
	public Map<String, Object> getKobusRceptHist(Map mapParameter) {
		return selectOne("rcept.getKobusRceptHist",mapParameter);
	}
	
	//KOBUS 예약취소
	public void cancelKobusRceptHist(Map mapParameter) {
		update("rcept.cancelKobusRceptHist",mapParameter);
	}
}