package com.neighbor.ServiceAPI.api.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neighbor.ServiceAPI.controller.service.UserSiteTemplateService;

public class MultiFileUpload {
	private static final Log logger = LogFactory.getLog(MultiFileUpload.class);
	private static MultiFileUpload mfu = new MultiFileUpload();
	
	private MultiFileUpload(){
		
	}
	
	public static MultiFileUpload getInstance(){
		return mfu;
	}

	public List fileUpload(MultipartHttpServletRequest mRequest) throws Exception{
		List list = new ArrayList();
		
		List<MultipartFile> fileList = mRequest.getFiles("attachFiles[]");
		
		String PROPERTY_PATH = "/globals.properties";
		ClassPathResource resource = new ClassPathResource(PROPERTY_PATH);
		Properties props;
		
		try{
			props = PropertiesLoaderUtils.loadProperties(resource);
			
			String CNTNTS_TYPE2_CD2 = mRequest.getAttribute("CNTNTS_TYPE2_CD2").toString();
			String CNTNTS_NO = "";
			String NOTICE_CLS_CD2 = "";
			String fileSavePath = ""; 
			String thumbnailfileSavePath = ""; 
			
			if(!CNTNTS_TYPE2_CD2.equals("LINKED_SITE")) {
				CNTNTS_NO = mRequest.getAttribute("CNTNTS_NO").toString();
				NOTICE_CLS_CD2 = mRequest.getAttribute("NOTICE_CLS_CD2").toString();
				
				if(CNTNTS_TYPE2_CD2.equals("40")) { //Q&A
					fileSavePath = props.getProperty("Globals.File.QA.Path");
				} else if(CNTNTS_TYPE2_CD2.equals("50")) { //FAQ
					fileSavePath = props.getProperty("Globals.File.FAQ.Path");
				} else if(CNTNTS_TYPE2_CD2.equals("60")) { //60 공지사항
					fileSavePath = props.getProperty("Globals.File.NOTICE.Path");
					thumbnailfileSavePath = props.getProperty("Globals.File.THUMBNAIL.Path"); 
				} else if(CNTNTS_TYPE2_CD2.equals("70")) { //MEMBER
					fileSavePath = props.getProperty("Globals.File.HANDICAP.Path");
				}
			} else {
				fileSavePath = props.getProperty("Globals.File.LINKED_SITE.Path");
			}
			
			File targetDir = new File(fileSavePath);
			if (!targetDir.exists()){
				targetDir.mkdirs();
			}
			
			for(int i =0; i < fileList.size(); i ++){
				Map map = new HashMap();
				Calendar date = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssS");
				String idDate = df.format(date.getTimeInMillis()) +"_"+ i;
				MultipartFile mpf = fileList.get(i);
				String fileName = mpf.getOriginalFilename();
//				String saveName = mpf.getOriginalFilename()+idDate;
				String saveName = idDate+"_"+mpf.getOriginalFilename();
				
				if(CNTNTS_TYPE2_CD2.equals("LINKED_SITE")) {
					int pos = fileName.lastIndexOf(".");
					File nfile = new File(fileSavePath +"/"+ saveName + "." + fileName.substring(pos+1));
					
					map.put("fileRealName", fileName);
					map.put("file_url", fileSavePath +"/"+ saveName + "." + fileName.substring(pos+1));
					list.add(map);
					
					mpf.transferTo(nfile);
				} else {
					map.put("cntnts_no", CNTNTS_NO);
					map.put("file_attch_id", CNTNTS_TYPE2_CD2);
					map.put("file_url", fileSavePath +"/"+ saveName);
					map.put("fileRealName", fileName);
					map.put("fileSaveName", saveName);
					list.add(map);
					
					if(NOTICE_CLS_CD2.equals("50")) { //갤러리 썸네일 저장
						if(i == 0) {
							int pos = fileName.lastIndexOf(".");
							File nfile = new File(thumbnailfileSavePath +"/"+ saveName + "." + fileName.substring(pos+1));
							mpf.transferTo(nfile);
						}
					}
					
					File file = new File(fileSavePath +"/"+ saveName);
					mpf.transferTo(file);
				}
			}
		}catch(IllegalStateException e){
			logger.error(e);
			e.printStackTrace();
		}catch(IOException e){
			logger.error(e);
			e.printStackTrace();
		}
		
		//Globals.File.AttachFiles
		return list;
	}
}
