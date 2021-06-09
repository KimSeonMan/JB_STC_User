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
	W.emgHistoryMng = W.$emgHistoryMng || {};
	$(document).ready(function() {
		$("#searchStartDate").val($.datepicker.formatDate('yy-mm-dd', new Date()));
		$("#searchEndDate").val($.datepicker.formatDate('yy-mm-dd', new Date()));
		$emgHistoryMng.list(1);
		
		$("#searchBtn").click(function(){
			$emgHistoryMng.list(1);
		});
		
		$("#excelDown").click(function(){
			$emgHistoryMng.excelDown();
		});
	});
	
	$emgHistoryMng = {
		totalCnt : 0,
		currentPageIndex : 1,
		list : function (page){
			var senders = new tscp.emgHistoryList.api();
			$(".paging").empty();
			senders.addParam("page", page);
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			if($("#searchCNTER_CD").val() != "") {
				senders.addParam("searchCNTER_CD", $("#searchCNTER_CD").val());
			}
			if($("#searchNm").val() != "") {
				senders.addParam("searchNm", $("#searchNm").val());
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
				url : contextPath + "/ServiceAPI/mngr/emgHistoryList.json",
			});
		}
	
		, excelDown : function() {	
			var senders = new tscp.emgHistoryExcelDown.api();
			senders.addParam("CNTER_CD", session.cnter_cd);
			if($("#searchCNTER_CD").val() != "") {
				senders.addParam("searchCNTER_CD", $("#searchCNTER_CD").val());
			}
			if($("#searchNm").val() != "") {
				senders.addParam("searchNm", $("#searchNm").val());
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
				url : contextPath + "/ServiceAPI/mngr/emgHistoryExcelDown.json",
			});
		}
	};
	var basicOptionHtml = "<option value=''>선택</option>";
	var optionHtml = "<option value='[OPTION_VALUE]'>[OPTION_TEXT]</option>";
	/***********배차관련설정정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.emgHistoryList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				$(".listTable").empty(); //리스트 삭제
				$("#excelTable").empty();
				var result = res.result;
				
				// 검색 옵션 설정
				var cnterOption = basicOptionHtml;
				var cnterOriginOption = $("#searchCNTER_CD").val();
				$("#searchCNTER_CD").empty();
				if(result.searchCnter.length == 1) {
					cnterOption = optionHtml.replace("[OPTION_VALUE]", result.searchCnter[0].CNTER_CD)
					.replace("[OPTION_TEXT]", result.searchCnter[0].CNTER_CD + "(" + result.searchCnter[0].CNTER_NM + ")");
					$("#searchCNTER_CD").html(cnterOption);
					$("#searchCNTER_CD").val(result.searchCnter[0].CNTER_CD);
				} else {
					for(var i = 0; i < result.searchCnter.length; i++) {
						cnterOption += optionHtml.replace("[OPTION_VALUE]", result.searchCnter[i].CNTER_CD)
						.replace("[OPTION_TEXT]", result.searchCnter[i].CNTER_NM);
					}
					$("#searchCNTER_CD").html(cnterOption);
					$("#searchCNTER_CD").val(cnterOriginOption);
				}
				
				var html = "";
				html += "<colgroup>";
				html += "<col width='30' />";
				html += "<col width='150' />";
				html += "<col width='60' />";
				html += "<col width='50' />";
				html += "<col width='' />";
				html += "<col width='120' />";
				html += "<col width='55' />";
				html += "<col width='60' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>No</th>";
				html += "<th>센터구분</th>";
				html += "<th>회원구분</th>";
				html += "<th>이름</th>";
				html += "<th>주소</th>";
				html += "<th>발생일시</th>";
				html += "<th>확인여부</th>";
				html += "<th>확인자ID</th>";
				html += "</tr>";
				
				if(result.emgHistoryList.length == 0) {
					html += "<tr><td colspan='8'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.emgHistoryList.length; i++) {
						html += "<tr>";
						html += "<td>"+result.emgHistoryList[i].rnum+"</td>";
						html += "<td>"+result.emgHistoryList[i].CNTER_NM+"</td>";
						html += "<td>"+result.emgHistoryList[i].MBER_SE_VALUE+"</td>";
						html += "<td>"+result.emgHistoryList[i].MBER_NM+"</td>";
						html += "<td>"+result.emgHistoryList[i].ADRES+"</td>";
						html += "<td>"+result.emgHistoryList[i].COLCT_DT+"</td>";
						html += "<td>"+result.emgHistoryList[i].CNFIRM_VALUE+"</td>";
						html += "<td>"+result.emgHistoryList[i].CNFRMR_ID+"</td>";
						html += "</tr>";
					}
				}
				
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.emgHistoryListCount[0].CNT+" 건");
				$emgHistoryMng.totalCnt = result.emgHistoryListCount[0].CNT;
				$("#TOTAL_CNT").val(result.emgHistoryListCount[0].CNT);
				paging($emgHistoryMng,"list");
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 배차관련설정정보 확인 API 호출 End **********/
	
	/***********배차관련설정정보 확인 API 호출 Start **********/
	(function() {
		$class("tscp.emgHistoryExcelDown.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				$("#excelTable").empty(); //리스트 삭제
				var result = res.result;
				
				// 검색 옵션 설정
				var cnterOption = basicOptionHtml;
				var cnterOriginOption = $("#searchCNTER_CD").val();
				$("#searchCNTER_CD").empty();
				if(result.searchCnter.length == 1) {
					cnterOption = optionHtml.replace("[OPTION_VALUE]", result.searchCnter[0].CNTER_CD)
					.replace("[OPTION_TEXT]", result.searchCnter[0].CNTER_CD + "(" + result.searchCnter[0].CNTER_NM + ")");
					$("#searchCNTER_CD").html(cnterOption);
					$("#searchCNTER_CD").val(result.searchCnter[0].CNTER_CD);
				} else {
					for(var i = 0; i < result.searchCnter.length; i++) {
						cnterOption += optionHtml.replace("[OPTION_VALUE]", result.searchCnter[i].CNTER_CD)
						.replace("[OPTION_TEXT]", result.searchCnter[i].CNTER_NM);
					}
					$("#searchCNTER_CD").html(cnterOption);
					$("#searchCNTER_CD").val(cnterOriginOption);
				}
				
				var html = "";
				html += "<colgroup>";
				html += "<col width='30' />";
				html += "<col width='150' />";
				html += "<col width='60' />";
				html += "<col width='50' />";
				html += "<col width='' />";
				html += "<col width='120' />";
				html += "<col width='55' />";
				html += "<col width='60' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>No</th>";
				html += "<th>센터구분</th>";
				html += "<th>회원구분</th>";
				html += "<th>이름</th>";
				html += "<th>주소</th>";
				html += "<th>발생일시</th>";
				html += "<th>확인여부</th>";
				html += "<th>확인자ID</th>";
				html += "</tr>";
				
				if(result.emgHistoryList.length == 0) {
					html += "<tr><td colspan='8'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.emgHistoryList.length; i++) {
						html += "<tr>";
						html += "<td>"+result.emgHistoryList[i].rnum+"</td>";
						html += "<td>"+result.emgHistoryList[i].CNTER_NM+"</td>";
						html += "<td>"+result.emgHistoryList[i].MBER_SE_VALUE+"</td>";
						html += "<td>"+result.emgHistoryList[i].MBER_NM+"</td>";
						html += "<td>"+result.emgHistoryList[i].ADRES+"</td>";
						html += "<td>"+result.emgHistoryList[i].COLCT_DT+"</td>";
						html += "<td>"+result.emgHistoryList[i].CNFIRM_VALUE+"</td>";
						html += "<td>"+result.emgHistoryList[i].CNFRMR_ID+"</td>";
						html += "</tr>";
					}
				}
				
				$("#excelTable").html(html);
				downExcel();
				$emgHistoryMng.list(1);
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
		filename: "긴급호출관리이력_" + new Date().toISOString().replace(/[\-\:\.]/g, ""),
		fileext: ".xls",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});
};
