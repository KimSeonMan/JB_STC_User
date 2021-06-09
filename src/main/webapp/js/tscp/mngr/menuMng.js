/**
 * 메뉴관리 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
var accessIndex = 0;
(function(W, D) {
	//"use strict";
	W.menuMng = W.$menuMng || {};
	$(document).ready(function() {
		$menuMng.list(1); //목록 조회
		
		$menuMng.bigList("1"); //area1 삽입
		
		//엔터키
		$('#MENU_NAME').on('keypress', function(e) {
			if (e.which == 13) {
				$menuMng.list(1);
			}
		});
		
		//조회조건 selectbox 이벤트
		$("#MENU_LEVEL").change(function(){
			$menuMng.list(1);
		});
		$("#USE_YN").change(function(){
			$menuMng.list(1);
		});
		
		$("#searchList").click(function(){
			$menuMng.list(1);
		});
		
		//메뉴관리등록 팝업
		$("#menuMngOpen").click(function(){
			document.getElementById("menuMngForm").reset();
			
			$("#menuMngAdd").show();
			$("#menuMngUpdate").hide();
			$("#menuMngDelete2").hide();
			
			$("#menuMngPopup").show();
		});
		
		$("#MENU_LEVEL2").change(function(){
			var menuLevel = $("#MENU_LEVEL2").val();
			var html = "";
			$("#area1").html(""); //상위메뉴ID td 변경
				
			if(menuLevel == "1") {
				$("#area1").html("<input type='text' id='PARENT_MENU_ID2' class='inp' maxlength='15' placeholder='alloc300' onkeyup='javascript:changeMenuId(this.value);'/>");
				document.getElementById("MENU_ID2").disabled = true;
				document.getElementById("MENU_GUBN2").disabled = false;
			} else {
				$menuMng.bigList("1");
				document.getElementById("MENU_ID2").disabled = false;
				document.getElementById("MENU_GUBN2").disabled = true;
			}
		});
		
		//메뉴관리등록
		$("#menuMngAdd").click(function(){
			$menuMng.menuMngDoubleAddChk();
		});
		
		//메뉴관리사용중지
		$("#menuMngDelete").click(function(){
			var str = checkReturnVal("chk"); 
			if(str == "") {
				alert("사용중지할 메뉴를 선택해주세요!");
			} else {
				$menuMng.menuMngDelete("U",str);
			}
		});
		
		//메뉴관리삭제
		$("#menuMngDelete2").click(function(){
			$menuMng.menuMngDelete("D", $("#MENU_ID2").val());
		});
		
		//메뉴관리수정
		$("#menuMngUpdate").click(function(){
			$menuMng.menuMngUpdate();
		});
		
		//메뉴관리권한 팝업
		$("#menuMngAccessOpen").click(function(){
			var chklen = document.getElementsByName("chk");
			var count = 0;
			var menuId = "";
			// 체크박스 갯수만큼 for 문을 돌려 체크된 넘의 value값을 가져온다.
		 	for(i=0; i<chklen.length; i++){
				if(chklen[i].checked == true) {
					menuId = chklen[i].value;
					count++;
			    }
		 	}
		 	
		 	if(count != 1) {
		 		alert("한 개의 메뉴만 선택해주세요!");
		 		return false;
		 	} else {
		 		$("#MENU_ID3").val(menuId);
		 		$menuMng.menuMngAccessList(menuId);
		 	}
		});
		
		//메뉴관리권한 등록/변경
		$("#menuMngAccessAdd").click(function(){
			var result_array = new Array;
			for(i=0; i<accessIndex; i++){
				var chklen = document.getElementsByName("chkAc"+i);
				result_array.push($(":radio[name='chkAc"+i+"']:checked").val());
			}
	 		$menuMng.menuMngAccessAdd(result_array);
		});
		
	});
	
	$menuMng = {
		totalCnt : 0,
		currentPageIndex : 1,
		// menuMng API 호출
		list : function(page) {	
			$(".paging").empty();
			var senders = new tscp.menuMngList.api();
			senders.addParam("page", page);
			if($("#MENU_LEVEL").val() != "") {
				senders.addParam("MENU_LEVEL", $("#MENU_LEVEL").val());
			}
			if($("#USE_YN").val() != "") {
				senders.addParam("USE_YN", $("#USE_YN").val());
			}
			if($("#MENU_NAME").val() != "") {
				senders.addParam("MENU_NAME", $("#MENU_NAME").val());
			}
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/mngr/menuMngList.json",
			});
		},
		bigList : function(str) {	
			var senders = new tscp.bigList.api();
			senders.addParam("MENU_LEVEL", str);
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/mngr/menuMngSelectboxList.json",
			});
		},
		menuMngAdd : function() {	
			if(validationMenuMngAdd()) {
				if(confirm("메뉴관리를 등록하시겠습니까?")) {
					var senders = new tscp.menuMngAdd.api();
					
					senders.addParam("MENU_LEVEL", $("#MENU_LEVEL2").val());
					senders.addParam("PARENT_MENU_ID", $("#PARENT_MENU_ID2").val().split("||")[0]);
					senders.addParam("MENU_ID", $("#MENU_ID2").val());
					senders.addParam("MENU_NAME", $("#MENU_NAME2").val());
					senders.addParam("MENU_SEQ", $("#MENU_SEQ2").val());
					senders.addParam("USE_YN", $("#USE_YN2").val());
					senders.addParam("MENU_GUBN", $("#MENU_GUBN2").val());
					if($("#CTNT_URL2").val() != null) {
						senders.addParam("CTNT_URL", $("#CTNT_URL2").val());
					}
					senders.addParam("CNTER_CD",session.cnter_cd);
					
					senders.request({
						method : "POST",
						async : true,
						url : contextPath + "/ServiceAPI/mngr/menuMngAdd.json",
					});
				}
			}
		},
		menuMngDoubleAddChk : function() {	
			var senders = new tscp.menuMngDoubleAddChk.api();
			senders.addParam("MENU_ID", $("#MENU_ID2").val());
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/mngr/menuMngDoubleAddChk.json",
			});
		},
		menuMngDelete : function(str1,str2) {	
			var tempText = "";
			if(str1 == "U") {
				tempText = "사용중지";
			} else {
				tempText = "삭제";
			}
			if(confirm(tempText+"하시겠습니까?")) {
				var senders = new tscp.menuMngDelete.api();
				senders.addParam("GUBN", str1);
				senders.addParam("MENU_ID", str2);
				senders.addParam("CNTER_CD",session.cnter_cd);
				senders.request({
					method : "POST",
					async : true,
					url : contextPath + "/ServiceAPI/mngr/menuMngDelete.json",
				});
			}
		},
		menuMngDetail : function(str) {	
			var senders = new tscp.menuMngDetail.api();
			senders.addParam("MENU_ID", str);			
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/mngr/menuMngDetail.json",
			});
		},
		menuMngUpdate : function() {	
			if(validationMenuMngAdd()) {
				if(confirm("수정하시겠습니까?")) {
					var senders = new tscp.menuMngUpdate.api();
					
					senders.addParam("MENU_LEVEL", $("#MENU_LEVEL2").val());
					senders.addParam("PARENT_MENU_ID", $("#PARENT_MENU_ID2").val().split("||")[0]);
					senders.addParam("MENU_ID", $("#MENU_ID2").val());
					senders.addParam("MENU_NAME", $("#MENU_NAME2").val());
					senders.addParam("MENU_SEQ", $("#MENU_SEQ2").val());
					senders.addParam("USE_YN", $("#USE_YN2").val());
					senders.addParam("MENU_GUBN", $("#MENU_GUBN2").val());
					if($("#CTNT_URL2").val() != null) {
						senders.addParam("CTNT_URL", $("#CTNT_URL2").val());
					}
					senders.addParam("CNTER_CD",session.cnter_cd);
					
					senders.request({
						method : "POST",
						async : true,
						url : contextPath + "/ServiceAPI/mngr/menuMngUpdate.json",
					});
				}
			}
		},
		menuMngAccessList : function(str) {	
			var senders = new tscp.menuMngAccessList.api();
			senders.addParam("MENU_ID", str);
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/mngr/menuMngAccessList.json",
			});
		},
		menuMngAccessAdd : function(str) {	
			if(confirm("등록/변경하시겠습니까?")) {
				var senders = new tscp.menuMngAccessAdd.api();
				senders.addParam("ARRAYVAL", str);
				senders.addParam("MENU_ID", $("#MENU_ID3").val());
				senders.addParam("CNTER_CD",session.cnter_cd);
				
				senders.request({
					method : "POST",
					async : true,
					url : contextPath + "/ServiceAPI/mngr/menuMngAccessAdd.json",
				});
			}
		}
	};
	
	/*********** 메뉴관리목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.menuMngList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$(".listTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<colgroup>";
				html += "<col width='30' />";
				html += "<col width='50' />";
				html += "<col width='70' />";
				html += "<col width='150' />";
				html += "<col width='' />";
				html += "<col width='60' />";
				html += "<col width='40' />";
				html += "<col width='40' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th><input type='checkbox' name='all' onclick='javascript:checkAll(listTable);'/></th>";
				html += "<th>순번</th>";
				html += "<th>메뉴ID</th>";
				html += "<th>메뉴명</th>";
				html += "<th>컨텐츠URL</th>";
				html += "<th>상위<br/>메뉴ID</th>";
				html += "<th>메뉴<br/>단계</th>";
				html += "<th>사용<br/>여부</th>";
				html += "</tr>";

				if(result.menuMngList.length == 0) {
					html += "<tr><td colspan='8'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.menuMngList.length; i++) {
						html += "<tr>";
						html += "<td><input type='checkbox' name='chk' value='"+result.menuMngList[i].MENU_ID+"'/></td>";
						html += "<td>"+result.menuMngList[i].MENU_SEQ+"</td>";
						html += "<td>"+result.menuMngList[i].MENU_ID+"</td>";
						html += "<td class='link' onclick=\"javascript:$menuMng.menuMngDetail(\'"+result.menuMngList[i].MENU_ID+"\');\">"+result.menuMngList[i].MENU_NAME+"</td>";
						html += "<td>"+result.menuMngList[i].CTNT_URL+"</td>";
						html += "<td>"+result.menuMngList[i].PARENT_MENU_ID+"</td>";
						html += "<td>"+result.menuMngList[i].MENU_LEVEL+"</td>";
						html += "<td>"+result.menuMngList[i].USE_YN+"</td>";
						html += "</tr>";
					}
				}
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.menuMngListCount[0].CNT+" 건");
				$menuMng.totalCnt = result.menuMngListCount[0].CNT;
				paging($menuMng,"menuMngList");
				
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
	/*********** 메뉴관리목록조회 API 호출 End **********/
	
	/*********** 대메뉴목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.bigList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				var html = "";
				html += "<select id='PARENT_MENU_ID2' class='select' onchange='javascript:changeParentID(this.value);'>";
				for(var i=0; i<result.menuMngSelectboxList.length; i++) {
					html += "<option value='"+result.menuMngSelectboxList[i].PARENT_MENU_ID+"||"+result.menuMngSelectboxList[i].MENU_GUBN+"'>"+result.menuMngSelectboxList[i].MENU_NAME+"</option>";
				}
				html += "</select>";
				$("#area1").html(html);
				document.getElementById("MENU_GUBN2").disabled = true;
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 대메뉴목록조회 API 호출 End **********/
	
	/*********** 메뉴관리등록 API 호출 Start **********/
	(function() {
		$class("tscp.menuMngAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("메뉴가 정상적으로 등록되었습니다.\n 메뉴권한을 추가해야 상단메뉴에 추가됩니다.");
					$(".dialog").hide();
					$menuMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 메뉴관리등록 API 호출 End **********/
	
	/*********** 메뉴ID 중복체크 API 호출 Start **********/
	(function() {
		$class("tscp.menuMngDoubleAddChk.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					var result = res.result;
					if(result.confirm.CNT == "0") {
						$menuMng.menuMngAdd();
					} else {
						alert("메뉴ID가 중복되었습니다. 변경해주세요!");
						$("#MENU_ID2").focus();
					}
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 메뉴관리등록 API 호출 End **********/
	
	/*********** 메뉴관리삭제 API 호출 Start **********/
	(function() {
		$class("tscp.menuMngDelete.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 처리되었습니다.");
					$(".dialog").hide();
					$menuMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 메뉴관리삭제 API 호출 End **********/
	
	/*********** 메뉴관리상세 API 호출 Start **********/
	(function() {
		$class("tscp.menuMngDetail.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#menuMngAdd").hide();
				$("#menuMngUpdate").show();
				$("#menuMngDelete2").show();
				
				var result = res.result;
				$("#MENU_LEVEL2").val(result.menuMngDetail.MENU_LEVEL);
				if($("#MENU_LEVEL2").val() == "1") {
					$("#area1").html("<input type='text' id='PARENT_MENU_ID2' class='inp' maxlength='15' placeholder='alloc300' />");
					document.getElementById("MENU_GUBN2").disabled = false;
				} else {
					$menuMng.bigList("1");
				}
				$("#PARENT_MENU_ID2").val(result.menuMngDetail.PARENT_MENU_ID).attr("selected","selected");
				$("#MENU_ID2").val(result.menuMngDetail.MENU_ID);
				document.getElementById("MENU_ID2").disabled = true;
				$("#MENU_NAME2").val(result.menuMngDetail.MENU_NAME);
				$("#MENU_SEQ2").val(result.menuMngDetail.MENU_SEQ);
				$("#USE_YN2").val(result.menuMngDetail.USE_YN);
				$("#CTNT_URL2").val(result.menuMngDetail.CTNT_URL);
				$("#MENU_GUBN2").val(result.menuMngDetail.MENU_GUBN);
				
				$("#menuMngPopup").show();
				
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
	/*********** 메뉴관리목록조회 API 호출 End **********/
	
	/*********** 메뉴관리수정 API 호출 Start **********/
	(function() {
		$class("tscp.menuMngUpdate.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 수정되었습니다.");
					$(".dialog").hide();
					$menuMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 메뉴관리수정 API 호출 End **********/
	
	/*********** 메뉴관리권한 API 호출 Start **********/
	(function() {
		$class("tscp.menuMngAccessList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#menuMngAccessList").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<colgroup>";
				html += "<col width='80' />";
				html += "<col width='50' />";
				html += "<col width='50' />";
				html += "<col width='50' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th>권한구분</th>";
				html += "<th>접근불가</th>";
				html += "<th>조회가능</th>";
				html += "<th>CRUD</th>";
				html += "</tr>";

				if(result.menuMngAccessList.length == 0) {
					html += "<tr><td colspan='4'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.menuMngAccessList.length; i++) {
						var access_cd = result.menuMngAccessList[i].ACCESS_CD;
						html += "<tr>";
						html += "<td>"+result.menuMngAccessList[i].CD_VAL_DESC+"</td>";
						if(access_cd == "00") {
							html += "<td><input type='radio' name='chkAc"+i+"' value='"+result.menuMngAccessList[i].CD_VAL+" 00' checked/></td>";
						} else {
							html += "<td><input type='radio' name='chkAc"+i+"' value='"+result.menuMngAccessList[i].CD_VAL+" 00' /></td>";
						} 
						if(access_cd == "10") {
							html += "<td><input type='radio' name='chkAc"+i+"' value='"+result.menuMngAccessList[i].CD_VAL+" 10' checked/></td>";
						} else {
							html += "<td><input type='radio' name='chkAc"+i+"' value='"+result.menuMngAccessList[i].CD_VAL+" 10' /></td>";
						} 
						if(access_cd == "20") {
							html += "<td><input type='radio' name='chkAc"+i+"' value='"+result.menuMngAccessList[i].CD_VAL+" 20' checked/></td>";
						} else {
							html += "<td><input type='radio' name='chkAc"+i+"' value='"+result.menuMngAccessList[i].CD_VAL+" 20' /></td>";
						}
						html += "</tr>";
					}
				}
				
				$("#menuMngAccessList").html(html);
				$("#menuMngAccessPopup").show();
				accessIndex = result.menuMngAccessList.length;
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 메뉴관리권한 API 호출 End **********/
	
	/*********** 메뉴관리권한 등록/변경 API 호출 Start **********/
	(function() {
		$class("tscp.menuMngAccessAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 등록/변경되었습니다.");
					$(".dialog").hide();
					$menuMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 메뉴관리권한 등록/변경 API 호출 End **********/
}(window, document));

//메뉴관리 등록 체크
function validationMenuMngAdd() {
	if ($("#PARENT_MENU_ID2").val() == "") {
		alert("상위메뉴ID를 입력 또는 선택하세요.");
		$("#PARENT_MENU_ID2").focus();
		return false;
	}
	
	if ($("#MENU_ID2").val() == "") {
		alert("메뉴ID를 입력하세요.");
		$("#MENU_ID2").focus();
		return false;
	}
	
	if ($("#MENU_NAME2").val() == "") {
		alert("메뉴명을 입력하세요.");
		$("#MENU_NAME2").focus();
		return false;
	}
	
	if ($("#MENU_SEQ2").val() == "") {
		alert("메뉴순번을 입력하세요.");
		$("#MENU_SEQ2").focus();
		return false;
	}
	
	return true;
}

function changeParentID(str) {
	$("#MENU_GUBN2").val(str.split("||")[1]);
	document.getElementById("MENU_GUBN2").disabled = true;
	$("#MENU_ID2").val("");
	$("#MENU_ID2").val(str.split("||")[0]+1);
}

function changeMenuId(str) {
	$("#MENU_ID2").val(str);
}