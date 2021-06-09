package com.neighbor.ServiceAPI.controller.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neighbor.ServiceAPI.controller.mapper.SmsSendMapper;
import com.neighbor.ServiceAPI.controller.service.MngrService;
import com.neighbor.ServiceAPI.controller.service.SmsSendService;
import com.neighbor.ServiceAPI.controller.service.StcMngService;
import com.neighbor.ServiceAPI.mobile.dao.MobileApiDao;
import com.neighbor.ServiceAPI.mobile.domain.MberInfo;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("smsSendService")
public class SmsSendServiceImpl extends EgovAbstractServiceImpl implements SmsSendService{

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsSendServiceImpl.class);
	
	@Resource(name="smsSendMapper")
	private SmsSendMapper smsSendMapper;
	
	@Resource(name="mngrService")
	private MngrService mngrService;
	
	@Resource(name="StcMngService")
	StcMngService stcMngService;
	
	@Autowired
    private MobileApiDao mobileApiDao;

	private int AppTestMode = 1; 	// 0:상용, 1:테스트

	@Override
	public void sendSms(int type,String CNTER_CD,String MBER_ID, String RESVE_DT) throws Exception {
		
		MberInfo mberId = new MberInfo();
		mberId.setMber_id(MBER_ID);
		MberInfo mber = mobileApiDao.getMberInfo(mberId);

		if(mber.getSms_recptn_at().equals("Y")) { //sms수신여부
			Map userInfo = new HashMap();
			userInfo.put("CNTER_CD", CNTER_CD);
			userInfo.put("MBER_ID", MBER_ID);
			userInfo.put("RESVE_DT", RESVE_DT);
			
			Map userName = mngrService.selectUserMngDetail(userInfo);
			
			Map centerInfo = stcMngService.getSctMngDetailInfo(userInfo);
			
			String smsContent = "";
			Map mapParameter = new HashMap();
			
			String phoneNo = (String) userName.get("PHONE");
			String phone = phoneNo.replaceAll("-", "");
			
			String telNo = (String) centerInfo.get("TELNO");
			String tel = telNo.replaceAll("-", "");

			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); // yyyyMMddHHmm -> yyyyMMddHHmmss 변경
			String curDate = df.format(date);

			mapParameter.put("CALL_TO", userName.get("MBER_NM")+"^"+phone);
			//mapParameter.put("MSG_TYPE", 4);
			mapParameter.put("CNTER_CD", CNTER_CD);
			mapParameter.put("MBER_ID", MBER_ID);
			mapParameter.put("CUR_STATE", 0);
			mapParameter.put("NOW_DATE",curDate);
			mapParameter.put("SEND_DATE",curDate);

			if(AppTestMode == 1) 	mapParameter.put("CALL_FROM", "0632270002");
			else					mapParameter.put("CALL_FROM", tel);
			
			switch (type) {
			case 1:
				smsContent = userName.get("MBER_NM") + "님 "+mber.getCnter_name()+" 서비스 회원가입을 환영합니다.";
				mapParameter.put("USERDATA", 50);
				mapParameter.put("SMS_TXT", smsContent);
				smsSendMapper.sendSms(mapParameter);
				break;
			case 2:
				smsContent = mber.getCnter_name()+" 서비스 회원가입이 승인되었습니다.";
				mapParameter.put("USERDATA", 10);
				mapParameter.put("SMS_TXT", smsContent);
				smsSendMapper.sendSms(mapParameter);
				break;
			case 3:
				Map rvInfo = smsSendMapper.reservationComplete(userInfo);
				smsContent = "차량 예약이 신청 되었습니다.\n배차확정시 다시 알려드리겠습니다.\n예약일시: " + rvInfo.get("RESVE_DT") + "\n출발지: " + rvInfo.get("STRTPNT_ADRES") + "\n도착지: " + rvInfo.get("ALOC_ADRES");
				mapParameter.put("USERDATA", 60);
				mapParameter.put("SMS_TXT", smsContent);
				smsSendMapper.sendSms(mapParameter);
				break;
			case 4:
				Map dpInfo = smsSendMapper.dispatchComplete(userInfo);
				smsContent = "차량 배차가 완료 되었습니다.\n예약일시: " + dpInfo.get("RESVE_DT") + "\n출발지: " + dpInfo.get("STRTPNT_ADRES") + "\n도착지: " + dpInfo.get("ALOC_ADRES")
				+ "\n차량번호: " + dpInfo.get("VHCLE_NO");
				mapParameter.put("USERDATA", 40);
				mapParameter.put("SMS_TXT", smsContent);
				smsSendMapper.sendSms(mapParameter);
				break;
			case 5:
				smsContent = "차량 배차에 실패하였습니다.\n상담원에게 문의해주세요.\n문의전화: " + centerInfo.get("TELNO");
				mapParameter.put("USERDATA", 30);
				mapParameter.put("SMS_TXT", smsContent);
				smsSendMapper.sendSms(mapParameter);
				break;
			case 6:
				smsContent = "예약 접수가 취소 되었습니다.";
				mapParameter.put("USERDATA", 20);
				mapParameter.put("SMS_TXT", smsContent);
				smsSendMapper.sendSms(mapParameter);
				break;
			case 7:
				smsContent = mber.getCnter_name()+" 서비스에서 탈퇴하셨습니다.";
				mapParameter.put("USERDATA", 70);
				mapParameter.put("SMS_TXT", smsContent);
				smsSendMapper.sendSms(mapParameter);
			case 8:
				smsContent = mber.getCnter_name()+"에 가입하신 아이디 : \n" + MBER_ID;
				mapParameter.put("USERDATA", 80);
				mapParameter.put("SMS_TXT", smsContent);
				smsSendMapper.sendSms(mapParameter);
			default:
				break;
			}
		}
	}
	
	@Override
	public void sendSmsPw(String CNTER_CD, String MBER_ID, String pw) throws Exception {
		Map userInfo = new HashMap();
		userInfo.put("CNTER_CD", CNTER_CD);
		userInfo.put("MBER_ID", MBER_ID);
		
		
		Map userName = mngrService.selectUserMngDetail(userInfo);
		
		Map centerInfo = stcMngService.getSctMngDetailInfo(userInfo);
		
		String smsContent = "";
		Map mapParameter = new HashMap();
		
		String phoneNo = (String) userName.get("PHONE");
		String[] array1 = phoneNo.split("-");
		
		String telNo = (String) centerInfo.get("TELNO");
		String[] array2 = telNo.split("-");
		
		String phone = "";
		for(int i=0;i<array1.length;i++){
			phone += array1[i];
		}
		String tel = "";
		for(int i=0;i<array2.length;i++){
			tel += array2[i];
		}

		mapParameter.put("CALL_TO", userName.get("MBER_NM")+"^"+phone);

		if(AppTestMode == 1) 	mapParameter.put("CALL_FROM", "0632270002");
		else					mapParameter.put("CALL_FROM", tel);

		mapParameter.put("CNTER_CD", CNTER_CD);
		mapParameter.put("MBER_ID", MBER_ID);
		mapParameter.put("CUR_STATE", 0);

		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); // yyyyMMddHHmm -> yyyyMMddHHmmss 변경
		String curDate = df.format(date);
		mapParameter.put("SEND_DATE",curDate);
		
		smsContent = "이동지원센터 비밀번호가 초기화 되었습니다. 임시 비밀번호 : \n" + pw;
		mapParameter.put("USERDATA", 90);
		mapParameter.put("SMS_TXT", smsContent);
		smsSendMapper.sendSms(mapParameter);
		
	}
	

}
