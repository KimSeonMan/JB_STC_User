package com.neighbor.ServiceAPI.controller.mapper;

import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("smsSendMapper")
public class SmsSendMapper extends EgovAbstractMapper {
	
	/**
	 * Descript section
	 * @param mapParameter
	 * @exception Exception
	 */
	@Transactional
	public void sendSms(Map mapParameter) throws Exception{
		String msg = mapParameter.get("SMS_TXT").toString().trim();
		String []k= {"",""};
		int i=0;
		
		for(char ch: msg.toCharArray()) {
			if(i<90) { //sms 최대90byte
				i+=String.valueOf(ch).getBytes(Charset.forName("EUC-KR")).length;
				k[0]+=ch;
			}else {
				i+=String.valueOf(ch).getBytes(Charset.forName("EUC-KR")).length;
				k[1]+=ch;
			}	
		}
		
		for(i=0; i<k.length;i++) {
			if(k[i]!="") {
				mapParameter.replace("SMS_TXT", k[i]);
				insert("smsSend.send", mapParameter);
			}
		}
	}
	
	public Map reservationComplete(Map mapParameter) throws Exception{
		return selectOne("smsSend.reservationComplete", mapParameter);
	}
	
	public Map dispatchComplete(Map mapParameter) throws Exception{
		return selectOne("smsSend.dispatchComplete", mapParameter);
	}
}
