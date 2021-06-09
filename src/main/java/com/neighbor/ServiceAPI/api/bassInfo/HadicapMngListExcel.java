package com.neighbor.ServiceAPI.api.bassInfo;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.BassInfoService;
import com.neighbor.ServiceAPI.exception.ApiException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import com.neighborsystem.durian.exception.AbsAPIException;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

/**
 * 1. 기능 : 정보컨텐츠관리 목록 Excel다운 클래스.<p>
 * 2. 처리개요 : <p>
 * 3. 주의사항 : <p>
 *  <pre>
 *  <b>History:</b> 
 *     작성자 : 김희철, 1.0, 2017/02/08  초기 작성
 *  </pre>
 *  
 * @author 최종 수정자 : 김희철
 * @version 1.0
 * @see
 * <p/>
 */
public class HadicapMngListExcel extends AbsQuery<Map> {
	private static final Log logger = LogFactory.getLog(HadicapMngListExcel.class);
	
	@Resource(name="bassInfoService")
	private BassInfoService bassInfoService;
	
	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1302";
	}

	@Override
	public HttpMethod getHttpMethod() {
		// TODO Auto-generated method stub
		return HttpMethod.POST;
	}

	@Override
	public Class getMustParameter() throws AbsException {
		// TODO Auto-generated method stub
		return MustParam.class;
	}

	@Override
	public Class getOptionParameter() throws AbsException {
		// TODO Auto-generated method stub
		return OptionParam.class;
	}

	@Override
	protected String getQueryStr() {
		return null;
	}
	
	enum MustParam
	{
		TOTAL_CNT
	}
	
	enum OptionParam
	{
		NAME, ADDRESS, SUPPORTER_YN
	}	
	
	@Override
	public Map executeAPI(HttpServletRequest req, HttpServletResponse res,
			String trId) throws AbsException {
		// TODO Auto-generated method stub
		httpSession = req.getSession();
		
		Map resultData = new HashMap();

		try {
			logger.info("START Query - ApiID[" + this.getApiId() + "] ");

			Map mapParameter = getParameterMap(req);

			_checkNullParameterValue(mapParameter);
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = null;
			XSSFRow row = null;
			XSSFCell cell = null;
			String[] headerList = {"자격번호","이름","생년월일","연락처","장애종류","보조인유무","휠체어유무","의사소통여부","SMS가능여부","등록자","등록일"};
			/*FileOutputStream out = new FileOutputStream("D:\\이용자정보목록.xlsx");*/
			OutputStream out = res.getOutputStream();
			
			try {
				List hadicapMngList = bassInfoService.selectHadicapMngList(mapParameter);
				
				if(hadicapMngList != null && hadicapMngList.size() > 0) {
					sheet = (XSSFSheet) workbook.createSheet("이용자정보목록");
					for(int i=0; i<hadicapMngList.size(); i++) {
						//sheet = (XSSFSheet) workbook.createSheet("이용자정보목록");
						row = (XSSFRow) sheet.createRow(0);
						
						for(int l=0; l<headerList.length; l++) {
							cell = (XSSFCell) row.createCell(l);
							cell.setCellValue(headerList[l]);
						}
						
						int total = Integer.parseInt(req.getParameter(MustParam.TOTAL_CNT.name()).toString());
						int rowSCnt = 1;
						int rowECnt = 10000;
						int num = 1;
						
						if(total > 0) {
							if(total < 10000) {
								rowECnt = total;
							} else {
								num = (int) Math.ceil(total/10000)+1;
							}
							for(int z=0; z<num; z++) {
								if(z>0) {
									rowSCnt = rowECnt + 1;
									rowECnt = 10000 * (z+1);
									if(z == num-1) {
										rowECnt = total;
									}
								}
								
								for(int j=0; j<rowECnt-rowSCnt+1; j++) {
									row = (XSSFRow) sheet.createRow(rowSCnt+j);
									Map hadicapMng = (Map) hadicapMngList.get(j);
									
									for(int k=0; k<headerList.length; k++) {
										cell = (XSSFCell) row.createCell(k);
										switch(k){
											case 0://자격번호
												cell.setCellValue(hadicapMng.get("WELFAIR_CARD_NO").toString());
												break;	
											case 1://이름
												cell.setCellValue(hadicapMng.get("NAME").toString());
												break;
											case 2://생년월일
												cell.setCellValue(hadicapMng.get("BIRTH_DATE").toString());
												break;	
											case 3://연락처
												cell.setCellValue(hadicapMng.get("MOBILE").toString());
												break;	
											case 4://장애종류
												cell.setCellValue(hadicapMng.get("DISABLE_TYPE1_DESC").toString());
												break;	
											case 5://보조인유무
												cell.setCellValue(hadicapMng.get("SUPPORTER_YN").toString());
												break;	
											case 6://휠체어유무
												cell.setCellValue(hadicapMng.get("WHEELCHAIR_CD").toString());
												break;	
											case 7://의사소통여부
												cell.setCellValue(hadicapMng.get("COMMUNICALBE_YN").toString());
												break;	
											case 8://SMS가능여부
												cell.setCellValue(hadicapMng.get("SMS_RECEIVE_YN").toString());
												break;	
											case 9://등록자
												cell.setCellValue(hadicapMng.get("REG_USER_ID").toString());
												break;	
											case 10://등록일
												cell.setCellValue(hadicapMng.get("REG_TS").toString());
												break;	
										}
									}
								}
							}
						}
					}
				}
				
				res.setContentType("application/vnd.ms-excel; charset=UTF-8");
				String fileName = "이용자정보목록.xlsx";
				String userAgent = req.getHeader("User-Agent");
				
				boolean ie = userAgent.indexOf("MSIE") > -1;
				boolean ie11 = userAgent.indexOf("Trident") > -1;
				
				if(userAgent.indexOf("Firefox") > -1){
					fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
					res.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\";");
				}else if(userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") < 0){
					fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
					res.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\";");
				}else{
					res.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\";");
				}
				res.setHeader("Content-Type", "application/octet-stream");
				res.setHeader("Content-Transfer-Encoding", "binary");
				workbook.write(out);
				out.flush();
				out.close();
			} catch(Exception e) {
				out.close();
				logger.error(e);
				e.printStackTrace();
			}
			
			logger.info("END Query - TXID[" + getApiId() + "] ");
		}catch (AbsAPIException e) {
			logger.error(e);
			throw e;
		} catch (IllegalArgumentException e) {
			logger.error(e);
			throw new ApiException("Input Error");
		} catch (Exception e) {
			logger.error(e);
			throw new ApiException("Error");
		}
		return resultData;
	}
}