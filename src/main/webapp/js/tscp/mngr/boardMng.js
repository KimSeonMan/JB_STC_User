/**
 * 공지사항 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
var fileInfo = "";
(function(W, D) {
	//"use strict";
	W.notice = W.$notice || {};
	$(document).ready(function() {
		$notice.bigList(2); //메뉴명 검색조건 조회
		
		//엔터키
		$('#SEARCH_TEXT').on('keypress', function(e) {
			if (e.which == 13) {
				$notice.list(1);
			}
		});
		
		$("#NOTICE_CLS_CD").change(function(){
			$notice.list(1);
		});
		
		$("#NOTICE_CLS_CD2").change(function(){
			var str1 = $("#CNTNTS_TYPE2_CD2").val();
			var str2 = $("#NOTICE_CLS_CD2").val();
			$notice.noticeAddConfirm(str1, str2);
		});
		
//		$("#NOTICE_CLS_CD3").change(function(){
//			$notice.list(1);
//		});
		
		$("#searchList").click(function(){
			$notice.list(1);
		});
		
		$("#noticeOpen").click(function(){
			document.getElementById('noticeForm').reset();
			$("#attachNameUl").html("");
			$("#area1").show();
			$("#noticeAdd").show();
			$("#noticeUpdate").hide();
			$("#replyBtn").hide();

			$("#CNTER_CD").val(session.cnter_cd);
			
			changeGubn("NOTICE_CLS_CD2","40","10");
			$notice.noticeAddConfirm("40", "10");
			$("#noticePopup").show();
		});
		
		$("#noticeAdd").click(function(){
			$notice.noticeAdd();
		});
		
		$("#noticeUpdate").click(function(){
			$notice.noticeUpdate();
		});
		
		$("#noticeDelete").click(function(){
			var str = checkReturnVal("chk"); 
			$notice.noticeDelete(str);
		});
		
		$("#replyBtn").click(function(){
			var str1 = $("#CNTNTS_TYPE2_CD2").val();
			var str2 = $("#CNTNTS_CLS_CD").val();
			var str3 = $("#U_CNTNTS_NO").val();
			var str4 = $("#NOTICE_NO").val();
			
			$notice.replyList(str1, str2, str3, str4);
		});
		
		$("#replyAdd").click(function(){
			$notice.replyAdd();
		});
		
	});
	
	$notice = {
		totalCnt : 0,
		currentPageIndex : 1,
		// notice API 호출
		list : function(page) {	
			$(".paging").empty();
			var senders = new tscp.noticeList.api();
			senders.addParam("page", page);
			senders.addParam("GALLERY_YN", "N");
			if($("#CNTNTS_TYPE2_CD").val() != "") {
				senders.addParam("GUBN", $("#CNTNTS_TYPE2_CD").val());
			}
			if($("#SEARCH_KEY").val() != "" && $("#SEARCH_TEXT").val() != "") {
				senders.addParam("SEARCH_KEY", $("#SEARCH_KEY").val());
				senders.addParam("SEARCH_TEXT", $("#SEARCH_TEXT").val());
			}
			if($("#NOTICE_CLS_CD").val() != "") {
				senders.addParam("NOTICE_CLS_CD", $("#NOTICE_CLS_CD").val());
			}
//			if($("#NOTICE_CLS_CD3").val() != "") {
//				senders.addParam("CNTNTS_NO", $("#NOTICE_CLS_CD3").val());
//			}
			if($("#ST_DT").val() != "" && $("#EN_DT").val() != "") {
				if($("#ST_DT").val() <= $("#EN_DT").val()) {
					senders.addParam("ST_DT", $("#ST_DT").val());
					senders.addParam("EN_DT", $("#EN_DT").val());
				} else {
					alert("등록 시작일이 종료일보다 큽니다. 다시 확인해주세요.");
					$("#ST_DT").focus();
					return false;
				}
			}
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/noticeList.json",
			});
		},
		noticeAddConfirm : function(str1, str2) {	
			//등록 가능한 메뉴가 있는 지 확인
			var senders = new tscp.noticeAddConfirm.api();
			if(str1 != null) {
				senders.addParam("CNTNTS_TYPE2_CD", str1);
			}
			if(str2 != null) {
				senders.addParam("CNTNTS_CLS_CD", str2);
			}
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/noticeAddConfirm.json",
			});
		},	
		noticeAdd : function() {	
			//noticeForm
			$common.attachFileValidate();
			
			if(validationAdd("I")) {
				if(confirm("등록하시겠습니까?")) {
					$("#noticeForm").ajaxForm({
						async : false,
						type : "POST",
						url : "/ServiceAPI/mngr/noticeAdd.json",
						dataType : "json",
						data : { },
						success : function(data){
							alert("정상적으로 등록되었습니다.");
							$(".dialog").hide();
							$notice.list(1);
						},
						error : function(xhr,textStatus,error){
						},
						complete : function(data){
						}
					}).submit();
				}
			}
		},		
		noticeUpdate : function() {	
			//noticeForm
			$common.attachFileValidate();
			
			if(validationAdd("U")) {
				if(confirm("수정하시겠습니까?")) {
					$("#noticeForm").ajaxForm({
						async : false,
						type : "POST",
						url : "/ServiceAPI/mngr/noticeUpdate.json",
						dataType : "json",
						data : { },
						success : function(data){
							alert("정상적으로 수정되었습니다.");
							$(".dialog").hide();
							$notice.list(1);
						},
						error : function(xhr,textStatus,error){
						},
						complete : function(data){
						}
					}).submit();
				}
			}
		},
		noticeDelete : function(str) {	
			if(confirm("선택하신 게시물을 삭제하시겠습니까?")) {
				var senders = new tscp.noticeDelete.api();
				senders.addParam("NOTICE_NO", str);
				senders.addParam("CNTER_CD",session.cnter_cd);
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/mngr/noticeDelete.json",
				});
			}
		},
		noticeDetail : function(str1, str2, str3, str4, str5) {	
			var senders = new tscp.noticeDetail.api();
			senders.addParam("CNTNTS_TYPE2_CD", str1);
			senders.addParam("CNTNTS_CLS_CD", str2);
			senders.addParam("CNTNTS_NO", str3);
			senders.addParam("HIT_CNT", str4);
			senders.addParam("NOTICE_NO", str5);
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/noticeDetail.json",
			});
		},
		noticeMenuFind : function() {	
			var senders = new tscp.noticeMenuFind.api();
			if($("#CNTNTS_TYPE2_CD").val() != "") {
				senders.addParam("CNTNTS_TYPE2_CD", $("CNTNTS_TYPE2_CD").val());
			}
			if($("#NOTICE_CLS_CD").val() != "") {
				senders.addParam("CNTNTS_CLS_CD", $("#NOTICE_CLS_CD").val());
			}
			if($("#CNTNTS_NO2").val() != "") {
				senders.addParam("CNTNTS_NO", $("#CNTNTS_NO2").val());
			}
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/noticeAddConfirm.json",
			});
		},
		bigList : function(str) {	
			var senders = new tscp.bigList.api();
			senders.addParam("MENU_LEVEL", str);
			senders.addParam("MENU_GUBN", "U");
			senders.addParam("BOARD_YN", "Y");
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/menuMngSelectboxList.json",
			});
		},
		replyList : function(str1, str2, str3, str4, str5) {	
			var senders = new tscp.replyList.api();
			senders.addParam("CNTNTS_TYPE2_CD", str1);
			senders.addParam("CNTNTS_CLS_CD", str2);
			senders.addParam("CNTNTS_NO", str3);
			senders.addParam("HIT_CNT", str4);
			senders.addParam("NOTICE_NO", str5);
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : false,
				url : contextPath + "/ServiceAPI/mngr/replyList.json",
			});
		},
		replyAdd : function() {	
			if ($("#replayCtnt").val() == "") {
				alert("댓글을 입력하세요.");
				$("#replayCtnt").focus();
				return false;
			} else {
				var senders = new tscp.replyAdd.api();
				senders.addParam("CNTNTS_TYPE2_CD", $("#CNTNTS_TYPE2_CD2").val());
				senders.addParam("CNTNTS_CLS_CD", $("#CNTNTS_CLS_CD").val());
				senders.addParam("CNTNTS_NO", $("#U_CNTNTS_NO").val());
				senders.addParam("NOTICE_NO", $("#NOTICE_NO").val());
				senders.addParam("REPLAY", $("#replayCtnt").val());
				senders.addParam("CNTER_CD",session.cnter_cd);
				senders.request({
					method : "POST",
					async : false,
					url : contextPath + "/ServiceAPI/mngr/replyAdd.json",
				});
			}
		}
	};
	
	/*********** 공지사항목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.noticeList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var user_gbn_cd = session.user_gbn_cd;
				
				$(".listTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<colgroup>";
//				if(user_gbn_cd == "80" || user_gbn_cd == "90") {
					html += "<col width='30' />";
//				}
				html += "<col width='30' />";
				html += "<col width='60' />";
				html += "<col width='' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "<col width='60' />";
				html += "</colgroup>";
				html += "<tr>";
//				if(user_gbn_cd == "80" || user_gbn_cd == "90") {
					html += "<th><input type='checkbox' name='all' onclick='javascript:checkAll(listTable);'/></th>";
//				}
				html += "<th>No</th>";
				html += "<th>메뉴명</th>";
				html += "<th>제목</th>";
				html += "<th>파일첨부<br/>여부</th>";
				html += "<th>조회수</th>";
				html += "<th>등록자</th>";
				html += "<th>등록일</th>";
				html += "</tr>";

				if(result.noticeList.length == 0) {
//					if(user_gbn_cd == "80" || user_gbn_cd == "90") {
						html += "<tr><td colspan='8'>조회된 결과가 없습니다.</td></tr>";
//					} else {
//						html += "<tr><td colspan='5'>조회된 결과가 없습니다.</td></tr>";
//					}
				} else {
					for(var i=0; i<result.noticeList.length; i++) {
						html += "<tr>";
//						if(user_gbn_cd == "80" || user_gbn_cd == "90") {
							html += "<td><input type='checkbox' name='chk' value='"
									+result.noticeList[i].CNTNTS_TYPE2_CD+" "
									+result.noticeList[i].CNTNTS_CLS_CD+" "
									+result.noticeList[i].CNTNTS_NO+" "
									+result.noticeList[i].NOTICE_NO+"'/></td>";
//						}
						html += "<td>"+result.noticeList[i].rnum+"</td>";
						html += "<td>"+result.noticeList[i].MENU_NAME+"</td>";
						
						
						if(session.user_id != "null") {
							if(session.user_id == result.noticeList[i].USER_ID) {
								if(result.noticeList[i].STEP_CNT > 0) {
									html += "<td style='text-indent:"+result.noticeList[i].STEP_CNT+"em;' class='al link' onclick=\"javascript:$notice.replyList(\'"
									+result.noticeList[i].CNTNTS_TYPE2_CD+"\',\'"
									+result.noticeList[i].CNTNTS_CLS_CD+"\',\'"
									+result.noticeList[i].CNTNTS_NO+"\',\'"
									+result.noticeList[i].HIT_CNT+"\',\'"
									+result.noticeList[i].NOTICE_NO+"\');\">"+result.noticeList[i].TITLE+"</td>";
								} else {
									html += "<td style='text-indent:"+result.noticeList[i].STEP_CNT+"em;' class='al link' onclick=\"javascript:$notice.noticeDetail(\'"
									+result.noticeList[i].CNTNTS_TYPE2_CD+"\',\'"
									+result.noticeList[i].CNTNTS_CLS_CD+"\',\'"
									+result.noticeList[i].CNTNTS_NO+"\',\'"
									+result.noticeList[i].HIT_CNT+"\',\'"
									+result.noticeList[i].NOTICE_NO+"\');\">"+result.noticeList[i].TITLE+"</td>";
								}
							} else {
								html += "<td style='text-indent:"+result.noticeList[i].STEP_CNT+"em;' class='al link' onclick=\"javascript:$notice.replyList(\'"
								+result.noticeList[i].CNTNTS_TYPE2_CD+"\',\'"
								+result.noticeList[i].CNTNTS_CLS_CD+"\',\'"
								+result.noticeList[i].CNTNTS_NO+"\',\'"
								+result.noticeList[i].HIT_CNT+"\',\'"
								+result.noticeList[i].NOTICE_NO+"\');\">"+result.noticeList[i].TITLE+"</td>";
							}
						} else {
							html += "<td style='text-indent:"+result.noticeList[i].STEP_CNT+"em;' class='al link' onclick=\"javascript:$notice.replyList(\'"
							+result.noticeList[i].CNTNTS_TYPE2_CD+"\',\'"
							+result.noticeList[i].CNTNTS_CLS_CD+"\',\'"
							+result.noticeList[i].CNTNTS_NO+"\',\'"
							+result.noticeList[i].HIT_CNT+"\',\'"
							+result.noticeList[i].NOTICE_NO+"\');\">"+result.noticeList[i].TITLE+"</td>";
						}
						
						html += "<td>"+result.noticeList[i].FILE_YN+"</td>";
						html += "<td>"+result.noticeList[i].HIT_CNT+"</td>";
						html += "<td>"+result.noticeList[i].NAME+"</td>";
						html += "<td>"+result.noticeList[i].REG_TS+"</td>";
						html += "</tr>";
					}
				}
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.noticeListCount[0].CNT+" 건");
				$notice.totalCnt = result.noticeListCount[0].CNT;
				paging($notice,"noticeList");
				
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
	/*********** 공지사항목록조회 API 호출 End **********/
	
	/*********** 공지등록가능메뉴확인 API 호출 Start **********/
	(function() {
		$class("tscp.noticeAddConfirm.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#menuArea").html("");

				var result = res.result;
				var html = "";
				
				fileInfo = result.noticeAddConfirm;
				
				if (result.noticeAddConfirm.length == 0) { //성공
					$("#menuArea").html("등록할 수 있는 메뉴가 없습니다.");
				} else {
					html += "<select name='CNTNTS_NO' id='CNTNTS_NO' class='select w150' onchange='javascript:fileArea(this.value,FILE_YN);' >";
					for(var i=0; i<result.noticeAddConfirm.length; i++) {
						if(i == 0) {
							html += "<option value='"+result.noticeAddConfirm[i].CNTNTS_NO+"' selected>"+result.noticeAddConfirm[i].MENU_NAME+"</option>";
							fileArea(result.noticeAddConfirm[i].CNTNTS_NO, "FILE_YN");
						} else {
							html += "<option value='"+result.noticeAddConfirm[i].CNTNTS_NO+"'>"+result.noticeAddConfirm[i].MENU_NAME+"</option>";
						}
					}
					html += "</select>";
					$("#menuArea").html(html);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공지등록가능메뉴확인 API 호출 End **********/
	
	/*********** 공지등록가능메뉴확인2 API 호출 Start **********/
	(function() {
		$class("tscp.noticeMenuFind.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				var html = "";
				
				for(var i=0; i<result.noticeAddConfirm.length; i++) {
					if(i == 0) {
						html += "<option value='"+result.noticeAddConfirm[i].CNTNTS_NO+"' selected>"+result.noticeAddConfirm[i].MENU_NAME+"</option>";
					} else {
						html += "<option value='"+result.noticeAddConfirm[i].CNTNTS_NO+"'>"+result.noticeAddConfirm[i].MENU_NAME+"</option>";
					}
				}
				$("#NOTICE_CLS_CD3").append(html);
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공지등록가능메뉴확인2 API 호출 End **********/
	
	/*********** 공지사항삭제 API 호출 Start **********/
	(function() {
		$class("tscp.noticeDelete.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 처리되었습니다.");
					$notice.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 공지사항삭제 API 호출 End **********/
	
	
	/*********** 정보컨텐츠관리등록 API 호출 Start **********/
	(function() {
		$class("tscp.noticeAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("메뉴가 정상적으로 등록되었습니다.\n 메뉴권한을 추가해야 상단메뉴에 추가됩니다.");
					document.getElementById('noticeForm').reset();
					$(".dialog").hide();
					$notice.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 정보컨텐츠관리등록 API 호출 End **********/
	
	/*********** 정보컨텐츠관리수정 API 호출 Start **********/
	(function() {
		$class("tscp.noticeUpdate.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 수정되었습니다.");
					document.getElementById('noticeForm').reset();
					$(".dialog").hide();
					$notice.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 정보컨텐츠관리수정 API 호출 End **********/
	
	
	/*********** 공지사항상세 API 호출 Start **********/
	(function() {
		$class("tscp.noticeDetail.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#attachNameUl").html(""); //초기화
				
				var result = res.result;
				$("#CNTNTS_TYPE2_CD2").val(result.noticeDetail.CNTNTS_TYPE2_CD);
				$("#CNTNTS_CLS_CD").val(result.noticeDetail.NOTICE_CLS_CD);
				$("#U_CNTNTS_NO").val(result.noticeDetail.CNTNTS_NO);
				$("#TITLE").val(result.noticeDetail.TITLE);
				$("#CTNT").val(result.noticeDetail.CTNT);
				$("#NOTICE_NO").val(result.noticeDetail.NOTICE_NO);
				
				$("#FILE_YN").val(result.noticeDetail.FILE_YN);
				$("#FILE_CNT").val(result.noticeDetail.FILE_CNT);
				$("#FILE_MAX_QTY").val(result.noticeDetail.FILE_MAX_QTY);
				$("#FILE_EXT").val(result.noticeDetail.FILE_EXT);
				
				$("#CNTER_CD").val(result.noticeDetail.CNTER_CD);
				
				if($("#FILE_YN").val() != "Y") {
					$("#fileArea").hide();
				} else {
					$("#fileArea").show();
				}
				
				var fileList = result.fileInfoList;
				var html ="";
				
				if(fileList.length > 0) {
					for(var i = 0; i < fileList.length; i++){
						html += "<li>";
						html += fileList[i].FILE_REAL_NAME;
						html += "&nbsp;<a href='javascript:void(0);' onclick='$common.attachFileRemove(this)'>삭제</a>";
						html += "</li>";
					}
					$("#attachNameUl").append(html);
				}
				
				$("#area1").hide();
				$("#noticeAdd").hide();
				$("#noticeUpdate").show();
				$("#noticePopup").show();
				
				if($("#CNTNTS_TYPE2_CD2").val() == "40") { //QA
					$("#replyBtn").show();
				} else {
					$("#replyBtn").hide();
				}
				
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
	/*********** 공지사항상세 API 호출 End **********/
	
	/*********** 대메뉴목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.bigList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				var html = "";
				html += "<option value=''>선택</option>";
				for(var i=0; i<result.menuMngSelectboxList.length; i++) {
					if($("#CNTNTS_NO2").val() != "") {
						if($("#CNTNTS_NO2").val() == result.menuMngSelectboxList[i].CNTNTS_NO) {
							html += "<option value='"+result.menuMngSelectboxList[i].CNTNTS_NO+"' selected>"+result.menuMngSelectboxList[i].MENU_NAME+"</option>";
							
							$("#CNTNTS_TYPE2_CD").val(result.menuMngSelectboxList[i].CNTNTS_TYPE2_CD);
							changeGubn("NOTICE_CLS_CD", $("#CNTNTS_TYPE2_CD").val(), result.menuMngSelectboxList[i].CNTNTS_CLS_CD);
						} else {
							html += "<option value='"+result.menuMngSelectboxList[i].CNTNTS_NO+"'>"+result.menuMngSelectboxList[i].MENU_NAME+"</option>";
						}
					} else {
						html += "<option value='"+result.menuMngSelectboxList[i].CNTNTS_NO+"'>"+result.menuMngSelectboxList[i].MENU_NAME+"</option>";
						$notice.list(1);
					}
				}
				
				if($("#CNTNTS_NO2").val() == "") {
					changeGubn('NOTICE_CLS_CD',"60","10");
				}
//				$("#NOTICE_CLS_CD3").html(html);
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 대메뉴목록조회 API 호출 End **********/
	
	/*********** QA답글작성 API 호출 Start **********/
	(function() {
		$class("tscp.replyList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				document.getElementById('replyForm').reset();
				
				var result = res.result;
				var replyList = result.replyList;
				var html1 ="";
				var html2 ="";
				var cnt = 0;
				
				for(var i = 0; i < replyList.length; i++){
					if(replyList.length == 1 || i == replyList.length-1) {
						$("#CNTNTS_CLS_CD").val(replyList[i].NOTICE_CLS_CD);
						$("#U_CNTNTS_NO").val(replyList[i].CNTNTS_NO);
						$("#NOTICE_NO").val(replyList[i].NOTICE_NO);
						$("#CNTER_CD").val(replyList[i].CNTER_CD);
					}
					if(i == 0) {
						$("#val1").html(result.replyList[i].NAME);
						$("#val2").html(result.replyList[i].REG_TS);
						$("#val3").html((Number(result.replyList[i].HIT_CNT)+1)+"");
						$("#val4").html(result.replyList[i].CTNT);
					} else {
						if(session.user_id == replyList[i].USER_ID) {
							cnt++;
						}
						html2 += "<dd>";
						html2 += "<div class='fl'>"+replyList[i].NAME+"</div>";
						html2 += "<div class='fc'>"+replyList[i].REPLAY+"</div>";
						html2 += "<div class='fr'>"+replyList[i].REG_TS+"</div>";
						html2 += "</dd>";
					}
				}
				html1 += "<dt>나의의견 <span>(총 <strong>"+cnt+"</strong> 개)</span></dt>";
				html1 += html2;
				$(".replydl").html(html1);
				$("#replyPopup").show();
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** QA답글작성 API 호출 End **********/
	
	/*********** QA답글등록 API 호출 Start **********/
	(function() {
		$class("tscp.replyAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 등록되었습니다.");
					$(".dialog").hide();
					$notice.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** QA답글등록 API 호출 End **********/
	
}(window, document));

//공지사항 등록 체크
function validationAdd(str) {
	if ($("#TITLE").val() == "") {
		alert("제목을 입력하세요.");
		$("#TITLE").focus();
		return false;
	}
	
	if ($("#CTNT").val() == "") {
		alert("내용을 입력하세요.");
		$("#CTNT").focus();
		return false;
	} else {
		if (byteCheck($("#CTNT").val()) > 4000) {
			alert("내용은 4000byte 이상 입력할 수 없습니다.");
			$("#CTNT").focus();
			return false;
		}
	}
	
	if(str == "I") {
		if ($("#CNTNTS_NO option:selected").val() == "") {
			alert("등록 가능한 메뉴가 없습니다. 메뉴 및 컨텐츠관리에 등록해주세요.");
			$("#CNTNTS_NO").focus();
			return false;
		}
	}
	
	return true;
}

function noticeOpen() {
	$("#noticePopup").show();
}

function byteCheck(str) {
	var totalByte = 0;
    for(var i=0; i<str.length; i++) {
    	var currentByte = str.charCodeAt(i);
    	if(currentByte > 128) {
    		totalByte += 2;
    	} else {
    		totalByte++;
    	}
    }
    return totalByte;
}

function changeGubn(str1, str2, word) {
	$("#"+str1).html("");
	if(str2 != "") {
		if(str2 == "40") { //Q&A
			codeDetailSearch2(str1, "BOARD_CLS_CD", word); //게시판QA분류코드
		} else if(str2 == "50") { //FAQ
			codeDetailSearch2(str1, "FAQ_CLS_CD", word); //FAQ분류코드
		} else { //공지사항
			codeDetailSearch2(str1, "NOTICE_CLS_CD", word); //공지분류코드
		}
	}
}

function fileArea(str1, str2) {
	for(var i=0; i<fileInfo.length; i++) {
		if(str1 == fileInfo[i].CNTNTS_NO) {
			$("#FILE_YN").val(fileInfo[i].FILE_YN);
			$("#FILE_CNT").val(fileInfo[i].FILE_CNT);
			$("#FILE_MAX_QTY").val(fileInfo[i].FILE_MAX_QTY);
			$("#FILE_EXT").val(fileInfo[i].FILE_EXT);
		}
	}
	if(str2 == "FILE_YN") {
		if($("#FILE_YN").val() != "Y") {
			$("#fileArea").hide();
		} else {
			$("#fileArea").show();
		}
	}
}

function fileAdd() {
	var str1 = $("#FILE_CNT").val();
	var str2 = $("#FILE_MAX_QTY").val();
	var str3 = $("#FILE_EXT").val();
	$common.attachFileFieldAdd(str1, str2, str3, "A");
}