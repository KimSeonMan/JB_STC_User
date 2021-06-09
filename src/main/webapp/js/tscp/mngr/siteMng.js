/**
 * 관련사이트 관리 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
(function(W, D) {
	//"use strict";
	W.site = W.$site || {};
	$(document).ready(function() {
		$site.list(1);
		
		//엔터키
		$('#SEARCH_TEXT').on('keypress', function(e) {
			if (e.which == 13) {
				$site.list(1);
			}
		});
		
		$("#searchList").click(function(){
			$site.list(1);
		});
		
		$("#siteMngOpen").click(function(){
			document.getElementById('siteMngForm').reset();
			
			$("#attachNameUl").html("");
			$("#siteMngUpdate").hide();
			$("#siteMngAdd").show();
			
			$("#siteMngPopup").show();
			
			$("#CNTER_CD").val(session.cnter_cd);
			
		});
		
		$("#siteMngAdd").click(function(){
			$site.siteMngAdd();
		});
		
		$("#siteMngUpdate").click(function(){
			$site.siteMngUpdate();
		});
		
		$("#siteMngDelete").click(function(){
			var str = checkReturnVal("chk"); 
			$site.siteMngDelete(str);
		});
		
	});
	
	$site = {
		totalCnt : 0,
		currentPageIndex : 1,
		// site API 호출
		list : function(page) {	
			$(".paging").empty();
			var senders = new tscp.siteMngList.api();
			senders.addParam("page", page);
			if($("#SEARCH_TEXT").val() != "") {
				senders.addParam("SEARCH_TEXT", $("#SEARCH_TEXT").val());
			}
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/siteList.json",
			});
		},
		siteMngAdd : function() {	
			//siteForm
			$common.attachFileValidate();
			
			if(validationAdd()) {
				if(confirm("등록하시겠습니까?")) {
					$("#siteMngForm").ajaxForm({
						async : false,
						type : "POST",
						url : "/ServiceAPI/mngr/siteMngAdd.json",
						dataType : "json",
						data : { },
						success : function(data){
							alert("정상적으로 등록되었습니다.");
							$(".dialog").hide();
							$site.list(1);
						},
						error : function(xhr,textStatus,error){
						},
						complete : function(data){
						}
					}).submit();
				}
			}
		},		
		siteMngUpdate : function() {	
			//siteForm
			$common.attachFileValidate();
			
			if(validationAdd()) {
				if(confirm("수정하시겠습니까?")) {
					$("#siteMngForm").ajaxForm({
						async : false,
						type : "POST",
						url : "/ServiceAPI/mngr/siteMngUpdate.json",
						dataType : "json",
						data : { },
						success : function(data){
							alert("정상적으로 수정되었습니다.");
							$(".dialog").hide();
							$site.list(1);
						},
						error : function(xhr,textStatus,error){
						},
						complete : function(data){
						}
					}).submit();
				}
			}
		},
		siteMngDelete : function(str) {	
			if(confirm("선택하신 관련사이트를 삭제하시겠습니까?")) {
				var senders = new tscp.siteMngDelete.api();
				senders.addParam("SITE_NO", str);
				senders.addParam("CNTER_CD",session.cnter_cd);
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/mngr/siteMngDelete.json",
				});
			}
		},
		siteMngDetail : function(str) {	
			var senders = new tscp.siteMngDetail.api();
			senders.addParam("SITE_NO", str);
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/siteMngDetail.json",
			});
		}
	};
	
	/*********** 관련사이트관리목록 API 호출 Start **********/
	(function() {
		$class("tscp.siteMngList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$(".listTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<colgroup>";
				html += "<col width='30' />";
				html += "<col width='60' />";
				html += "<col width='180' />";
				html += "<col width='' />";
				html += "<col width='80' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th><input type='checkbox' name='all' onclick='javascript:checkAll(listTable);'/></th>";
				html += "<th>순번</th>";
				html += "<th>사이트명</th>";
				html += "<th>링크URL</th>";
				html += "<th>등록일</th>";
				html += "</tr>";
				
				if(result.siteMngList.length == 0) {
					html += "<tr><td colspan='5'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.siteMngList.length; i++) {
						html += "<tr>";
						html += "<td><input type='checkbox' name='chk' value='"+result.siteMngList[i].SITE_NO+"'/></td>";
						html += "<td>"+result.siteMngList[i].SITE_SEQ+"</td>";
						html += "<td class='link' onclick=\"javascript:$site.siteMngDetail(\'"+result.siteMngList[i].SITE_NO+"\');\">"+result.siteMngList[i].SITE_NAME+"</td>";
						html += "<td>"+result.siteMngList[i].LINKED_URL+"</td>";
						html += "<td>"+result.siteMngList[i].REG_TS+"</td>";
						html += "</tr>";
					}
				}
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.siteMngListCount[0].CNT+" 건");
				$site.totalCnt = result.siteMngListCount[0].CNT;
				paging($site,"siteMngList");
				
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
	/*********** 관련사이트관리목록 API 호출 End **********/
	
	/*********** 관련사이트관리삭제 API 호출 Start **********/
	(function() {
		$class("tscp.siteMngDelete.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 처리되었습니다.");
					$site.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 관련사이트관리삭제 API 호출 End **********/
	
	/*********** 관련사이트관리상세 API 호출 Start **********/
	(function() {
		$class("tscp.siteMngDetail.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#attachNameUl").html(""); //초기화
				
				var result = res.result;
				$("#SITE_NAME").val(result.siteMngDetail.SITE_NAME);
				$("#SITE_SEQ").val(result.siteMngDetail.SITE_SEQ);
				$("#LINKED_URL").val(result.siteMngDetail.LINKED_URL);
				$("#SITE_NO").val(result.siteMngDetail.SITE_NO);
				
				var html ="";
				
				html += "<li>";
				html += result.siteMngDetail.SITE_IMG_REAL_NAME;
				html += "&nbsp;<a href='javascript:void(0);' onclick='$common.attachFileRemove(this)'>삭제</a>";
				html += "</li>";
				$("#attachNameUl").append(html);
				
				$("#siteMngAdd").hide();
				$("#siteMngUpdate").show();
				$("#siteMngPopup").show();
				
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
	/*********** 관련사이트관리상세 API 호출 End **********/
	
}(window, document));

//관련사이트관리 등록 체크
function validationAdd() {
	if ($("#SITE_NAME").val() == "") {
		alert("사이트명을 입력하세요.");
		$("#SITE_NAME").focus();
		return false;
	}
	
	if ($("#SITE_SEQ").val() == "") {
		alert("사이트순번을 입력하세요.");
		$("#SITE_SEQ").focus();
		return false;
	}
	
	if ($("#LINKED_URL").val() == "") {
		alert("링크URL을 입력하세요.");
		$("#LINKED_URL").focus();
		return false;
	}
	
	var attachFiles = $("input[name='attachFiles[]']");
	
	if($("#attachNameUl").text().length == 0) {
		alert("파일첨부를 입력하세요.");
		$("#newAttachFile").focus();
		return false;
	}
	
	return true;
}

function fileAdd() {
	$common.attachFileFieldAdd(1, 2048, "jpg,png,gif,tif","B");
}