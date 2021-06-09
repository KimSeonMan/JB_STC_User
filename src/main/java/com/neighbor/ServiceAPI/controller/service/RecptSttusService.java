package com.neighbor.ServiceAPI.controller.service;

import java.util.List;
import java.util.Map;

public interface RecptSttusService {

	List selectRecptList(Map mapParameter);

	List selectRecptListCount(Map mapParameter);

	List wheelChairTpList();

	List moveTpList();

	List getCnterInfo(String string);

	Map rceptHistInsert(Map mapParameter);

	List startPointList(Map mapParameter);

	List endPointList(Map mapParameter);
	
	//현재 시간이 예약가능한 시간인지 여부 확인
	Map rceptPossibleTimeChk(Map mapParameter);

	Map searchHandicap(Map param);
}