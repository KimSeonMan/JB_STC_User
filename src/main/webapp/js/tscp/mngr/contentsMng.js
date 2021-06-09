/**
 * 정보컨텐츠관리 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
(function(W, D) {
	//"use strict";
	W.contentsMng = W.$contentsMng || {};

	$(document).ready(function() {
		$contentsMng.list(1); //목록 조회
		
		codeDetailSearch("CNTNTS_TYPE2_CD", "CNTNTS_MLSFC_CD"); //컨텐츠유형코드2
		codeDetailSearch("CNTNTS_TYPE1_CD2", "CNTNTS_LCLAS_CD"); //컨텐츠유형코드1
		codeDetailSearch2("CNTNTS_TYPE2_CD2", "CNTNTS_MLSFC_CD", "cntntsType2Cd"); //컨텐츠유형코드2
		
		//ckeditor
		CKEDITOR.replace('CNTNTS_CTNT2', {
			/*filebrowserUploadUrl : contextPath + "/js/plugins/ckeditor/ckeditorImageUpload.jsp?realUrl=" + contextPath
					+ "/upload/temp/&realDir=/upload/temp/"       ,*/
			filebrowserUploadUrl : contextPath + "/js/plugins/ckeditor/ckeditorImageUpload.jsp?realUrl=" + contextPath
			+ "/upload/info_contents/&realDir=/upload/info_contents/"       ,
			toolbar : [
					{ name: 'document', groups: [ 'mode', 'document', 'doctools' ],
					  items: [ 'Source', '-', 'Save', 'NewPage', 'Preview', 'Print', '-', 'Templates' ]},
					{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
					{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
					{ name: 'forms', groups: [ 'forms' ] },   
					{
						name : 'basicstyles',
						groups : [ 'basicstyles', 'cleanup' ],
						items : [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ]
					}, {
						name : 'colors',
						items : [ 'TextColor', 'BGColor' ]
					},
					{
						name : 'paragraph',
						groups : [ 'list', 'indent', 'blocks', 'align', 'bidi' ],
						items : [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft',
								'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl', 'Language' ]
					}, {
						name : 'links',
						items : [ 'Link', 'Anchor' ]
					}, '/', {
						name : 'insert',
						items : [ 'Image', 'Flash', 'Table', 'HorizontalRule',  'SpecialChar' ]
					}, {
						name : 'styles',
						items : [ 'Styles', 'Format', 'Font', 'FontSize' ]
					} ]
		}).on("key", function(e) {
			var tempCCdata = CKEDITOR.instances.CNTNTS_CTNT2.getData(); 
			byteCheck(tempCCdata);
		});
		
		//엔터키
		$('#MENU_NAME').on('keypress', function(e) {
			if (e.which == 13) {
				$contentsMng.list(1);
			}
		});
		
		//조회조건 selectbox 이벤트
		$("#CNTNTS_TYPE2_CD").change(function(){
			$contentsMng.list(1);
		});
		$("#USE_YN").change(function(){
			$contentsMng.list(1);
		});
		
		$("#searchList").click(function(){
			$contentsMng.list(1);
		});
		
		//정보컨텐츠 등록 팝업
		$("#contentsMngOpen").click(function(){
			document.getElementById("contentsMngForm").reset();
			$contentsMng.bigList("2");
			changeType1("10");
			$("#contentsMngUpdate").hide();
			$("#contentsMngPopup").show();
		});
		
		$("#contentsMngAdd").click(function(){
			$contentsMng.contentsMngAdd();
		});
		
		$("#contentsMngUpdate").click(function(){
			$contentsMng.contentsMngUpdate();
		});
		
		$("#contentsMngDelete").click(function(){
			var str = checkReturnVal("chk"); 
			$contentsMng.contentsMngDelete(str);
		});
		
	});
	
	$contentsMng = {
		totalCnt : 0,
		currentPageIndex : 1,
		// contentsMng API 호출
		list : function(page) {	
			$(".paging").empty();
			var senders = new tscp.contentsMngList.api();
			senders.addParam("page", page);
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			if($("#CNTNTS_TYPE2_CD").val() != "") {
				senders.addParam("CNTNTS_TYPE2_CD", $("#CNTNTS_TYPE2_CD").val());
			}
			if($("#USE_YN").val() != "") {
				senders.addParam("USE_YN", $("#USE_YN").val());
			}
			if($("#MENU_NAME").val() != "") {
				senders.addParam("MENU_NAME", $("#MENU_NAME").val());
			}
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/mngr/contentsMngList.json",
			});
		},
		bigList : function(str) {	
			var senders = new tscp.bigList.api();
			senders.addParam("MENU_LEVEL", str);
			senders.addParam("MENU_GUBN", "U");
			senders.addParam("PARENT_MENU_ID", "carUseResve300");
			senders.addParam("CNTER_CD",session.cnter_cd);
			
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/mngr/menuMngSelectboxList.json",
			});
		},
		contentsMngAdd : function() {	
			if(validationcontentsMngAdd()) {
				if(confirm("컨텐츠정보를 등록하시겠습니까?")) {
					var senders = new tscp.contentsMngAdd.api();
					senders.addParam("MENU_ID", $("#MENU_ID2").val());
					senders.addParam("CNTNTS_TYPE1_CD", $("#CNTNTS_TYPE1_CD2").val());
					senders.addParam("CNTNTS_TYPE2_CD", $("#CNTNTS_TYPE2_CD2").val());
					senders.addParam("CNTNTS_CLS_CD", $("#CNTNTS_CLS_CD2").val());
					senders.addParam("FILE_YN", $("#FILE_YN2").val());
					senders.addParam("CNTER_CD",session.cnter_cd);
					if($("#FILE_CNT2").val() != "") {
						senders.addParam("FILE_CNT", $("#FILE_CNT2").val());
					}
					if($("#FILE_MAX_QTY2").val() != "") {
						senders.addParam("FILE_MAX_QTY", $("#FILE_MAX_QTY2").val());
					}
					if($("#FILE_EXT2").val() != "") {
						senders.addParam("FILE_EXT", $("#FILE_EXT2").val());
					}
					if($("#CNTNTS_CTNT2").val() != "") {
						tempCCdata = CKEDITOR.instances.CNTNTS_CTNT2.getData();
						tempCCdata = tempCCdata.replace(/\&nbsp;/g,''); //특정문자 제거
						senders.addParam("CNTNTS_CTNT", encodeURIComponent(tempCCdata));
					}
					
					senders.request({
						method : "POST",
						async : true,
						url : contextPath + "/ServiceAPI/mngr/contentsMngAdd.json",
					});
				}
			}
		},
		contentsMngUpdate : function() {	
			if(validationcontentsMngAdd()) {
				if(confirm("수정하시겠습니까?")) {
					var senders = new tscp.contentsMngUpdate.api();
					var tempCCdata = '';
					senders.addParam("CNTNTS_NO", $("#CNTNTS_NO2").val());
					senders.addParam("MENU_ID", $("#MENU_ID2").val());
					senders.addParam("CNTNTS_TYPE1_CD", $("#CNTNTS_TYPE1_CD2").val());
					senders.addParam("CNTNTS_TYPE2_CD", $("#CNTNTS_TYPE2_CD2").val());
					senders.addParam("CNTNTS_CLS_CD", $("#CNTNTS_CLS_CD2").val());
					senders.addParam("FILE_YN", $("#FILE_YN2").val());
					senders.addParam("CNTER_CD",session.cnter_cd);
					if($("#FILE_CNT2").val() != "") {
						senders.addParam("FILE_CNT", $("#FILE_CNT2").val());
					}
					if($("#FILE_MAX_QTY2").val() != "") {
						senders.addParam("FILE_MAX_QTY", $("#FILE_MAX_QTY2").val());
					}
					if($("#FILE_EXT2").val() != "") {
						senders.addParam("FILE_EXT", $("#FILE_EXT2").val());
					}
					if($("#CNTNTS_CTNT2").val() != "") {
						tempCCdata = CKEDITOR.instances.CNTNTS_CTNT2.getData();
						tempCCdata = tempCCdata.replace(/\&nbsp;/g,''); //특정문자 제거
						senders.addParam("CNTNTS_CTNT", encodeURIComponent(tempCCdata));
						//senders.addParam("CNTNTS_CTNT", $("#CNTNTS_CTNT2").val());
					}
					
					senders.request({
						method : "POST",
						async : true,
						url : contextPath + "/ServiceAPI/mngr/contentsMngUpdate.json",
					});
				}
			}
		},
		contentsMngDelete : function(str) {	
			if(confirm("선택하신 컨텐츠를 삭제하시겠습니까?")) {
				var senders = new tscp.contentsMngDelete.api();
				senders.addParam("CNTNTS_NO", str);
				senders.addParam("CNTER_CD",session.cnter_cd);
				senders.request({
					method : "POST",
					async : true,
					url : contextPath + "/ServiceAPI/mngr/contentsMngDelete.json",
				});
			}
		},
		contentsMngDetail : function(str) {	
			var senders = new tscp.contentsMngDetail.api();
			senders.addParam("CNTNTS_NO", str);
			senders.addParam("CNTER_CD",session.cnter_cd);
			senders.request({
				method : "POST",
				async : true,
				url : contextPath + "/ServiceAPI/mngr/contentsMngDetail.json",
			});
		}
	};
	
	/*********** 정보컨텐츠관리 목록 조회 API 호출 Start **********/
	(function() {
		$class("tscp.contentsMngList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$(".listTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";
				html += "<colgroup>";
				html += "<col width='30' />";
				html += "<col width='30' />";
				html += "<col width='60' />";
				html += "<col width='100' />";
				html += "<col width='100' />";
				html += "<col width='100' />";
				html += "<col width='' />";
				html += "<col width='50' />";
				html += "<col width='50' />";
				html += "</colgroup>";
				html += "<tr>";
				html += "<th><input type='checkbox' name='all' onclick='javascript:checkAll(listTable);'/></th>";
				html += "<th>No</th>";
				html += "<th>대메뉴</th>";
				html += "<th>유형1</th>";
				html += "<th>유형2</th>";
				html += "<th>메뉴명</th>";
				html += "<th>컨텐츠URL</th>";
				html += "<th>사용<br/>여부</th>";
				html += "<th>컨텐츠<br/>관리</th>";
				html += "</tr>";

				if(result.contentsMngList.length == 0) {
					html += "<tr><td colspan='9'>조회된 결과가 없습니다.</td></tr>";
				} else {
					for(var i=0; i<result.contentsMngList.length; i++) {
						html += "<tr>";
						html += "<td><input type='checkbox' name='chk' value='"+result.contentsMngList[i].CNTNTS_NO+"'/></td>";
						html += "<td>"+result.contentsMngList[i].CNTNTS_NO+"</td>";
						html += "<td>"+result.contentsMngList[i].PARENT_MENU_NAME+"</td>";
						html += "<td>"+result.contentsMngList[i].CNTNTS_TYPE1_DESC+"</td>";
						html += "<td>"+result.contentsMngList[i].CNTNTS_TYPE2_DESC+"</td>";
						html += "<td class='link' onclick=\"javascript:$contentsMng.contentsMngDetail(\'"+result.contentsMngList[i].CNTNTS_NO+"\');\">"+result.contentsMngList[i].MENU_NAME+"</td>";
						html += "<td>"+result.contentsMngList[i].CTNT_URL+"</td>";
						html += "<td>"+result.contentsMngList[i].USE_YN+"</td>";
						if(result.contentsMngList[i].CNTNTS_TYPE1_CD == "20") {
							html += "<td><a href='/view/mngr/boardMng.do?CNTNTS_NO="+result.contentsMngList[i].CNTNTS_NO+"' class='btn01' style='padding: 0 0px;width:45px;'>수정</a></td>";
						} else {
							html += "<td></td>";
						}
						html += "</tr>";
					}
				}
				
				$(".listTable").html(html);
				$(".fl").html("검색목록 : 총 "+result.contentsMngListCount[0].CNT+" 건");
				$contentsMng.totalCnt = result.contentsMngListCount[0].CNT;
				paging($contentsMng,"contentsMngList");
				
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
	/*********** 정보컨텐츠관리 목록 조회 API 호출 End **********/
	
	/*********** 대메뉴목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.bigList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				var result = res.result;
				var html = "";
				
				if(result.menuMngSelectboxList.length == 0) {
					html += "등록할 수 있는 메뉴가 없습니다.";
				} else {
					html += "<select id='MENU_ID2' class='select' >";
					for(var i=0; i<result.menuMngSelectboxList.length; i++) {
						html += "";
						
						html += "<option value='"+result.menuMngSelectboxList[i].MENU_ID+"'>"+result.menuMngSelectboxList[i].MENU_NAME+"</td>";
					}
					html += "</select>";
				}
				
				$("#area3").html(html);
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 대메뉴목록조회 API 호출 End **********/
	
	/*********** 정보컨텐츠관리등록 API 호출 Start **********/
	(function() {
		$class("tscp.contentsMngAdd.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("메뉴가 정상적으로 등록되었습니다.\n 메뉴권한을 추가해야 상단메뉴에 추가됩니다.");
					$(".dialog").hide();
					$contentsMng.list(1);
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
		$class("tscp.contentsMngUpdate.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 처리되었습니다.");
					$(".dialog").hide();
					$contentsMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 정보컨텐츠관리수정 API 호출 End **********/
	
	/*********** 정보컨텐츠관리삭제 API 호출 Start **********/
	(function() {
		$class("tscp.contentsMngDelete.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				if (res.errCd == "0") { //성공
					alert("정상적으로 처리되었습니다.");
					$contentsMng.list(1);
				} else {
					alert(res.errMsg);
				}
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 정보컨텐츠관리삭제 API 호출 End **********/
	
	/*********** 정보컨텐츠관리상세 API 호출 Start **********/
	(function() {
		$class("tscp.contentsMngDetail.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				document.getElementById("contentsMngForm").reset();
								
				var result = res.result;
				$("#CNTNTS_NO2").val(result.contentsMngDetail.CNTNTS_NO);
				$("#MENU_ID2").val(result.contentsMngDetail.MENU_ID);
				$("#CNTNTS_TYPE1_CD2").val(result.contentsMngDetail.CNTNTS_TYPE1_CD);
				$("#FILE_YN2").val(result.contentsMngDetail.FILE_YN);
				if(result.contentsMngDetail.FILE_CNT != "") {
					$("#FILE_CNT2").val(result.contentsMngDetail.FILE_CNT);
				}
				if(result.contentsMngDetail.FILE_MAX_QTY != "") {
					$("#FILE_MAX_QTY2").val(result.contentsMngDetail.FILE_MAX_QTY);
				}
				if(result.contentsMngDetail.FILE_EXT != "") {
					$("#FILE_EXT2").val(result.contentsMngDetail.FILE_EXT);
				}
				if(result.contentsMngDetail.CNTNTS_CTNT != "") {
					$("#CNTNTS_CTNT2").val(result.contentsMngDetail.CNTNTS_CTNT);
					
					CKEDITOR.instances.CNTNTS_CTNT2.setData(result.contentsMngDetail.CNTNTS_CTNT);

				}
				
				changeType1(result.contentsMngDetail.CNTNTS_TYPE1_CD);
				$("#CNTNTS_TYPE2_CD2").val(result.contentsMngDetail.CNTNTS_TYPE2_CD);
				
				changeGubn("CNTNTS_CLS_CD2", result.contentsMngDetail.CNTNTS_TYPE2_CD, result.contentsMngDetail.CNTNTS_CLS_CD);
				
				byteCheck(result.contentsMngDetail.CNTNTS_CTNT);
				$("#area3").html(result.contentsMngDetail.MENU_NAME);
				$("#contentsMngAdd").hide();
				$("#contentsMngPopup").show();
				
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
	/*********** 정보컨텐츠관리상세 API 호출 End **********/
}(window, document));

//메뉴관리 등록 체크
function validationcontentsMngAdd() {
	if ($("#MENU_ID2").val() == "") {
		alert("메뉴명을 선택하세요.");
		$("#MENU_ID2").focus();
		return false;
	}
	
	if ($("#CNTNTS_TYPE1_CD2").val() == "") {
		alert("컨텐츠유형을 선택하세요.");
		$("#CNTNTS_TYPE1_CD2").focus();
		return false;
	}
	
	if ($("#CNTNTS_TYPE2_CD2").val() == "") {
		alert("컨텐츠유형을 선택하세요.");
		$("#CNTNTS_TYPE2_CD2").focus();
		return false;
	}
	
	if ($("#FILE_YN2").val() == "") {
		alert("파일첨부여부를 선택하세요.");
		$("#FILE_YN2").focus();
		return false;
	}
	
	if ($("#FILE_CNT2").val() > 5) {
		alert("첨부파일은 최대 5까지 가능합니다.");
		$("#FILE_CNT2").focus();
		return false;
	}
	
	if ($("#FILE_MAX_QTY2").val() > 2048) {
		alert("첨부파일용량은 최대 2048kb까지 가능합니다.");
		$("#FILE_MAX_QTY2").focus();
		return false;
	}
	
	if ($("#area2").html() > 4000) {
		alert("컨텐츠내용은 최대 4000byte까지 가능합니다.");
		$("#CNTNTS_CTNT2").focus();
		return false;
	}
	
	var tempCCdata = CKEDITOR.instances.CNTNTS_CTNT2.getData();
	tempCCdata = tempCCdata.replace(/\&nbsp;/g,''); //특정문자 제거
	
	if (byteCheck2(tempCCdata) > 4000) {
		alert("컨텐츠내용은 최대 4000byte까지 가능합니다.\n붙여넣기 후 스페이스를 쳐서 byte 체크하세요.");
		$("#CNTNTS_CTNT2").focus();
		return false;
	}
	
	return true;
}

function changeType1(str) {
	$("#CNTNTS_TYPE2_CD2").html(cntntsType2Cd);
	
	if(str == "10") {
		$("#area1").show();
		$("#CNTNTS_TYPE2_CD2 option:eq(3)").remove();
		$("#CNTNTS_TYPE2_CD2 option:eq(3)").remove();
		$("#CNTNTS_TYPE2_CD2 option:eq(3)").remove();
		$("#CNTNTS_TYPE2_CD2 option:eq(3)").remove();
		$("#CNTNTS_CLS_CD2").hide();
	} else {
		$("#area1").hide();
		$("#CNTNTS_TYPE2_CD2 option:eq(0)").remove();
		$("#CNTNTS_TYPE2_CD2 option:eq(0)").remove();
		$("#CNTNTS_TYPE2_CD2 option:eq(0)").remove();
		changeGubn("CNTNTS_CLS_CD2", $("#CNTNTS_TYPE2_CD2 option:eq(0)").val(), "CNTNTS_CLS_CD2");
		$("#CNTNTS_CLS_CD2").show();
	}
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
    $("#area2").html(totalByte);
}

function byteCheck2(str) {
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
			codeDetailSearch2(str1, "BOARD_CLS_CD",word); //게시판QA분류코드
		} else if(str2 == "50") { //FAQ
			codeDetailSearch2(str1, "FAQ_CLS_CD",word); //FAQ분류코드
		} else { //공지사항
			codeDetailSearch2(str1, "NOTICE_CLS_CD",word); //공지분류코드
		}
	}
}