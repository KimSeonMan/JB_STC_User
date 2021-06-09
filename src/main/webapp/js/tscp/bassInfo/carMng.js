/**
 * 차량정보관리 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */

(function(W, D) {
	//"use strict";
	W.carMng = W.$carMng || {};
	$(document).ready(function() {
		$carMng.list(1); //목록 조회
		
		//엔터키
		$('#searchVHCLE_NO').on('keypress', function(e) {
			if (e.which == 13) {
				$carMng.list(1);
			}
		});
		
		$("#searchList").click(function(){
			$carMng.list(1);
		});
		
		$("#carMngOpen").click(function(){
			$("#carMngAdd").show();
			$("#carMngUpdate").hide();
			$("#carMngDeleteModal").hide();
			$("#area3").show();
			$("#area4").hide();
			$("#area1").html(dayToWeekHtml());
			$("#area2").html(timeListHtml());
			$("#CAR_TYPE_CD2").html($("#CAR_TYPE_CD").html());
			$("#CAR_STAT_CD2").html($("#CAR_STAT_CD").html());
			$("#carMngPopup").show();
		});
		
		$("#carMngDelete").click(function(){
			var str = checkReturnVal("chk"); 
			$carMng.carMngDelete(str);
		});
		$("#carMngDeleteModal").click(function(){
			var str = [];
			str.push($("#VHCLE_NO").val() + "&"+ $("#CNTER_CD").val());
			$carMng.carMngDelete(str);
		});
		
		$("#carMngExcel").click(function(){
			$carMng.carMngExcelDown();
		});
		
		//차량정보 등록 이벤트
		$("#carMngAdd").click(function(){
			$carMng.carMngAdd();
		});
		
		//차량정보 상세 이벤트
		$("#carMngDetail").click(function(){
			$carMng.carMngDetail(str);
		});
		
		//차량정보 수정 이벤트
		$("#carMngUpdate").click(function(){
			$carMng.carMngUpdate();
		});
		
		$("#checkCarNumber").click(function(){
			$carMng.carNumberCheck($("#VHCLE_NO").val());
		});
		$("#checkCarNumber").keyup(function(){
			$carMng.carNumberCheckCnt = 0;
		});
		
	});
	
	$carMng = {
		totalCnt : 0,
		currentPageIndex : 1,
		carNumberCheckCnt : 0,
		// carMng API 호출
		list : function(page) {	
			$(".paging").empty();
			var senders = new tscp.carMngList.api();
			senders.addParam("page", page);
			if($("#searchCNTER_CD").val() != "") {
				senders.addParam("searchCNTER_CD", $("#searchCNTER_CD").val());
			}
			if($("#searchCOPERTN_CARALC_AT").val() != "") {
				senders.addParam("searchCOPERTN_CARALC_AT", $("#searchCOPERTN_CARALC_AT").val());
			}
			if($("#searchRESVE_VHCLE_AT").val() != "") {
				senders.addParam("searchRESVE_VHCLE_AT", $("#searchRESVE_VHCLE_AT").val());
			}
			if($("#searchVHCLE_TY_CD").val() != "") {
				senders.addParam("searchVHCLE_TY_CD", $("#searchVHCLE_TY_CD").val());
			}
			if($("#searchVHCLE_NO").val() != "") {
				senders.addParam("searchVHCLE_NO", $("#searchVHCLE_NO").val());
			}
			senders.addParam("CNTER_CD", session.cnter_cd);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/carMngList.json",
			});
		},	
		carMngDelete : function(str) {	
			if(confirm("차량 정보를 삭제하시겠습니까?")) {
				var senders = new tscp.carMngDelete.api();	
				var carArray = [];
				var cnterArray = [];
				for(var i = 0; i < str.length; i++) {
					var splitVal = str[i].split("&");
					
					carArray.push(splitVal[0]);
					cnterArray.push(splitVal[1]);
				}
				
				senders.addParam("VHCLE_NO", carArray);
				senders.addParam("CNTER_CD", cnterArray);
				
				
				senders.request({
					method : "POST",
					async : true,
					url : contextPath + "/ServiceAPI/bassInfo/carMngDelete.json",
				});
			}
		},
		carMngAdd : function() {	
			if (validation()) {
				if(confirm("차량정보를 등록하시겠습니까?")) {
					var senders = new tscp.carMngAdd.api();			
					senders.addParam("CNTER_CD", session.cnter_cd);
					senders.addParam("COPERTN_CARALC_AT", $("#COPERTN_CARALC_AT").val());
					senders.addParam("RESVE_VHCLE_AT", $("#RESVE_VHCLE_AT").val());
					senders.addParam("VHCLE_NO", $("#VHCLE_NO").val());
					if($("#YRIDNW").val() != ''){
						senders.addParam("YRIDNW", $("#YRIDNW").val());
					}
					if($("#MODL_NM").val() != ''){
						senders.addParam("MODL_NM", $("#MODL_NM").val());
					}
					if($("#MXMM_BRDNG_NMPR").val() != ''){
						senders.addParam("MXMM_BRDNG_NMPR", $("#MXMM_BRDNG_NMPR").val());
					}
					if($("#VHCLE_TY_CD").val() != ''){
						senders.addParam("VHCLE_TY_CD", $("#VHCLE_TY_CD").val());
					}
					if($("#MAKR").val() != ''){
						senders.addParam("MAKR", $("#MAKR").val());
					}
					if($("#VIN").val() != ''){
						senders.addParam("VIN", $("#VIN").val());
					}
					if($("#RM").val() != ''){
						senders.addParam("RM", $("RM").val());
					}
					senders.request({
						method : "POST",
						async : true,
						url : contextPath + "/ServiceAPI/bassInfo/carMngAdd.json",
					});
				}
			}
		},
		carMngDetail : function(str, str2) {	
			var senders = new tscp.carMngDetail.api();
			senders.addParam("VHCLE_NO", str);
			senders.addParam("CNTER_CD", str2);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/carMngDetail.json",
			});
		},
		carMngUpdate : function() {	
			if (validation()) {
				if(confirm("차량정보를 수정하시겠습니까?")) {
					var senders = new tscp.carMngUpdate.api();			
					senders.addParam("CNTER_CD", $("#CNTER_CD").val());
					senders.addParam("COPERTN_CARALC_AT", $("#COPERTN_CARALC_AT").val());
					senders.addParam("RESVE_VHCLE_AT", $("#RESVE_VHCLE_AT").val());
					senders.addParam("VHCLE_NO", $("#VHCLE_NO").val());
					if($("#YRIDNW").val() != ''){
						senders.addParam("YRIDNW", $("#YRIDNW").val());
					}
					if($("#MODL_NM").val() != ''){
						senders.addParam("MODL_NM", $("#MODL_NM").val());
					}
					if($("#MXMM_BRDNG_NMPR").val() != ''){
						senders.addParam("MXMM_BRDNG_NMPR", $("#MXMM_BRDNG_NMPR").val());
					}
					if($("#VHCLE_TY_CD").val() != ''){
						senders.addParam("VHCLE_TY_CD", $("#VHCLE_TY_CD").val());
					}
					if($("#MAKR").val() != ''){
						senders.addParam("MAKR", $("#MAKR").val());
					}
					if($("#VIN").val() != ''){
						senders.addParam("VIN", $("#VIN").val());
					}
					if($("#RM").val() != ''){
						senders.addParam("RM", $("RM").val());
					}
					
					senders.request({
						method : "POST",
						async : true,
						url : contextPath + "/ServiceAPI/bassInfo/carMngUpdate.json",
					});
				}
			}
		}
		,carMngExcelDown : function() {	
			var senders = new tscp.carMngExcelDown.api();
			if($("#searchCNTER_CD").val() != "") {
				senders.addParam("searchCNTER_CD", $("#searchCNTER_CD").val());
			}
			if($("#searchCOPERTN_CARALC_AT").val() != "") {
				senders.addParam("searchCOPERTN_CARALC_AT", $("#searchCOPERTN_CARALC_AT").val());
			}
			if($("#searchRESVE_VHCLE_AT").val() != "") {
				senders.addParam("searchRESVE_VHCLE_AT", $("#searchRESVE_VHCLE_AT").val());
			}
			if($("#searchVHCLE_TY_CD").val() != "") {
				senders.addParam("searchVHCLE_TY_CD", $("#searchVHCLE_TY_CD").val());
			}
			if($("#searchVHCLE_NO").val() != "") {
				senders.addParam("searchVHCLE_NO", $("#searchVHCLE_NO").val());
			}
			senders.addParam("CNTER_CD", session.cnter_cd);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/carMngExcelDown.json",
			});
		},	
		carNumberCheck : function(carNo){
			var senders = new tscp.carNumberCheck.api();
			senders.addParam("VHCLE_NO", carNo);
			senders.addParam("WATCH_TABLE", "VHCLE_INFO");
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/carNumberCheck.json",
			});
		},
	};
	
	var basicOptionHtml = "<option value=''>선택</option>";
	var optionHtml = "<option value='[OPTION_VALUE]'>[OPTION_TEXT]</option>";
	
	/*********** 차량정보목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.carMngList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$(".listTable").empty(); //리스트 삭제
				$("#excelTable").empty();
				var result = res.result;
				var html = "";
				html += "<colgroup>";
				html += "<col width='30' />";
				html += "<col width='30' />";
				html += "<col width='120' />";
				html += "<col width='60' />";
				html += "<col width='' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th><input type='checkbox' name='all' onclick='javascript:checkAll(listTable);'/></th>";
				html += "<th>No</th>";
				html += "<th>센터구분</th>";
				html += "<th>차량번호</th>";
				html += "<th>차량유형</th>";
				html += "<th>모델명</th>";
				html += "<th>최대<br/>탑승인원</th>";
				html += "<th>연식</th>";
				html += "<th>예약차량<br/>여부</th>";
				html += "<th>공동배차<br/>차량여부</th>";
				html += "<th>운전자명</th>";
				html += "<th>사용여부</th>";
				html += "</tr>";
				
				if(result.carMngList.length == 0) {
					html += "<tr><td colspan='12'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.carMngList.length; i++) {
						html += "<tr>";
						html += "<td><input type='checkbox' name='chk' value='"+result.carMngList[i].VHCLE_NO + "&" + result.carMngList[i].CNTER_CD +"'/></td>";
						html += "<td>"+result.carMngList[i].rnum+"</td>";
						html += "<td>"+result.carMngList[i].CNTER_NM+"</td>";
						html += "<td class='link' onclick=\"javascript:$carMng.carMngDetail(\'"+result.carMngList[i].VHCLE_NO+"\' , \'"+result.carMngList[i].CNTER_CD+"\');\">"+result.carMngList[i].VHCLE_NO+"</td>";
						html += "<td>"+result.carMngList[i].VHCLE_TYPE_DESC+"</td>";
						html += "<td>"+result.carMngList[i].MODL_NM+"</td>";
						html += "<td>"+result.carMngList[i].MXMM_BRDNG_NMPR+"</td>";
						html += "<td>"+result.carMngList[i].YRIDNW+"</td>";
						html += "<td>"+result.carMngList[i].RESVE_VHCLE_AT+"</td>";
						html += "<td>"+result.carMngList[i].COPERTN_CARALC_AT+"</td>";
						html += "<td>"+result.carMngList[i].MBER_NM+"</td>";
						html += "<td>"+result.carMngList[i].USE_AT+"</td>";
						html += "</tr>";
					}
				}
				
//					basicOptionHtml optionHtml
				var cnterOption = basicOptionHtml;
				var cnterOriginOption = $("#searchCNTER_CD").val();
				$("#searchCNTER_CD").empty();
				if(result.searchCnter.length == 1) {
					cnterOption = optionHtml.replace("[OPTION_VALUE]", result.searchCnter[0].CNTER_CD)
					.replace("[OPTION_TEXT]", result.searchCnter[0].CNTER_CD + "(" + result.searchCnter[0].CNTER_NM + ")");
					$("#searchCNTER_CD").html(cnterOption);
					$("#searchCNTER_CD").val(result.searchCnter[0].CNTER_CD);
					 $("#searchCNTER_CD").attr("disabled",true);
				} else {
					for(var i = 0; i < result.searchCnter.length; i++) {
						cnterOption += optionHtml.replace("[OPTION_VALUE]", result.searchCnter[i].CNTER_CD)
						.replace("[OPTION_TEXT]", result.searchCnter[i].CNTER_NM);
					}
					$("#searchCNTER_CD").html(cnterOption);
					$("#searchCNTER_CD").val(cnterOriginOption);
					$("#searchCNTER_CD").attr("disabled",false);
				}
				
//				searchVhcleType
				
				var carTypeOption = basicOptionHtml;
				var carTypeOriginOption = $("#searchVHCLE_TY_CD").val();
				$("#searchVHCLE_TY_CD").empty();
				for(var i = 0; i < result.searchVhcleType.length; i++) {
					carTypeOption += optionHtml.replace("[OPTION_VALUE]", result.searchVhcleType[i].CD_VALUE)
					.replace("[OPTION_TEXT]", result.searchVhcleType[i].CD_VALUE_DE);
				}
				
				$("#searchVHCLE_TY_CD").html(carTypeOption);
				$("#VHCLE_TY_CD").html(carTypeOption);
				$("#searchVHCLE_TY_CD").val(carTypeOriginOption);
				
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.carMngListCount[0].CNT+" 건");
				$carMng.totalCnt = result.carMngListCount[0].CNT;
				$("#TOTAL_CNT").val(result.carMngListCount[0].CNT);
				paging($carMng,"carMngList");
				
				//CRUD 버튼 막기
				if(CRUDInfo == "10") {
					$(".CRUDBtn").hide();
				} else {
					$(".CRUDBtn").show();
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 차량정보목록조회 API 호출 End **********/
	
	/*********** 차량정보삭제 API 호출 Start **********/
	(function() {
		$class("tscp.carMngDelete.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 처리되었습니다.");
					$(".dialog").hide();
					$carMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 차량정보삭제 API 호출 End **********/
	
	/*********** 차량정보등록 API 호출 Start **********/
	(function() {
		$class("tscp.carMngAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 등록되었습니다.");
					$(".dialog").hide();
					$carMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 차량정보등록 API 호출 End **********/
	
	/*********** 차량정보관리상세 API 호출 Start **********/
	(function() {
		$class("tscp.carMngDetail.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				
				$("#VHCLE_NO").val(result.carMngDetail.VHCLE_NO);
				$("#RESVE_VHCLE_AT").val(result.carMngDetail.RESVE_VHCLE_AT);
				$("#COPERTN_CARALC_AT").val(result.carMngDetail.COPERTN_CARALC_AT);
				$("#VHCLE_TY_CD").val(result.carMngDetail.VHCLE_TY_CD);
				$("#MODL_NM").val(result.carMngDetail.MODL_NM);
				$("#MAKR").val(result.carMngDetail.MAKR);
				$("#MXMM_BRDNG_NMPR").val(result.carMngDetail.MXMM_BRDNG_NMPR);
				$("#YRIDNW").val(result.carMngDetail.YRIDNW);
				$("#VIN").val(result.carMngDetail.VIN);
				$("#RM").val(result.carMngDetail.RM);
				$("#CNTER_CD").val(result.carMngDetail.CNTER_CD);
				
				
				
				//CRUD 버튼 막기
				if(CRUDInfo == "10") {
					$(".CRUDBtn").hide();
				} else {
					$(".CRUDBtn").show();
				}
				
				if(result.carMngDetail.USE_AT == 'Y'){
					$("#carMngDeleteModal").show();
				} else {
					$("#carMngDeleteModal").hide();
				}
				$("#carMngAdd").hide();
				$("#carMngUpdate").show();
				$("#area4").show();
				
				$("#carMngPopup").show();
				$carMng.carNumberCheckCnt = 1;
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 차량정보관리상세 API 호출 End **********/
	
	/*********** 차량정보수정 API 호출 Start **********/
	(function() {
		$class("tscp.carMngUpdate.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 수정되었습니다.");
					$(".dialog").hide();
					$carMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 차량정보수정 API 호출 End **********/
	
	/*********** 차량 엑셀 다운로드 API 호출 Start **********/
	(function() {
		$class("tscp.carMngExcelDown.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#excelTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<colgroup>";
				html += "<col width='30' />";
				html += "<col width='120' />";
				html += "<col width='60' />";
				html += "<col width='' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>No</th>";
				html += "<th>센터구분</th>";
				html += "<th>차량번호</th>";
				html += "<th>차량유형</th>";
				html += "<th>모델명</th>";
				html += "<th>최대<br/>탑승인원</th>";
				html += "<th>연식</th>";
				html += "<th>예약차량<br/>여부</th>";
				html += "<th>공동배차<br/>차량여부</th>";
				html += "<th>운전자명</th>";
				html += "<th>사용여부</th>";
				html += "</tr>";
				
				if(result.carMngList.length == 0) {
					html += "<tr><td colspan='12'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.carMngList.length; i++) {
						html += "<tr>";
						html += "<td>"+result.carMngList[i].rnum+"</td>";
						html += "<td class='link' onclick=\"javascript:$carMng.carMngDetail(\'"+result.carMngList[i].VHCLE_NO+"\' , \'"+result.carMngList[i].CNTER_CD+"\');\">"+result.carMngList[i].CNTER_NM+"</td>";
						html += "<td>"+result.carMngList[i].VHCLE_NO+"</td>";
						html += "<td>"+result.carMngList[i].VHCLE_TYPE_DESC+"</td>";
						html += "<td>"+result.carMngList[i].MODL_NM+"</td>";
						html += "<td>"+result.carMngList[i].MXMM_BRDNG_NMPR+"</td>";
						html += "<td>"+result.carMngList[i].YRIDNW+"</td>";
						html += "<td>"+result.carMngList[i].RESVE_VHCLE_AT+"</td>";
						html += "<td>"+result.carMngList[i].COPERTN_CARALC_AT+"</td>";
						html += "<td>"+result.carMngList[i].MBER_NM+"</td>";
						html += "<td>"+result.carMngList[i].USE_AT+"</td>";
						html += "</tr>";
					}
				}
				
//					basicOptionHtml optionHtml
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
				
//				searchVhcleType
				
				var carTypeOption = basicOptionHtml;
				var carTypeOriginOption = $("#searchVHCLE_TY_CD").val();
				$("#searchVHCLE_TY_CD").empty();
				for(var i = 0; i < result.searchVhcleType.length; i++) {
					carTypeOption += optionHtml.replace("[OPTION_VALUE]", result.searchVhcleType[i].CD_VALUE)
					.replace("[OPTION_TEXT]", result.searchVhcleType[i].CD_VALUE_DE);
				}
				
				$("#searchVHCLE_TY_CD").html(carTypeOption);
				$("#VHCLE_TY_CD").html(carTypeOption);
				$("#searchVHCLE_TY_CD").val(carTypeOriginOption);
				
				$("#excelTable").html(html);
				
				
				
				//CRUD 버튼 막기
				if(CRUDInfo == "10") {
					$(".CRUDBtn").hide();
				} else {
					$(".CRUDBtn").show();
				}
				
				downExcel();
				$carMng.list(1);
				
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 차량정보목록조회 API 호출 End **********/
	
	/*********** VHCLE_NO CHECK API 호출 Start **********/
	(function() {
		$class("tscp.carNumberCheck.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				
				if (res.result.carNumberCheck.CAR_NO == 0) { //성공
					alert("사용가능한 차량입니다.");
					$carMng.carNumberCheckCnt = 1;
				} else {
					alert("중복된 차량번호가 있습니다.");
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** VHCLE_NO CHECK API 호출 End **********/
	
}(window, document));

function validation() {
	if ($("#COPERTN_CARALC_AT").val() == "") {
		alert("공동배차여부를 선택하세요.");
		$("#COPERTN_CARALC_AT").focus();
		return false;
	}
	
	if ($("#RESVE_VHCLE_AT").val() == "") {
		alert("예약차량여부를 선택하세요 ");
		$("#RESVE_VHCLE_AT").focus();
		return false;
	}
	
	if ($("#VHCLE_NO").val() == "") {
		alert("차량번호를 입력하세요  ");
		$("#VHCLE_NO").focus();
		return false;
	}
	
	if ($("#MXMM_BRDNG_NMPR").val() == "") {
		alert("최대탑승인원을  입력하세요  ");
		$("#MXMM_BRDNG_NMPR").focus();
		return false;
	}
	
	if ($("#MXMM_BRDNG_NMPR").val() == 0) {
		alert("최대탑승인원은 0명일수 없습니다.");
		$("#MXMM_BRDNG_NMPR").focus();
		return false;
	}
	
	if ($("#VHCLE_TY_CD").val() == 0) {
		alert("차량유형코드를 선택하세요.");
		$("#VHCLE_TY_CD").focus();
		return false;
	}
	
	if($carMng.carNumberCheckCnt == 0) {
		alert("차량번호 중복체크를 해야합니다..");
		$("#checkCarNumber").focus();
		return false;
	}
	
	return true;
}

function userSearchPopup(str) {
	document.getElementById('listTablePop').reset();
	$("#userSearchPopup").show();
	$("#USER_GBN_CD_POP").val(str);
	$common.list(1);
}

//회원정보 조회 팝업 값 넘기기
function parentValMove(str1, str2) {
	$("#USER_ID2").val(str1);
	$("#NAME2").val(str2);
	
	document.getElementById('listTablePop').reset();
	$("#userSearchTable").empty(); //리스트 삭제
	$("#paging2").empty();
	$("#userSearchPopup").hide();
}

downExcel = function(){
	$("#excelTable").table2excel({
		exclude: ".noExl",
		name: "Excel Document Name",
		filename: "차량정보목록_" + new Date().toISOString().replace(/[\-\:\.]/g, ""),
		fileext: ".xls",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});
};