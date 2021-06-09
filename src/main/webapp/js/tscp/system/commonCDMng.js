/**
 * 회원관리 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
(function(W, D) {
	//"use strict";
	W.commonCDMng = W.$commonCDMng || {};
	$(document).ready(function() {
		$commonCDMng.listA(1); //목록 조회
		
		//엔터키
		$('#cont1').on('keypress', function(e) {
			if (e.which == 13) {
				$commonCDMng.listA(1);
			}
		});
		$('#cont2').on('keypress', function(e) {
			if (e.which == 13) {
				$commonCDMng.listA(1);
			}
		});
		
		$("#searchList").click(function(){
			$commonCDMng.listA(1);
		});
		
		//공통코드등록 팝업
		$("#cdAOpen").click(function(){
			document.getElementById('commonCDForm').reset();
			
			$("#commonCDAdd").show();
			$("#commonCDUpdate").hide();
			$("#area1").show();
			$("#area2").hide();
			$("#area3").hide();
			$("#idCheckA").show();
			$("#CD_GUBN").val("A");
			$("#CD_CHECK_YN").val("N");
			
			document.getElementById("CD_ID2").disabled = false;
			
			$("#commonCDPopup").show();
		});
		
		//공통코드상세등록 팝업
		$("#cdBOpen").click(function(){
			document.getElementById('commonCDForm').reset();

			$("#commonCDAdd").show();
			$("#commonCDUpdate").hide();
			$("#area1").hide();
			$("#area2").show();
			$("#area3").show();
			$("#idCheckB").show();
			$("#CD_GUBN").val("B");
			$("#CD_CHECK_YN").val("N");
			
			document.getElementById("CD_VAL2").disabled = false;
			
			$("#commonCDPopup").show();
		});
		
//		$("#cdADelete").click(function(){
//			var str = checkReturnVal("chkA"); 
//			$commonCDMng.commonCDMngDelete(str, "A");
//		});
//		
//		$("#cdBDelete").click(function(){
//			var str = checkReturnVal("chkB"); 
//			$commonCDMng.commonCDMngDelete(str, "B");
//		});
		
		$("#idCheckA").click(function(){
			var str = $("#CD_ID2").val();
			$commonCDMng.commonCDMngIdCheck(str, "A");
		});
		
		$("#idCheckB").click(function(){
			var str = $("#CD_VAL2").val();
			$commonCDMng.commonCDMngIdCheck(str, "B");
		});
		
		$("#commonCDAdd").click(function(){
			$commonCDMng.commonCDMngAdd();
		});
		
		$("#commonCDUpdate").click(function(){
			$commonCDMng.commonCDMngUpdate();
		});
		
	});
	
	$commonCDMng = {
		totalCntA : 0,
		currentPageIndexA : 1,
		totalCntB : 0,
		currentPageIndexB : 1,
		// commonCDMng API 호출
		listA : function(page) {	
			$("#pagingA").empty();
			var senders = new tscp.commonCDMnglistA.api();
			senders.addParam("page", page);
			if($("#cont1").val() != "") {
				senders.addParam("CD_ID", $("#cont1").val());
			}
			if($("#cont2").val() != "") {
				senders.addParam("CD_DESC", $("#cont2").val());
			}
			
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/system/commonCDMnglistA.json",
			});
		},
		listB : function(page, str) {	
			$("#pagingB").empty();
			var senders = new tscp.commonCDMnglistB.api();
			$("#CD_ID").val(str);
			senders.addParam("page", page);
			senders.addParam("CD_ID", str);
			if($("#cont2").val() != "") {
				senders.addParam("CD_DESC", $("#cont2").val());
			}
			
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/system/commonCDMnglistB.json",
			});
		},
		commonCDMngDetailA : function(str) {	
			var senders = new tscp.commonCDMngDetailA.api();
			senders.addParam("CD_GUBN", "A");
			senders.addParam("CD_ID", str);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/system/commonCDMngDetail.json"
			});
		},
		commonCDMngDetailB : function(str1, str2) {	
			var senders = new tscp.commonCDMngDetailB.api();
			senders.addParam("CD_GUBN", "B");
			senders.addParam("CD_ID", str1);
			senders.addParam("CD_VAL", str2);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/system/commonCDMngDetail.json"
			});
		},
//		commonCDMngDelete : function(str1, str2) {	//공통코드 삭제
//			if(confirm("선택하신 공통코드를 삭제하시겠습니까?")) {
//				var senders = new tscp.commonCDMngDelete.api();
//				senders.addParam("CD_GUBN", str2);
//				if (str2 == "A") {
//					senders.addParam("CD_ID", str1);
//				} else {
//					senders.addParam("CD_ID", $("#CD_ID3").val());
//					senders.addParam("CD_VAL", str1);
//				}
//				senders.request({
//					method : "POST",
//					async : false,
//					url : contextPath + "/ServiceAPI/system/commonCDMngDelete.json",
//				});
//			}
//		},
		commonCDMngAdd : function() {	//공통코드등록
			if(validationAdd()) {
				if(confirm("공통코드를 등록하시겠습니까?")) {
					var senders = new tscp.commonCDMngAdd.api();
					senders.addParam("CD_GUBN", $("#CD_GUBN").val());
					if ($("#CD_GUBN").val() == "A") {
						senders.addParam("CD_ID", $("#CD_ID2").val());
						senders.addParam("CD_DESC", $("#CD_DESC2").val());
					} else {
						senders.addParam("CD_ID", $("#CD_ID3").val());
						senders.addParam("CD_VAL", $("#CD_VAL2").val());
						senders.addParam("CD_VAL_DESC", $("#CD_VAL_DESC2").val());
					}
					
					senders.request({
						method : "POST",
						async : false,
						url : contextPath + "/ServiceAPI/system/commonCDMngAdd.json",
						options : {
							word : $("#CD_GUBN").val()
						}
					});
				}
			}
		},
		commonCDMngUpdate : function() {	//회원가입 승인
			if(validationAdd()) {
				if(confirm("공통코드를 수정하시겠습니까?")) {
					var senders = new tscp.commonCDMngUpdate.api();
					senders.addParam("CD_GUBN", $("#CD_GUBN").val());
					if ($("#CD_GUBN").val() == "A") {
						senders.addParam("CD_ID", $("#CD_ID2").val());
						senders.addParam("CD_DESC", $("#CD_DESC2").val());
					} else {
						senders.addParam("CD_ID", $("#CD_ID3").val());
						senders.addParam("CD_VAL", $("#CD_VAL2").val());
						senders.addParam("CD_VAL_DESC", $("#CD_VAL_DESC2").val());
					}
					
					senders.request({
						method : "POST",
						async : false,
						url : contextPath + "/ServiceAPI/system/commonCDMngUpdate.json",
						options : {
							word : $("#CD_GUBN").val()
						}
					});
				}
			}
		},
		commonCDMngIdCheck : function(str1, str2) {	//공통코드 중복체크
			var senders = new tscp.commonCDMngIdCheck.api();
			senders.addParam("CD_GUBN", str2);
			if(str2 == "A") {
				senders.addParam("CD_ID", str1);
			} else {
				senders.addParam("CD_VAL", str1);
				senders.addParam("CD_ID", $("#CD_ID3").val());
			}
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/system/commonCDMngIdCheck.json",
				options : {
					word : str2
				}
			});
		}
	};
	
	/*********** 공통코드목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.commonCDMnglistA.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#listA").empty(); //리스트 삭제
				
				var result = res.result;
				
				//공통코드목록
				var html1 = "";
				var cd_id = "";
				
				html1 += "<colgroup>";
				html1 += "<col width='40' />";
				html1 += "<col width='' />";
				html1 += "<col width='' />";
				html1 += "</colgroup>";
				html1 += "<tr>";
				html1 += "<th><input type='checkbox' name='all' onclick='javascript:checkAll(listTableA);'/></th>";
				html1 += "<th>코드ID</th>";
				html1 += "<th>코드명</th>";
				html1 += "</tr>";
				
				if(result.commonCDMngListA.length == 0) {
					html1 += "<tr><td colspan='3'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.commonCDMngListA.length; i++) {
						if(i == 0) {
							cd_id = result.commonCDMngListA[i].CD_ID;
						}
						html1 += "<tr>";
						html1 += "<td><input type='checkbox' name='chkA' value='"+result.commonCDMngListA[i].CD_ID+"'/></td>";
						html1 += "<td class='link' onclick=\"javascript:$commonCDMng.listB(1,\'"+result.commonCDMngListA[i].CD_ID+"\');\">"+result.commonCDMngListA[i].CD_ID+"</td>";
						html1 += "<td class='link' onclick=\"javascript:$commonCDMng.commonCDMngDetailA(\'"+result.commonCDMngListA[i].CD_ID+"\');\">"+result.commonCDMngListA[i].CD_DESC+"</td>";
						html1 += "</tr>";
					}
				}
				
				$("#listA").html(html1);
				
				$commonCDMng.totalCntA = result.commonCDMngListCountA[0].CNT;
				paging($commonCDMng,"commonCDMnglistA");
				$commonCDMng.listB(1, cd_id);
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공통코드목록조회 API 호출 End **********/
	
	/*********** 공통코드상세목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.commonCDMnglistB.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#listB").empty(); //리스트 삭제
				
				var result = res.result;
				
				//공통코드상세목록
				var html2 = "";
				var cd_id = "";
				var cd_desc = "";
				
				html2 += "<colgroup>";
				html2 += "<col width='40' />";
				html2 += "<col width='' />";
				html2 += "<col width='' />";
				html2 += "<col width='' />";
				html2 += "</colgroup>";
				html2 += "<tr>";
				html2 += "<th><input type='checkbox' name='all' onclick='javascript:checkAll(listTableB);'/></th>";
				html2 += "<th>코드ID</th>";
				html2 += "<th>코드값</th>";
				html2 += "<th>코드값명</th>";
				html2 += "</tr>";
				
				if(result.commonCDMngListB.length == 0) {
					html2 += "<tr><td colspan='4'>조회된 결과가 없습니다.</td></tr>";
					cd_id = $("#CD_ID").val();
				} else {
					for(var i=0; i<result.commonCDMngListB.length; i++) {
						html2 += "<tr>";
						html2 += "<td><input type='checkbox' name='chkB' value='"+result.commonCDMngListB[i].CD_ID+" "+result.commonCDMngListB[i].CD_VAL+"'/></td>";
						html2 += "<td>"+result.commonCDMngListB[i].CD_ID+"</td>";
						html2 += "<td>"+result.commonCDMngListB[i].CD_VAL+"</td>";
						html2 += "<td class='link' onclick=\"javascript:$commonCDMng.commonCDMngDetailB(\'"+result.commonCDMngListB[i].CD_ID+"\',\'"
									+result.commonCDMngListB[i].CD_VAL+"\');\">"+result.commonCDMngListB[i].CD_VAL_DESC+"</td>";
						html2 += "</tr>";
						
						//신규 등록용
						cd_id = result.commonCDMngListB[i].CD_ID;
						cd_desc = result.commonCDMngListB[i].CD_DESC;
					}
				}
				
				$("#listB").html(html2);
				
				$commonCDMng.totalCntB = result.commonCDMngListCountB[0].CNT;
				paging($commonCDMng,"commonCDMnglistB");
				
				//CRUD 버튼 막기
				if(CRUDInfo == "10") {
					$(".CRUDBtn").hide();
				} else {
					$(".CRUDBtn").show();
				}
				
				$("#CD_ID3").val(cd_id);
				$("#val1").html(cd_id);
				$("#val2").html(cd_desc);
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공통코드상세목록조회 API 호출 End **********/
	
	/*********** 공통코드상세A API 호출 Start **********/
	(function() {
		$class("tscp.commonCDMngDetailA.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				document.getElementById('commonCDForm').reset();
				
				$("#commonCDAdd").hide();
				$("#commonCDUpdate").show();
				$("#area1").show();
				$("#area2").hide();
				$("#area3").hide();
				$("#idCheckA").hide();
				
				$("#CD_GUBN").val("A");
				$("#CD_CHECK_YN").val("Y");
				
				var result = res.result;
				
				$("#CD_ID2").val(result.commonCDMngDetail.CD_ID);
				$("#CD_DESC2").val(result.commonCDMngDetail.CD_DESC);
				
				document.getElementById("CD_ID2").disabled = true;
				
				$("#commonCDPopup").show();
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공통코드상세A API 호출 End **********/
	
	/*********** 공통코드상세B API 호출 Start **********/
	(function() {
		$class("tscp.commonCDMngDetailB.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				document.getElementById('commonCDForm').reset();

				$("#commonCDAdd").hide();
				$("#commonCDUpdate").show();
				$("#area1").hide();
				$("#area2").show();
				$("#area3").show();
				$("#idCheckB").hide();
				
				$("#CD_GUBN").val("B");
				$("#CD_CHECK_YN").val("Y");
				
				var result = res.result;
				
				if(result.length < 0 ) {
					$("#val1").val($("#CD_ID").val());
				} else {
					$("#val1").val(result.commonCDMngDetail.CD_ID);
				}
				
				$("#val2").val(result.commonCDMngDetail.CD_DESC);
				$("#CD_ID3").val(result.commonCDMngDetail.CD_ID);
				$("#CD_VAL2").val(result.commonCDMngDetail.CD_VAL);
				$("#CD_VAL_DESC2").val(result.commonCDMngDetail.CD_VAL_DESC);
				
				document.getElementById("CD_VAL2").disabled = true;
				
				$("#commonCDPopup").show();
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공통코드상세B API 호출 End **********/
	
	/*********** 공통코드등록 API 호출 Start **********/
	(function() {
		$class("tscp.commonCDMngAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					var result = res.result;
					alert("정상적으로 등록되었습니다.");
					if(options.word == "B") {
						$commonCDMng.listB(1, $("#CD_ID3").val()); //목록 조회
					} else {
						$("#cont1").val($("#CD_ID2").val());
						$commonCDMng.listA(1); //목록 조회
					}
					$(".dialog").hide();
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공통코드등록 API 호출 End **********/
	
	/*********** 공통코드수정 API 호출 Start **********/
	(function() {
		$class("tscp.commonCDMngUpdate.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					var result = res.result;
					alert("정상적으로 수정되었습니다.");
					if(options.word == "B") {
						$commonCDMng.listB(1, $("#CD_ID3").val()); //목록 조회
					} else {
						$("#cont1").val($("#CD_ID2").val());
						$commonCDMng.listA(1); //목록 조회
					}
					$(".dialog").hide();
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공통코드수정 API 호출 End **********/
	
	
	/*********** 공통코드중복체크 API 호출 Start **********/
	(function() {
		$class("tscp.commonCDMngIdCheck.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					var result = res.result;
					if(result.confirm.CNT == "0") {
						alert("사용할 수 있는 공통코드입니다.");
						$("#CD_CHECK_YN").val("Y");
					} else {
						alert("이미 사용하고 있는 공통코드입니다.");
						if(options.word == "B") {
							$("#CD_VAL2").val("");
						} else {
							$("#CD_ID2").val("");
						}
					}
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공통코드중복체크 API 호출 End **********/
	
}(window, document));

function validationAdd() {
	
	//공통코드상세일 경우
	if ($("#CD_GUBN").val() == "B") {
		if ($("#CD_VAL2").val() == "") {
			alert("메뉴값을 입력하세요.");
			$("#CD_VAL2").focus();
			return false;
		}
		
		if ($("#CD_VAL_DESC2").val() == "") {
			alert("메뉴값명을 입력하세요.");
			$("#CD_VAL_DESC2").focus();
			return false;
		}
		
		if ($("#CD_CHECK_YN").val() == "N") {
			alert("코드값 중복체크해주세요.");
			$("#CD_VAL2").focus();
			return false;
		}
	} else {
		if ($("#CD_ID2").val() == "") {
			alert("코드ID를 입력하세요.");
			$("#CD_ID2").focus();
			return false;
		}
		
		if ($("#CD_DESC2").val() == "") {
			alert("코드명을 입력하세요.");
			$("#CD_DESC2").focus();
			return false;
		}
		
		if ($("#CD_CHECK_YN").val() == "N") {
			alert("코드ID 중복체크해주세요.");
			$("#CD_ID2").focus();
			return false;
		}
	}
	
	return true;
}