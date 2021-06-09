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
	W.statHistory = W.$statHistory || {};
	$(document).ready(function() {
		$("#searchStartDate").val($.datepicker.formatDate('yy-mm-dd', new Date()));
		$("#searchEndDate").val($.datepicker.formatDate('yy-mm-dd', new Date()));
		$statHistory.list(1);
		
		$("#searchBtn").click(function(){
			$statHistory.list(1);
		});
		
		$("#excelDown").click(function(){
			$statHistory.excelDown();
		});
	});
	
	$statHistory = {
		totalCnt : 0,
		currentPageIndex : 1,
		list : function (page){
			var senders = new tscp.statHistoryList.api();
			$(".paging").empty();
			senders.addParam("page", page);
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			if($("#searchCNTER_NM").val() != "") {
				senders.addParam("searchCNTER_NM", $("#searchCNTER_NM").val());
			}
			if($("#searchDATE_OPTION").val() != "") {
				senders.addParam("searchDATE_OPTION", $("#searchDATE_OPTION").val());
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
				url : contextPath + "/ServiceAPI/mngr/statHistory.json",
			});
		}
	
		, excelDown : function() {	
			var senders = new tscp.statHistoryExcelDown.api();
			senders.addParam("CNTER_CD", session.cnter_cd);
			if($("#searchCNTER_NM").val() != "") {
				senders.addParam("searchCNTER_NM", $("#searchCNTER_NM").val());
			}
			if($("#searchDATE_OPTION").val() != "") {
				senders.addParam("searchDATE_OPTION", $("#searchDATE_OPTION").val());
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
				url : contextPath + "/ServiceAPI/mngr/statHistoryExcelDown.json",
			});
		}
	};
	/***********배차관련설정정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.statHistoryList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				$(".listTable").empty(); //리스트 삭제
				$("#excelTable").empty();
				var result = res.result;
				
				var html = "";
				html += "<colgroup>";
				html += "<col width='70' />";
				html += "<col width='130' />";
				html += "<col width='130' />";
				html += "<col width='120' />";
				html += "<col width='120' />";
				html += "<col width='80' />";
				html += "<col width='60' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>구분</th>";
				html += "<th>이용센터명</th>";
				html += "<th>소속센터명</th>";
				html += "<th>출발지</th>";
				html += "<th>도착지</th>";
				html += "<th>거리(km)</th>";
				html += "<th>운임(원)</th>";
				html += "</tr>";
				
				
				if(result.statHistoryList.length == 0) {
					html += "<tr><td colspan='7'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.statHistoryList.length; i++) {
						html += "<tr>";
						html += "<td>"+result.statHistoryList[i].RESVE_DT+"</td>";
						html += "<td>"+result.statHistoryList[i].CNTER_NM+"</td>";
						if(result.statHistoryList[i].SOSOK_CNTER_NM == session.CNTER_CD){
							html += "<td>"+result.statHistoryList[i].SOSOK_CNTER_NM+"</td>";
						} else {
							html += "<td>"+result.statHistoryList[i].SOSOK_CNTER_NM+"</td>";
						}
						html += "<td>"+result.statHistoryList[i].STRTPNT_ADRES+"</td>";
						html += "<td>"+result.statHistoryList[i].ALOC_ADRES+"</td>";
						html += "<td>"+result.statHistoryList[i].MVMN_DSTNC+"</td>";
						html += "<td>"+result.statHistoryList[i].CYCHG+"</td>";
						html += "</tr>";
					}
				}
				
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.statHistoryListCount[0].CNT+" 건");
				$statHistory.totalCnt = result.statHistoryListCount[0].CNT;
				$("#TOTAL_CNT").val(result.statHistoryListCount[0].CNT);
				paging($statHistory,"list");
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 정산관리 목록 API 호출 End **********/
	
	/*********** 정산관리 엑셀다운 API 호출 Start **********/
	(function() {
		$class("tscp.statHistoryExcelDown.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				$("#excelTable").empty(); //리스트 삭제
				var result = res.result;
				
				var html = "";
				html += "<colgroup>";
				html += "<col width='80' />";
				html += "<col width='150' />";
				html += "<col width='150' />";
				html += "<col width='80' />";
				html += "<col width='80' />";
				html += "<col width='80' />";
				html += "<col width='120' />";
				html += "<col width='80' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>구분</th>";
				html += "<th>이용센터명</th>";
				html += "<th>소속센터명</th>";
				html += "<th>출발지</th>";
				html += "<th>도착지</th>";
				html += "<th>거리(km)</th>";
				html += "<th>운임(원)</th>";
				html += "</tr>";
				
				if(result.statHistoryList.length == 0) {
					html += "<tr><td colspan='7'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.statHistoryList.length; i++) {
						html += "<tr>";
						html += "<td>"+result.statHistoryList[i].RESVE_DT+"</td>";
						html += "<td>"+result.statHistoryList[i].CNTER_NM+"</td>";
						if(result.statHistoryList[i].SOSOK_CNTER_NM == session.CNTER_CD){
							html += "<td>"+result.statHistoryList[i].SOSOK_CNTER_NM+"</td>";
						} else {
							html += "<td>"+result.statHistoryList[i].SOSOK_CNTER_NM+"</td>";
						}
						html += "<td>"+result.statHistoryList[i].STRTPNT_ADRES+"</td>";
						html += "<td>"+result.statHistoryList[i].ALOC_ADRES+"</td>";
						html += "<td>"+result.statHistoryList[i].MVMN_DSTNC+"</td>";
						html += "<td>"+result.statHistoryList[i].CYCHG+"</td>";
						html += "</tr>";
					}
				}
				
				$("#excelTable").html(html);
				downExcel();
				$statHistory.list(1);
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
		filename: "정산관리_" + new Date().toISOString().replace(/[\-\:\.]/g, ""),
		fileext: ".xls",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});
};
