/**
 * 배차관련 설정 관리
 * 
 * history : 웨이브엠(주), 1.0, 2018/01/04  초기 작성
 * author : 송용석
 * version : 1.0
 * see : 
 *
 */
var fileInfo = "";
(function(W, D) {
	//"use strict";
	W.smsHistory = W.$smsHistory || {};
	$(document).ready(function() {
		$("#searchStartDate").val($.datepicker.formatDate('yy-mm-dd', new Date()));
		$("#searchEndDate").val($.datepicker.formatDate('yy-mm-dd', new Date()));
		$smsHistory.list(1);
		
		$("#searchBtn").click(function(){
			$smsHistory.list(1);
		});
		
		$("#excelDown").click(function(){
			$smsHistory.excelDown();
		});
	});
	
	$smsHistory = {
		totalCnt : 0,
		currentPageIndex : 1,
		list : function (page){
			var senders = new tscp.smsHistoryList.api();
			$(".paging").empty();
			senders.addParam("page", page);
			
			if($("#searchNM").val() != "") {
				senders.addParam("searchNM", $("#searchNM").val());
			}
			if($("#searchSMS_TYPE").val() != "") {
				senders.addParam("searchSMS_TYPE", $("#searchSMS_TYPE").val());
			}
			if($("#searchStartDate").val() != "") {
				senders.addParam("searchStartDate", $("#searchStartDate").val());
			}
			if($("searchEndDate").val() != "") {
				senders.addParam("searchEndDate", $("#searchEndDate").val());
			}
			
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/smsHistory.json",
			});
		}
	
		, excelDown : function() {	
			var senders = new tscp.smsHistoryExcelDown.api();
			if($("#searchNM").val() != "") {
				senders.addParam("searchNM", $("#searchNM").val());
			}
			if($("#searchSMS_TYPE").val() != "") {
				senders.addParam("searchSMS_TYPE", $("#searchSMS_TYPE").val());
			}
			if($("#searchStartDate").val() != "") {
				senders.addParam("searchStartDate", $("#searchStartDate").val());
			}
			if($("searchEndDate").val() != "") {
				senders.addParam("searchEndDate", $("#searchEndDate").val());
			}
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/smsHistoryExcelDown.json",
			});
		}
	};
	
	var basicOptionHtml = "<option value=''>선택</option>";
	var optionHtml = "<option value='[OPTION_VALUE]'>[OPTION_TEXT]</option>";
	/***********배차관련설정정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.smsHistoryList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				$(".listTable").empty(); //리스트 삭제
				$("#excelTable").empty();
				var result = res.result;
				
				// 검색 옵션 설정
				var smsTypeOption = basicOptionHtml;
				var smsTypeOrigin = $("#searchSMS_TYPE").val();
				$("#searchSMS_TYPE").empty();
				for(var i = 0; i < result.smsTypeList.length; i++) {
					smsTypeOption += optionHtml.replace("[OPTION_VALUE]", result.smsTypeList[i].CD_VALUE)
					.replace("[OPTION_TEXT]", result.smsTypeList[i].CD_VALUE_DE);
				}
				$("#searchSMS_TYPE").html(smsTypeOption);
				$("#searchSMS_TYPE").val(smsTypeOrigin);
			
				var html = "";
				html += "<colgroup>";
				html += "<col width='40' />";
				html += "<col width='100' />";
				html += "<col width='60' />";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "<col width='80' />";
				html += "<col width='120' />";
				html += "<col width='80' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>No.</th>";
				html += "<th>구분</th>";
				html += "<th>이름</th>";
				html += "<th>수신번호</th>";
				html += "<th>송신번호</th>";
				html += "<th>발송상태</th>";
				html += "<th>발송일시</th>";
				html += "<th>재발송</th>";
				html += "</tr>";
				
				
				if(result.smsHistoryList.length == 0) {
					html += "<tr><td colspan='8'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.smsHistoryList.length; i++) {
						html += "<tr>";
						html += "<td>"+result.smsHistoryList[i].rnum+"</td>";
						html += "<td>"+result.smsHistoryList[i].TR_TYPE_NM+"</td>";
						html += "<td>"+result.smsHistoryList[i].MBER_NM+"</td>";
						html += "<td>"+result.smsHistoryList[i].TR_PHONE+"</td>";
						html += "<td>"+result.smsHistoryList[i].TR_CALLBACK+"</td>";
						html += "<td>"+result.smsHistoryList[i].TR_RSLTSTAT+"</td>";
						html += "<td>"+result.smsHistoryList[i].TR_SENDDATE+"</td>";
						html += '<td><a href="javascript:void(0)" class="btn02 tbInBtn">재발송</a></td>';
						html += "</tr>";
					}
				}
				
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.smsHistoryListCount[0].CNT+" 건");
				$smsHistory.totalCnt = result.smsHistoryListCount[0].CNT;
				$("#TOTAL_CNT").val(result.smsHistoryListCount[0].CNT);
				paging($smsHistory,"list");
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 정산관리 목록 API 호출 End **********/
	
	/*********** 정산관리 엑셀다운 API 호출 Start **********/
	(function() {
		$class("tscp.smsHistoryExcelDown.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				$("#excelTable").empty(); //리스트 삭제
				var result = res.result;
				
				var html = "";
				html += "<colgroup>";
				html += "<col width='40' />";
				html += "<col width='100' />";
				html += "<col width='60' />";
				html += "<col width='' />";
				html += "<col width='' />";
				html += "<col width='80' />";
				html += "<col width='120' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>No.</th>";
				html += "<th>구분</th>";
				html += "<th>이름</th>";
				html += "<th>수신번호</th>";
				html += "<th>송신번호</th>";
				html += "<th>발송상태</th>";
				html += "<th>발송일시</th>";
				html += "</tr>";
				
				
				if(result.smsHistoryList.length == 0) {
					html += "<tr><td colspan='8'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.smsHistoryList.length; i++) {
						html += "<tr>";
						html += "<td>"+result.smsHistoryList[i].rnum+"</td>";
						html += "<td>"+result.smsHistoryList[i].TR_TYPE_NM+"</td>";
						html += "<td>"+result.smsHistoryList[i].MBER_NM+"</td>";
						html += "<td>"+result.smsHistoryList[i].TR_PHONE+"</td>";
						html += "<td>"+result.smsHistoryList[i].TR_CALLBACK+"</td>";
						html += "<td>"+result.smsHistoryList[i].TR_RSLTSTAT+"</td>";
						html += "<td>"+result.smsHistoryList[i].TR_SENDDATE+"</td>";
						html += "</tr>";
					}
				}
				
				$("#excelTable").html(html);
				downExcel();
				$smsHistory.list(1);
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 배차관련설정정보 확인 API 호출 End **********/
	
}(window, document));

downExcel = function(){
	$("#excelTable").table2excel({
		exclude: ".noExl",
		name: "Excel Document Name",
		filename: "sms발송이력관리_" + new Date().toISOString().replace(/[\-\:\.]/g, ""),
		fileext: ".xls",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});
};
