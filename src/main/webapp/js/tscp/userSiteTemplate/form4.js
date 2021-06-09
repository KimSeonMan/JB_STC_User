/**
 * 갤러리 메소드
 * 
 * history : 네이버시스템(주), 1.0, 2017/02/08  초기 작성
 * author : 김희철
 * version : 1.0
 * see : 
 *
 */
(function(W, D) {
	//"use strict";
	W.notice = W.$notice || {};
	$(document).ready(function() {
		$notice.list(1); //목록 조회
		$("#ST_DT").val($.datepicker.formatDate('yy-mm-dd', new Date(new Date().setDate(new Date().getDate() -7))));
		$("#EN_DT").val($.datepicker.formatDate('yy-mm-dd', new Date()));
		//엔터키
		$('#SEARCH_TEXT').on('keypress', function(e) {
			if (e.which == 13) {
				$notice.list(1);
			}
		});
		
		$("#NOTICE_CLS_CD").change(function(){
			$notice.list(1);
		});
		
		$("#searchList").click(function(){
			$notice.list(1);
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
			senders.addParam("GUBN", $("#GUBN").val());
			senders.addParam("NOTICE_CLS_CD", $("#NOTICE_CLS_CD").val());
			senders.addParam("GALLERY_YN", "Y");
			if($("#SEARCH_KEY").val() != "" && $("#SEARCH_TEXT").val() != "") {
				senders.addParam("SEARCH_KEY", $("#SEARCH_KEY").val());
				senders.addParam("SEARCH_TEXT", $("#SEARCH_TEXT").val());
			}
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
				url : contextPath + "/ServiceAPI/notice/noticeList.json",
			});
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
				url : contextPath + "/ServiceAPI/notice/noticeDetail.json",
			});
		}
	};
	
	/*********** 갤러리목록조회 API 호출 Start **********/
	(function() {
		$class("tscp.noticeList.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$(".photoList").empty(); //리스트 삭제
				$(".listTable").empty(); //리스트 삭제
				
				var result = res.result;
				var html = "";

				if(result.noticeList.length == 0) {
					html += "<tr><td>조회된 결과가 없습니다.</td></tr>";
					$(".photoList").hide();
					$(".listTable").html(html);
					$(".listTable").show();
				} else {
					for(var i=0; i<result.noticeList.length; i++) {
						if(result.noticeList[i].FILE_SAVE_NAME != undefined) {
							html += "<li>";
							html += "<a class='holder' href=\"javascript:$notice.noticeDetail(\'"
								+result.noticeList[i].CNTNTS_TYPE2_CD+"\',\'"
								+result.noticeList[i].CNTNTS_CLS_CD+"\',\'"
								+result.noticeList[i].CNTNTS_NO+"\',\'"
								+result.noticeList[i].HIT_CNT+"\',\'"
//								+result.noticeList[i].NOTICE_NO+"\');\" class='pic'><img style='width:309px; height:268px;' src='/upload/"+result.noticeList[i].FILE_SAVE_NAME+"' alt='galley image' /></a>";
//								+result.noticeList[i].NOTICE_NO+"\');\" class='pic'><img style='width:170px; height:170px;' src='/upload/"+result.noticeList[i].FILE_SAVE_NAME+"."+result.noticeList[i].FILE_SAVE_NAME.slice(result.noticeList[i].FILE_SAVE_NAME.indexOf(".")+1)+"' alt='galley image' /></a>";
								+result.noticeList[i].NOTICE_NO+"\');\" class='pic'><img style='width:170px; height:170px;' src='"+result.noticeList[i].FILE_SAVE_NAME+"' alt='galley image' /></a>";
//								html += "<a href=\"javascript:$notice.noticeDetail(\'"
//								+result.noticeList[i].CNTNTS_TYPE2_CD+"\',\'"
//								+result.noticeList[i].CNTNTS_CLS_CD+"\',\'"
//								+result.noticeList[i].CNTNTS_NO+"\',\'"
//								+result.noticeList[i].HIT_CNT+"\',\'"
//								+result.noticeList[i].NOTICE_NO+"\');\" class='subj'>"+result.noticeList[i].TITLE+"</a>";
							
							html += '<div class="cont">';
							html += '<p class="t01">'+result.noticeList[i].NAME+'</p>';
							html += '<p class="t02">'+result.noticeList[i].MBER_NM+'</p>';
							html += '<div class="etc">'
							html += '<span class="date">'+result.noticeList[i].REG_TS+'</span>';
							html += '<span class="view">'+result.noticeList[i].HIT_CNT+'</span>';
							html += '</div>';
							html += '</div>';
							
							
//							html += "<div class='petc'>"+result.noticeList[i].NAME+"</div>";
//							html += "<div class='petc'>";
//							html += "<span class='fl'>"+result.noticeList[i].REG_TS+"</span>";
//							html += "<span class='fr'>"+result.noticeList[i].HIT_CNT+"</span>";
//							html += "</div>";
							html += "</li>";
						}
					}
					$(".listTable").hide();
					$(".photoList").show();
					$(".photoList").html(html);
				}
				
				$notice.totalCnt = result.noticeListCount[0].CNT;
				paging($notice,"galleyList");
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 갤러리목록조회 API 호출 End **********/
	
	/*********** 갤러리상세 API 호출 Start **********/
	(function() {
		$class("tscp.noticeDetail.api").extend($d.api.ajaxAPI).define({
			onSuccess : function(status, res, options) {
				$("#downloadUl").html(""); //파일첨부리스트 초기화
				
				var result = res.result;
				$("#val1").html(result.noticeDetail.TITLE);
				$("#val2").html(result.noticeDetail.NAME);
				$("#val3").html(result.noticeDetail.REG_TS);
				$("#val4").html((Number(result.noticeDetail.HIT_CNT)+1)+"");
				$("#val5").html(result.noticeDetail.CTNT);
				
				var fileList = result.fileInfoList;
				var html = "";
				if(fileList.length > 0) {
					for(var i = 0; i < fileList.length; i++){
						var file_real_name = fileList[i].FILE_REAL_NAME;
						html += "<img style='max-width:700px;' src='/upload/"+fileList[i].FILE_SAVE_NAME+"."+file_real_name.slice(file_real_name.indexOf(".")+1)+"' alt='galleyImage' />";
//						html += "<img style='max-width:700px;' src='"+fileList[i].FILE_URL+"."+file_real_name.slice(file_real_name.indexOf(".")+1)+"' alt='galleyImage' />";
						$(".contImg").html(html);
					}
					$("#noticeDetail").css("border-bottom","");
					$("#downloadUl").append(html);
					$("#area1").show();
				} 
				
				$("#noticePopup").show();
			},
			onFail : function(status) {
			}
		});
	}());
	/*********** 갤러리상세 API 호출 End **********/
	
}(window, document));