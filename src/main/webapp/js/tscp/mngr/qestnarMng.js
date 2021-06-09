/**
 * 설문조사 관리 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
var tempForm = "";
(function(W, D) {
	//"use strict";
	W.qestnar = W.$qestnar || {};
	$(document).ready(function() {
		tempForm = $(".subType").html(); //초기화시 사용
		
		$("#resetForm").hide();
		
		$qestnar.list(1);
		
		//엔터키
		$('#SEARCH_TEXT').on('keypress', function(e) {
			if (e.which == 13) {
				$qestnar.list(1);
			}
		});
		
		$("#SEARCH_KEY").change(function(){
			$qestnar.list(1);
		});
		
		$("#searchList").click(function(){
			$qestnar.list(1);
		});
		
		$("#qestnarMngOpen").click(function(e){
			$(".subType").html(tempForm);
			$("#resetForm").trigger("click");
			
			$("#qestnarMngUpdate").hide();
			$("#qestnarMngAdd").show();
			
			$("#qestnarMngPopup").show();
		});
		
		$("#qestnarMngAdd").click(function(){
			$qestnar.qestnarMngAdd();
		});
		
		$("#qestnarMngUpdate").click(function(){
			$qestnar.qestnarMngUpdate();
		});
		
		$("#qestnarMngDelete").click(function(){
			var str = checkReturnVal("chk"); 
			$qestnar.qestnarMngDelete(str);
		});
		
	});
	
	$qestnar = {
		totalCnt : 0,
		currentPageIndex : 1,
		// qestnar API 호출
		list : function(page) {	
			$(".paging").empty();
			var senders = new tscp.qestnarMngList.api();
			senders.addParam("page", page);
			if($("#SEARCH_TEXT").val() != "") {
				senders.addParam("SEARCH_TEXT", $("#SEARCH_TEXT").val());
			}
			if($("#SEARCH_KEY").val() != "") {
				senders.addParam("SEARCH_KEY", $("#SEARCH_KEY").val());
			}
			if($("#ST_DT").val() != "" && $("#EN_DT").val() != "") {
				if($("#ST_DT").val() <= $("#EN_DT").val()) {
					senders.addParam("ST_DT", $("#ST_DT").val());
					senders.addParam("EN_DT", $("#EN_DT").val());
				} else {
					alert("조회기간 시작일이 종료일보다 큽니다. 다시 확인해주세요.");
					$("#ST_DT").focus();
					return false;
				}
			}
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/qestnarMngList.json",
			});
		},
		qestnarMngAdd : function() {	
			if(validationAdd()) {
				if(confirm("등록하시겠습니까?")) {
					var senders = new tscp.qestnarMngAdd.api();
					var SURVEY_TYPE_CD = new Array;
					var SURVEY_CHOICE_CD = new Array;
					var SURVEY_GBN_CD = new Array;
					var SURVEY_CTNT = new Array;
					var cnt = $(".SURVEY_CTNT").length;
					
					if(cnt > 0) {
						for(var i=0; i<cnt; i++) {
							var TEMP_SURVEY_GBN_CD = new Array;
							var TEMP_SURVEY_CTNT = new Array;
							
							SURVEY_TYPE_CD.push(checkReturnVal("SURVEY_TYPE_CD"+(i+1)));
							SURVEY_CHOICE_CD.push(checkReturnVal("SURVEY_CHOICE_CD"+(i+1)));
							
							$("input[name='SURVEY_CTNT"+(i+1)+"']").each(function(idx){    
								TEMP_SURVEY_CTNT.push($(this).val());
							});
							SURVEY_GBN_CD.push($("input[name='SURVEY_CTNT"+(i+1)+"']").length);
							SURVEY_CTNT.push(TEMP_SURVEY_CTNT);
						}
						
						senders.addParam("SURVEY_TYPE_CD", SURVEY_TYPE_CD);
						senders.addParam("SURVEY_CHOICE_CD", SURVEY_CHOICE_CD);
						senders.addParam("SURVEY_GBN_CD", SURVEY_GBN_CD);
						senders.addParam("SURVEY_CTNT", SURVEY_CTNT);
					}
					
					senders.addParam("SURVEY_TITLE", $("#SURVEY_TITLE").val());
					senders.addParam("SURVEY_ST_DATE", $("#SURVEY_ST_DATE").val().replace(/-/gi,""));
					senders.addParam("SURVEY_EN_DATE", $("#SURVEY_EN_DATE").val().replace(/-/gi,""));
					senders.addParam("TARGET_USER_CD", checkReturnVal("TARGET_USER_CD"));
					senders.addParam("MAIN_VIEW_YN", checkReturnVal("MAIN_VIEW_YN"));
					senders.addParam("SURVEY_REMARK", $("#SURVEY_REMARK").val());

					senders.request({
						method : "POST",
						async : false,
						url : contextPath + "/ServiceAPI/mngr/qestnarMngAdd.json",
					});
				}
			}
		},		
		qestnarMngUpdate : function() {	
			if(validationAdd()) {
				if(confirm("수정하시겠습니까?")) {
					var senders = new tscp.qestnarMngUpdate.api();
					var SURVEY_TYPE_CD = new Array;
					var SURVEY_CHOICE_CD = new Array;
					var SURVEY_GBN_CD = new Array;
					var SURVEY_CTNT = new Array;
					var cnt = $(".SURVEY_CTNT").length;
					
					if(cnt > 0) {
						for(var i=0; i<cnt; i++) {
							var TEMP_SURVEY_GBN_CD = new Array;
							var TEMP_SURVEY_CTNT = new Array;
							
							SURVEY_TYPE_CD.push(checkReturnVal("SURVEY_TYPE_CD"+(i+1)));
							SURVEY_CHOICE_CD.push(checkReturnVal("SURVEY_CHOICE_CD"+(i+1)));
							
							$("input[name='SURVEY_CTNT"+(i+1)+"']").each(function(idx){    
								TEMP_SURVEY_CTNT.push($(this).val());
							});
							SURVEY_GBN_CD.push($("input[name='SURVEY_CTNT"+(i+1)+"']").length);
							SURVEY_CTNT.push(TEMP_SURVEY_CTNT);
						}
						
						senders.addParam("SURVEY_TYPE_CD", SURVEY_TYPE_CD);
						senders.addParam("SURVEY_CHOICE_CD", SURVEY_CHOICE_CD);
						senders.addParam("SURVEY_GBN_CD", SURVEY_GBN_CD);
						senders.addParam("SURVEY_CTNT", SURVEY_CTNT);
					}
					
					senders.addParam("SURVEY_ID", $("#SURVEY_ID").val());
					senders.addParam("SURVEY_TITLE", $("#SURVEY_TITLE").val());
					senders.addParam("SURVEY_ST_DATE", $("#SURVEY_ST_DATE").val().replace(/-/gi,""));
					senders.addParam("SURVEY_EN_DATE", $("#SURVEY_EN_DATE").val().replace(/-/gi,""));
					senders.addParam("TARGET_USER_CD", checkReturnVal("TARGET_USER_CD"));
					senders.addParam("MAIN_VIEW_YN", checkReturnVal("MAIN_VIEW_YN"));
					senders.addParam("SURVEY_REMARK", $("#SURVEY_REMARK").val());

					senders.request({
						method : "POST",
						async : false,
						url : contextPath + "/ServiceAPI/mngr/qestnarMngUpdate.json",
					});
				}
			}
		},
		qestnarMngDelete : function(str) {	
			if(confirm("선택하신 설문조사를 삭제하시겠습니까?")) {
				var senders = new tscp.qestnarMngDelete.api();
				senders.addParam("SURVEY_ID", str);
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/mngr/qestnarMngDelete.json",
				});
			}
		},
		qestnarMngDetail : function(str) {	
			var senders = new tscp.qestnarMngDetail.api();
			senders.addParam("SURVEY_ID", str);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/qestnarMngDetail.json",
			});
		},
		qestnarMngResult : function(str) {	
			var senders = new tscp.qestnarMngResult.api();
			senders.addParam("SURVEY_ID", str);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/qestnarMngResult.json",
			});
		}
	};
	
	/*********** 설문조사관리목록 API 호출 Start **********/
	(function() {
		$class("tscp.qestnarMngList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$(".listTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<colgroup>";
				html += "<col width='30' />";
				html += "<col width='30' />";
				html += "<col width='' />";
				html += "<col width='80' />";
				html += "<col width='80' />";
				html += "<col width='80' />";
				html += "<col width='80' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th><input type='checkbox' name='all' onclick='javascript:checkAll(listTable);'/></th>";
				html += "<th>번호</th>";
				html += "<th>설문제목</th>";
				html += "<th>설문<br/>시작일</th>";
				html += "<th>설문<br/>종료일</th>";
				html += "<th>등록일</th>";
				html += "<th>관리</th>";
				html += "</tr>";
				
				if(result.qestnarMngList.length == 0) {
					html += "<tr><td colspan='7'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.qestnarMngList.length; i++) {
						html += "<tr>";
						html += "<td><input type='checkbox' name='chk' value='"+result.qestnarMngList[i].SURVEY_ID+"'/></td>";
						html += "<td>"+result.qestnarMngList[i].rnum+"</td>";
						html += "<td class='link' onclick=\"javascript:$qestnar.qestnarMngDetail(\'"+result.qestnarMngList[i].SURVEY_ID+"\');\">"+result.qestnarMngList[i].SURVEY_TITLE+"</td>";
						html += "<td>"+result.qestnarMngList[i].SURVEY_ST_DATE+"</td>";
						html += "<td>"+result.qestnarMngList[i].SURVEY_EN_DATE+"</td>";
						html += "<td>"+result.qestnarMngList[i].REG_TS+"</td>";
						html += "<td><a href=\"javascript:$qestnar.qestnarMngResult(\'"+result.qestnarMngList[i].SURVEY_ID+"\');\" class='btn04'>결과보기</a></td>";
						html += "</tr>";
					}
				}
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.qestnarMngListCount[0].CNT+" 건");
				$qestnar.totalCnt = result.qestnarMngListCount[0].CNT;
				paging($qestnar,"qestnarMngList");
				
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
	/*********** 설문조사관리목록 API 호출 End **********/
	
	/*********** 설문조사관리등록 API 호출 Start **********/
	(function() {
		$class("tscp.qestnarMngAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 등록되었습니다.");
					$(".dialog").hide();
					$qestnar.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 설문조사관리등록 API 호출 End **********/
	
	/*********** 설문조사관리삭제 API 호출 Start **********/
	(function() {
		$class("tscp.qestnarMngDelete.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 처리되었습니다.");
					$qestnar.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 설문조사관리삭제 API 호출 End **********/
	
	/*********** 설문조사관리상세 API 호출 Start **********/
	(function() {
		$class("tscp.qestnarMngDetail.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				var cnt = 1;
				var cnt2 = 0;
				var html = "";
				
				html += "<colgroup>";
				html += "<col width='70' />";
				html += "<col width='' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<td colspan='2'>";
				html += "<a href=\"javascript:optionsAdd(\'A\',\'SURVEY_CTNT\',\'\');\" class='btn04'>문항추가</a>";
				html += "<a href=\"javascript:optionsDelete(\'A\',\'SURVEY_CTNT\');\" class='btn04'>문항삭제</a>";
				html += "</td>";
				html += "</tr>";	
				
				for(var i=0; i<result.qestnarMngDetail.length; i++) {
					if(i == 0) {
						$("#SURVEY_ID").val(result.qestnarMngDetail[i].SURVEY_ID);
						$("#SURVEY_TITLE").val(result.qestnarMngDetail[i].SURVEY_TITLE);
						$("#SURVEY_ST_DATE").val(result.qestnarMngDetail[i].SURVEY_ST_DATE);
						$("#SURVEY_EN_DATE").val(result.qestnarMngDetail[i].SURVEY_EN_DATE);
						$("input:radio[name='TARGET_USER_CD']:radio[value='"+result.qestnarMngDetail[i].TARGET_USER_CD+"']").prop("checked",true);
						$("input:radio[name='MAIN_VIEW_YN']:radio[value='"+result.qestnarMngDetail[i].MAIN_VIEW_YN+"']").prop("checked",true);
						$("#SURVEY_REMARK").val(result.qestnarMngDetail[i].SURVEY_REMARK);
						
						$(".subType").html(html); //초기화

						optionsAdd("A","SURVEY_CTNT",result.qestnarMngDetail[i].SURVEY_CTNT);
						
						//문항 1
						$("input:radio[name='SURVEY_TYPE_CD"+cnt+"']:radio[value='"+result.qestnarMngDetail[i].SURVEY_TYPE_CD+"']").prop("checked",true);
						changeType("SURVEY_TYPE_CD"+cnt, "SURVEY_CHOICE_CD"+cnt, "SURVEY_CTNT"+cnt);
						$("input:radio[name='SURVEY_CHOICE_CD"+cnt+"']:radio[value='"+result.qestnarMngDetail[i].SURVEY_CHOICE_CD+"']").prop("checked",true);
						
						cnt2 = 0;
					} else {
						if(result.qestnarMngDetail[i].SURVEY_GBN_CD == "1") {
							cnt++;
							
							optionsAdd("A","SURVEY_CTNT",result.qestnarMngDetail[i].SURVEY_CTNT);
							
							$("input:radio[name='SURVEY_TYPE_CD"+cnt+"']:radio[value='"+result.qestnarMngDetail[i].SURVEY_TYPE_CD+"']").prop("checked",true);
							changeType("SURVEY_TYPE_CD"+cnt, "SURVEY_CHOICE_CD"+cnt, "SURVEY_CTNT"+cnt);
							$("input:radio[name='SURVEY_CHOICE_CD"+cnt+"']:radio[value='"+result.qestnarMngDetail[i].SURVEY_CHOICE_CD+"']").prop("checked",true);
							
							cnt2 = 0;
						} else {
							if(cnt2 == 0) {
								$(".SURVEY_CTNT"+cnt+":last input[name=SURVEY_CTNT"+cnt+"]").val(result.qestnarMngDetail[i].SURVEY_CTNT);
							} else {
								optionsAdd("B","SURVEY_CTNT"+cnt,result.qestnarMngDetail[i].SURVEY_CTNT);
							}
							
							cnt2++;
						}
					}
				}
				
				$("#qestnarMngAdd").hide();
				$("#qestnarMngUpdate").show();
				$("#qestnarMngPopup").show();
				
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
	/*********** 설문조사관리상세 API 호출 End **********/
	
	/*********** 설문조사관리수정 API 호출 Start **********/
	(function() {
		$class("tscp.qestnarMngUpdate.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 수정되었습니다.");
					$(".dialog").hide();
					$qestnar.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 설문조사관리수정 API 호출 End **********/
	
	/*********** 설문조사결과 API 호출 Start **********/
	(function() {
		$class("tscp.qestnarMngResult.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				var html = "";
				var html1 = "";
				var cnt = 1;
				var cnt2 = 1;
				
				html += "<table class='formTable'>";
				html += "<tr>";
				html += "<th>제목</th>";
				html += "<td>"+result.qestnarMngDetail[0].SURVEY_TITLE+"</td>";   
				html += "</tr>";
				html += "<tr>";
				html += "<th>상태</th>";
				if(currentDate() > result.qestnarMngDetail[0].SURVEY_EN_DATE) {
					html += "<td>완료</td>";  
				} else {
					html += "<td>진행중</td>";
				}
				html += "</tr>";
				html += "<tr>"; 
				html += "<th>기간설정</th>";
				html += "<td>시작 : "+result.qestnarMngDetail[0].SURVEY_ST_DATE+"   ~  종료 : "+result.qestnarMngDetail[0].SURVEY_EN_DATE+" 까지</td>"; 
				html += "</tr>";
				html += "<tr>"; 
				html += "<th>설명</th>";
				html += "<td>"+result.qestnarMngDetail[0].SURVEY_REMARK+"</td>"; 
				html += "</tr>";
				html += "<tr>"; 
				html += "<th>응답자 수</th>";
				if(result.qestnarMngResult.length == 0) {
					html += "<td>0 명</td>";
				} else {
					html += "<td>"+result.qestnarMngResult[0].REPLAY_CNT+" 명</td>";
				}
				 
				html += "</tr>";  
				html += "</table>";
				
				html1 += "<table class='listTable subType'>";
				html1 += "<colgroup>";
				html1 += "<col width='40' />";
				html1 += "<col width='' />";
				html1 += "<col width='60' />";
				html1 += "<col width='300' />";
				html1 += "</colgroup>";
				html1 += "<tr>";
				html1 += "<th>No</th>";
				html1 += "<th>문항</th>";
				html1 += "<th>응답자수</th>";
				html1 += "<th>응답비율</th>";
				html1 += "</tr>"; 
				
				for(var i=0; i<result.qestnarMngDetail.length; i++) {
					if(result.qestnarMngDetail[i].SURVEY_GBN_CD == "1") {
						if(cnt != 1) {
							html += "</table>";
						}
						html += "<div class='etcTitle'>"+cnt+". "+result.qestnarMngDetail[i].SURVEY_CTNT+"</div>";
						html += html1;
						cnt++;
						cnt2 = 1; //초기화
					} else {
						html += "<tr>";
						html += "<td>"+cnt2+"</td>";
						html += "<td class='al'>"+result.qestnarMngDetail[i].SURVEY_CTNT+"</td>";
						html += "<td>0명</td>";
						html += "<td class='al'>";
						html += "<div class='perTxt' id='1"+result.qestnarMngDetail[i].SURVEY_SEQ+"'>0%</div>";
						html += "<div class='perBar' id='2"+result.qestnarMngDetail[i].SURVEY_SEQ+"'><span style='width:0%'></span></div>";
						html += "</td>";   
						html += "</tr>";
						cnt2++;
					}
				}
				
				html += "</table>";
				html += "<div class='btnbox'>";
				html += "<a href=\"javascript:$(\'.dialog\').hide();\" class='btn03'>닫기</a>";
				html += "</div>";
				
				$("#qestnarResultHtml").html(html);
				$("#qestnarMngResultPopup").show();
				
				for(var i=0; i<result.qestnarMngResult.length; i++) {
					var tempCnt;
					if(result.qestnarMngResult[i].REPLAY_CNT == 0) {
						tempCnt = 0;
					} else {
						tempCnt = (result.qestnarMngResult[i].SEQ_CNT / result.qestnarMngResult[i].REPLAY_CNT * 100).toFixed(2);
					}
					$("#1"+result.qestnarMngResult[i].SURVEY_SEQ).html(tempCnt);
					$("#2"+result.qestnarMngResult[i].SURVEY_SEQ).html("<span style='width:"+tempCnt+"%'></span>");
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 설문조사결과 API 호출 End **********/
	
}(window, document));

//설문조사 등록 체크
function validationAdd() {
	if ($("#SURVEY_TITLE").val() == "") {
		alert("설문제목을 입력하세요.");
		$("#SURVEY_TITLE").focus();
		return false;
	}
	
	if ($("#SURVEY_ST_DATE").val() == "") {
		alert("기간설정(시작)을 입력하세요.");
		$("#SURVEY_ST_DATE").focus();
		return false;
	}
	
	if ($("#SURVEY_EN_DATE").val() == "") {
		alert("기간설정(종료)을 입력하세요.");
		$("#SURVEY_EN_DATE").focus();
		return false;
	}
	
	if($("#SURVEY_ST_DATE").val() > $("#SURVEY_EN_DATE").val()) {
		alert("기간설정 시작일이 종료일보다 큽니다. 다시 확인해주세요.");
		$("#SURVEY_ST_DATE").focus();
		return false;
	}
	
	if ($("#SURVEY_REMARK").val() == "") {
		alert("설문설명을 입력하세요.");
		$("#SURVEY_REMARK").focus();
		return false;
	}
	
	return true;
}

function optionsAdd(str1, str2, str3) {
	var html = ""; //취합
	var html1 = ""; //문항
	var html2 = ""; //옵션
	var cnt1 = 0;
	var cnt2 = 0;
	
	if(str1 == "B") { //옵션 추가
		cnt2 = $("."+str2).length;
		
		html2 += "<tr class='"+str2+"'>";
		html2 += "<td class='ac'>"+(cnt2+1)+"</td>";
		html2 += "<td>";	
		html2 += "<input type='text' class='inp t01' name='"+str2+"' value='"+str3+"' />";		
		html2 += "</td>";		
		html2 += "</tr>";
		html += html2;
		
		$("."+str2+":last").after(html);
	} else { //문항 추가
		cnt1 = $(".SURVEY_CTNT").length;
		
		html1 += "<tr class='SETTINGS"+(cnt1+1)+"'>";
		html1 += "<th>형식</th>";
		html1 += "<td>";
		html1 += "<input type='radio' name='SURVEY_TYPE_CD"+(cnt1+1)+"' value='01' checked onclick=\"javascript:changeType(\'SURVEY_TYPE_CD"+(cnt1+1)+"\', \'SURVEY_CHOICE_CD"+(cnt1+1)+"\', \'SURVEY_CTNT"+(cnt1+1)+"\');\" />";
		html1 += "<label for='typeCk01'>객관식</label>";
		html1 += "<input type='radio' name='SURVEY_TYPE_CD"+(cnt1+1)+"' value='02' onclick=\"javascript:changeType(\'SURVEY_TYPE_CD"+(cnt1+1)+"\', \'SURVEY_CHOICE_CD"+(cnt1+1)+"\', \'SURVEY_CTNT"+(cnt1+1)+"\');\" />";
		html1 += "<label for='typeCk02'>주관식</label>";
		html1 += "<input type='radio' name='SURVEY_CHOICE_CD"+(cnt1+1)+"' value='01' checked />";
		html1 += "<label for='typeCk03'>단일선택</label>";
		html1 += "<input type='radio' name='SURVEY_CHOICE_CD"+(cnt1+1)+"' value='02' />";
		html1 += "<label for='typeCk04'>중복선택</label>";
		html1 += "</td>";
		html1 += "</tr>";
		html1 += "<tr class='SURVEY_CTNT'>";
		html1 += "<th>문항</th>";
		html1 += "<td>";
		html1 += "<input type='text' class='inp' name='SURVEY_CTNT"+(cnt1+1)+"' value='"+str3+"' maxlength='250' placeholder='질문하실 내용을 입력하세요.' />";
		html1 += "</td>";   
		html1 += "</tr>";
		
		html2 += "<tr class='SURVEY_CTNT"+(cnt1+1)+"'>";
		html2 += "<td class='ac'>1</td>";
		html2 += "<td>";	
		html2 += "<input type='text' class='inp t01' name='SURVEY_CTNT"+(cnt1+1)+"' />";		
		html2 += "<a href=\"javascript:optionsAdd(\'B\',\'SURVEY_CTNT"+(cnt1+1)+"\',\'\');\" class='btn04'>추가</a>";
		html2 += "<a href=\"javascript:optionsDelete(\'B\',\'SURVEY_CTNT"+(cnt1+1)+"\',\'\');\" class='btn04'>삭제</a>";
		html2 += "</td>";		
		html2 += "</tr>";
		
		html += html1;
		html += html2;
		
		$(".subType").append(html);
	}
}

function optionsDelete(str1, str2) {
	var cnt1 = 0; //문항 개수
	var cnt2 = 0; //옵션 개수
	
	if(str1 == "B") { //옵션 삭제
		cnt2 = $("."+str2).length;
		if(cnt2 == 1) {
			alert("답변 내용이 1개 이상일 때 삭제됩니다.");
		} else {
			$("."+str2+":last").remove();
		}
	} else { //문항 삭제
		var cnt1 = $("."+str2).length; 
		
		if(cnt1 == 1) {
			alert("첫번째 문항은 삭제할 수 없습니다.");
		} else {
			$(".SETTINGS"+cnt1).remove();
			$(".SURVEY_CTNT"+cnt1).remove();
			$(".SURVEY_CTNT:last").remove();
		}
	}
}

function changeType(str1, str2, str3) {
	var str = checkReturnVal(str1); 
	var html2 = "";
	
	if(str == "01") {
		$("input[name="+str2+"]").attr("disabled",false);
		$("input:radio[name='"+str2+"']:radio[value='01']").prop("checked",true);
		$("."+str3).show();
	} else {
		$("input[name="+str2+"]").attr("disabled",true);
		$("input:radio[name='"+str2+"']").removeAttr("checked");
		$("."+str3).hide();
	}
}