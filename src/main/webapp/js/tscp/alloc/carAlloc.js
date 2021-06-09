/**
 * 차량배차목록 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
(function(W, D) {
	//"use strict";
	W.carAlloc = W.$carAlloc || {};
	$(document).ready(function() {
		$carAlloc.statSearch();
		$carAlloc.list(1);
		
		codeDetailSearch("ALLOC_STAT_CD", "ALLOC_STAT_CD"); //배차상태코드
		
		//엔터키
		$('#NAME').on('keypress', function(e) {
			if (e.which == 13) {
				$carAlloc.list(1);
			}
		});
		
		//조회조건 selectbox 이벤트
		$("#ALLOC_STAT_CD").change(function(){
			$carAlloc.list(1);
		});
		
		$("#searchList").click(function(){
			$carAlloc.list(1);
		});
	});
	
	$carAlloc = {
		totalCnt : 0,
		currentPageIndex : 1,
		statSearch : function() {	
			var senders = new tscp.statSearch.api();
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/alloc/statSearch.json",
			});
		},
		list : function(page) {	
			$(".paging").empty();
			var senders = new tscp.carAllocList.api();
			senders.addParam("page", page);
			if($("#ALLOC_STAT_CD").val() != "") {
				senders.addParam("ALLOC_STAT_CD", $("#ALLOC_STAT_CD").val());
			}
			if($("#NAME").val() != "") {
				senders.addParam("NAME", $("#NAME").val());
			}
			if($("#ST_DT").val() != "" && $("#EN_DT").val() != "") {
				if($("#ST_DT").val() <= $("#EN_DT").val()) {
					senders.addParam("ST_DT", $("#ST_DT").val());
					senders.addParam("EN_DT", $("#EN_DT").val());
				} else {
					alert("배차 시작일이 종료일보다 큽니다. 다시 확인해주세요.");
					$("#ST_DT").focus();
					return false;
				}
			}
			
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/alloc/carAllocList.json",
			});
		},
		carAllocDetail : function(str1, str2) {	//배차상세정보
			var senders = new tscp.carAllocDetail.api();
			senders.addParam("USER_ID", str1);
			senders.addParam("RECEIPT_DATETIME", str2);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/alloc/carAllocDetail.json",
			});
		}
	};
	
	/*********** 차량배차통계현황 API 호출 Start **********/
	(function() {
		$class("tscp.statSearch.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				var html = "";
				
				var TOTAL_CNT = 0; //전체 건수
				var RECEIPT_CNT = 0; //전체 접수 건수 : 10
				var CANCEL_CNT = 0; //전체 취소 건수 : 00
				var ALLOC_CNT = 0; //전체 배차완료 건수 : 20
				var FAIL_CNT = 0; //전체 배차실패 건수 : 30
				var DAY_TOTAL_CNT = 0; //금일 건수
				var DAY_RECEIPT_CNT = 0; //금일 접수 건수 : 10
				var DAY_CANCEL_CNT = 0; //금일 취소 건수 : 00
				var DAY_ALLOC_CNT = 0; //금일 배차완료 건수 : 20
				var DAY_FAIL_CNT = 0; //금일 배차실패 건수 : 30
				
				if(result.statSearch.length > 0) {
					for(var i=0; i<result.statSearch.length; i++) {
						if(result.statSearch[i].GUBN == "ALL") {
							if(result.statSearch[i].ALLOC_STAT_CD == "10") { //접수
								RECEIPT_CNT = result.statSearch[i].CNT;
							} else if(result.statSearch[i].ALLOC_STAT_CD == "00") { //취소
								CANCEL_CNT = result.statSearch[i].CNT;
							} else if(result.statSearch[i].ALLOC_STAT_CD == "20") { //배차완료
								ALLOC_CNT = result.statSearch[i].CNT;
							} else if(result.statSearch[i].ALLOC_STAT_CD == "30") { //배차실패
								FAIL_CNT = result.statSearch[i].CNT;
							}
						} else {
							if(result.statSearch[i].ALLOC_STAT_CD == "10") { //접수
								DAY_RECEIPT_CNT = result.statSearch[i].CNT;
							} else if(result.statSearch[i].ALLOC_STAT_CD == "00") { //취소
								DAY_CANCEL_CNT = result.statSearch[i].CNT;
							} else if(result.statSearch[i].ALLOC_STAT_CD == "20") { //배차완료
								DAY_ALLOC_CNT = result.statSearch[i].CNT;
							} else if(result.statSearch[i].ALLOC_STAT_CD == "30") { //배차실패
								DAY_FAIL_CNT = result.statSearch[i].CNT;
							}
						}
					}
				}
				
				TOTAL_CNT = RECEIPT_CNT + CANCEL_CNT + ALLOC_CNT + FAIL_CNT;
				DAY_TOTAL_CNT = DAY_RECEIPT_CNT + DAY_CANCEL_CNT + DAY_ALLOC_CNT + DAY_FAIL_CNT;
				
				var dt = new Date();
				var month = dt.getMonth()+1;
				
				html += "<tr>";
				html += "<th>전체건수("+month+"월)</th>";
				html += "<td>"+TOTAL_CNT;
				html += "건  ( 접수 : "+RECEIPT_CNT+"건 / 취소 : "+CANCEL_CNT+"건 / ";
				html += "배차 : "+ALLOC_CNT+"건 / 실패 : "+FAIL_CNT+"건 )</td>";
				html += "</tr>";
				html += "<th>금일건수</th>";
				html += "<td>"+DAY_TOTAL_CNT;
				html += "건  ( 접수 : "+DAY_RECEIPT_CNT+"건 / 취소 : "+DAY_CANCEL_CNT+"건 / ";
				html += "배차 : "+DAY_ALLOC_CNT+"건 / 실패 : "+DAY_FAIL_CNT+"건 )</td>";
				html += "</tr>";
				
				$("#area1").html(html);
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 차량배차통계현황 API 호출 End **********/
	
	/*********** 차량배차목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.carAllocList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$(".listTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<colgroup>";
				html += "<col width='30' />";
				html += "<col width='50' />";
				html += "<col width='' />";
				html += "<col width='80' />";
				html += "<col width='80' />";
				html += "<col width='50' />";
				html += "<col width='50' />";
				html += "<col width='50' />";
				html += "<col width='80' />";
				html += "<col width='80' />";
				html += "<col width='50' />";
				html += "<col width='50' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>No</th>";
				html += "<th>회원ID</th>";
				html += "<th>예약자명</th>";
				html += "<th>예약일시</th>";
				html += "<th>배차일시</th>";
				html += "<th>탑승인원</th>";
				html += "<th>차량번호</th>";
				html += "<th>운전자ID</th>";
				html += "<th>출발지</th>";
				html += "<th>목적지</th>";
				html += "<th>목적</th>";
				html += "<th>배차상태</th>";
				html += "</tr>";

				if(result.carAllocList.length == 0) {
					html += "<tr><td colspan='12'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.carAllocList.length; i++) {
						html += "<tr style='cursor:pointer;' onclick=\"javascript:$carAlloc.carAllocDetail(\'"
								+result.carAllocList[i].USER_ID+"\',\'"
								+result.carAllocList[i].RECEIPT_DATETIME+"\');\">";
						html += "<td>"+result.carAllocList[i].rnum+"</td>";
						html += "<td>"+result.carAllocList[i].USER_ID+"</td>";
						html += "<td>"+result.carAllocList[i].NAME+"</td>";
						html += "<td>"+result.carAllocList[i].RECEIPT_DATETIME+"</td>";
						html += "<td>"+result.carAllocList[i].ALLOC_DATETIME+"</td>";
						html += "<td>"+result.carAllocList[i].PASSENSERS+"</td>";
						html += "<td>"+result.carAllocList[i].CAR_NO+"</td>";
						html += "<td>"+result.carAllocList[i].DRIVER_ID+"</td>";
						html += "<td>"+result.carAllocList[i].DEPT_DESC+"</td>";
						html += "<td>"+result.carAllocList[i].DEST_DESC+"</td>";
						html += "<td>"+result.carAllocList[i].PURPOSE_DESC+"</td>";
						html += "<td>"+result.carAllocList[i].ALLOC_STAT_DESC+"</td>";
						html += "</tr>";
					}
				}
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.carAllocListCount[0].CNT+" 건");
				$carAlloc.totalCnt = result.carAllocListCount[0].CNT;
				paging($carAlloc,"carAllocList");
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 차량배차목록조회 API 호출 End **********/
	
	/*********** 차량배차상세정보 API 호출 Start **********/
	(function() {
		$class("tscp.carAllocDetail.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				$("#val1").html(result.carAllocDetail.ALLOC_STAT_DESC);
				$("#val2").html(result.carAllocDetail.USER_ID);
				$("#val3").html(result.carAllocDetail.NAME);
				$("#val4").html(result.carAllocDetail.DEPT_DESC); //출발지
				$("#val5").html(result.carAllocDetail.DEST_DESC); //목적지
				$("#val6").html(result.carAllocDetail.RECEIPT_DATETIME);
				$("#val7").html(result.carAllocDetail.PURPOSE_DESC);
				$("#val8").html(result.carAllocDetail.CAR_NO);
				$("#val9").html(result.carAllocDetail.PASSENSERS);
				$("#val10").html(result.carAllocDetail.GETIN_DATETIME);
				$("#val11").html(result.carAllocDetail.GETOFF_DATETIME);
				$("#val12").html(result.carAllocDetail.DRIVER_ID);
				$("#val13").html(result.carAllocDetail.DRIVER_NAME);
				$("#val14").html(result.carAllocDetail.EXP_TRAVEL_DISTANCE);
				$("#val15").html(result.carAllocDetail.EXP_TRAVEL_HOURS);
				$("#val16").html(result.carAllocDetail.EXP_SERICE_FEE);
				$("#val17").html(result.carAllocDetail.TRAVEL_DISTANCE);
				$("#val18").html(result.carAllocDetail.TRAVEL_HOURS);
				$("#val19").html(result.carAllocDetail.SERVICE_FEE);
				$("#val20").html(result.carAllocDetail.REMARK);
				
				$("#carAllocPopup").show();
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 차량배차상세정보 API 호출 End **********/
	
}(window, document));