package com.neighbor.ServiceAPI.controller.service;

import java.util.Map;

public interface SmsSendService {

	public void sendSms(int type,String CNTER_CD,String MBER_ID,String RESVE_DT) throws Exception;
	public void sendSmsPw(String CNTER_CD,String MBER_ID,String pw) throws Exception;
	
}
