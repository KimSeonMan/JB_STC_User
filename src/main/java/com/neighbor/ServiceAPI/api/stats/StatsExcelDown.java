package com.neighbor.ServiceAPI.api.stats;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.neighbor.ServiceAPI.common.controller.AbsQuery;
import com.neighbor.ServiceAPI.controller.service.StatsService;
import com.neighborsystem.durian.exception.AbsException;
import com.neighborsystem.durian.restapi.api.HttpMethod;

public class StatsExcelDown extends AbsQuery<Map>{

	private static final Log logger = LogFactory.getLog(StatsExcelDown.class);
	
	@Resource(name="statsService")
	private StatsService statsService;
	
	@Override
	public Map executeAPI(HttpServletRequest req, HttpServletResponse res, String trId) throws AbsException {
		// TODO Auto-generated method stub
		logger.info("START Query - ApiID[" + this.getApiId() + "] ");
		List header = new ArrayList();
		header.add("운행일자");
		header.add("접수건수");
		header.add("배차건수");
		header.add("취소건수");
		
		Map mapParameter = getParameterMap(req);
		List list = statsService.getStatsList(mapParameter.get("startDate").toString(), mapParameter.get("endDate").toString());
		
		
		XSSFRow row;
		XSSFCell cell = null;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("dataSheet");
		
		int cellCnt = 0;		//컬럼 카운트
		int rowCnt = 0; //로우 카운트
		row = sheet.createRow(rowCnt);
		rowCnt = rowCnt + 1;
		
		for(int i = 0; i < header.size();i++){
			row.createCell(cellCnt).setCellValue(header.get(i).toString());
			cellCnt = cellCnt + 1;
		}
		
		for(int i = 0; i < list.size(); i++){
			row = sheet.createRow(rowCnt);
			cellCnt = 0;
			Map rowMap = (Map) list.get(i);
			
			row.createCell(cellCnt).setCellValue(rowMap.get("SEARCHDATE").toString());
			cellCnt = cellCnt+1;
			row.createCell(cellCnt).setCellValue(rowMap.get("receiptCnt").toString());
			cellCnt = cellCnt+1;
			row.createCell(cellCnt).setCellValue(rowMap.get("allocCnt").toString());
			cellCnt = cellCnt+1;
			row.createCell(cellCnt).setCellValue(rowMap.get("cancelCnt").toString());
			rowCnt = rowCnt + 1;
		}
		
		res.setContentType("application/vnd.ms-excel; charset=UTF-8");
		String filename ="stats.xlsx";
	
		try{
			OutputStream out = null;
			
			String userAgent = req.getHeader("User-Agent");
			boolean ie = userAgent.indexOf("MSIE") > -1; // MSIE 6~10
	        boolean ie11 = userAgent.indexOf("Trident") > -1; // MSIE 11
	        
	        String browserType = req.getHeader("user-agent");
			if(browserType.indexOf("Firefox") > -1) {
				filename = new String (filename.getBytes ("UTF-8"), "iso-8859-1");
				res.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\";");
			} else if(browserType.indexOf("Safari") > -1 && browserType.indexOf("Chrome") < 0) {
				filename = new String (filename.getBytes ("UTF-8"), "iso-8859-1");
				res.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\";");
			} else {
				res.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(filename, "utf-8") + "\";");
			}
			
			res.setHeader("Content-Type", "application/octet-stream");
			res.setHeader("Content-Transfer-Encoding", "binary");
			out = res.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
			
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
		}
		
		
		
		
		return null;
	}

	@Override
	public String getApiId() {
		// TODO Auto-generated method stub
		return "1803";
	}

	@Override
	public HttpMethod getHttpMethod() {
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
		// TODO Auto-generated method stub
		return null;
	}
	
	enum MustParam{
		startDate,endDate
	}
	
	enum OptionParam{
		
	}
	

}
