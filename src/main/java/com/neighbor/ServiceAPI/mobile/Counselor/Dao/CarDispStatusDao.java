package com.neighbor.ServiceAPI.mobile.Counselor.Dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neighbor.ServiceAPI.mobile.Counselor.Command.CarDispStatusCommand;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CarDispDetailDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CarDispStatusDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CarInfoDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CarListDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CaralcSetupInfoDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.DetailMberDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.MberListDomain;
import com.neighbor.ServiceAPI.mobile.Counselor.Domain.VhcleInfoDomain;
import com.neighbor.ServiceAPI.mobile.domain.CaralcHist;
import com.neighbor.ServiceAPI.mobile.domain.RceptHist;

@Repository("carDispStatusDao")
public class CarDispStatusDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int getTotal(CarDispStatusCommand command){
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.getTotal", command);
	}

	public List<CarDispStatusDomain> selectRceptList(CarDispStatusCommand command) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.selectRceptList", command);
	}

	public CarDispDetailDomain selectDetail(CarDispStatusCommand command) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.selectDetail", command);
	}

	public int getMberTotal(CarDispStatusCommand command) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.getMberTotal", command);
	}

	public List<MberListDomain> getMberList(CarDispStatusCommand command) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.getMberList", command);
	}
	
	public CaralcSetupInfoDomain selectCaralcSetupInfo(String cnter_cd) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.selectCaralcSetup", cnter_cd);
	}

	public int insertCarDisp(CarDispDetailDomain detailDomain) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.inserlCarDisp", detailDomain);
	}
	
	public int updateCaralcDetail(CarDispDetailDomain detailDomain) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.updateCaralcDetail", detailDomain);
	}

	public int updateCarDispStatus(CarDispDetailDomain detailDomain) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.updateCarDispStatus", detailDomain);
	}

	public int mberUpdate(DetailMberDomain mber) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.mberUpdate", mber);
	}

	public int handicapUpdate(DetailMberDomain mber) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.handicapUpdate", mber);
	}

	public int deleteMber(DetailMberDomain mber) {
		// TODO Auto-generated method stub
		return sqlSession.update("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.deleteMber", mber);
	}

	public int carInsert(CaralcHist findCaralcHist) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.carInsert", findCaralcHist);
	}

	public int updateCaralcHist(CarDispDetailDomain detailDomain) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.updateCaralcHist", detailDomain);
	}
	
	public DetailMberDomain getMberDetail(DetailMberDomain domain){
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.getMberDetail", domain);
	}

//	public ResveUseTimeSetupInfoDomain selectUseTime(ResveUseTimeSetupInfoDomain resveUseTime) {
//		// TODO Auto-generated method stub
//		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.selectUseTime", resveUseTime);
//	}
	
	public List<VhcleInfoDomain> selectCarStatus(CarDispStatusCommand command) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.selectCarStatus", command);
	}
	public Map<String, Object> carlcPossibleChk(CarDispStatusCommand command) {//배차 가능한 차량
		if(command.getSelect_date() == null) {
			try {
				Date resveDt = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(command.getResve_dt());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Calendar cal = Calendar.getInstance();
				cal.setTime(resveDt);
				int dayNum = cal.get(Calendar.DAY_OF_WEEK);
				int hour = cal.get(Calendar.HOUR_OF_DAY);
				command.setSelect_date(String.valueOf(dayNum));
				command.setSelect_time(String.valueOf(hour));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.carlcPossibleChk", command);
	}

	public List<String> resveAbleTimeForHour(Map param) {//배차 가능한 차량
		String resveDate = param.get("full_resve_date").toString();

		try {
			Date resveDt = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(resveDate);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar cal = Calendar.getInstance();
			cal.setTime(resveDt);
			int dayNum = cal.get(Calendar.DAY_OF_WEEK);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			param.put("select_date",String.valueOf(dayNum));
			param.put("select_time",String.valueOf(hour));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		param.put("resve_date",param.get("resve_date").toString()+ " " + param.get("time").toString());

		Map result = sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.resveAbleTimeForHour", param);

		List<String> timeList = new ArrayList();

		if (result.get("able00").toString().equals("Y")){
			String ableTime = param.get("time").toString() + ":00";
			timeList.add(ableTime);
		}

		if (result.get("able10").toString().equals("Y")){
			String ableTime = param.get("time").toString() + ":10";
			timeList.add(ableTime);
		}

		if (result.get("able20").toString().equals("Y")){
			String ableTime = param.get("time").toString() + ":20";
			timeList.add(ableTime);
		}

		if (result.get("able30").toString().equals("Y")){
			String ableTime = param.get("time").toString() + ":30";
			timeList.add(ableTime);
		}
		
		if (result.get("able40").toString().equals("Y")){
			String ableTime = param.get("time").toString() + ":40";
			timeList.add(ableTime);
		}

		if (result.get("able50").toString().equals("Y")){
			String ableTime = param.get("time").toString() + ":50";
			timeList.add(ableTime);
		}

		return timeList;
	}
	
	public CarInfoDomain findCarInfo(String vhcle_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.findCarInfo", vhcle_no);
	}
	public CarInfoDomain findCarInfo_Work(String resve_dt, String vhcle_no) {
		// TODO Auto-generated method stub
		Map param= new HashMap();
		param.put("resve_dt", resve_dt);
		param.put("vhcle_no", vhcle_no);
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.findCarInfo_Work", param);
	}

	public CaralcHist selectCaralcHist(CaralcHist caralcHist) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.selectCaralcHist", caralcHist);
	}
	
	public RceptHist selectRceptHist(RceptHist rcept) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.selectRceptHist", rcept);
	}
	
	public List<CarListDomain> findLcList(CarDispStatusCommand command) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.findLcList", command);
	}
	
	public CarListDomain findLcList_drive(CarDispStatusCommand command) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.neighbor.ServiceAPI.dao.mapper.carDispStatus.findLcList_drive", command);
	}
}
