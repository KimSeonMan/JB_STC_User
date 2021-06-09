/**
 * 이용자정보관리 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
(function(W, D) {
	//"use strict";
	W.hadicapMng = W.$hadicapMng || {};
	$(document).ready(function() {
		codeDetailSearch("PHONE1", "AREA_CLS_CD"); //지역번호구분코드
		codeDetailSearch("MOBILE1", "MOBLPHON_CLS_CD"); //이동번호구분코드
		codeDetailSearch("EMAIL3", "EMAILADR_CLS_CD"); //이메일구분코드
		codeDetailSearch("DISABLE_TYPE1_CD2", "DISABLE_TYPE_CD"); //장애종류코드1
		codeDetailSearch("DISABLE_TYPE2_CD2", "DISABLE_TYPE_CD"); //장애종류코드2
		codeDetailSearch("WHEELCHAIR_CD2", "WHEELCHAIR_CD"); //휠체어구분코드
		
		$hadicapMng.list(1); //목록 조회
		
		//엔터키
		$('#NAME').on('keypress', function(e) {
			if (e.which == 13) {
				$hadicapMng.list(1);
			}
		});
		$('#ADDRESS').on('keypress', function(e) {
			if (e.which == 13) {
				$hadicapMng.list(1);
			}
		});
		
		//조회조건 selectbox 이벤트
		$("#SUPPORTER_YN").change(function(){
			$hadicapMng.list(1);
		});
		
		$("#searchList").click(function(){
			$hadicapMng.list(1);
		});
		
		$("#hadicapMngOpen").click(function(){
			$("#hadicapMngAdd").show();
			$("#hadicapMngUpdate").hide();
			$("#hadicapMngPopup").show();
		});
		
		$("#hadicapMngDelete").click(function(){
			var str = checkReturnVal("chk"); 
			$hadicapMng.hadicapMngDelete(str);
		});
		
		$("#hadicapMngExcel").click(function(){
			$hadicapMng.hadicapMngExcel();
		});
		
		//이동약자 등록 이벤트
		$("#hadicapMngAdd").click(function(){
			$hadicapMng.hadicapMngAdd();
		});
		
		//이동약자 수정 이벤트
		$("#hadicapMngUpdate").click(function(){
			$hadicapMng.hadicapMngUpdate();
		});
		
		//우편번호조회 이벤트(daum)
		$("#zipSearch").click(function(){
			execDaumPostcode("ZIP2","ADDRESS2","ADDRESSDTL2");
		});
		
	});
	
	$hadicapMng = {
		totalCnt : 0,
		currentPageIndex : 1,
		// hadicapMng API 호출
		list : function(page) {	
			$(".paging").empty();
			var senders = new tscp.hadicapMngList.api();
			senders.addParam("page", page);
			if($("#NAME").val() != "") {
				senders.addParam("NAME", $("#NAME").val());
			}
			if($("#ADDRESS").val() != "") {
				senders.addParam("ADDRESS", $("#ADDRESS").val());
			}
			if($("#SUPPORTER_YN").val() != "") {
				senders.addParam("SUPPORTER_YN", $("#SUPPORTER_YN").val());
			}
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/bassInfo/hadicapMngList.json",
			});
		},	
		hadicapMngDelete : function(str) {	
			if(confirm("이용자 정보를 삭제하시겠습니까?")) {
				var senders = new tscp.hadicapMngDelete.api();	
				senders.addParam("USER_ID", str);
				senders.request({
					method : "POST",
					async : true,
					url : contextPath + "/ServiceAPI/login/MemberShipDelete.json",
				});
			}
		},
		hadicapMngExcel : function() {	
			var url = "/ServiceAPI/bassInfo/hadicapMngListExcel.download";
    		var target = $('body');
			
			target.prepend("<form id='excelDownloadForm'></form>");
			target = $("#excelDownloadForm");
			target.attr("method", "post");
	        target.attr("style", "top:-3333333333px;");
	        target.attr("action", url);
	        target.append("<input type='hidden' id='TOTAL_CNT' name='TOTAL_CNT' value='"+$("#TOTAL_CNT").val()+"'>");
	        if($("#NAME").val() != "") {
	        	target.append("<input type='hidden' id='NAME' name='NAME' value='"+$("#NAME").val()+"'>");
	        }
	        if($("#ADDRESS").val() != "") {
	        	target.append("<input type='hidden' id='ADDRESS' name='ADDRESS' value='"+$("#ADDRESS").val()+"'>");
	        }
	        if($("#SUPPORTER_YN").val() != "") {
	        	target.append("<input type='hidden' id='SUPPORTER_YN' name='SUPPORTER_YN' value='"+$("#SUPPORTER_YN").val()+"'>");
	        }
	        
	        $('#excelDownloadForm').submit();
	        $('#excelDownloadForm').remove();
		},
		hadicapMngAdd : function() {	
			if (validation()) {
				if(confirm("이용자정보를 등록하시겠습니까?")) {
					var senders = new tscp.hadicapMngAdd.api();			
					senders.addParam("SMS_RECEIVE_YN", $("#SMS_RECEIVE_YN2").val());
					senders.addParam("NAME", $("#NAME2").val());
					senders.addParam("BIRTH_DATE", $("#BIRTH_DATE2").val());
					if($("#PHONE2").val() != "" && $("#PHONE2").val() != "") {
						senders.addParam("PHONE", $("#PHONE1").val()+"-"+$("#PHONE2").val()+"-"+$("#PHONE3").val()+"");
					}
					senders.addParam("MOBILE", $("#MOBILE1").val()+"-"+$("#MOBILE2").val()+"-"+$("#MOBILE3").val()+"");
					senders.addParam("ZIP", $("#ZIP2").val());
					senders.addParam("ADDRESS", $("#ADDRESS2").val());
					senders.addParam("ADDRESSDTL", $("#ADDRESSDTL2").val());
					senders.addParam("EMAIL", $("#EMAIL1").val()+"@"+$("#EMAIL2").val());
					senders.addParam("WELFAIR_CARD_NO", $("#WELFAIR_CARD_NO2").val());
					senders.addParam("DISABLE_TYPE1_CD", $("#DISABLE_TYPE1_CD2").val());
					senders.addParam("DISABLE_TYPE2_CD", $("#DISABLE_TYPE2_CD2").val());
					senders.addParam("SUPPORTER_YN", $("#SUPPORTER_YN2").val());
					senders.addParam("COMMUNICALBE_YN", $("#COMMUNICALBE_YN2").val());
					senders.addParam("HELPER_YN", $("#HELPER_YN2").val());
					senders.addParam("WHEELCHAIR_CD", $("#WHEELCHAIR_CD2").val());
					if($("#WHEELCHAIR_USE_YEAR2").val() == "") {
						senders.addParam("WHEELCHAIR_USE_YEAR", "0");
					} else {
						senders.addParam("WHEELCHAIR_USE_YEAR", $("#WHEELCHAIR_USE_YEAR2").val()+"");
					}
					
					senders.addParam("TOTAL_CNT", $("#TOTAL_CNT").val());
					
					senders.request({
						method : "POST",
						async : true,
						url : contextPath + "/ServiceAPI/bassInfo/hadicapMngAdd.json",
					});
				}
			}
		},
		hadicapMngDetail : function(str) {	
			var senders = new tscp.hadicapMngDetail.api();
			senders.addParam("USER_ID", str);
			senders.addParam("USER_GBN_CD", "10");
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/mngr/userMngDetail.json",
			});
		},
		hadicapMngUpdate : function() {	
			if (validation()) {
				if(confirm("이용자정보를 수정하시겠습니까?")) {
					var senders = new tscp.hadicapMngUpdate.api();	
					senders.addParam("USER_GBN_CD", "10");
					senders.addParam("USER_ID", $("#USER_ID2").val());
					senders.addParam("PASSWORD", "N");
					senders.addParam("SMS_RECEIVE_YN", $("#SMS_RECEIVE_YN2").val());
					senders.addParam("NAME", $("#NAME2").val());
					senders.addParam("BIRTH_DATE", $("#BIRTH_DATE2").val());
					if($("#PHONE2").val() != "" && $("#PHONE2").val() != "") {
						senders.addParam("PHONE", $("#PHONE1").val()+"-"+$("#PHONE2").val()+"-"+$("#PHONE3").val()+"");
					}
					senders.addParam("MOBILE", $("#MOBILE1").val()+"-"+$("#MOBILE2").val()+"-"+$("#MOBILE3").val()+"");
					senders.addParam("ZIP", $("#ZIP2").val());
					senders.addParam("ADDRESS", $("#ADDRESS2").val());
					senders.addParam("ADDRESSDTL", $("#ADDRESSDTL2").val());
					senders.addParam("EMAIL", $("#EMAIL1").val()+"@"+$("#EMAIL2").val());
					senders.addParam("WELFAIR_CARD_NO", $("#WELFAIR_CARD_NO2").val());
					senders.addParam("DISABLE_TYPE1_CD", $("#DISABLE_TYPE1_CD2").val());
					senders.addParam("DISABLE_TYPE2_CD", $("#DISABLE_TYPE2_CD2").val());
					senders.addParam("SUPPORTER_YN", $("#SUPPORTER_YN2").val());
					senders.addParam("COMMUNICALBE_YN", $("#COMMUNICALBE_YN2").val());
					senders.addParam("HELPER_YN", $("#HELPER_YN2").val());
					senders.addParam("WHEELCHAIR_CD", $("#WHEELCHAIR_CD2").val());
					if($("#WHEELCHAIR_USE_YEAR2").val() == "") {
						senders.addParam("WHEELCHAIR_USE_YEAR", "0");
					} else {
						senders.addParam("WHEELCHAIR_USE_YEAR", $("#WHEELCHAIR_USE_YEAR2").val()+"");
					}
					
					senders.request({
						method : "POST",
						async : true,
						url : contextPath + "/ServiceAPI/login/MemberShipUpdate.json",
					});
				}
			}
		},
		
		
	};
	
	/*********** 이용자정보목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.hadicapMngList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$(".listTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<colgroup>";
				html += "<col width='30' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='80' />";
				html += "<col width='80' />";
				html += "<col width='50' />";
				html += "<col width='50' />";
				html += "<col width='50' />";
				html += "<col width='50' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th><input type='checkbox' name='all' onclick='javascript:checkAll(listTable);'/></th>";
				html += "<th>자격번호</th>";
				html += "<th>이름</th>";
				html += "<th>생년월일</th>";
				html += "<th>연락처</th>";
				html += "<th>장애종류</th>";
				html += "<th>보조인<br/>유무</th>";
				html += "<th>휠체어<br/>유무</th>";
				html += "<th>의사소통<br/>여부</th>";
				html += "<th>SMS가능<br/>여부</th>";
				html += "<th>등록자</th>";
				html += "<th>등록일</th>";
				html += "</tr>";

				if(result.hadicapMngList.length == 0) {
					html += "<tr><td colspan='12'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.hadicapMngList.length; i++) {
						html += "<tr>";
						html += "<td><input type='checkbox' name='chk' value='"+result.hadicapMngList[i].USER_ID+"'/></td>";
						html += "<td>"+result.hadicapMngList[i].WELFAIR_CARD_NO+"</td>";
						html += "<td class='link' onclick=\"javascript:$hadicapMng.hadicapMngDetail(\'"+result.hadicapMngList[i].USER_ID+"\');\">"+result.hadicapMngList[i].NAME+"</td>";
						html += "<td>"+result.hadicapMngList[i].BIRTH_DATE+"</td>";
						html += "<td>"+result.hadicapMngList[i].MOBILE+"</td>";
						html += "<td>"+result.hadicapMngList[i].DISABLE_TYPE1_DESC+"</td>";
						html += "<td>"+result.hadicapMngList[i].SUPPORTER_YN+"</td>";
						html += "<td>"+result.hadicapMngList[i].WHEELCHAIR_CD+"</td>";
						html += "<td>"+result.hadicapMngList[i].COMMUNICALBE_YN+"</td>";
						html += "<td>"+result.hadicapMngList[i].SMS_RECEIVE_YN+"</td>";
						html += "<td>"+result.hadicapMngList[i].REG_USER_ID+"</td>";
						html += "<td>"+result.hadicapMngList[i].REG_TS+"</td>";
						html += "</tr>";
					}
				}
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.hadicapMngListCount[0].CNT+" 건");
				$hadicapMng.totalCnt = result.hadicapMngListCount[0].CNT;
				$("#TOTAL_CNT").val(result.hadicapMngListCount[0].CNT);
				paging($hadicapMng,"hadicapMngList");
				
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
	/*********** 이용자정보목록조회 API 호출 End **********/
	
	/*********** 이용자정보삭제 API 호출 Start **********/
	(function() {
		$class("tscp.hadicapMngDelete.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 처리되었습니다.");
					$hadicapMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 이용자정보삭제 API 호출 End **********/
	
	/*********** 이용자정보등록 API 호출 Start **********/
	(function() {
		$class("tscp.hadicapMngAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 등록되었습니다.");
					$(".dialog").hide();
					$hadicapMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 이용자정보등록 API 호출 End **********/
	
	/*********** 이용자정보관리상세 API 호출 Start **********/
	(function() {
		$class("tscp.hadicapMngDetail.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				
				$("#USER_ID2").val(result.userMngDetail.USER_ID);
				$("#SMS_RECEIVE_YN2").val(result.userMngDetail.SMS_RECEIVE_YN);
				$("#NAME2").val(result.userMngDetail.NAME);
				$("#BIRTH_DATE2").val(result.userMngDetail.BIRTH_DATE);
				$("#ZIP2").val(result.userMngDetail.ZIP);
				$("#ADDRESS2").val(result.userMngDetail.ADDRESS2);
				$("#ADDRESSDTL2").val(result.userMngDetail.ADDRESSDTL);
				$("#WELFAIR_CARD_NO2").val(result.userMngDetail.WELFAIR_CARD_NO);
				$("#DISABLE_TYPE1_CD2").val(result.userMngDetail.DISABLE_TYPE1_CD);
				$("#DISABLE_TYPE2_CD2").val(result.userMngDetail.DISABLE_TYPE2_CD);
				$("#SUPPORTER_YN2").val(result.userMngDetail.SUPPORTER_YN);
				$("#WHEELCHAIR_CD2").val(result.userMngDetail.WHEELCHAIR_CD);
				$("#HELPER_YN2").val(result.userMngDetail.HELPER_YN);
				$("#COMMUNICALBE_YN2").val(result.userMngDetail.COMMUNICALBE_YN);
				$("#WHEELCHAIR_USE_YEAR2").val(result.userMngDetail.WHEELCHAIR_USE_YEAR);
				
				if(result.userMngDetail.PHONE != "") {
					var phoneArray = result.userMngDetail.PHONE.split("-");
					$("#PHONE1").val(phoneArray[0]);
					$("#PHONE2").val(phoneArray[1]);
					$("#PHONE3").val(phoneArray[2]);
				}
				
				var mobileArray = result.userMngDetail.MOBILE.split("-");
				$("#MOBILE1").val(mobileArray[0]);
				$("#MOBILE2").val(mobileArray[1]);
				$("#MOBILE3").val(mobileArray[2]);
				
				var emailArray = result.userMngDetail.EMAIL.split("@");
				$("#EMAIL1").val(emailArray[0]);
				$("#EMAIL2").val(emailArray[1]);
				
				$("#hadicapMngAdd").hide();
				$("#hadicapMngUpdate").show();
				$("#hadicapMngPopup").show();
				
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
	/*********** 이용자정보관리상세 API 호출 End **********/
	
	/*********** 이용자정보수정 API 호출 Start **********/
	(function() {
		$class("tscp.hadicapMngUpdate.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 수정되었습니다.");
					$(".dialog").hide();
					$hadicapMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 이용자정보수정 API 호출 End **********/
	
}(window, document));

function changeEmail(str) {
	if(str == "10") {
		document.getElementById("EMAIL2").disabled = false;
		$("#EMAIL2").val("");
	} else {
		document.getElementById("EMAIL2").disabled = true;
		$("#EMAIL2").val($("#EMAIL3>option:selected").html());
	}
}

function changeWheechairCD(str) {
	if(str == "N") {
		document.getElementById("WHEELCHAIR_USE_YEAR2").disabled = true;
		$("#WHEELCHAIR_USE_YEAR2").val(0);
	} else {
		document.getElementById("WHEELCHAIR_USE_YEAR2").disabled = false;
		$("#WHEELCHAIR_USE_YEAR2").val("");
	}
}

function validation() {
	if ($("#NAME2").val() == "") {
		alert("이름을 입력하세요.");
		$("#NAME2").focus();
		return false;
	}
	
	if ($("#WELFAIR_CARD_NO2").val() == "") {
		alert("복지카드번호를 입력하세요.");
		$("#WELFAIR_CARD_NO2").focus();
		return false;
	}
	
	if ($("#BIRTH_DATE2").val() == "") {
		alert("생년월일을 입력하세요.");
		$("#BIRTH_DATE2").focus();
		return false;
	}
	
	if ($("#MOBILE2").val() == "") {
		alert("휴대폰 번호를 입력하세요.");
		$("#MOBILE2").focus();
		return false;
	}
	
	if ($("#MOBILE3").val() == "") {
		alert("휴대폰 번호를 입력하세요.");
		$("#MOBILE3").focus();
		return false;
	}
	
	if ($("#ADDRESS2").val() == "") {
		alert("주소를 입력하세요.");
		$("#ADDRESS2").focus();
		return false;
	}
	
	if ($("#EMAIL1").val() == "") {
		alert("이메일 주소를 입력하세요.");
		$("#EMAIL1").focus();
		return false;
	}
	
	if ($("#EMAIL2").val() == "") {
		alert("이메일 주소를 입력하세요.");
		$("#EMAIL2").focus();
		return false;
	}
	
	if ($("#WHEELCHAIR_USE_YEAR2").val() == "" && $("#WHEELCHAIR_CD2").val() == "Y") {
		alert("휠체어이용기간을 입력하세요.");
		$("#WHEELCHAIR_USE_YEAR2").focus();
		return false;
	}
	
	return true;
}